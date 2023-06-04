/**
 * 
 */
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
public class VerifyRemoveUnitsWeeklyAndLineLevel extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerWorkSpaceDataprovider.class, dataProvider = "localSmokeSuitePlannerLines")
	public void verifyRemoveUnitsWeeklyAndLineLevel(String planClass, String station,
			String flightDates, String flightStartDate, String flightEndDate, String advertiser, String buyingAgency,
			String accountExecutive, String calenderValue, String startPeriodValue, String endPeriodValue, String demo,
			String dayPart, String marketPlace, String ratingStream, String product, String spotValue)
			throws ParseException, InterruptedException {
		PlannerHeaderPage planHeaderPage = new PlannerHeaderPage(getPageBrowser());
		BasePage getBasePage = new BasePage(getPageBrowser());
		final PlannerWorkBookPage workSpacebook = new PlannerWorkBookPage(getPageBrowser());

		String planName = AutoConfigPlannerHeader.linearPlanName + getBasePage.getRandomNumber();

		// creating Plan Header
		planHeaderPage.creationLocalPlanHeader(planName, planClass, advertiser, buyingAgency, accountExecutive,
				calenderValue, startPeriodValue, endPeriodValue, marketPlace, flightDates, station, demo);
		// Select Rating Stream
		planHeaderPage.selectRatingStream(ratingStream);

		planHeaderPage.clickSaveHeader();
		// navigate to WorkSpace
		PlannerWorkSpacePage planworkspace = new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();

		// get all quarters and weeks
		final List<String> generateQuarterlyValue = new DateTimeFunctions().generateQuarterlySequence(flightStartDate,
				flightEndDate);

		Logger.log("Quarter values: " + generateQuarterlyValue.toString());

		String qtrName = generateQuarterlyValue.get(0).substring(4);

		// click product chooser tab
		planworkspace.clickProductChooserTab();
		// select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttributeLocal,
				AutoConfigPlannerWorkSpace.lineClassAttributeLocal, AutoConfigPlannerWorkSpace.spotLengthAttribute)
				.clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(product).clickAddSelectedProductLocal();
		
		// enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(product, dayPart, spotValue, 1);
		// Save Workspace
		planworkspace.clickSaveWorkSpace();

		workSpacebook.clickonBookChooser();

		workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
		// apply Book for Selected product
		workSpacebook.applyBookForSelectedProductSelectAll(product, AutoConfigPlannerWorkSpace.bookAug21, qtrName);
		workSpacebook.clickApply();

		
		//click weekly spots value
		planworkspace.clickWeeklySpotsValue(product, dayPart, 1);
		
		//remove units
		planworkspace.clickRemoveUnits();
		
		//get weekly spot of index 1
		String weeklyCellvalueAfterRemoveUnits =planworkspace.getWeeklySpotsIndexbased(product, dayPart, 1, 1);
		getCustomeVerification().verifyTrue(getSoftAssert(), weeklyCellvalueAfterRemoveUnits.equalsIgnoreCase("0"), "Weekly unit is not zero after removing units");
		
		// enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(product, dayPart, spotValue, 1);
		// Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//remove weekly spots units
		planworkspace.removeUnits(product, 1);

		
		//get weekly spots value
		String weeklyUnits[] =planworkspace.getWorkSpaceDataInWeeklySpots(product,dayPart, 1);
		
		for(int i=0;i<weeklyUnits.length;i++) {
			getCustomeVerification().verifyEquals(getSoftAssert(),weeklyUnits[i], "0","Weekly spot value are not zero after removing line units");
			}
		getCustomeVerification().assertAll(getSoftAssert());
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}

