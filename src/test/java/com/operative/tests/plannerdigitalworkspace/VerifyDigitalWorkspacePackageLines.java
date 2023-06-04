package com.operative.tests.plannerdigitalworkspace;

import static com.operative.aos.configs.AutoConfigDigitalPlannerWorkSpace.digital_PlanName;
import static com.operative.aos.configs.AutoConfigDigitalPlannerWorkSpace.planClass_Digital;

import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerDigitalWorkSpaceDataprovider;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.DigitalPlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerDigitalWorkspacePage;

/**
 * @author ajay_bhave
 *
 */
public class VerifyDigitalWorkspacePackageLines extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerDigitalWorkSpaceDataprovider.class, dataProvider = "DigitalWorkspacePackage")
	public void createDigitalPlannerHeader(String advertiser, String agency, String AE, String calender,
			String startPeriod, String endPeriod, String products, String quantity) throws InterruptedException {
		
		DigitalPlannerHeaderPage digitalHeader = new DigitalPlannerHeaderPage(getPageBrowser());
		digitalHeader.createOnlyDigitalPlanner(digital_PlanName + "10012123", planClass_Digital, "", advertiser,
				agency, AE, calender, startPeriod, endPeriod, "");
		String [] productNames = products.split(";");
		digitalHeader.clickSaveHeader();

		PlannerDigitalWorkspacePage digitalWorkspace = new PlannerDigitalWorkspacePage(getPageBrowser());
		digitalWorkspace.navigateToWorkSpaceTab();
		digitalWorkspace.addPackageInChooserDigital(productNames[0]);
	
		String lineNumber = digitalWorkspace.getProductLineNo(productNames[0]);
		Logger.log("line number of package " + lineNumber);

		digitalWorkspace.expandPackageLineItem(lineNumber);
		List<String> childLines = digitalWorkspace.getPackageChildLineList(lineNumber);
		Logger.log(childLines + "");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), childLines.get(0),productNames[1],"child line value mismatch");
		getCustomeVerification().verifyEquals(getSoftAssert(), childLines.get(1), productNames[2],"child lines value mismatch");
		
		getCustomeVerification().assertAll(getSoftAssert());
		
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
