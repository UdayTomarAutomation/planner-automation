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


/**
 * @author tharun.kg
 *this is to check default filters are present in spotlight
 */
@Story("PAOS_139147:Add all the filter and enable default Filters - UI")
public class PAOS_139147_DefaultFilters extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = SpotLightDataProvider.class, description = "C16785443", dataProvider = "defaultFilters")
	public void navigateToSpotLightHomepage(String textFilters,String dropdownFilters, String commaSeperator) {
		String[] defaultTextFilters = textFilters.split(commaSeperator);
		String[] defaultDropdownFilters = dropdownFilters.split(commaSeperator);
		
		SpotLightPage spotLightPage = new SpotLightPage(getPageBrowser());

		// navigating to spotlight page
		spotLightPage.navigatetoSpotLight();
		
		spotLightPage.waitForPageLoadUsingNetwork();
		
		//clear if any filters are applied
		spotLightPage.clickFiltersClearButton();
		
		boolean textFiltersFlag = spotLightPage.checkTextFiltersArePresent(defaultTextFilters);
		
		boolean dropdownFiltersFlag = spotLightPage.checkDropdownFiltersArePresent(defaultDropdownFilters);
		
		getCustomeVerification().verifyEquals(getSoftAssert(), textFiltersFlag, true, "some text filters are not enabled");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), dropdownFiltersFlag, true, "some dropdown filters are not enabled");
		
		getCustomeVerification().assertAll(getSoftAssert());
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
