/**
 * 
 */
package com.operative.tests.connect;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.dataprovider.ConnectDataprovider;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.operative.aos.configs.AutoConfigConnect;
import com.operative.aos.configs.AutoConfigDigitalPlannerWorkSpace;
import com.operative.aos.configs.AutoConfigPlannerHeader;
import com.operative.aos.configs.AutoGlobalConstants;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.connect.ConnectDemandOrderPage;
import com.operative.pages.connect.ConnectSupplyOrderPage;
import com.operative.pages.connectAOS.ConnectMappingsPage;
import com.operative.pages.o1sales.MediaPlanPage;
import com.operative.pages.o1sales.SalesOrderAdOpsPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerDigitalWorkspacePage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;

/**
 * @author akrathi
 *
 */
public class ConnectExposedProduct extends BaseTest {
	@BeforeTest
	public void beforeTest() throws InterruptedException {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();

	}

	@Test(dataProviderClass = ConnectDataprovider.class, dataProvider = "AOS_VerifySupplyExposeProducttoProductGrid")
	public void verifyExposedProduct(String productName) throws ParseException {
		
		ConnectMappingsPage connectMappingsPage = new ConnectMappingsPage(getPageBrowser());
		connectMappingsPage.navigateTomoduleSettings();
		connectMappingsPage.expandConnect();
		connectMappingsPage.navigateTomappingPage();
		
		connectMappingsPage.selectPartnerValue(AutoGlobalConstants.tenantName+" (Demand)");
		connectMappingsPage.navigateTospecificTab(AutoConfigConnect.tabExposedProduct);
		connectMappingsPage.clickOnSpecificButton(AutoConfigConnect.btnSelectProduct);
		connectMappingsPage.exposeProductfromAOS(productName);
		Assert.assertTrue(connectMappingsPage.filterAndVerifyExposedProduct(productName, AutoConfigConnect.btnApply),"Exposed product is present in Exposed product grid");
		Assert.assertTrue(connectMappingsPage.filterAndVerifyUnExposedProduct(productName,AutoConfigConnect.btnClear, AutoConfigConnect.btnApply),"UnExposed product is still present in Exposed product grid");
		
					}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
