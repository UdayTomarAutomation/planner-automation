package com.operative.tests.plannerHeader;

/**
 * @author subhakanta.swain 1.create Plan Header 2. add all fields from pop up
 * 3. Verify fields after save from pop up
 * 
 */
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerHeaderDataprovider;
import com.operative.aos.configs.AutoConfigPlannerHeader;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;

public class AdvPopUpValidationsModifyDeal extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerHeaderDataprovider.class, dataProvider = "PlanCreationAllEnv")
	public void advPopUpValidationsModifyDeal(String dealName, String PlanClass, String advName, String agencyName,
			String accountExecute, String calendarvalue, String startPeriodValue, String endPeriodValue,
			String dealYearValue, String marketPlaceValue, String flightvalue, String channelValue, String demovalue)
			throws InterruptedException {
		PlannerHeaderPage planHeader = new PlannerHeaderPage(getPageBrowser());
		// EnterPlanName
		final String planName = AutoConfigPlannerHeader.linearPlanName + getBasePage().getRandomNumber();
		// creating Plan Header.
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(planName, AutoConfigPlannerWorkSpace.planClass,
				advName, agencyName, accountExecute, calendarvalue, startPeriodValue, endPeriodValue, dealYearValue,
				marketPlaceValue, flightvalue, channelValue, demovalue);
		// save Plan Header
		planHeader.clickSaveHeader();
		// open Adv pop up
		planHeader.clickAdvertiserdotsPopUp();
		// Add sec cat value
		planHeader.selectAdvertiserValueFromWindowPopup(AutoConfigPlannerHeader.secondaryCategory,
				AutoConfigPlannerHeader.secondaryCategoryValue);
		// Add brand
		planHeader.selectAdvertiserValueFromWindowPopup(AutoConfigPlannerHeader.defaultBrand,
				AutoConfigPlannerHeader.defaultBrandVaue);
		// Save adv pop up
		planHeader.clicksaveAdvertiserPopupWindow();
		// save Plan Header
		planHeader.clickSaveHeader();
		// open Adv pop up
		planHeader.clickAdvertiserdotsPopUp();
		// verify adv pop up values
		//verify secondaryCategoryValue
		String advSecCategoryPopUpValue=planHeader.getAdvertiserValueFromWindowPopup(AutoConfigPlannerHeader.advSecCategory);
		getCustomeVerification().verifyEquals(getSoftAssert(), advSecCategoryPopUpValue,
				AutoConfigPlannerHeader.secondaryCategoryValue,"Adv advSecCategoryPopUpValue is not matched ");
		//verify advBrandPopUpValue
		String advBrandPopUpValue=planHeader.getAdvertiserValueFromWindowPopup(AutoConfigPlannerHeader.advBrand);
		getCustomeVerification().verifyEquals(getSoftAssert(), advBrandPopUpValue,
				AutoConfigPlannerHeader.defaultBrandVaue,"Adv advBrandPopUpValue is not matched ");
		getCustomeVerification().assertAll(getSoftAssert());
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
