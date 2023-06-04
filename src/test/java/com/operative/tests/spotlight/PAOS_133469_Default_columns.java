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
 * @author tharun.kg this test cae is to check default columns
 *
 */
@Story("PAOS_133469:Verify Default Columns visible - Spotlight")
public class PAOS_133469_Default_columns extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = SpotLightDataProvider.class, description = "C16752645", dataProvider = "checkingDefaultColumns")
	public void navigateToSpotLightHomepage(String defaultColumns, String usnColumn, String commaSeperator) {

		String[] defaultColumnsArray = defaultColumns.split(commaSeperator);

		SpotLightPage spotLightPage = new SpotLightPage(getPageBrowser());

		// navigating to spotlight page
		spotLightPage.navigatetoSpotLight();

		spotLightPage.waitForPageLoadUsingNetwork();

		spotLightPage.resetGrid();

		spotLightPage.waitForPageLoadUsingNetwork();

		spotLightPage.makeGridEmpty();
		//
		String[] columnsInGridAfterMakingItEmpty = spotLightPage.getGridColumns();
		String columnAfterMakingGridEmpty = columnsInGridAfterMakingItEmpty[0];

		spotLightPage.resetGrid();

		spotLightPage.waitForPageLoadUsingNetwork();

		Logger.log("default columns before refresh");

		// getting the columns before
		String[] columnsInGridBeforeRefresh = spotLightPage.getGridColumns();

		spotLightPage.refreshTheGrid();

		// relaoding the current page
		// --navigate to task list comback and check
		spotLightPage.refreshTheCurrentWebPage();

		spotLightPage.waitForPageLoadUsingNetwork();

		// navigate to task list
		spotLightPage.navigateToTaskList();

		spotLightPage.waitForPageLoadUsingNetwork();

		// navigate back to spotlight
		spotLightPage.navigateToSpotlight();

		Logger.log("default columns after refresh");

		String[] columnsInGridAfterRefresh = spotLightPage.getGridColumns();

		getCustomeVerification().verifyEquals(getSoftAssert(), columnAfterMakingGridEmpty, usnColumn,
				"default columns and columns before refresh are not matching");

		getCustomeVerification().verifyArrayEquals(getSoftAssert(), columnsInGridBeforeRefresh, defaultColumnsArray,
				"default columns and columns before refresh are not matching");

		getCustomeVerification().verifyArrayEquals(getSoftAssert(), columnsInGridBeforeRefresh,
				columnsInGridAfterRefresh, "columns before and columns after refresh are not matching");

		getCustomeVerification().verifyArrayEquals(getSoftAssert(), columnsInGridAfterRefresh, defaultColumnsArray,
				"default columns and columns after refresh are not matching");

		getCustomeVerification().assertAll(getSoftAssert());
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
