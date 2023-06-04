package com.operative.tests.plannerHeader;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerHeaderDataprovider;
import com.operative.aos.configs.AutoConfigPlannerHeader;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;

/**
 * @author subhakanta.swain 1. create Plan Header 2. save deal 3. validate plan
 *         ID is present in master header 4. move to deallist page and verify
 *         dealid is visible 5. ApplyFilter for dealID 6. Verify Filtered dealID
 */
public class SingleFilterDealList extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerHeaderDataprovider.class, dataProvider = "PlanCreation")
	public void singleFilterDealList(String dealName, String PlanClass, String advName, String agencyName,
			String accountExecute, String calendarvalue, String startPeriodValue, String endPeriodValue,
			String dealYearValue, String marketPlaceValue, String flightvalue, String channelValue, String demovalue) {
		PlannerHeaderPage planHeader = new PlannerHeaderPage(getPageBrowser());
		// EnterPlanName
		final String planName = AutoConfigPlannerHeader.linearPlanName + getBasePage().getRandomNumber();
		// creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(planName, AutoConfigPlannerWorkSpace.planClass,
				advName, agencyName, accountExecute, calendarvalue, startPeriodValue, endPeriodValue, dealYearValue,
				marketPlaceValue, flightvalue, channelValue, demovalue);
		// save Plan Header
		planHeader.clickSaveHeader();
		// Get dealID from master header
		String dealId = planHeader.getAllMasterHeaderValue(AutoConfigPlannerHeader.masterHeaderDealID);
		String dealIDFilter = dealId.substring(9);
		// click Deal List
		planHeader.clickDealList();
		// verify DealID in Deal List
		boolean planId = planHeader.verifyDealIdInDealList(dealIDFilter);
		getCustomeVerification().verifyTrue(getSoftAssert(), planId, "DealID is not displaying from deal list page");
		// apply filter by DealID
		planHeader.filterBySingleSelectValue(AutoConfigPlannerHeader.dealIDFilter, dealIDFilter);
		// verify the deal ID after apply filter
		getCustomeVerification().verifyTrue(getSoftAssert(), planId,
				"DealID is not displaying from deal list page after appying filter");
		// Clear the applied filter
		planHeader.filterApplyOrClear(AutoConfigPlannerHeader.clear);
		// apply deal name filter
		planHeader.filterBySingleSelectValue(AutoConfigPlannerHeader.dealNameFilter, planName);
		boolean planNameAfterFilter = planHeader.verifyPlanNameInDealList(planName);
		// verify the deal name after apply filter
		getCustomeVerification().verifyTrue(getSoftAssert(), planNameAfterFilter,
				"DeaName is not displaying from deal list page after appying filter");
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
