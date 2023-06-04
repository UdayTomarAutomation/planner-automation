/**
 * 
 */
package com.operative.tests.spotlight;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.SpotLightDataProvider;
import com.mongodb.diagnostics.logging.Logger;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.spotLight.SpotLightPage;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("PAOS-127041:Spotlight Grid")
/**
 * @author tharun.kg this test case whether notifications icon is present and it
 *         changes the date separator in user settings and verifies it is
 *         getting reflected in week of column in grid
 */
public class PAOS_84925_C16747985_UpdateNotificationsAndUserSettingsComponentInAllServices_Spotlight extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();

	}

	@Severity(SeverityLevel.NORMAL)
	@Story("PAOS-84925:Update Notifications and User Settings component in all Services - Spotlight")
	@Test(dataProviderClass = SpotLightDataProvider.class, description = "C16747985", dataProvider = "applyDateSeperator")
	public void UpdateNotificationsAndUserSettingsComponentInAllServices_Spotlight(String slashOption, String dotOption,
			String hyphenOption, String slashSeperator, String dotSeperator, String hyphenSeperator,String dateSeperatorDropDown) {

		SpotLightPage spotLightPage = new SpotLightPage(getPageBrowser());
		BasePage getBasePage = new BasePage(getPageBrowser());

		String appliedSeperator = dotSeperator;

		// navigating to spotlight page
		spotLightPage.navigatetoSpotLight();

		// waiting for page to load by waiting for network request to stop
		spotLightPage.waitForPageLoadUsingNetwork();

		// seperator before changing date seperator in user settings
		String oldSeperator = spotLightPage.getDateSeperatorFromWeekColumn();

		// get displayed gid columns
		String[] gridFieldsBeforeRefresh = spotLightPage.getCheckedFilterDropdownOptions();

		// clicking on support operations
		spotLightPage.clickOnSupportOpeations();

		// user settings
		spotLightPage.clickOnUserSetings();
		spotLightPage.verifyUserSettingsIsVisible();

		// clicking on Date Seperator dropdown
		spotLightPage.clickOnCustomSettingsDropdown(dateSeperatorDropDown);

		// Select option from Date Seperator dropdown
		spotLightPage.selectOptionFromDateSeperatorDropdown(slashOption);

		// click apply on user settings
		spotLightPage.clickOnUseerSettingsApplyButton();

		// wait for refresh to complete
		spotLightPage.waitForPageLoadUsingNetwork();

		// refresh the page
		spotLightPage.refreshTheCurrentWebPage();

		// wait for refresh to complete
		spotLightPage.waitForPageLoadUsingNetwork();

		// fields in the grid after refresh
		String[] gridfieldsAfterRefresh = spotLightPage.getCheckedFilterDropdownOptions();

		// getting new date seperator from week of column in grid
		String newSeperator = spotLightPage.getDateSeperatorFromWeekColumn();

		// checking if old and new date seperator are different
		getCustomeVerification().verifyNotEquals(getSoftAssert(), newSeperator, oldSeperator, "seperator before '"
				+ oldSeperator + "' applying and after applying '" + newSeperator + "'user settings are not different");

		// checking if data seperator applied in user settigns and data serator in
		// grid are same
		getCustomeVerification().verifyEquals(getSoftAssert(), slashSeperator, newSeperator,
				"date seperator in user settings and grid are not same");

		// checking if fields in grid are same before and after refresh
		getCustomeVerification().verifyArrayEquals(getSoftAssert(), gridFieldsBeforeRefresh, gridfieldsAfterRefresh,
				"displayed fields ingrid are not ame before and after refresh");

		// Resetting the date seperator in user settings afer checking
		// clicking on support operations
		spotLightPage.clickOnSupportOpeations();

		// user settings
		spotLightPage.clickOnUserSetings();
		spotLightPage.verifyUserSettingsIsVisible();

		// clicking on Date Seperator dropdown
		spotLightPage.clickOnCustomSettingsDropdown(dateSeperatorDropDown);

		// Select option from Date Seperator dropdown
		spotLightPage.selectOptionFromDateSeperatorDropdown(dotOption);

		// click apply on user settings
		spotLightPage.clickOnUseerSettingsApplyButton();

		// wait for refresh to complete
		spotLightPage.waitForPageLoadUsingNetwork();
		
		// refresh page
		spotLightPage.refreshTheCurrentWebPage();
		
		// wait for refresh to complete
		spotLightPage.waitForPageLoadUsingNetwork();

		getCustomeVerification().assertAll(getSoftAssert());
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
