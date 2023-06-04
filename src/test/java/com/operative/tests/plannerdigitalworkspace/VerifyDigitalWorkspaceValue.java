/**
 * 
 */
package com.operative.tests.plannerdigitalworkspace;

import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerDigitalWorkSpaceDataprovider;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.DigitalPlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerDigitalWorkspacePage;
import static com.operative.aos.configs.AutoConfigDigitalPlannerWorkSpace.digital_PlanName;
import static com.operative.aos.configs.AutoConfigDigitalPlannerWorkSpace.planClass_Digital;
import static com.operative.aos.configs.AutoConfigDigitalPlannerWorkSpace.quantityColId;

/**
 * @author ajay_bhave
 *
 */
public class VerifyDigitalWorkspaceValue extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerDigitalWorkSpaceDataprovider.class, dataProvider = "createDigitalPlan")
	public void createDigitalPlannerHeader(String advertiser, String agency, String AE, String calender,
			String startPeriod, String endPeriod, String products, String quantity) throws InterruptedException {
		DigitalPlannerHeaderPage digitalHeader = new DigitalPlannerHeaderPage(getPageBrowser());
		digitalHeader.createOnlyDigitalPlanner(digital_PlanName + "22319989", planClass_Digital, "",advertiser,
				agency, AE, calender, startPeriod, endPeriod, "");

		digitalHeader.clickSaveHeader();

		PlannerDigitalWorkspacePage digitalWorkspace = new PlannerDigitalWorkspacePage(getPageBrowser());
		digitalWorkspace.navigateToWorkSpaceTab();
		digitalWorkspace.addDigitalProduct(products);
		
		
		//enter quantity to lines in workspace
		digitalWorkspace.editDigitalProductLineTextField(quantityColId, quantity, 1);
		digitalWorkspace.editDigitalProductLineTextField(quantityColId, quantity, 2);
		digitalWorkspace.editDigitalProductLineTextField(quantityColId, quantity, 3);

		//save workspace
		digitalWorkspace.saveDigitalWorkspace();
		
		//get quantity value from the lines in workspace
		String valueForPrd1 = digitalWorkspace.getDigitalProductLineTextFieldvalue(quantityColId, 1);
		String valueForPrd2 = digitalWorkspace.getDigitalProductLineTextFieldvalue(quantityColId, 2);
		String valueForPrd3 = digitalWorkspace.getDigitalProductLineTextFieldvalue(quantityColId, 3);
		
		//verify the quantity with entered quantity in workspace
		getCustomeVerification().verifyEquals(getSoftAssert(), valueForPrd1, quantity);
		getCustomeVerification().verifyEquals(getSoftAssert(), valueForPrd2, quantity);
		getCustomeVerification().verifyEquals(getSoftAssert(), valueForPrd3, quantity);

		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
