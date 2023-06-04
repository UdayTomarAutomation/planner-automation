package com.operative.tests.plannerWorkspaceSmoke;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
import com.google.common.base.Joiner;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;
/**
 * @author uday madan
 *
 */


public class  quidViewUpdate_WithMatrixUpdating_AtCellLevel extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuitePlannerDataWeekly")
	public void quidViewUpdateMatrixUpdatingAtCellLevel(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String flightStartDate,String flightEndDate,String channelValue,
			String demovalue,String RatingStream,String spotsvalue,String Ratecard,String Product1)
	{
		PlannerHeaderPage  planHeaderPage=new PlannerHeaderPage(getPageBrowser());
		//creating Plan Header
		planHeaderPage.creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//Select Rating Stream
		planHeaderPage.selectRatingStream(RatingStream);
		//Add Ratecard
		planHeaderPage.selectRateCardName(Ratecard);
		//save Plan Header
		planHeaderPage.clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttribute, 
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		//add product
		planworkspace.addProductInChooserWindow(Product1).clickAddClose();
		
		//click weekly spots value
		planworkspace.enterWorkSpaceDataInWeeklySpots(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue);
		
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
			    
		//Get Cell Property Rate Value
		String cellRates[] =planworkspace.getCellPropertyMetricsValues(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.cellRate);

		//Get Cell Property imps Value
		String cellImps[]=planworkspace.getCellPropertyMetricsValues(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.cellImps);
		
		//Get Cell Property cpm Value
		String cellCPM[] =planworkspace.getCellPropertyMetricsValues(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.grossCpm);	
		
		Logger.log("cellRates is==> "+Joiner.on(",").join(cellRates)+"\n cellImps is==>>"+Joiner.on(",").join(cellImps)+"\n cellCPM is ==> "+Joiner.on(",").join(cellCPM));
		
		//Select Quad view Metrics from View Metrics
		planworkspace.addColumnsWeekInQuadView(AutoConfigPlannerWorkSpace.quadViewMetrics);
		
		//Quad view Rate Value for all Weeks
		String quadViewRates[] =planworkspace.getQuadViewMetricsValue(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,1);
		
		//Quad view Rate Value for all Weeks
		String quadViewImps[] =planworkspace.getQuadViewMetricsValue(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,2);
				
		//Quad view Rate Value for all Weeks
		String quadViewCPM[] =planworkspace.getQuadViewMetricsValue(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,3);
		
		Logger.log("quadViewRates is==> "+Joiner.on(",").join(quadViewRates)+"\n quadViewImps is==>>"+Joiner.on(",").join(quadViewImps)+"\n quadViewCPM is ==> "+Joiner.on(",").join(quadViewCPM));
		
		
		//Verify Weekly cell Rate and quad view rate value
		for (int i=0;i<cellRates.length;i++) {
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellRates[i]), getBasePage().
				format2Digi(quadViewRates[i]),"Weekly cell rate value is not matching with quad view rate value");
		}
		
		//Verify Weekly cell Imps and quad view Imps value
		for (int i=0;i<cellRates.length;i++) {
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellImps[i]), getBasePage().
				format2Digi(quadViewImps[i]),"Weekly cell rate value is not matching with quad view rate value");
		}
		
		//Verify Weekly cell CPM and quad view CPM value
		for (int i=0;i<cellRates.length;i++) {
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPM[i]), getBasePage().
				format2Digi(quadViewCPM[i]),"Weekly cell rate value is not matching with quad view rate value");
		}
		
		
		//Remove Quad view Metrics from View Metrics
		planworkspace.removeColumnsWeekInQuadView(AutoConfigPlannerWorkSpace.quadViewMetrics);
		
		/*
		 * Update Weekly cell rate & IMPS value and veriy quad view metrics value			
		 */
		
		//click weekly spots value
      	planworkspace.clickWeeklySpotsValue(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay, 1);
      	
		String cellRateUpdatedvalue=String.valueOf(Double.valueOf(cellRates[1])*2);
		String cellImpsUpdatedvalue=String.valueOf(Double.valueOf(cellImps[1])*2);
		Logger.log("cellRateUpdatedvalue is==> "+cellRateUpdatedvalue+"\n cellImpsUpdatedvalue is==>>"+cellImpsUpdatedvalue);
		
		//enter cell properties in imps 
		planworkspace.enterCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps, cellImpsUpdatedvalue);
		//enter cell properties in rate 
		planworkspace.enterCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate, cellRateUpdatedvalue);
		
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//Get Cell Property Rate Value
		String cellRatesUpated[] =planworkspace.getCellPropertyMetricsValues(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.cellRate);

		//Get Cell Property imps Value
		String cellImpsUpated[]=planworkspace.getCellPropertyMetricsValues(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.cellImps);
		
		//Get Cell Property cpm Value
		String cellCPMUpated[] =planworkspace.getCellPropertyMetricsValues(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.grossCpm);	
		
		Logger.log("cellRatesUpated is==> "+Joiner.on(",").join(cellRatesUpated)+"\n cellImpsUpated is==>>"+Joiner.on(",").join(cellImpsUpated)+"\n cellCPM is ==> "+Joiner.on(",").join(cellCPMUpated));
		
		//Select Quad view Metrics from View Metrics
		planworkspace.addColumnsWeekInQuadView(AutoConfigPlannerWorkSpace.quadViewMetrics);
		
		//Quad view Rate Value for all Weeks
		String quadViewRatesUpated[] =planworkspace.getQuadViewMetricsValue(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,1);
		
		//Quad view Rate Value for all Weeks
		String quadViewImpsUpated[] =planworkspace.getQuadViewMetricsValue(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,2);
				
		//Quad view Rate Value for all Weeks
		String quadViewCPMUpated[] =planworkspace.getQuadViewMetricsValue(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,3);
		
		Logger.log("quadViewRatesUpated is==> "+Joiner.on(",").join(quadViewRatesUpated)+"\n quadViewImpsUpated is==>>"+Joiner.on(",").join(quadViewImpsUpated)+"\n quadViewCPMUpated is ==> "+Joiner.on(",").join(quadViewCPMUpated));
		
		
		//Verify Weekly cell Rate and quad view rate value
		for (int i=0;i<cellRates.length;i++) {
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellRatesUpated[i]), getBasePage().
				format2Digi(quadViewRatesUpated[i]),"Weekly cell rate value is not matching with quad view rate value after cell rate and imps changed");
		}
		
		//Verify Weekly cell Imps and quad view Imps value
		for (int i=0;i<cellRates.length;i++) {
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellImpsUpated[i]), getBasePage().
				format2Digi(quadViewImpsUpated[i]),"Weekly cell rate value is not matching with quad view rate value after cell rate and imps changed");
		}
		
		//Verify Weekly cell CPM and quad view CPM value
		for (int i=0;i<cellRates.length;i++) {
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPMUpated[i]), getBasePage().
				format2Digi(quadViewCPMUpated[i]),"Weekly cell rate value is not matching with quad view rate value after cell rate and imps changed");
		}
		
		
		//Remove Quad view Metrics from View Metrics
		planworkspace.removeColumnsWeekInQuadView(AutoConfigPlannerWorkSpace.quadViewMetrics);
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
	
}
