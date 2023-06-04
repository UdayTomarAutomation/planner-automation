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
 * @author subhakanta.swain 1.create Plan Header 2.Add rating stream value from
 *         pop up for header level and channel level.
 * 
 */
public class AddAllValueFromRatingStreamPopUp extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerHeaderDataprovider.class, dataProvider = "PlanCreationAllEnv")
	public void addAllValueFromRatingStreamPopUp(String dealName, String PlanClass, String advName, String agencyName,
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
		// Add division
		planHeader.selectDivision_SubDivision(AutoConfigPlannerHeader.divDropDown, AutoConfigPlannerHeader.divValue);
		// Add Sub division
		planHeader.selectDivision_SubDivision(AutoConfigPlannerHeader.subDivDropDown,
				AutoConfigPlannerHeader.subDivValue);
		// save Plan Header
		planHeader.clickSaveHeader();
		// Click on Rating Stream Pop up
		planHeader.clickRatingStreamDotsPopUp();
		// Select header level RS from RS pop up
		planHeader.selectRatingStreamValueFromWindowPopup(AutoConfigPlannerHeader.rsValue, "", "", "", "");
		// Add on channel level RS
		planHeader.selectRatingStreamValueFromWindowPopup("", channelValue, AutoConfigPlannerHeader.rsValue, "", "");
		// Click on Apply
		planHeader.cliCKOnApplyFromRSpopUp();
		// save the deal
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
