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
 * @author subhakanta.swain
 * 
 *         1. create Plan Header 2.Verify RS kebab is present 3. Add RS value
 *         from deal header and save it 4. Verify RS from deal header
 */
public class RatingStreamDealHeaderValidations extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerHeaderDataprovider.class, dataProvider = "PlanCreationAllEnv")
	public void ratingStreamDealHeaderValidations(String dealName, String PlanClass, String advName, String agencyName,
			String accountExecute, String calendarvalue, String startPeriodValue, String endPeriodValue,
			String dealYearValue, String marketPlaceValue, String flightvalue, String channelValue, String demovalue) {
		PlannerHeaderPage planHeader = new PlannerHeaderPage(getPageBrowser());
		// EnterPlanName
		final String planName = AutoConfigPlannerHeader.linearPlanName + getBasePage().getRandomNumber();
		// creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(planName, AutoConfigPlannerWorkSpace.planClass,
				advName, agencyName, accountExecute, calendarvalue, startPeriodValue, endPeriodValue, dealYearValue,
				marketPlaceValue, flightvalue, channelValue, demovalue);
		// save Plan Header
		planHeader.clickSaveHeader();
		// Verify Rating stream kebab icon not present
		getCustomeVerification().verifyTrue(getSoftAssert(), planHeader.isRatingStreamKebabPresent(),
				"Rating Stream Kebab Icon not present from UI");
		// Add rs from deal header page
		planHeader.selectRatingStream(AutoConfigPlannerHeader.rsValue);
		// save Plan Header
		planHeader.clickSaveHeader();
		// Get the Rating stream value from header UI
		String rsHeaderValue = planHeader.getSelectedValueFromPlanheader(AutoConfigPlannerHeader.ratingstramHeader);
		// Verify Rating the header rs value after deal modification
		getCustomeVerification().verifyEquals(getSoftAssert(), rsHeaderValue, AutoConfigPlannerHeader.rsValue,
				"Rating Stream value not present from header UI");
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
