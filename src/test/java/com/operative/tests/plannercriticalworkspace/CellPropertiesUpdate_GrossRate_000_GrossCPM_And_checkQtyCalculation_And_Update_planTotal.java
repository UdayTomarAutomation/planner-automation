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


public class  CellPropertiesUpdate_GrossRate_000_GrossCPM_And_checkQtyCalculation_And_Update_planTotal extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuitePlannerData")
	public void verifyCellpropertiesRateValue(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
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
		//click weekly spots value
		planworkspace.clickWeeklySpotsValue(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay, 1);
		
		//Get Cell Property Rate Value
		String cellRate=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value
		String cellImps=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		//Get Cell Property cpm Value
		String cellCPM=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");	
		Logger.log("cellRate is==> "+cellRate+"\n cellImps is==>>"+cellImps+"\n cellCPM is ==> "+cellCPM);
		
		//expected Cell CPM is cellRate/cellImps
		Double expectedCellCPM = Double.valueOf(cellRate)/Double.valueOf(cellImps);
		Logger.log("expectedCellCPM is==> "+expectedCellCPM);
		
		//Verify Cell CPM is CellRate/CellIMPS
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPM), getBasePage().
				format2Digi(String.valueOf(expectedCellCPM)),"Cell CPM is not expected");
		
		//expected Quarter Rate, IMPS and CPM
		Double expectedRateQtr=Double.valueOf(cellRate)* Double.valueOf(spotsvalue);
		Double expectedImpsQtr=Double.valueOf(cellImps)* Double.valueOf(spotsvalue);
		Double expectedCPMQtr=expectedRateQtr/expectedImpsQtr;
		Logger.log("expectedRateQtr is==> "+expectedRateQtr+"\n expectedImpsQtr is==>>"+expectedImpsQtr+"\n expectedCPMQtr is ==> "+expectedCPMQtr);
		
		//get rate value in total qtr
		String grossValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr);
		
		String impsValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr);
		
		String cpmValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm);
		
		Logger.log("grossValueQtr is==> "+grossValueQtr+"\n impsValueQtr is==>>"+impsValueQtr+"\n cpmValueQtr is ==> "+cpmValueQtr);
		
		//Verify Gross Rate, IMPS and CPM quarter values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(expectedRateQtr)),"rate total qtr not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(expectedImpsQtr)),"imps total qtr not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(expectedCPMQtr)),"cpm total qtr not same");
		
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
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValuePlanTotalTab.replace(",", "").replace("$", ""))),"rate total qtr and plan total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValuePlanTotalTab.replace(",", "").replace("$", ""))),"imps total qtr and plan total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValuePlanTotalTab.replace(",", "").replace("$", ""))),"cpm total qtr and plan total is not same");

				
	/*
	 * Update Weekly cell rate value and veriy calculation on Qtr and Plan Total Tab			
	 */
		//Navigate to Period View Tab
        planworkspace.navigateToPeriodViewTab();
        
      //click weekly spots value
      	planworkspace.clickWeeklySpotsValue(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay, 1);
      	
		String cellRateUpdatedvalue=String.valueOf(Double.valueOf(cellRate)*2);
		String cellImpsUpdatedvalue=String.valueOf(Double.valueOf(cellImps)*2);
		Logger.log("cellRateUpdatedvalue is==> "+cellRateUpdatedvalue+"\n cellImpsUpdatedvalue is==>>"+cellImpsUpdatedvalue);
		
		//enter cell properties in imps 
		planworkspace.enterCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps, cellImpsUpdatedvalue);
		//enter cell properties in rate 
		planworkspace.enterCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate, cellRateUpdatedvalue);

		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		//click weekly spots value
		planworkspace.clickWeeklySpotsValue(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay, 1);
				
		//Get Cell Property Rate Value
		String cellRateUpdated=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value
		String cellImpsUpdated=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		//Get Cell Property cpm Value
		String cellCPMUpdated=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");	
		Logger.log("cellRateUpdated is==> "+cellRateUpdated+"\n cellImpsUpdated is==>>"+cellImpsUpdated+"\n cellCPMUpdated is ==> "+cellCPMUpdated);
		
		//expected Cell CPM is cellRate/cellImps
		Double expectedCellCPMUpdated = Double.valueOf(cellRateUpdated)/Double.valueOf(cellImpsUpdated);
		Logger.log("expectedCellCPMUpdated is==> "+expectedCellCPMUpdated);
		
		//Verify Cell CPM is CellRate/CellIMPS
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPMUpdated), getBasePage().
				format2Digi(String.valueOf(expectedCellCPMUpdated)),"Cell CPM is not expected after weekly rate and IMPS update");
		
		//expected Quarter Rate, IMPS and CPM
		Double expectedRateQtrUpdated=Double.valueOf(cellRateUpdated)* Double.valueOf(spotsvalue);
		Double expectedImpsQtrUpdated=Double.valueOf(cellImpsUpdated)* Double.valueOf(spotsvalue);
		Double expectedCPMQtrUpdated=expectedRateQtrUpdated/expectedImpsQtrUpdated;
		Logger.log("expectedRateQtrUpdated is==> "+expectedRateQtrUpdated+"\n expectedImpsQtrUpdated is==>>"+expectedImpsQtrUpdated+"\n expectedCPMQtrUpdated is ==> "+expectedCPMQtrUpdated);
		
		//get rate value in total qtr
		String grossValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr);
		
		String impsValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr);
		
		String cpmValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm);
		
		Logger.log("grossValueQtrUpdated is==> "+grossValueQtrUpdated+"\n impsValueQtrUpdated is==>>"+impsValueQtrUpdated+"\n cpmValueQtrUpdated is ==> "+cpmValueQtrUpdated);
		
		//Verify Gross Rate, IMPS and CPM quarter values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(expectedRateQtrUpdated)),"rate total qtr not same after weekly rate and IMPS update");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(expectedImpsQtrUpdated)),"imps total qtr not same after weekly rate and IMPS update");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(expectedCPMQtrUpdated)),"cpm total qtr not same after weekly rate and IMPS update");
		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		
		//get total values from Plan Total Tab
		String grossValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr);
		
		String impsValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr);
		
		String cpmValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm);
		
		Logger.log("grossValuePlanTotalTabUpdated is==> "+grossValuePlanTotalTabUpdated+"\n impsValuePlanTotalTabUpdated is==>>"+impsValuePlanTotalTabUpdated+"\n cpmValuePlanTotalTabUpdated is ==> "+cpmValuePlanTotalTabUpdated);
		
		//Verify Gross Rate, IMPS and CPM Plan Total values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValuePlanTotalTabUpdated.replace(",", "").replace("$", ""))),"rate total qtr and plan total is not same after weekly rate and IMPS update");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValuePlanTotalTabUpdated.replace(",", "").replace("$", ""))),"imps total qtr and plan total is not same after weekly rate and IMPS update");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValuePlanTotalTabUpdated.replace(",", "").replace("$", ""))),"cpm total qtr and plan total is not same after weekly rate and IMPS update");

		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
