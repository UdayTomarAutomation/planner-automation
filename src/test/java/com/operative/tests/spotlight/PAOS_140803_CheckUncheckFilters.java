/**
 * 
 */
package com.operative.tests.spotlight;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.operative.pages.LoginPage;
import com.operative.pages.spotLight.SpotLightPage;

import io.qameta.allure.Story;

import com.dataprovider.SpotLightDataProvider;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;

/**
 * @author tharun.kg
 *this is to check whether checking uncecking filters is working in spotlight
 */
@Story(" PAOS_140803:filter api enhancement for remaining fields from the given epic - Spotlight")
public class  PAOS_140803_CheckUncheckFilters extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = SpotLightDataProvider.class, description = "C16832241", dataProvider = "checkUncheckFilters")
	public void navigateToSpotLightHomepage(String filterNames,String textFilters,String dropdownFilters, String commaSeperator) {
		String[] filtersList = filterNames.split(commaSeperator);
		
		String[] defaultTextFilters = textFilters.split(commaSeperator);
		
		String[] defaultDropdownFilters = dropdownFilters.split(commaSeperator);
		
		SpotLightPage spotLightPage = new SpotLightPage(getPageBrowser());

		// navigating to spotlight page
		spotLightPage.navigatetoSpotLight();
		
		spotLightPage.waitForPageLoadUsingNetwork();
		
		//clear if any filters are applied
		spotLightPage.clickFiltersClearButton();
		
		spotLightPage.clickOnFiltersDropdown();
		
		spotLightPage.enableFilters(filtersList);
		
		spotLightPage.clickOnFiltersDropdown();
		
		boolean textFiltersEnabledFlag = spotLightPage.checkTextFiltersArePresent(defaultTextFilters);
		
		boolean dropdownEnabledFiltersFlag = spotLightPage.checkDropdownFiltersArePresent(defaultDropdownFilters);	
		
		spotLightPage.clickFiltersClearButton();
		
		spotLightPage.clickOnFiltersDropdown();
		
		spotLightPage.disableFilters(filtersList);
		
		spotLightPage.clickOnFiltersDropdown();
		
		boolean textFiltersDisabledFlag = spotLightPage.checkTextFiltersArePresent(defaultTextFilters);
		
		boolean dropdownFiltersDisabledFlag = spotLightPage.checkDropdownFiltersArePresent(defaultDropdownFilters);
		
		getCustomeVerification().verifyEquals(getSoftAssert(), textFiltersEnabledFlag, true, "some text filters are not enabled");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), dropdownEnabledFiltersFlag, true, "some dropdown filters are not enabled");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), textFiltersDisabledFlag, false, "some text filters are not disabled");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), dropdownFiltersDisabledFlag, false, "some dropdown filters are not disabled");
		
		getCustomeVerification().assertAll(getSoftAssert());
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
