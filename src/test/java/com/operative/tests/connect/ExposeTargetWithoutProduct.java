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

import groovyjarjarantlr4.v4.codegen.model.SrcOp;

/**
 * @author SonaliKhakal
 *
 */
public class ExposeTargetWithoutProduct extends BaseTest {
	@BeforeTest
	public void beforeTest() throws InterruptedException {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();

	}

	@Test(dataProviderClass = ConnectDataprovider.class, dataProvider = "AOS_verifyExposedTargetwithoutProd")
	public void verifyExposedTargetwithoutProd(String tName, String topName) throws ParseException {

		ConnectMappingsPage connectMappingsPage = new ConnectMappingsPage(getPageBrowser());
		connectMappingsPage.navigateTomoduleSettings();
		connectMappingsPage.expandConnect();
		connectMappingsPage.navigateTomappingPage();
		connectMappingsPage.selectPartnerValue(AutoGlobalConstants.tenantName+" (Demand)");
		connectMappingsPage.navigateTospecificTab(AutoConfigConnect.tabExposedTarget);
		connectMappingsPage.clickOnSpecificButton(AutoConfigConnect.btnSelectTarget);
		connectMappingsPage.exposeTargetfromAOS(tName,topName);
		Assert.assertTrue(connectMappingsPage.filterAndVerifyExposedTarget(tName,topName, AutoConfigConnect.btnApply),"Exposed Target is present in Exposed Targets grid");
		Assert.assertTrue(connectMappingsPage.filterAndVerifyUnExposedTarget(tName,topName,AutoConfigConnect.btnClear, AutoConfigConnect.btnApply),"UnExposed Target is still present in Exposed Targets grid");
		

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}