package com.operative.tests.plannerLocalWorkspaceSmoke;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
 * @author uday madan
 *
 */

public class verifyCalculation_AllMetrices_WeeklyPannelWithbookAndRateCard_Local extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerWorkSpaceDataprovider.class, dataProvider = "localSmokeSuitePlannerDataWeekly")
	public void verifyCalculationAllMetricesPeriodViewWithbookAndRateCard(String planClass, String station,
			String flightDates, String flightStartDate, String flightEndDate, String advertiser, String buyingAgency,
			String accountExecutive, String calenderValue, String startPeriodValue, String endPeriodValue, String demo,
			String dayPart, String marketPlace, String ratingStream, String product, String ratecard, String spotValue1,
			String spotValue2) throws ParseException, InterruptedException {
		PlannerHeaderPage planHeaderPage = new PlannerHeaderPage(getPageBrowser());
		BasePage getBasePage = new BasePage(getPageBrowser());
		final PlannerWorkBookPage workSpacebook = new PlannerWorkBookPage(getPageBrowser());

		String planName = AutoConfigPlannerHeader.linearPlanName + getBasePage.getRandomNumber();

		// creating Plan Header
		planHeaderPage.creationLocalPlanHeader(planName, planClass, advertiser, buyingAgency, accountExecutive,
				calenderValue, startPeriodValue, endPeriodValue, marketPlace, flightDates, station, demo);
		// Select Rating Stream
		planHeaderPage.selectRatingStream(ratingStream);
		// Add Ratecard
		planHeaderPage.selectRateCardName(ratecard);
		// save Plan Header
		planHeaderPage.clickSaveHeader();
		// navigate to WorkSpace
		PlannerWorkSpacePage planworkspace = new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();

		// get all quarters and weeks
		final List<String> generateQuarterlyValue = new DateTimeFunctions().generateQuarterlySequence(flightStartDate,
				flightEndDate);

		Logger.log("Quarter values: " + generateQuarterlyValue.toString());

		String qtrName = generateQuarterlyValue.get(0).substring(4);

		// get all weeks
		final Map<String, List<String>> weeksMap = planworkspace.getWeeksBetweenDatesWorkspaceInQuarter(flightStartDate,
				flightEndDate);
		final List<String> weeeksDates = weeksMap.get(generateQuarterlyValue.get(0));
		Logger.log(weeeksDates.toString());

		// click product chooser tab
		planworkspace.clickProductChooserTab();
		// select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttributeLocal,
				AutoConfigPlannerWorkSpace.lineClassAttributeLocal, AutoConfigPlannerWorkSpace.spotLengthAttribute)
				.clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(product).clickAddSelectedProductLocal();

		// enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(product, dayPart, spotValue1, 1);
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(product, dayPart, spotValue2, 2);
		// Save Workspace
		planworkspace.clickSaveWorkSpace();

		workSpacebook.clickonBookChooser();

		workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
		// apply Book for Selected product
		workSpacebook.applyBookForSelectedProductSelectAll(product, AutoConfigPlannerWorkSpace.bookAug21, qtrName);

		workSpacebook.clickApply();

		// click weekly spots value
		planworkspace.clickWeeklySpotsValue(product, dayPart, 1);

		// Get Cell Property Rate Value
		String cellRate1 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "")
				.replace("$", "");

		// Expected Week 1 Gross Rate
		Double expectedWeek1GrossRate = Double.valueOf(cellRate1) * Double.valueOf(spotValue1);
		Logger.log("expectedWeek1GrossRate is==> " + expectedWeek1GrossRate);

		// expected weekly cell 1 net rate is( CellRate * (100 - DBCommission))/100
		Double expectedWeek1NetRate = (expectedWeek1GrossRate
				* Double.valueOf(100 - AutoConfigPlannerWorkSpace.DBCommission)) / 100.00;
		Logger.log("expectedWeek1NetRate is==> " + expectedWeek1NetRate);

		// Get Cell Property imps Value
		String cellImps1 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "")
				.replace("$", "");

		// Expected Week 1 000
		Double expectedWeek1Imps000 = Double.valueOf(cellImps1) * Double.valueOf(spotValue1);
		Logger.log("expectedWeek1Imps000 is==> " + expectedWeek1Imps000);

		// Get Cell Property cpm Value
		String cellCPM1 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm).replace(",", "")
				.replace("$", "");

		// Get Cell Property TRG Value
		String cellRTG1 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "")
				.replace("$", "");

		// Expected Week 1 GRP or RTG
		Double expectedWeek1RTGOrGRP = Double.valueOf(cellRTG1) * Double.valueOf(spotValue1);
		Logger.log("expectedWeek1RTGOrGRP is==> " + expectedWeek1RTGOrGRP);

		// Get Cell Property TRG Value
		String cellPercentRC1 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellPercentRC)
				.replace(",", "").replace("$", "").replace("%", "");

		Logger.log("cellRate1 is==> " + cellRate1 + "\n cellImps1 is==>>" + cellImps1 + "\n cellCPM1 is ==> " + cellCPM1
				+ "\n cellRTG1 is ==> " + cellRTG1 + "\n cellPercentRC1 is ==> " + cellPercentRC1);

		// click weekly spots value
		planworkspace.clickWeeklySpotsValue(product, dayPart, 2);

		// Get Cell Property Rate Value
		String cellRate2 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "")
				.replace("$", "");

		// Expected Week 1 Gross Rate
		Double expectedWeek2GrossRate = Double.valueOf(cellRate2) * Double.valueOf(spotValue2);
		Logger.log("expectedWeek2GrossRate is==> " + expectedWeek2GrossRate);

		// expected weekly cell 1 net rate is( CellRate * (100 - DBCommission))/100
		Double expectedWeek2NetRate = (expectedWeek2GrossRate
				* Double.valueOf(100 - AutoConfigPlannerWorkSpace.DBCommission)) / 100.00;
		Logger.log("expectedWeek2NetRate is==> " + expectedWeek2NetRate);

		// Get Cell Property imps Value
		String cellImps2 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "")
				.replace("$", "");

		// Expected Week 1 000
		Double expectedWeek2Imps000 = Double.valueOf(cellImps2) * Double.valueOf(spotValue2);
		Logger.log("expectedWeek2Imps000 is==> " + expectedWeek2Imps000);

		// Get Cell Property cpm Value
		String cellCPM2 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm).replace(",", "")
				.replace("$", "");

		// Get Cell Property TRG Value
		String cellRTG2 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "")
				.replace("$", "");

		// Expected Week 1 GRP or RTG
		Double expectedWeek2RTGOrGRP = Double.valueOf(cellRTG2) * Double.valueOf(spotValue2);
		Logger.log("expectedWeek2RTGOrGRP is==> " + expectedWeek2RTGOrGRP);

		// Get Cell Property TRG Value
		String cellPercentRC2 = planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellPercentRC)
				.replace(",", "").replace("$", "").replace("%", "");

		Logger.log("cellRate2 is==> " + cellRate2 + "\n cellImps2 is==>>" + cellImps2 + "\n cellCPM2 is ==> " + cellCPM2
				+ "\n cellRTG2 is ==> " + cellRTG2 + "\n cellPercentRC2 is ==> " + cellPercentRC2);

		try {
			planworkspace.resetFilterInWeeklyTotalsWorkspace(AutoConfigPlannerWorkSpace.weeklyColumnsGrossRate + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsNetRate + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumns000Imps);

			final String weeklyRateTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyTotalRateColumn, weeeksDates);
			final String weeklyNetRateTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsNetRate, weeeksDates);
			final String weeklyImps000Total[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyTotalImps000Column, weeeksDates);

			Logger.log("Weekly Rate is: " + Joiner.on(",").join(weeklyRateTotal) + "\n Weekly Imps000 is: "
					+ Joiner.on(",").join(weeklyImps000Total) + "\n Weekly Net rate is: "
					+ Joiner.on(",").join(weeklyNetRateTotal));

			// Verify Weekly Cell 1 & 2 Rate Net Rate and 000 value
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyRateTotal[0])),
					getBasePage().format2Digi(String.valueOf(expectedWeek1GrossRate)),
					"Weekly cell 1 rate is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyRateTotal[1])),
					getBasePage().format2Digi(String.valueOf(expectedWeek2GrossRate)),
					"Weekly cell 2 rate is as not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyNetRateTotal[0])),
					getBasePage().format2Digi(String.valueOf(expectedWeek1NetRate)),
					"Weekly cell 1 Net rate is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyNetRateTotal[1])),
					getBasePage().format2Digi(String.valueOf(expectedWeek2NetRate)),
					"Weekly cell 2 Net rate is as not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyImps000Total[0])),
					getBasePage().format2Digi(String.valueOf(expectedWeek1Imps000)),
					"Weekly cell 1 000 is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyImps000Total[1])),
					getBasePage().format2Digi(String.valueOf(expectedWeek2Imps000)),
					"Weekly cell 2 000 is as not expected");

			// get HH 000 Value
			workSpacebook.ChangeDemo(AutoConfigPlannerWorkSpace.demoValueHH);

			final String weeklyHHImps000Total[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyTotalImps000Column, weeeksDates);

			Logger.log("weeklyHHImps000Total is==> " + Joiner.on(",").join(weeklyHHImps000Total));

			// Change demo to primary
			workSpacebook.ChangeDemo(AutoConfigPlannerWorkSpace.demoValueP2134);

			// Select %IMPS, Gross CPM and Net CPM
			planworkspace.resetFilterInWeeklyTotalsWorkspace(AutoConfigPlannerWorkSpace.weeklyColumnsPercentImps + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsGrossCPM + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsNetCPM);

			final String weeklyPercentImpsTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsPercentImps, weeeksDates);
			final String weeklyGrossCPMTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsGrossCPM, weeeksDates);
			final String weeklyNetCPMTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsNetCPM, weeeksDates);

			Logger.log("Weekly Percent Imps is: " + Joiner.on(",").join(weeklyPercentImpsTotal) + "\n Weekly CPM is: "
					+ Joiner.on(",").join(weeklyGrossCPMTotal) + "\n Weekly Net CPM is: "
					+ Joiner.on(",").join(weeklyNetCPMTotal));

			// Verify Weekly Cell 1 & 2 R %IMPS, Gross CPM and Net CPM
			Double expWeek1PercentImps = (expectedWeek1Imps000 / (expectedWeek1Imps000 + expectedWeek2Imps000))
					* 100.00;
			Double expWeek2PercentImps = (expectedWeek2Imps000 / (expectedWeek1Imps000 + expectedWeek2Imps000))
					* 100.00;
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentImpsTotal[0].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expWeek1PercentImps)),
					"Weekly cell 1 %Imps is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentImpsTotal[1].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expWeek2PercentImps)),
					"Weekly cell 2 %Imps is as not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyGrossCPMTotal[0])),
					getBasePage().format2Digi(String.valueOf(cellCPM1)), "Weekly cell 1 Gross CPM is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyGrossCPMTotal[1])),
					getBasePage().format2Digi(String.valueOf(cellCPM1)), "Weekly cell 2 Gross CPM is as not expected");

			Double expWeek1NetCPM = expectedWeek1NetRate / expectedWeek1Imps000;
			Double expWeek2NetCPM = expectedWeek2NetRate / expectedWeek2Imps000;

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyNetCPMTotal[0])),
					getBasePage().format2Digi(String.valueOf(expWeek1NetCPM)),
					"Weekly cell 1 Net CPM is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyNetCPMTotal[1])),
					getBasePage().format2Digi(String.valueOf(expWeek2NetCPM)),
					"Weekly cell 2 Net CPM is as not expected");

			// Select units %GRP and Gross CPP
			planworkspace.resetFilterInWeeklyTotalsWorkspace(AutoConfigPlannerWorkSpace.weeklyColumnsUnits + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsPercentGRP + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsGrossCPP);

			final String weeklyUnitsTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsUnits, weeeksDates);
			final String weeklyPercentGRPTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsPercentGRP, weeeksDates);
			final String weeklyGrossCPPTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsGrossCPP, weeeksDates);

			Logger.log("Weekly Units is: " + Joiner.on(",").join(weeklyUnitsTotal) + "\n Weekly Percent GRP is: "
					+ Joiner.on(",").join(weeklyPercentGRPTotal) + "\n Weekly Gross CPP is: "
					+ Joiner.on(",").join(weeklyGrossCPPTotal));

			Double expectedWeek1PercentGRPorRTG = (expectedWeek1RTGOrGRP
					/ (expectedWeek1RTGOrGRP + expectedWeek2RTGOrGRP)) * 100.00;
			Double expectedWeek2PercentGRPorRTG = (expectedWeek2RTGOrGRP
					/ (expectedWeek1RTGOrGRP + expectedWeek2RTGOrGRP)) * 100.00;

			// Verify Weekly Cell 1 & 2 units %GRP and Gross CPP
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyUnitsTotal[0])),
					getBasePage().format2Digi(spotValue1), "Weekly cell 1 units is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyUnitsTotal[1])),
					getBasePage().format2Digi(spotValue2), "Weekly cell 2 units is as not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentGRPTotal[0].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expectedWeek1PercentGRPorRTG)),
					"Weekly cell 1 %GRP is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentGRPTotal[1].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expectedWeek2PercentGRPorRTG)),
					"Weekly cell 2 %GRP is as not expected");

			// Select Net CPP, Percent Units, GRP
			planworkspace.resetFilterInWeeklyTotalsWorkspace(AutoConfigPlannerWorkSpace.weeklyColumnsNetCPP + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsPercentUnits + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsGRP);

			final String weeklyNetCPPTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsNetCPP, weeeksDates);
			final String weeklyPercentUnitsTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsPercentUnits, weeeksDates);
			final String weeklyGRPTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsGRP, weeeksDates);

			Logger.log("Weekly Net CPP is: " + Joiner.on(",").join(weeklyNetCPPTotal) + "\n Weekly Percent Units is: "
					+ Joiner.on(",").join(weeklyPercentUnitsTotal) + "\n Weekly GRP is: "
					+ Joiner.on(",").join(weeklyGRPTotal));

			// Verify Weekly Cell 1 & 2 Gross CPP, Net CPP,Percent Units GRP
			Double expWeek1GrossCPP = Double.valueOf(weeklyRateTotal[0]) / Double.valueOf(weeklyGRPTotal[0]);
			Double expWeek2GrossCPP = Double.valueOf(weeklyRateTotal[1]) / Double.valueOf(weeklyGRPTotal[1]);

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyGrossCPPTotal[0])),
					getBasePage().format2Digi(String.valueOf(expWeek1GrossCPP)),
					"Weekly cell 1 Gross Cpp is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyGrossCPPTotal[1])),
					getBasePage().format2Digi(String.valueOf(expWeek2GrossCPP)),
					"Weekly cell 2 Gross Cpp is as not expected");

			Double expWeek1NetCPP = Double.valueOf(weeklyNetRateTotal[0]) / Double.valueOf(weeklyGRPTotal[0]);
			Double expWeek2NetCPP = Double.valueOf(weeklyNetRateTotal[1]) / Double.valueOf(weeklyGRPTotal[1]);

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyNetCPPTotal[0])),
					getBasePage().format2Digi(String.valueOf(expWeek1NetCPP)),
					"Weekly cell 1 Net CPP is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyNetCPPTotal[1])),
					getBasePage().format2Digi(String.valueOf(expWeek2NetCPP)),
					"Weekly cell 2 Net CPP is as not expected");

			Double expWeek1PercentUnits = (Double.valueOf(spotValue1)
					/ (Double.valueOf(spotValue1) + Double.valueOf(spotValue2))) * 100.00;
			Double expWeek2PercentUnits = (Double.valueOf(spotValue2)
					/ (Double.valueOf(spotValue1) + Double.valueOf(spotValue2))) * 100.00;

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentUnitsTotal[0].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expWeek1PercentUnits)),
					"Weekly cell 1 %Units is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentUnitsTotal[1].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expWeek2PercentUnits)),
					"Weekly cell 2 %Units is as not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyGRPTotal[0])),
					getBasePage().format2Digi(String.valueOf(expectedWeek1RTGOrGRP)),
					"Weekly cell 1 GRP is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyGRPTotal[1])),
					getBasePage().format2Digi(String.valueOf(expectedWeek2RTGOrGRP)),
					"Weekly cell 2 GRP is as not expected");

			// Select Percent RC, Percent $$ , VPVH
			planworkspace.resetFilterInWeeklyTotalsWorkspace(AutoConfigPlannerWorkSpace.weeklyColumnsPercentRC + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsPercent$$ + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsVPVH);

			final String weeklyPercentRCTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsPercentRC, weeeksDates);
			final String weeklyPercent$$Total[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsPercent$$, weeeksDates);
			final String weeklyVPVHTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsVPVH, weeeksDates);

			Logger.log("Weekly Percent RC is: " + Joiner.on(",").join(weeklyPercentRCTotal)
					+ "\n Weekly Percent $$ is: " + Joiner.on(",").join(weeklyPercent$$Total) + "\n Weekly VPVH is: "
					+ Joiner.on(",").join(weeklyVPVHTotal));

			// Verify Weekly Cell 1 & 2 Percent RC, Percent $$ , VPVH
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentRCTotal[0].replace("%", ""))),
					getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent),
					"Weekly cell 1 %RC is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentRCTotal[1].replace("%", ""))),
					getBasePage().format2Digi(AutoConfigPlannerWorkSpace.hundredPercent),
					"Weekly cell 2 %RC is as not expected");

			Double expWeek1PercentRate = (expectedWeek1GrossRate / (expectedWeek1GrossRate + expectedWeek2GrossRate))
					* 100.00;
			Double expWeek2PercentRate = (expectedWeek2GrossRate / (expectedWeek1GrossRate + expectedWeek2GrossRate))
					* 100.00;

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercent$$Total[0].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expWeek1PercentRate)),
					"Weekly cell 1 %$$ is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercent$$Total[1].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expWeek2PercentRate)),
					"Weekly cell 2 %$$ is as not expected");

			Double expWeek1Vpvh = Double.valueOf(weeklyImps000Total[0]) / Double.valueOf(weeklyHHImps000Total[0]);
			Double expWeek2Vpvh = Double.valueOf(weeklyImps000Total[1]) / Double.valueOf(weeklyHHImps000Total[1]);

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyVPVHTotal[0])),
					getBasePage().format2Digi(String.valueOf(expWeek1Vpvh)), "Weekly cell 1 VPVH is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyVPVHTotal[1])),
					getBasePage().format2Digi(String.valueOf(expWeek2Vpvh)), "Weekly cell 2 VPVH is as not expected");

			// Select Eq Units, Percent Eq Units , Imps
			planworkspace.resetFilterInWeeklyTotalsWorkspace(AutoConfigPlannerWorkSpace.weeklyColumnsEqUnits + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsPercentEqUnits + ";"
					+ AutoConfigPlannerWorkSpace.weeklyColumnsImps);

			final String weeklyEqUnitsTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsEqUnits, weeeksDates);
			final String weeklyPercentEqUnitsTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsPercentEqUnits, weeeksDates);
			final String weeklyImpsTotal[] = planworkspace
					.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyColumnsImps, weeeksDates);

			Logger.log("Weekly Eq Units is: " + Joiner.on(",").join(weeklyEqUnitsTotal)
					+ "\n Weekly Percent Eq Units is: " + Joiner.on(",").join(weeklyPercentEqUnitsTotal)
					+ "\n Weekly imps is: " + Joiner.on(",").join(weeklyImpsTotal));

			// Verify Weekly Cell 1 & 2 Eq Units, Percent Eq Units , Imps
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyEqUnitsTotal[0])),
					getBasePage().format2Digi(spotValue1), "Weekly cell 1 Eq Units is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyEqUnitsTotal[1])),
					getBasePage().format2Digi(spotValue2), "Weekly cell 2Eq Units is as not expected");

			Double expWeek1PercentEqUnits = (Double.valueOf(weeklyEqUnitsTotal[0])
					/ (Double.valueOf(weeklyEqUnitsTotal[0]) + Double.valueOf(weeklyEqUnitsTotal[1]))) * 100.00;
			Double expWeek2PercentEqUnits = (Double.valueOf(weeklyEqUnitsTotal[1])
					/ (Double.valueOf(weeklyEqUnitsTotal[0]) + Double.valueOf(weeklyEqUnitsTotal[1]))) * 100.00;

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentEqUnitsTotal[0].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expWeek1PercentEqUnits)),
					"Weekly cell 1 %Eq Units is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyPercentEqUnitsTotal[1].replace("%", ""))),
					getBasePage().format2Digi(String.valueOf(expWeek2PercentEqUnits)),
					"Weekly cell 2 %Eq Units is as not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyImpsTotal[0])),
					getBasePage().format2Digi(String.valueOf(expectedWeek1Imps000 * 1000.00)),
					"Weekly cell 1 Imps is as not expected");
			getCustomeVerification().verifyEquals(getSoftAssert(),
					getBasePage().format2Digi(String.valueOf(weeklyImpsTotal[1])),
					getBasePage().format2Digi(String.valueOf(expectedWeek2Imps000 * 1000.00)),
					"Weekly cell 2 Imps is as not expected");

			getCustomeVerification().assertAll(getSoftAssert());

		} finally {
			planworkspace.resetFilterInWeeklyTotalsWorkspace(AutoConfigPlannerWorkSpace.weeklyTotalDefaultColumns);
		}

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
