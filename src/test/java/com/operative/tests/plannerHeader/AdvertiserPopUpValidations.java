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
 * @author subhakanta.swain 1.create Plan Header 2. Verify the values from adv
 *         pop up for ADv and Category
 *         3. Verify the Category is auto populated 
 * 
 */
public class AdvertiserPopUpValidations extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerHeaderDataprovider.class, dataProvider = "PlanCreationAllEnv")
	public void advertiserPopUpValidations(String dealName, String PlanClass, String advName, String agencyName,
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
		// save Plan Header
		planHeader.clickSaveHeader();
		//open Adv pop up
		planHeader.clickAdvertiserdotsPopUp();
		//verify selected value for adv from pop up
		String advPopUpValue=planHeader.getAdvertiserValueFromWindowPopup(AutoConfigPlannerHeader.advValue);
		getCustomeVerification().verifyEquals(getSoftAssert(), advPopUpValue, advName,
				"Adv pop up value is diferent from adv header value");
		//Verify the advCategory is auto populated after deal save from pop up
		String advCategory=planHeader.getAdvertiserValueFromWindowPopup(AutoConfigPlannerHeader.advCategory);
		getCustomeVerification().verifyNotNull(getSoftAssert(), advCategory,
				"adv Category is not auto selected");
		
		getCustomeVerification().assertAll(getSoftAssert());
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
