package com.operative.tests.plannerHeader;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerHeaderDataprovider;
import com.operative.aos.configs.AutoConfigPlannerHeader;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;

/**
 * @author subhakanta.swain 1. create a deal and verify master header field
 *         after deal creation2. deal name 3. deal status 4. version 5. revision
 * 
 * 
 *
 */
public class VerifyMasterHeaderValues extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerHeaderDataprovider.class, dataProvider = "PlanCreationAllEnv")
	public void verifyMasterHeaderValues(String dealName, String planClass, String advName, String agencyName,
			String accountExecute, String calendarvalue, String startPeriodValue, String endPeriodValue,
			String dealYearValue, String marketPlaceValue, String flightvalue, String channelValue, String demovalue) {
		PlannerHeaderPage planHeader = new PlannerHeaderPage(getPageBrowser());
		// EnterPlanName
		final String planNameInput = AutoConfigPlannerHeader.linearPlanName + getBasePage().getRandomNumber();
		// creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(planNameInput, planClass, advName, agencyName,
				accountExecute, calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue,
				flightvalue, channelValue, demovalue);
		// save Plan Header
		new PlannerHeaderPage(getPageBrowser()).clickSaveHeader();
		String dealId = planHeader.getAllMasterHeaderValue(AutoConfigPlannerHeader.masterHeaderDealID);
		String dealIDFilter = dealId.substring(9);
		// click Deal List
		planHeader.clickDealList();
		// verify DealID in Deal List
		boolean planId = planHeader.verifyDealIdInDealList(dealIDFilter);
		getCustomeVerification().verifyTrue(getSoftAssert(), planId, "DealID is not displaying from deal list page");
		// apply filter by DealID
		planHeader.filterBySingleSelectValue(AutoConfigPlannerHeader.dealIDFilter, dealIDFilter);
		// click on filtered deal
		planHeader.clickOnFilteredDael(dealIDFilter);
		// verify dealID from master header
		planHeader.getAllMasterHeaderValue(AutoConfigPlannerHeader.masterHeaderDealID);
		// verify Deal version is displayed from master header after deal creation
		String masterHeaderVersion = planHeader.getAllMasterHeaderValue(AutoConfigPlannerHeader.masterHeaderVersion);
		getCustomeVerification().verifyTrue(getSoftAssert(),
				masterHeaderVersion.contains(AutoConfigPlannerHeader.dealcreateVersion),
				"New plan not created with Version V1");
		// verify Deal revision is displayed from master header after deal creation
		String masterHeaderRevision = planHeader.getAllMasterHeaderValue(AutoConfigPlannerHeader.masterHeaderRevision);
		getCustomeVerification().verifyTrue(getSoftAssert(), masterHeaderRevision.contains("0"),
				"New plan not created with Revision no 0");
		// verify Deal status is displayed from master header after deal creation
		String masterHeaderDealStatus = planHeader
				.getAllMasterHeaderValue(AutoConfigPlannerHeader.masterHeaderDealStatus);
		getCustomeVerification().verifyTrue(getSoftAssert(),
				masterHeaderDealStatus.contains(AutoConfigPlannerHeader.dealcreateStatus),
				"New plan status is other than Speculative");
		// Verify Deal name from master header
		String DealNameMasterHeader = planHeader.getNameFromMasterHeader();
		getCustomeVerification().verifyEquals(getSoftAssert(), DealNameMasterHeader, planNameInput,
				"Rating Stream value not present from header UI");
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}

}
