package com.operative.tests.plannercriticalworkspaceLocal;

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
 * @author uday 
 *
 */


public class  verifyCalculation_AllMetrices_PlanTotalWithbookAndRateCard_Local extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="localSmokeSuitePlannerData")
	public void verifyCalculationAllMetricesPlanTotalWithbookAndRateCard(String planClass,String station,String flightDates,String flightStartDate,String flightEndDate,
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
		//select secondary demo
	    planHeaderPage.selectDemoPrimayAnd_secondary("", AutoConfigPlannerWorkSpace.demoValueHH);
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
		
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(product,
				dayPart,spotValue,2);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		workSpacebook.clickonBookChooser();

	    workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
	       // apply Book for Selected product
	    workSpacebook.applyBookForSelectedProductSelectAll(product,
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);

	    workSpacebook.clickApply();
		//click weekly spots value
		planworkspace.clickWeeklySpotsValue(product, dayPart, 2);
		
		//Get Cell Property Rate Value
		String cellRate=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value
		String cellImps=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		//Get Cell Property cpm Value
		String cellCPM=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm).replace(",", "").replace("$", "");	
		
		//Get Cell Property TRG Value
		String cellRTG=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		
		//Get Cell Property TRG Value
		String cellPercentRC=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellPercentRC).replace(",", "").replace("$", "").replace("%", "");	
		Logger.log("cellRate is==> "+cellRate+"\n cellImps is==>>"+cellImps+"\n cellCPM is ==> "+cellCPM +"\n cellRTG is ==> "+cellRTG +"\n cellPercentRC is ==> "+cellPercentRC);
		
		//expected Cell CPM is cellRate/cellImps
		Double expectedCellCPM = Double.valueOf(cellRate)/Double.valueOf(cellImps);
		Logger.log("expectedCellCPM is==> "+expectedCellCPM);		
		
		//Verify Cell CPM is CellRate/CellIMPS
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellCPM), getBasePage().
				format2Digi(String.valueOf(expectedCellCPM)),"Cell CPM is not expected");
		
		//Verify Cell CPM is CellRate/CellIMPS
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cellPercentRC), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent),"Cell Percent RC value is not updated correctly");
				
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
				
		//Select columns on workspace
		planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.qtrNetRate+";"+AutoConfigPlannerWorkSpace.qtr000+";"+AutoConfigPlannerWorkSpace.qtrImps+";"+AutoConfigPlannerWorkSpace.qtrPercentImps+";"+AutoConfigPlannerWorkSpace.qtrPercentVar);
		
		
		//get rate value in total qtr
		String grossRateValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace(",", "").replace("$", "");
		
		String netRateValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.netRatetotalQtr).replace(",", "").replace("$", "");
		
		String imps000ValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		String impsValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.imps000totalQtr).replace(",", "").replace("$", "");
		
		String percentImpsValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentImpstotalQtr).replace(",", "").replace("$", "").replace("%", "");
		
		String percentVarValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentVartotalQtr).replace(",", "").replace("$", "").replace("%", "");
		
		Logger.log("grossRateValuePlanTotal is==> "+grossRateValuePlanTotal+"\n netRateValuePlanTotal is==>>"+netRateValuePlanTotal+"\n imps000ValuePlanTotal is ==> "+imps000ValuePlanTotal+"\n impsValuePlanTotal is ==> "+impsValuePlanTotal+"\n percentImpsValuePlanTotal is ==> "+percentImpsValuePlanTotal+"\n percentVarValuePlanTotal is ==> "+percentVarValuePlanTotal);
		
		//get HH 000 Value
		workSpacebook.ChangeDemo(AutoConfigPlannerWorkSpace.demoValueHH);
	
		String imps000ValuePlanTotalHHDemo=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		Logger.log("imps000ValuePlanTotalHHDemo is==> "+imps000ValuePlanTotalHHDemo);
		
		//Change demo to primary
		workSpacebook.ChangeDemo(AutoConfigPlannerWorkSpace.demoValueP2134);
	
		//Select columns on workspace
		planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.qtrGrossCPM+";"+AutoConfigPlannerWorkSpace.qtrNetCPM+";"+AutoConfigPlannerWorkSpace.qtrUnits+";"+AutoConfigPlannerWorkSpace.qtrPercentUnits+";"+AutoConfigPlannerWorkSpace.qtrGrossCPP+";"+AutoConfigPlannerWorkSpace.qtrNetCPP);
				
		String cpmValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.cpmtotalQtr).replace(",", "").replace("$", "");
		
		String netCpmValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.netCpmtotalQtr).replace(",", "").replace("$", "");
		
		String unitsValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.unitstotalQtr).replace(",", "").replace("$", "");
		
		String unitsPercentValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.unitsPercenttotalQtr).replace(",", "").replace("$", "").replace("%", "");
		
		String grossCppValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.grossCpptotalQtr).replace(",", "").replace("$", "");
		
		String netCppValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.netCpptotalQtr).replace(",", "").replace("$", "");
		
		Logger.log("cpmValuePlanTotal is==> "+cpmValuePlanTotal+"\n netCpmValuePlanTotal is==>>"+netCpmValuePlanTotal+"\n unitsValuePlanTotal is ==> "+unitsValuePlanTotal+"\n unitsPercentValuePlanTotal is ==> "+unitsPercentValuePlanTotal+"\n grossCppValuePlanTotal is ==> "+grossCppValuePlanTotal+"\n netCppValuePlanTotal is ==> "+netCppValuePlanTotal);
		
		
		//Select columns on workspace
		planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.qtrGRP+";"+AutoConfigPlannerWorkSpace.qtrPercentGRP+";"+AutoConfigPlannerWorkSpace.qtrPercentRC+";"+AutoConfigPlannerWorkSpace.qtrPercent$$+";"+AutoConfigPlannerWorkSpace.qtrVPVH+";"+AutoConfigPlannerWorkSpace.qtrEqUnits+";"+AutoConfigPlannerWorkSpace.qtrPercentEqUnits);
				
		String grpValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.grptotalQtr).replace(",", "").replace("$", "");
		
		String grpPercentValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.grpPercenttotalQtr).replace(",", "").replace("$", "").replace("%", "");
		
		String percentRCValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentRCQtr).replace(",", "").replace("$", "").replace("%", "");
		
		String percent$$ValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.percent$$Qtr).replace(",", "").replace("$", "").replace("%", "");
		
		String vpvhValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.vpvhQtr).replace(",", "").replace("$", "");
		
		String eqUnitsValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.eqUnitsQtr).replace(",", "").replace("$", "");
		
		String percentEqUnitsValuePlanTotal=planworkspace.getAggregateTotalValuePlanTotalTab(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentEqUnitsQtr).replace(",", "").replace("$", "").replace("%", "");
		
		Logger.log("grpValuePlanTotal is==> "+grpValuePlanTotal+"\n grpPercentValuePlanTotal is==>>"+grpPercentValuePlanTotal+"\n percentRCValuePlanTotal is ==> "+percentRCValuePlanTotal+"\n percent$$ValuePlanTotal is ==> "+percent$$ValuePlanTotal+"\n vpvhValuePlanTotal is ==> "+vpvhValuePlanTotal+"\n eqUnitsValuePlanTotal is ==> "+eqUnitsValuePlanTotal+"\n percentEqUnitsValuePlanTotal is ==> "+percentEqUnitsValuePlanTotal);
		
		/*
		 * Verify Metrics values on quarter/period view tab
		 */
		
		//expected quarter rate is cellRate * Weekly Units
		Double expectedPlanTotalRate = Double.valueOf(cellRate) * Double.valueOf(spotValue);
		Logger.log("expectedPlanTotalRate is==> "+expectedPlanTotalRate);
		
		//expected quarter rate is( QtrRate * (100 - DBCommission))/100
		Double expectedPlanTotalNetRate = (expectedPlanTotalRate * Double.valueOf(100 - AutoConfigPlannerWorkSpace.DBCommission))/100.00;
		Logger.log("expectedPlanTotalNetRate is==> "+expectedPlanTotalNetRate);
		
		//expected quarter 000 is cellRate/cellImps
		Double expectedPlanTotal000 = Double.valueOf(cellImps) * Double.valueOf(spotValue);
		Logger.log("expectedPlanTotal000 is==> "+expectedPlanTotal000);
		
		//expected quarter IMPS is 000 * 1000
		Double expectedPlanTotalIMPS = expectedPlanTotal000 * 1000.00;
		Logger.log("expectedPlanTotalIMPS is==> "+expectedPlanTotalIMPS);
		
		//expected quarter vpvh is primary Demo 000 / HH 000
		Double expectedPlanTotalVpvh = expectedPlanTotal000 /Double.valueOf(imps000ValuePlanTotalHHDemo);
		Logger.log("expectedPlanTotalVpvh is==> "+expectedPlanTotalVpvh);
		
	
		//expected quarter Gross CPM is qtrGrossRate/qtrGrossImps
		Double expectedPlanTotalCPM = expectedPlanTotalRate/expectedPlanTotal000;
		Logger.log("expectedQtrCPM is==> "+expectedPlanTotalCPM);	
		
		//expected quarter Gross CPM is qtrGrossRate/qtrGrossImps
		Double expectedPlanTotalNetCPM = expectedPlanTotalNetRate/expectedPlanTotal000;
		Logger.log("expectedPlanTotalNetCPM is==> "+expectedPlanTotalNetCPM);
		
		//expected quarter GRP/RTG is cellRTG * Double.valueOf(spotValue)
		Double expectedPlanTotalGrpOrRtg = Double.valueOf(cellRTG) * Double.valueOf(spotValue);
		Logger.log("expectedPlanTotalGrpOrRtg is==> "+expectedPlanTotalGrpOrRtg);
				
		//expected quarter Gross CPP is qtrGrossRate/qtrGRP/RTG
		Double expectedPlanTotalCPP = Double.valueOf(grossRateValuePlanTotal)/Double.valueOf(grpValuePlanTotal);
		Logger.log("expectedPlanTotalCPP is==> "+expectedPlanTotalCPP);	
				
		//expected quarter Gross CPP is qtrGrossRate/qtrGrossImps
		Double expectedPlanTotalNetCPP = Double.valueOf(netRateValuePlanTotal)/Double.valueOf(grpValuePlanTotal);
		Logger.log("expectedPlanTotalNetCPP is==> "+expectedPlanTotalNetCPP);	
				
		
		//Verify Quarter Rate
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotalRate)), 
				getBasePage().format2Digi(String.valueOf(grossRateValuePlanTotal)),"Plan Total rate is as not expected");
		
		//Verify Quarter NetRate
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotalNetRate)), 
			getBasePage().format2Digi(String.valueOf(netRateValuePlanTotal)),"Plan Total net rate is as not expected");
		
		//Verify Quarter 000
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotal000)), 
			getBasePage().format2Digi(String.valueOf(imps000ValuePlanTotal)),"Plan Total 000 is as not expected");
		
		//Verify Quarter Imps
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotalIMPS)), 
			getBasePage().format2Digi(String.valueOf(impsValuePlanTotal)),"Plan Total Imps is as not expected");
		
		//Verify Quarter Percent Imps
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent) , 
				getBasePage().format2Digi(percentImpsValuePlanTotal),"Plan Total Percent Imps is as not expected");

		//Verify Quarter Percent Var
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
				getBasePage().format2Digi(percentVarValuePlanTotal),"Plan Total Percent var is as not expected");

		//Verify Quarter  Gross CPM
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotalCPM)), 
			getBasePage().format2Digi(String.valueOf(cpmValuePlanTotal)),"Plan Total Gross CPM is as not expected");
		
		//Verify Quarter  Net CPM
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotalNetCPM)), 
			getBasePage().format2Digi(String.valueOf(netCpmValuePlanTotal)),"Plan Total Net CPM is as not expected");
		
		//Verify Quarter Units
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(spotValue)), 
			getBasePage().format2Digi(String.valueOf(unitsValuePlanTotal)),"Plan Total Units is as not expected");

		//Verify Quarter Percent Units
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
		getBasePage().format2Digi(unitsPercentValuePlanTotal),"Plan Total Percent Units is as not expected");


		//Verify Quarter Gross CPP
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotalCPP)), 
			getBasePage().format2Digi(String.valueOf(grossCppValuePlanTotal)),"Plan Total Cpp is as not expected");
		
		//Verify Quarter Net CPP
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotalCPP)), 
			getBasePage().format2Digi(String.valueOf(netCppValuePlanTotal)),"Plan Total Net Cpp is as not expected");
		
		//Verify Quarter GRP/TRG
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotalGrpOrRtg)), 
			getBasePage().format2Digi(String.valueOf(grpValuePlanTotal)),"Plan Total GRP/RTG is as not expected");
		
		//Verify Quarter Percent GRP/TRG
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
		getBasePage().format2Digi(grpPercentValuePlanTotal),"Plan Total Percent GRP/RTG is as not expected");

		//Verify Quarter Percent RC
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
				getBasePage().format2Digi(percentRCValuePlanTotal),"Plan Total Percent RC is as not expected");

		//Verify Quarter Percent $$
		getCustomeVerification().verifyEquals(getSoftAssert(),getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
		getBasePage().format2Digi(percent$$ValuePlanTotal),"Plan Total Percent $$ is as not expected");

		//Verify Quarter Vpvh
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedPlanTotalVpvh)), 
			getBasePage().format2Digi(String.valueOf(vpvhValuePlanTotal)),"Plan Total vpvh is as not expected");
		
		//Verify Quarter Eq Units
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(spotValue)), 
			getBasePage().format2Digi(String.valueOf(eqUnitsValuePlanTotal)),"Plan Total Eq Units is as not expected");
		
		//Verify Quarter Percent Eq Units
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
				getBasePage().format2Digi(percentEqUnitsValuePlanTotal),"Plan Total % Eq Units is as not expected");

		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
