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
 *this test case is to check whether all columns are present in grid
 */
@Story("PAOS_134397:Add the new enabled attributes under sales attributes in UI - Spotlight")
public class PAOS_134397_VerifyAllColumnsPresentInGrid extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test( dataProviderClass = SpotLightDataProvider.class, description = "C16754662", dataProvider = "allColumnsInGrid")
	public void navigateToSpotLightHomepage(String columnNames, String commaSeperator) {
		
		String [] allColumns = columnNames.split(commaSeperator);
		SpotLightPage spotLightPage = new SpotLightPage(getPageBrowser());
		System.out.println(allColumns.length);
		// navigating to spotlight page
		spotLightPage.navigatetoSpotLight();
		
		spotLightPage.waitForPageLoadUsingNetwork();

		
		spotLightPage.clickOnSelectColumnDropdown();
		
		spotLightPage.enableAllColumnsInGrid();
		
		Logger.log("enabled all columns");
		
		String[] columnspresentInGrid =spotLightPage.getGridColumns();
		
		getCustomeVerification().verifyArrayEquals(getSoftAssert(), columnspresentInGrid, allColumns,
				"some columns are missing");
		getCustomeVerification().assertAll(getSoftAssert());
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
