/**
 * 
 */
package com.operative.tests.plannercriticalworkspace;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
import com.microsoft.playwright.Page;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;

/**
 * @author uday 
 *
 */
public class UnitViewUpdate_GrossRate_000_GrossCPM_CheckQtyCalculation_Update_IncludingPlanTotal  extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuitePlannerData")
	public void UnitViewUpdateGrossRate000GrossCPMCheckQtyCalculationUpdateIncludingPlanTotal(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String RatingStream,String spotsvalue,String Ratecard,String Product1,String Product2,String Product3)
	{
		PlannerHeaderPage  planHeaderPage=new PlannerHeaderPage(getPageBrowser());
		
		//creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
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
		planworkspace.addProductInChooserWindow(Product1).clickAddClose();
		

		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,1,1);
		
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//click weekly spots value
		planworkspace.clickWeeklySpotsValue(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay, 1);
		
		//Get Cell Property Rate Value
		String cellRate=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value
		String cellImps=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		//click on unit Details page				
		Page unitDetailsPage=planworkspace.clickOnUnitDetails();
		planworkspace.enterPriceBySpotValue(1, Product1, String.valueOf(Integer.valueOf(spotsvalue)*3), 
				AutoConfigPlannerWorkSpace.unitDetailsPercentageRC, unitDetailsPage);
		planworkspace.enterPriceBySpotValue(2, Product1, String.valueOf(Integer.valueOf(spotsvalue)*20), 
				AutoConfigPlannerWorkSpace.unitDetailsImps, unitDetailsPage);
	
		
		//Get Metrics value from Unit Details
		String rateCustomSpot1=planworkspace.getMetricsValuesFromUnitDetails(1,Product1,6,unitDetailsPage);
		String impsCustomSpot1=planworkspace.getMetricsValuesFromUnitDetails(1,Product1,2,unitDetailsPage);
		String grossCpmCustomSpot1=planworkspace.getMetricsValuesFromUnitDetails(1,Product1,4,unitDetailsPage);
		
		Double expGrossCPMUnitDetailsCustomSpot1 = Double.valueOf(rateCustomSpot1)/Double.valueOf(impsCustomSpot1);
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expGrossCPMUnitDetailsCustomSpot1)), getBasePage().
				format2Digi(grossCpmCustomSpot1),"Gross CPM on Unit details for 1st custom spot is wrong");
		
		String rateCustomSpot2=planworkspace.getMetricsValuesFromUnitDetails(2,Product1,6,unitDetailsPage);
		String impsCustomSpot2=planworkspace.getMetricsValuesFromUnitDetails(2,Product1,2,unitDetailsPage);
		String grossCpmCustomSpot2=planworkspace.getMetricsValuesFromUnitDetails(2,Product1,4,unitDetailsPage);
		
		Double expGrossCPMUnitDetailsCustomSpot2 = Double.valueOf(rateCustomSpot2)/Double.valueOf(impsCustomSpot2);
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expGrossCPMUnitDetailsCustomSpot2)), getBasePage().
				format2Digi(grossCpmCustomSpot2),"Gross CPM on Unit details for 2nd custom spot is wrong");
		
		//Close Unit Details pop up
		unitDetailsPage.close();
		
		//Spots Remaining after making 2 spots custom
		String spotRemaining=String.valueOf(Integer.valueOf(spotsvalue) - 2);
		
		//Total Rate and IMPS on Unit details
		Double totalRateUnitDetails=(Double.valueOf(cellRate) * Double.valueOf(spotRemaining)) + Double.valueOf(rateCustomSpot1) + Double.valueOf(rateCustomSpot2);
		Double totalImpsUnitDetails=(Double.valueOf(cellImps) * Double.valueOf(spotRemaining)) + Double.valueOf(impsCustomSpot1) + Double.valueOf(impsCustomSpot2);
		
		Double expGrossCPMQtr = totalRateUnitDetails/totalImpsUnitDetails;
		
		//Get Gross Rate ,Imps and Gross CPM on Qtr Tab after updating value on Unit Details
		String grossRateValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr).replace(",", "").replace("$", "");
		
		String impsValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		String cpmValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");
		
		//Verify Gross Rate, IMPS and CPM Weekly Pannel
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtr), getBasePage().
				format2Digi(String.valueOf(totalRateUnitDetails)),"rate total qtr is wrong after updating values on Unit details");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtr), getBasePage().
				format2Digi(String.valueOf(totalImpsUnitDetails)),"imps total qtr is wrong after updating values on Unit details");

		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtr), getBasePage().
						format2Digi(String.valueOf(expGrossCPMQtr)),"gross cpm total qtr is wrong after updating values on Unit details");
		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		//get total values from Plan Total Tab
		String grossValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr);
		
		String impsValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr);
		
		String cpmValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm);
		
		Logger.log("grossValuePlanTotalTab is==> "+grossValuePlanTotalTab+"\n impsValuePlanTotalTab is==>>"+impsValuePlanTotalTab+"\n cpmValuePlanTotalTab is ==> "+cpmValuePlanTotalTab);
		
		//Verify Gross Rate, IMPS and CPM Plan Total values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValuePlanTotalTab.replace(",", "").replace("$", ""))),"rate total qtr and plan total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValuePlanTotalTab.replace(",", "").replace("$", ""))),"imps total qtr and plan total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValuePlanTotalTab.replace(",", "").replace("$", ""))),"cpm total qtr and plan total is not same");

		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
