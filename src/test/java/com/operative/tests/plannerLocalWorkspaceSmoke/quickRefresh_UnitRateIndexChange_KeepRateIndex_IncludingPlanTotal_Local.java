package com.operative.tests.plannerLocalWorkspaceSmoke;

import java.text.ParseException;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
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
 * @author uday madan
 *
 Unit index changed (Rate) with Quick refresh & Keep the deal level overridden rate index value.
 */


public class  quickRefresh_UnitRateIndexChange_KeepRateIndex_IncludingPlanTotal_Local extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="localSmokeSuitePlannerDataQuickRefresh")
	public void QuickRefreshIncludingPlanTotal(String planClass,String station,String flightDates,String flightStartDate,String flightEndDate,
			String advertiser,String buyingAgency,String accountExecutive,String calenderValue,String startPeriodValue,String endPeriodValue,String demo,String dayPart,String marketPlace,
			String ratingStream,String product,String ratecard, String spotValue,String rateIndexValue) throws ParseException, InterruptedException
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
		
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttributeLocal, 
				AutoConfigPlannerWorkSpace.lineClassAttributeLocal, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(product).clickAddSelectedProductLocal();
		//select the columns
		planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.defaultWorksapceColumn);
		
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(product,
				dayPart,spotValue,1);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		workSpacebook.clickonBookChooser();

	    workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
	       // apply Book for Selected product
	    workSpacebook.applyBookForSelectedProductSelectAll(product,
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);

	    workSpacebook.clickApply();
		//click weekly spots value
		planworkspace.clickWeeklySpotsValue(product, dayPart, 1);
		
		
		//Get Cell Property Rate Value
		String cellRate=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value
		String cellImps=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		//Get Cell Property cpm Value
		String cellCPM=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");	
		Logger.log("cellRate is==> "+cellRate+"\n cellImps is==>>"+cellImps+"\n cellCPM is ==> "+cellCPM);
		
		//Expected Cell CPM is cellRate/cellImps
		Double ExpectedCellCPM = Double.valueOf(cellRate)/Double.valueOf(cellImps);
		Logger.log("ExpectedCellCPM is==> "+ExpectedCellCPM);
		
		//Verify Cell CPM is CellRate/CellIMPS
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPM), getBasePage().
				format2Digi(String.valueOf(ExpectedCellCPM)),"Cell CPM is not expected");
		
		//Expected Quarter Rate, IMPS and CPM
		Double expectedRateQtr=Double.valueOf(cellRate)* Double.valueOf(spotValue);
		Double expectedImpsQtr=Double.valueOf(cellImps)* Double.valueOf(spotValue);
		Double expectedCPMQtr=expectedRateQtr/expectedImpsQtr;
		Logger.log("expectedRateQtr is==> "+expectedRateQtr+"\n expectedImpsQtr is==>>"+expectedImpsQtr+"\n expectedCPMQtr is ==> "+expectedCPMQtr);
		
		//get rate value in total qtr
		String grossValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr);
		
		String impsValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr);
		
		String cpmValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.grossCpm);
		
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
		planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.defaultWorksapceColumn);
		//get total values from Plan Total Tab
		String grossValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr);
		
		String impsValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr);
		
		String cpmValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.grossCpm);
		
		Logger.log("grossValuePlanTotalTab is==> "+grossValuePlanTotalTab+"\n impsValuePlanTotalTab is==>>"+impsValuePlanTotalTab+"\n cpmValuePlanTotalTab is ==> "+cpmValuePlanTotalTab);
		
		//Verify Gross Rate, IMPS and CPM Plan Total values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValuePlanTotalTab.replace(",", "").replace("$", ""))),"rate total qtr and plan total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValuePlanTotalTab.replace(",", "").replace("$", ""))),"imps total qtr and plan total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValuePlanTotalTab.replace(",", "").replace("$", ""))),"cpm total qtr and plan total is not same");


        /*
         * Update Unit Index
         */
		//Navigate to Period View Tab
        planworkspace.navigateToPeriodViewTab();
        
        workSpacebook.updateUnitIndex(AutoConfigPlannerWorkSpace.spotLengthAttribute, 2, rateIndexValue);
        workSpacebook.updateUnitIndex(AutoConfigPlannerWorkSpace.spotLengthAttribute, 1, rateIndexValue);

        //click weekly spots value
		planworkspace.clickWeeklySpotsValue(product, dayPart, 1);
      		
      		
		//Get Cell Property Rate Value
		String cellRateAfterRateIndexChanged=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");
		
		//Get Cell Property imps Value
		String cellImpsAfterRateIndexChanged=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		//Get Cell Property cpm Value
		String cellCPMAfterRateIndexChanged=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");	
		Logger.log("cellRateAfterRateIndexChanged is==> "+cellRateAfterRateIndexChanged+"\n cellImpsAfterRateIndexChanged is==>>"+cellImpsAfterRateIndexChanged+"\n cellCPMAfterRateIndexChanged is ==> "+cellCPMAfterRateIndexChanged);
	
      	//Expected Cell rate is cellRate/cellImps
  		Double ExpectedCellRateAfterRateIndexChanged = Double.valueOf(cellRate)*(Double.valueOf(rateIndexValue)/100.00);
  		Logger.log("ExpectedCellRateAfterRateIndexChanged is==> "+ExpectedCellRateAfterRateIndexChanged);
  		
  	    //Expected Cell imps is cellRate/cellImps
  		Double ExpectedCellImpsAfterRateIndexChanged = Double.valueOf(cellImps)*(Double.valueOf(rateIndexValue)/100.00);
  		Logger.log("ExpectedCellImpsAfterRateIndexChanged is==> "+ExpectedCellImpsAfterRateIndexChanged);
  		
      	//Verify Cell CPM is CellRate/CellIMPS
  		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellRateAfterRateIndexChanged), getBasePage().
  				format2Digi(String.valueOf(ExpectedCellRateAfterRateIndexChanged)),"Cell Rate is not expected after Rate Index Changed");
  		
  	    //Verify Cell Imps 
  		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellImpsAfterRateIndexChanged), getBasePage().
  				format2Digi(String.valueOf(ExpectedCellImpsAfterRateIndexChanged)),"Cell imps is not expected after Rate Index Changed");
  		
      		
  		//Expected Cell CPM is cellRate/cellImps
  		Double ExpectedCellCPMAfterRateIndexChanged = Double.valueOf(cellRateAfterRateIndexChanged)/Double.valueOf(cellImpsAfterRateIndexChanged);
  		Logger.log("ExpectedCellCPMAfterRateIndexChanged is==> "+ExpectedCellCPMAfterRateIndexChanged);
  		
  		//Verify Cell CPM is CellRate/CellIMPS
  		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPMAfterRateIndexChanged), getBasePage().
  				format2Digi(String.valueOf(ExpectedCellCPMAfterRateIndexChanged)),"Cell CPM is not expected after Rate Index Changed");
  		
  		//Expected Quarter Rate, IMPS and CPM
  		Double expectedRateQtrAfterRateIndexChanged=Double.valueOf(cellRateAfterRateIndexChanged)* Double.valueOf(spotValue);
  		Double expectedImpsQtrAfterRateIndexChanged=Double.valueOf(cellImpsAfterRateIndexChanged)* Double.valueOf(spotValue);
  		Double expectedCPMQtrAfterRateIndexChanged=expectedRateQtrAfterRateIndexChanged/expectedImpsQtrAfterRateIndexChanged;
  		Logger.log("expectedRateQtrAfterRateIndexChanged is==> "+expectedRateQtrAfterRateIndexChanged+"\n expectedImpsQtrAfterRateIndexChanged is==>>"+expectedImpsQtrAfterRateIndexChanged+"\n expectedCPMQtrAfterRateIndexChanged is ==> "+expectedCPMQtrAfterRateIndexChanged);
  		
  		//get rate value in total qtr
  		String grossValueQtrAfterRateIndexChanged=planworkspace.getAggregateTotalValueQtr(product, 
  				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
  		
  		String impsValueQtrAfterRateIndexChanged=planworkspace.getAggregateTotalValueQtr(product, 
  				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace("$", "").replace(",", "");
  		
  		String cpmValueQtrAfterRateIndexChanged=planworkspace.getAggregateTotalValueQtr(product, 
  				dayPart, AutoConfigPlannerWorkSpace.grossCpm).replace("$", "").replace(",", "");
  		
  		Logger.log("grossValueQtrAfterRateIndexChanged is==> "+grossValueQtrAfterRateIndexChanged+"\n impsValueQtrAfterRateIndexChanged is==>>"+impsValueQtrAfterRateIndexChanged+"\n cpmValueQtrAfterRateIndexChanged is ==> "+cpmValueQtrAfterRateIndexChanged);
  		
  		//Verify Gross Rate, IMPS and CPM quarter values
  		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValueQtrAfterRateIndexChanged.replace(",", "").replace("$", "")), getBasePage().
  				format2Digi(String.valueOf(expectedRateQtrAfterRateIndexChanged)),"rate total qtr not same after Rate Index Changed");
  		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrAfterRateIndexChanged.replace(",", "").replace("$", "")), getBasePage().
  				format2Digi(String.valueOf(expectedImpsQtrAfterRateIndexChanged)),"imps total qtr not same after Rate Index Changed");
  		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrAfterRateIndexChanged.replace(",", "").replace("$", "")), getBasePage().
  				format2Digi(String.valueOf(expectedCPMQtrAfterRateIndexChanged)),"cpm total qtr not same after Rate Index Changed");
  		
  		//Navigate to Plan Total Tab
  		planworkspace.navigateToPlanTotalTab();
  		
  		//get total values from Plan Total Tab
  		String grossValuePlanTotalTabAfterRateIndexChanged=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
  				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
  		
  		String impsValuePlanTotalTabAfterRateIndexChanged=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
  				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace("$", "").replace(",", "");
  		
  		String cpmValuePlanTotalTabAfterRateIndexChanged=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
  				dayPart, AutoConfigPlannerWorkSpace.grossCpm).replace("$", "").replace(",", "");
  		
  		Logger.log("grossValuePlanTotalTabAfterRateIndexChanged is==> "+grossValuePlanTotalTabAfterRateIndexChanged+"\n impsValuePlanTotalTabAfterRateIndexChanged is==>>"+impsValuePlanTotalTabAfterRateIndexChanged+"\n cpmValuePlanTotalTabAfterRateIndexChanged is ==> "+cpmValuePlanTotalTabAfterRateIndexChanged);
  		
  		//Verify Gross Rate, IMPS and CPM Plan Total values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValueQtrAfterRateIndexChanged.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValuePlanTotalTabAfterRateIndexChanged.replace(",", "").replace("$", ""))),"rate total qtr and plan total is not same after Rate Index Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrAfterRateIndexChanged.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValuePlanTotalTabAfterRateIndexChanged.replace(",", "").replace("$", ""))),"imps total qtr and plan total is not same after Rate Index Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrAfterRateIndexChanged.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValuePlanTotalTabAfterRateIndexChanged.replace(",", "").replace("$", ""))),"cpm total qtr and plan total is not same after Rate Index Changed");

        
				
	/*
	 * Update Weekly cell rate value and veriy calculation on Qtr and Plan Total Tab			
	 */
		//Navigate to Period View Tab
        planworkspace.navigateToPeriodViewTab();
        
        
        
      //click weekly spots value
      	planworkspace.clickWeeklySpotsValue(product, dayPart, 1);
      	
		String cellRateUpdatedvalue=String.valueOf(Integer.valueOf(cellRate)*2);
		String cellImpsUpdatedvalue=String.valueOf(Double.valueOf(cellImps)*2);
		Logger.log("cellRateUpdatedvalue is==> "+cellRateUpdatedvalue+"\n cellImpsUpdatedvalue is==>>"+cellImpsUpdatedvalue);
		
		//enter cell properties in imps 
		planworkspace.enterCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps, cellImpsUpdatedvalue);
		//enter cell properties in rate 
		planworkspace.enterCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate, cellRateUpdatedvalue);

		//Save Workspace
		planworkspace.clickSaveWorkSpace();

		//click weekly spots value
		planworkspace.clickWeeklySpotsValue(product, dayPart, 1);
				
		//Get Cell Property Rate Value
		String cellRateUpdated=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value
		String cellImpsUpdated=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		//Get Cell Property cpm Value
		String cellCPMUpdated=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");	
		Logger.log("cellRateUpdated is==> "+cellRateUpdated+"\n cellImpsUpdated is==>>"+cellImpsUpdated+"\n cellCPMUpdated is ==> "+cellCPMUpdated);
		
		//Expected Cell CPM is cellRate/cellImps
		Double ExpectedCellCPMUpdated = Double.valueOf(cellRateUpdated)/Double.valueOf(cellImpsUpdated);
		Logger.log("ExpectedCellCPMUpdated is==> "+ExpectedCellCPMUpdated);
		
		//Verify Cell CPM is CellRate/CellIMPS
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPMUpdated), getBasePage().
				format2Digi(String.valueOf(ExpectedCellCPMUpdated)),"Cell CPM is not expected after weekly rate and IMPS update");
		
		//Expected Quarter Rate, IMPS and CPM
		Double expectedRateQtrUpdated=Double.valueOf(cellRateUpdated)* Double.valueOf(spotValue);
		Double expectedImpsQtrUpdated=Double.valueOf(cellImpsUpdated)* Double.valueOf(spotValue);
		Double expectedCPMQtrUpdated=expectedRateQtrUpdated/expectedImpsQtrUpdated;
		Logger.log("expectedRateQtrUpdated is==> "+expectedRateQtrUpdated+"\n expectedImpsQtrUpdated is==>>"+expectedImpsQtrUpdated+"\n expectedCPMQtrUpdated is ==> "+expectedCPMQtrUpdated);
		
		//get rate value in total qtr
		String grossValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
		
		String impsValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace("$", "").replace(",", "");
		
		String cpmValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.grossCpm).replace("$", "").replace(",", "");
		
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
		String grossValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
		
		String impsValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace("$", "").replace(",", "");
		
		String cpmValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.grossCpm).replace("$", "").replace(",", "");
		
		Logger.log("grossValuePlanTotalTabUpdated is==> "+grossValuePlanTotalTabUpdated+"\n impsValuePlanTotalTabUpdated is==>>"+impsValuePlanTotalTabUpdated+"\n cpmValuePlanTotalTabUpdated is ==> "+cpmValuePlanTotalTabUpdated);
		
		//Verify Gross Rate, IMPS and CPM Plan Total values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValuePlanTotalTabUpdated.replace(",", "").replace("$", ""))),"rate total qtr and plan total is not same after weekly rate and IMPS update");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValuePlanTotalTabUpdated.replace(",", "").replace("$", ""))),"imps total qtr and plan total is not same after weekly rate and IMPS update");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(cpmValuePlanTotalTabUpdated.replace(",", "").replace("$", ""))),"cpm total qtr and plan total is not same after weekly rate and IMPS update");

		
		/*
		 * Perform Quick Refresh
		 */
		//Navigate to Period View Tab
        planworkspace.navigateToPeriodViewTab();
		
        
        //click Quick Refresh
        workSpacebook.openQuickRefreshBooks().keepOverriddenUnitInedexQuickRefresh().clickQuickRefreshBooks();
		
		
		//click weekly spots value After Quick Refresh
		planworkspace.clickWeeklySpotsValue(product, dayPart, 1);
				
		//Get Cell Property Rate Value
		String cellRateAfterQuickRefresh=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value
		String cellImpsAfterQuickRefresh=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		//Get Cell Property cpm Value
		String cellCPMAfterQuickRefresh=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");	
		Logger.log("cellRateUpdatedAfterQuickRefresh is==> "+cellRateAfterQuickRefresh+"\n cellImpsAfterQuickRefresh is==>>"+cellImpsAfterQuickRefresh+"\n cellCPMAfterQuickRefresh is ==> "+cellCPMAfterQuickRefresh);
		
		//Expected Cell CPM is cellRate/cellImps
		Double ExpectedCellCPMAfterQuickRefresh = Double.valueOf(cellRateAfterRateIndexChanged)/Double.valueOf(cellImpsUpdated);
		Logger.log("ExpectedCellCPMAfterQuickRefresh is==> "+ExpectedCellCPMAfterQuickRefresh);
		
		//Verify Cell CPM is CellRate/CellIMPS
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPMAfterQuickRefresh), getBasePage().
				format2Digi(String.valueOf(ExpectedCellCPMAfterQuickRefresh)),"Cell CPM is not expected  After quick refresh");
				
		//Verify Cell Rate, IMPS and CPM is CellRate/CellIMPS
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellRateAfterQuickRefresh), getBasePage().
				format2Digi(String.valueOf(cellRateAfterRateIndexChanged)),"Cell Rate is not expected after quick Refresh");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellImpsAfterQuickRefresh), getBasePage().
				format2Digi(String.valueOf(cellImpsUpdated)),"Cell Imps is not expected after quick Refresh");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPMAfterQuickRefresh), getBasePage().
				format2Digi(String.valueOf(ExpectedCellCPMAfterQuickRefresh)),"Cell CPM is not expected after quick Refresh");
		
		
		//get rate value in total qtr
		String grossValueQtrUpdatedAfterQuickRefresh=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
		
		String impsValueQtrUpdatedAfterQuickRefresh=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace("$", "").replace(",", "");
		
		String cpmValueQtrUpdatedAfterQuickRefresh=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.grossCpm).replace("$", "").replace(",", "");
		
		Logger.log("grossValueQtrUpdatedAfterQuickRefresh is==> "+grossValueQtrUpdatedAfterQuickRefresh+"\n impsValueQtrUpdatedAfterQuickRefresh is==>>"+impsValueQtrUpdatedAfterQuickRefresh+"\n cpmValueQtrUpdatedAfterQuickRefresh is ==> "+cpmValueQtrUpdatedAfterQuickRefresh);
		
		
		//Expected Cell CPM is cellRate/cellImps
		Double ExpectedcpmValueQtrUpdatedAfterQuickRefresh = Double.valueOf(grossValueQtrAfterRateIndexChanged)/Double.valueOf(expectedImpsQtrUpdated);
		Logger.log("ExpectedcpmValueQtrUpdatedAfterQuickRefresh is==> "+ExpectedcpmValueQtrUpdatedAfterQuickRefresh);

		//Verify Gross Rate, IMPS and CPM quarter values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValueQtrUpdatedAfterQuickRefresh.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValueQtrAfterRateIndexChanged.replace(",", "").replace("$", ""))),"rate total qtr not expected after quick Refresh");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrUpdatedAfterQuickRefresh.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValueQtrUpdated.replace(",", "").replace("$", ""))),"imps total qtr not expected after quick Refresh");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrUpdatedAfterQuickRefresh.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(ExpectedcpmValueQtrUpdatedAfterQuickRefresh)),"cpm total qtr not expected after quick Refresh");
		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		
		//get total values from Plan Total Tab
		String grossValuePlanTotalTabUpdatedAfterQuickRefresh=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
		
		String impsValuePlanTotalTabUpdatedAfterQuickRefresh=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace("$", "").replace(",", "");
		
		String cpmValuePlanTotalTabUpdatedAfterQuickRefresh=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.grossCpm).replace("$", "").replace(",", "");
		
		Logger.log("grossValuePlanTotalTabUpdatedAfterQuickRefresh is==> "+grossValuePlanTotalTabUpdatedAfterQuickRefresh+"\n impsValuePlanTotalTabUpdatedAfterQuickRefresh is==>>"+impsValuePlanTotalTabUpdatedAfterQuickRefresh+"\n cpmValuePlanTotalTabUpdatedAfterQuickRefresh is ==> "+cpmValuePlanTotalTabUpdatedAfterQuickRefresh);
		
		//Verify Gross Rate, IMPS and CPM Plan Total values
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossValuePlanTotalTabUpdatedAfterQuickRefresh.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(grossValueQtrAfterRateIndexChanged.replace(",", "").replace("$", ""))),"rate total qtr and plan total is not expected after wuick refresh");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValuePlanTotalTabUpdatedAfterQuickRefresh.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(impsValueQtrUpdated.replace(",", "").replace("$", ""))),"imps total qtr and plan total is not expected after wuick refresh");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValuePlanTotalTabUpdatedAfterQuickRefresh.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(ExpectedcpmValueQtrUpdatedAfterQuickRefresh)),"cpm total qtr and plan total is not expected after wuick refresh");
		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
