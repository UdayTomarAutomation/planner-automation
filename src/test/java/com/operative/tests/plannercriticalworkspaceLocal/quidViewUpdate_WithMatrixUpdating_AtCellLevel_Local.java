package com.operative.tests.plannercriticalworkspaceLocal;

import java.text.ParseException;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
import com.google.common.base.Joiner;
import com.operative.aos.configs.AutoConfigPlannerHeader;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.DateTimeFunctions;
import com.operative.pages.plannerworkspace.PlannerWorkBookPage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;
/**
 * @author uday 
 *
 */


public class  quidViewUpdate_WithMatrixUpdating_AtCellLevel_Local extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="localSmokeSuitePlannerDataquadView")
	public void quidViewUpdateMatrixUpdatingAtCellLevel(String planClass,String station,String flightDates,String flightStartDate,String flightEndDate,
			String advertiser,String buyingAgency,String accountExecutive,String calenderValue,String startPeriodValue,String endPeriodValue,String demo,String dayPart,String marketPlace,
			String ratingStream,String product,String ratecard, String spotValue) throws ParseException, InterruptedException
	{
		PlannerHeaderPage  planHeaderPage=new PlannerHeaderPage(getPageBrowser());
		BasePage getBasePage = new BasePage(getPageBrowser());
		final PlannerWorkBookPage workSpacebook = new PlannerWorkBookPage(getPageBrowser());
		
		String planName =  AutoConfigPlannerHeader.linearPlanName + getBasePage.getRandomNumber();
		
		//creating Plan Header
		planHeaderPage.creationLocalPlanHeader(planName,planClass,advertiser,buyingAgency,accountExecutive, 
				calenderValue, startPeriodValue, endPeriodValue, marketPlace, flightDates, station,demo);
		//Select Rating Stream
		planHeaderPage.selectRatingStream(ratingStream);
		//Add Ratecard
		planHeaderPage.selectRateCardName(ratecard);
		//save Plan Header
		planHeaderPage.clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		
		// get all quarters and weeks
	    final List<String> generateQuarterlyValue =
	        new DateTimeFunctions().generateQuarterlySequence(flightStartDate, flightEndDate);
	    
	    Logger.log("Quarter values: " + generateQuarterlyValue.toString());

	    String qtrName = generateQuarterlyValue.get(0).substring(4);
	    planworkspace.switchQuarters(generateQuarterlyValue.get(1));
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttributeLocal, 
				AutoConfigPlannerWorkSpace.lineClassAttributeLocal, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(product).clickAddSelectedProductLocal();
		
		
		//click weekly spots value
		planworkspace.enterWorkSpaceDataInWeeklySpots(product,dayPart,spotValue);
		
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		

		workSpacebook.clickonBookChooser();

	    workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
	       // apply Book for Selected product
	    workSpacebook.applyBookForSelectedProductSelectAll(product,
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);

	    workSpacebook.clickApply();
			    
		//Get Cell Property Rate Value
		String cellRates[] =planworkspace.getCellPropertyMetricsValues(product,dayPart,AutoConfigPlannerWorkSpace.cellRate);

		//Get Cell Property imps Value
		String cellImps[]=planworkspace.getCellPropertyMetricsValues(product,dayPart,AutoConfigPlannerWorkSpace.cellImps);
		
		//Get Cell Property cpm Value
		String cellCPM[] =planworkspace.getCellPropertyMetricsValues(product,dayPart,AutoConfigPlannerWorkSpace.grossCpm);	
		
		Logger.log("cellRates is==> "+Joiner.on(",").join(cellRates)+"\n cellImps is==>>"+Joiner.on(",").join(cellImps)+"\n cellCPM is ==> "+Joiner.on(",").join(cellCPM));
		
		planworkspace.browserZoomIn();
		//Select Quad view Metrics from View Metrics
		planworkspace.addColumnsWeekInQuadView(AutoConfigPlannerWorkSpace.quadViewMetrics);
		planworkspace.browserZoomOut();
		//Quad view Rate Value for all Weeks
		String quadViewRates[] =planworkspace.getQuadViewMetricsValue(product,dayPart,1);
		
		//Quad view Rate Value for all Weeks
		String quadViewImps[] =planworkspace.getQuadViewMetricsValue(product,dayPart,2);
				
		//Quad view Rate Value for all Weeks
		String quadViewCPM[] =planworkspace.getQuadViewMetricsValue(product,dayPart,3);
		
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
      	planworkspace.clickWeeklySpotsValue(product, dayPart, 1);
      	
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
		String cellRatesUpated[] =planworkspace.getCellPropertyMetricsValues(product,dayPart,AutoConfigPlannerWorkSpace.cellRate);

		//Get Cell Property imps Value
		String cellImpsUpated[]=planworkspace.getCellPropertyMetricsValues(product,dayPart,AutoConfigPlannerWorkSpace.cellImps);
		
		//Get Cell Property cpm Value
		String cellCPMUpated[] =planworkspace.getCellPropertyMetricsValues(product,dayPart,AutoConfigPlannerWorkSpace.grossCpm);	
		
		Logger.log("cellRatesUpated is==> "+Joiner.on(",").join(cellRatesUpated)+"\n cellImpsUpated is==>>"+Joiner.on(",").join(cellImpsUpated)+"\n cellCPM is ==> "+Joiner.on(",").join(cellCPMUpated));
		
		//Select Quad view Metrics from View Metrics
		planworkspace.addColumnsWeekInQuadView(AutoConfigPlannerWorkSpace.quadViewMetrics);
		
		//Quad view Rate Value for all Weeks
		String quadViewRatesUpated[] =planworkspace.getQuadViewMetricsValue(product,dayPart,1);
		
		//Quad view Rate Value for all Weeks
		String quadViewImpsUpated[] =planworkspace.getQuadViewMetricsValue(product,dayPart,2);
				
		//Quad view Rate Value for all Weeks
		String quadViewCPMUpated[] =planworkspace.getQuadViewMetricsValue(product,dayPart,3);
		
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
		
		planworkspace.browserZoomIn();
		//Remove Quad view Metrics from View Metrics
		planworkspace.removeColumnsWeekInQuadView(AutoConfigPlannerWorkSpace.quadViewMetrics);
		planworkspace.browserZoomOut();
		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
