package com.operative.tests.plannerHeader;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerHeaderDataprovider;
import com.operative.aos.configs.AutoConfigPlannerHeader;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;

/**
 * @author subhakanta.swain 1.create Plan Header 2.Add rating stream from header
 *         3. Click on Rating Stream Pop up 4. Verify the header level RS from
 *         pop up
 *         
 */
public class RatingStreamValidationfromPopUpAddedByHeader extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerHeaderDataprovider.class, dataProvider = "PlanCreationAllEnv")
	public void ratingStreamValidationfromPopUpAddedByHeader(String dealName, String PlanClass, String advName, String agencyName,
			String accountExecute, String calendarvalue, String startPeriodValue, String endPeriodValue,
			String dealYearValue, String marketPlaceValue, String flightvalue, String channelValue, String demovalue)
			throws InterruptedException {
		PlannerHeaderPage planHeader = new PlannerHeaderPage(getPageBrowser());
		// EnterPlanName
		final String planName = AutoConfigPlannerHeader.linearPlanName + getBasePage().getRandomNumber();
		// creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(planName, AutoConfigPlannerWorkSpace.planClass,
				advName, agencyName, accountExecute, calendarvalue, startPeriodValue, endPeriodValue, dealYearValue,
				marketPlaceValue, flightvalue, channelValue, demovalue);
		// Add rs from deal header page
		planHeader.selectRatingStream(AutoConfigPlannerHeader.rsValue);
		//Add division
		planHeader.selectDivision_SubDivision(AutoConfigPlannerHeader.divDropDown,AutoConfigPlannerHeader.divValue);
		//Add Sub division
		planHeader.selectDivision_SubDivision(AutoConfigPlannerHeader.subDivDropDown,AutoConfigPlannerHeader.subDivValue);
		// save Plan Header
		planHeader.clickSaveHeader();
		// Click on Rating Stream Pop up
		planHeader.clickRatingStreamDotsPopUp();
		// Get the header level Rating stream value from RS pop up
		String rsHeaderValuePopUp = planHeader.getRsHeaderLevelsValuesSelectedPopUp();
		// Verify header level Rating stream  value from pop up after deal modification
		getCustomeVerification().verifyEquals(getSoftAssert(), rsHeaderValuePopUp, AutoConfigPlannerHeader.rsValue,
				"header level Rating Stream value not present from RS pop up");

		getCustomeVerification().assertAll(getSoftAssert());
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}