package com.operative.tests.plannercriticalworkspace;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
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


public class  RateCardRefresh_IncludingPlanTotal extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuitePlannerData")
	public void RateCardRefreshIncludingPlanTotal(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String RatingStream,String spotsvalue,String Ratecard,String Product1,String Product2,String Product3)
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
		planworkspace.addProductInChooserWindow(Product1).clickAddClose();
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(Product1,
				AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,1);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//get rate value in total qtr
		String grossRateValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr).replace(",", "").replace("$", "");
		
		String impsValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		String cpmValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");
		
		Logger.log("grossRateValueQtr is==> "+grossRateValueQtr+"\n impsValueQtr is==>>"+impsValueQtr+"\n cpmValueQtr is ==> "+cpmValueQtr);
		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		
		//get total values from Plan Total Tab
		String grossValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr).replace(",", "").replace("$", "");
		
		String impsValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		String cpmValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");
		
		Logger.log("grossValuePlanTotalTab is==> "+grossValuePlanTotalTab+"\n impsValuePlanTotalTab is==>>"+impsValuePlanTotalTab+"\n cpmValuePlanTotalTab is ==> "+cpmValuePlanTotalTab);
		
		//Verify Gross Rate, IMPS and CPM quarter values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValuePlanTotalTab)),"rate total qtr and plan Total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValuePlanTotalTab)),"imps total qtr and plan Total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValuePlanTotalTab)),"cpm total qtr and plan Total is not same");
				
		
		//Navigate to Period View Tab
        planworkspace.navigateToPeriodViewTab();
        
        
		String impsQtrUpdatedvalue=String.valueOf(Double.valueOf(impsValueQtr)*2);
		Logger.log("impsQtrUpdatedvalue is==> "+impsQtrUpdatedvalue);
		
		//enter updated imps value on qtr field
		planworkspace.enterAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr,impsQtrUpdatedvalue,0);
		
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//get rate value in total qtr
		String grossRateValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr).replace(",", "").replace("$", "");
		
		String impsValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		String cpmValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");
		
		Logger.log("grossRateValueQtrUpdated is==> "+grossRateValueQtrUpdated+"\n impsValueQtrUpdated is==>>"+impsValueQtrUpdated+"\n cpmValueQtrUpdated is ==> "+cpmValueQtrUpdated);
		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		
		//get total values from Plan Total Tab
		String grossValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr).replace(",", "").replace("$", "");
		
		String impsValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		String cpmValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");
		
		Logger.log("grossValuePlanTotalTabUpdated is==> "+grossValuePlanTotalTabUpdated+"\n impsValuePlanTotalTabUpdated is==>>"+impsValuePlanTotalTabUpdated+"\n cpmValuePlanTotalTabUpdated is ==> "+cpmValuePlanTotalTabUpdated);
		
		
		//Verify Gross Rate, IMPS and CPM quarter values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValuePlanTotalTabUpdated)),"rate total qtr and plan Total is not same after updating imps");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValuePlanTotalTabUpdated)),"imps total qtr and plan Total is not same after updating imps");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValuePlanTotalTabUpdated)),"cpm total qtr and plan Total is not same after updating imps");
		
		//Navigate to Period View Tab
        planworkspace.navigateToPeriodViewTab();
		
		//Click on Refresh Ratecard button
		planworkspace.openRefreshRatecardPage();
		
		//Perform Refresh Ratecard button
		planworkspace.refreshRatecard(AutoConfigPlannerWorkSpace.rateConstant);
		
		//get rate imps and cpm value in total qtr After ratecard refresh
		String grossRateValueQtrAfterRateCardRefresh=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr).replace(",", "").replace("$", "");
		
		String impsValueQtrAfterRateCardRefresh=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		String cpmValueQtrAfterRateCardRefresh=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");
		
		Logger.log("grossRateValueQtrAfterRateCardRefresh is==> "+grossRateValueQtrAfterRateCardRefresh+"\n impsValueQtrAfterRateCardRefresh is==>>"+impsValueQtrAfterRateCardRefresh+"\n cpmValueQtrAfterRateCardRefresh is ==> "+cpmValueQtrAfterRateCardRefresh);
		
		Double expectedGrossCPMQtrAfterRatecardRefresh=Double.valueOf(grossRateValueQtrAfterRateCardRefresh)/Double.valueOf(impsValueQtrAfterRateCardRefresh);
		
		//Verify Gross Rate, IMPS and CPM quarter values after ratecard refresh with constant rate
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossRateValueQtrAfterRateCardRefresh)),"rate total qtr and plan Total is not same after ratecard refresh with constant rate");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValueQtrAfterRateCardRefresh)),"imps total qtr and plan Total is not same after ratecard refresh with constant rate");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedGrossCPMQtrAfterRatecardRefresh).replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValueQtrAfterRateCardRefresh)),"cpm total qtr and plan Total is not same after ratecard refresh with constant rate");
				
		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		
		//get total values from Plan Total Tab
		String grossValuePlanTotalTabAfterRateCardRefresh=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr).replace(",", "").replace("$", "");
		
		String impsValuePlanTotalTabAfterRateCardRefresh=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		String cpmValuePlanTotalTabAfterRateCardRefresh=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");
		
		Logger.log("grossValuePlanTotalTabAfterRateCardRefresh is==> "+grossValuePlanTotalTabAfterRateCardRefresh+"\n impsValuePlanTotalTabAfterRateCardRefresh is==>>"+impsValuePlanTotalTabAfterRateCardRefresh+"\n cpmValuePlanTotalTabAfterRateCardRefresh is ==> "+cpmValuePlanTotalTabAfterRateCardRefresh);
		
		//Verify Gross Rate, IMPS and CPM PlanTotal values after ratecard refresh with constant rate
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtrAfterRateCardRefresh.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValuePlanTotalTabAfterRateCardRefresh)),"rate total qtr and plan Total is not same after ratecard refresh with constant rate");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrAfterRateCardRefresh.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValuePlanTotalTabAfterRateCardRefresh)),"imps total qtr and plan Total is not same after ratecard refresh with constant rate");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrAfterRateCardRefresh.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValuePlanTotalTabAfterRateCardRefresh)),"cpm total qtr and plan Total is not same after ratecard refresh with constant rate");
				
		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
