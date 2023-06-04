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
 */


public class  verifyCalculation_AllMetrices_PeriodViewWithbookAndRateCard_Local extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="localSmokeSuitePlannerData")
	public void verifyCalculationAllMetricesPeriodViewWithbookAndRateCard(String planClass,String station,String flightDates,String flightStartDate,String flightEndDate,
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
		
		//Get Cell Property RTG Value
		String cellRTG=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		
		//Get Cell Property %RC Value
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
				
		planworkspace.navigateToPlanTotalTab();
		planworkspace.navigateToPeriodViewTab();
		//Select columns on workspace
		planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.qtrNetRate+";"+AutoConfigPlannerWorkSpace.qtr000+";"+AutoConfigPlannerWorkSpace.qtrImps+";"+AutoConfigPlannerWorkSpace.qtrPercentImps+";"+AutoConfigPlannerWorkSpace.qtrPercentVar);
		
		//get rate value in total qtr
		String grossRateValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace(",", "").replace("$", "");
		
		String netRateValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.netRatetotalQtr).replace(",", "").replace("$", "");
		
		String imps000ValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		String impsValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.imps000totalQtr).replace(",", "").replace("$", "");
		
		String percentImpsValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentImpstotalQtr).replace(",", "").replace("$", "").replace("%", "");
		
		String percentVarValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentVartotalQtr).replace(",", "").replace("$", "").replace("%", "");
		
		Logger.log("grossRateValueQtr is==> "+grossRateValueQtr+"\n netRateValueQtr is==>>"+netRateValueQtr+"\n imps000ValueQtr is ==> "+imps000ValueQtr+"\n impsValueQtr is ==> "+impsValueQtr+"\n percentImpsValueQtr is ==> "+percentImpsValueQtr+"\n percentVarValueQtr is ==> "+percentVarValueQtr);
		
		//get HH 000 Value
		workSpacebook.ChangeDemo(AutoConfigPlannerWorkSpace.demoValueHH);
	
		String imps000ValueQtrHHDemo=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.impstotalQtr).replace(",", "").replace("$", "");
		
		Logger.log("imps000ValueQtrHHDemo is==> "+imps000ValueQtrHHDemo);
		
		//Change demo to primary
		workSpacebook.ChangeDemo(AutoConfigPlannerWorkSpace.demoValueP2134);

		//Select columns on workspace
		planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.qtrGrossCPM+";"+AutoConfigPlannerWorkSpace.qtrNetCPM+";"+AutoConfigPlannerWorkSpace.qtrUnits+";"+AutoConfigPlannerWorkSpace.qtrPercentUnits+";"+AutoConfigPlannerWorkSpace.qtrGrossCPP+";"+AutoConfigPlannerWorkSpace.qtrNetCPP);
				
		String cpmValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.cpmtotalQtr).replace(",", "").replace("$", "");
		
		String netCpmValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.netCpmtotalQtr).replace(",", "").replace("$", "");
		
		String unitsValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.unitstotalQtr).replace(",", "").replace("$", "");
		
		String unitsPercentValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.unitsPercenttotalQtr).replace(",", "").replace("$", "").replace("%", "");
		
		String grossCppValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.grossCpptotalQtr).replace(",", "").replace("$", "");
		
		String netCppValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.netCpptotalQtr).replace(",", "").replace("$", "");
		
		Logger.log("cpmValueQtr is==> "+cpmValueQtr+"\n netCpmValueQtr is==>>"+netCpmValueQtr+"\n unitsValueQtr is ==> "+unitsValueQtr+"\n unitsPercentValueQtr is ==> "+unitsPercentValueQtr+"\n grossCppValueQtr is ==> "+grossCppValueQtr+"\n netCppValueQtr is ==> "+netCppValueQtr);
		
		
		//Select columns on workspace
		planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.qtrGRP+";"+AutoConfigPlannerWorkSpace.qtrPercentGRP+";"+AutoConfigPlannerWorkSpace.qtrPercentRC+";"+AutoConfigPlannerWorkSpace.qtrPercent$$+";"+AutoConfigPlannerWorkSpace.qtrVPVH+";"+AutoConfigPlannerWorkSpace.qtrEqUnits+";"+AutoConfigPlannerWorkSpace.qtrPercentEqUnits);
				
		String grpValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.grptotalQtr).replace(",", "").replace("$", "");
		
		String grpPercentValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.grpPercenttotalQtr).replace(",", "").replace("$", "").replace("%", "");
		
		String percentRCValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentRCQtr).replace(",", "").replace("$", "").replace("%", "");
		
		String percent$$ValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.percent$$Qtr).replace(",", "").replace("$", "").replace("%", "");
		
		String vpvhValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.vpvhQtr).replace(",", "").replace("$", "");
		
		String eqUnitsValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.eqUnitsQtr).replace(",", "").replace("$", "");
		
		String percentEqUnitsValueQtr=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentEqUnitsQtr).replace(",", "").replace("$", "").replace("%", "");
		
		Logger.log("grpValueQtr is==> "+grpValueQtr+"\n grpPercentValueQtr is==>>"+grpPercentValueQtr+"\n percentRCValueQtr is ==> "+percentRCValueQtr+"\n percent$$ValueQtr is ==> "+percent$$ValueQtr+"\n vpvhValueQtr is ==> "+vpvhValueQtr+"\n eqUnitsValueQtr is ==> "+eqUnitsValueQtr+"\n percentEqUnitsValueQtr is ==> "+percentEqUnitsValueQtr);
		
		/*
		 * Verify Metrics values on quarter/period view tab
		 */
		
		//expected quarter rate is cellRate * Weekly Units
		Double expectedQtrRate = Double.valueOf(cellRate) * Double.valueOf(spotValue);
		Logger.log("expectedQtrRate is==> "+expectedQtrRate);
		
		//expected quarter rate is( QtrRate * (100 - DBCommission))/100
		Double expectedQtrNetRate = (expectedQtrRate * Double.valueOf(100 - AutoConfigPlannerWorkSpace.DBCommission))/100.00;
		Logger.log("expectedQtrNetRate is==> "+expectedQtrNetRate);
		
		//expected quarter 000 is cellRate/cellImps
		Double expectedQtr000 = Double.valueOf(cellImps) * Double.valueOf(spotValue);
		Logger.log("expectedQtr000 is==> "+expectedQtr000);
		
		//expected quarter IMPS is 000 * 1000
		Double expectedQtrIMPS = expectedQtr000 * 1000.00;
		Logger.log("expectedQtrIMPS is==> "+expectedQtrIMPS);
		
		//expected quarter vpvh is primary Demo 000 / HH 000
		Double expectedQtrVpvh = expectedQtr000 /Double.valueOf(imps000ValueQtrHHDemo);
		Logger.log("expectedQtrVpvh is==> "+expectedQtrVpvh);
		
	
		//expected quarter Gross CPM is qtrGrossRate/qtrGrossImps
		Double expectedQtrCPM = expectedQtrRate/expectedQtr000;
		Logger.log("expectedQtrCPM is==> "+expectedQtrCPM);	
		
		//expected quarter Gross CPM is qtrGrossRate/qtrGrossImps
		Double expectedQtrNetCPM = expectedQtrNetRate/expectedQtr000;
		Logger.log("expectedQtrNetCPM is==> "+expectedQtrNetCPM);
		
		//expected quarter GRP/RTG is cellRTG * Double.valueOf(spotValue)
		Double expectedQtrGrpOrRtg = Double.valueOf(cellRTG) * Double.valueOf(spotValue);
		Logger.log("expectedQtrGrpOrRtg is==> "+expectedQtrGrpOrRtg);
				
		//expected quarter Gross CPP is qtrGrossRate/qtrGRP/RTG
		Double expectedQtrCPP = Double.valueOf(grossRateValueQtr)/Double.valueOf(grpValueQtr);
		Logger.log("expectedQtrCPP is==> "+expectedQtrCPP);	
				
		//expected quarter Gross CPP is qtrGrossRate/qtrGrossImps
		Double expectedQtrNetCPP = Double.valueOf(netRateValueQtr)/Double.valueOf(grpValueQtr);
		Logger.log("expectedQtrNetCPP is==> "+expectedQtrNetCPP);	
				
		
		//Verify Quarter Rate
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtrRate)), 
				getBasePage().format2Digi(String.valueOf(grossRateValueQtr)),"Quarter rate is as not expected");
		
		//Verify Quarter NetRate
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtrNetRate)), 
			getBasePage().format2Digi(String.valueOf(netRateValueQtr)),"Quarter net rate is as not expected");
		
		//Verify Quarter 000
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtr000)), 
			getBasePage().format2Digi(String.valueOf(imps000ValueQtr)),"Quarter 000 is as not expected");
		
		//Verify Quarter Imps
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtrIMPS)), 
			getBasePage().format2Digi(String.valueOf(impsValueQtr)),"Quarter Imps is as not expected");
		
		//Verify Quarter Percent Imps
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent) , 
				getBasePage().format2Digi(percentImpsValueQtr),"Quarter Percent Imps is as not expected");

		//Verify Quarter Percent Var
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
				getBasePage().format2Digi(percentVarValueQtr),"Quarter Percent var is as not expected");

		//Verify Quarter  Gross CPM
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtrCPM)), 
			getBasePage().format2Digi(String.valueOf(cpmValueQtr)),"Quarter Gross CPM is as not expected");
		
		//Verify Quarter  Net CPM
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtrNetCPM)), 
			getBasePage().format2Digi(String.valueOf(netCpmValueQtr)),"Quarter Net CPM is as not expected");
		
		//Verify Quarter Units
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(spotValue)), 
			getBasePage().format2Digi(String.valueOf(unitsValueQtr)),"Quarter Units is as not expected");

		//Verify Quarter Percent Units
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
		getBasePage().format2Digi(unitsPercentValueQtr),"Quarter Percent Units is as not expected");


		//Verify Quarter Gross CPP
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtrCPP)), 
			getBasePage().format2Digi(String.valueOf(grossCppValueQtr)),"Quarter Cpp is as not expected");
		
		//Verify Quarter Net CPP
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtrCPP)), 
			getBasePage().format2Digi(String.valueOf(netCppValueQtr)),"Quarter Net Cpp is as not expected");
		
		//Verify Quarter GRP/TRG
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtrGrpOrRtg)), 
			getBasePage().format2Digi(String.valueOf(grpValueQtr)),"Quarter GRP/RTG is as not expected");
		
		//Verify Quarter Percent GRP/TRG
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
		getBasePage().format2Digi(grpPercentValueQtr),"Quarter Percent GRP/RTG is as not expected");

		//Verify Quarter Percent RC
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
				getBasePage().format2Digi(percentRCValueQtr),"Quarter Percent RC is as not expected");

		//Verify Quarter Percent $$
		getCustomeVerification().verifyEquals(getSoftAssert(),getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
		getBasePage().format2Digi(percent$$ValueQtr),"Quarter Percent $$ is as not expected");

		//Verify Quarter Vpvh
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(expectedQtrVpvh)), 
			getBasePage().format2Digi(String.valueOf(vpvhValueQtr)),"Quarter vpvh is as not expected");
		
		//Verify Quarter Eq Units
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(spotValue)), 
			getBasePage().format2Digi(String.valueOf(eqUnitsValueQtr)),"Quarter Eq Units is as not expected");
		
		//Verify Quarter Percent Eq Units
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent), 
				getBasePage().format2Digi(percentEqUnitsValueQtr),"Quarter % Eq Units is as not expected");

		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
