/**
 * 
 */
package com.operative.tests.plannerLocalWorkspaceSmoke;

import java.text.ParseException;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
import com.operative.aos.configs.AutoConfigPlannerHeader;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.DateTimeFunctions;
import com.operative.pages.plannerworkspace.PlannerWorkBookPage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;

/**
 * @author mansi_vyas
 *
 */
public class VerifyEditLine extends BaseTest{

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerWorkSpaceDataprovider.class, dataProvider = "localSmokeSuitePlannerLines")
	public void verifyEditLine(String planClass, String station,
			String flightDates, String flightStartDate, String flightEndDate, String advertiser, String buyingAgency,
			String accountExecutive, String calenderValue, String startPeriodValue, String endPeriodValue, String demo,
			String dayPart, String marketPlace, String ratingStream, String product, String spotValue)
			throws ParseException, InterruptedException {
		PlannerHeaderPage planHeaderPage = new PlannerHeaderPage(getPageBrowser());
		BasePage getBasePage = new BasePage(getPageBrowser());
		final PlannerWorkBookPage workSpacebook = new PlannerWorkBookPage(getPageBrowser());

		String planName = AutoConfigPlannerHeader.linearPlanName + getBasePage.getRandomNumber();

		// creating Plan Header
		planHeaderPage.creationLocalPlanHeader(planName, planClass, advertiser, buyingAgency, accountExecutive,
				calenderValue, startPeriodValue, endPeriodValue, marketPlace, flightDates, station, demo);
		// Select Rating Stream
		planHeaderPage.selectRatingStream(ratingStream);

		planHeaderPage.clickSaveHeader();
		// navigate to WorkSpace
		PlannerWorkSpacePage planworkspace = new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();

		// get all quarters and weeks
		final List<String> generateQuarterlyValue = new DateTimeFunctions().generateQuarterlySequence(flightStartDate,
				flightEndDate);

		Logger.log("Quarter values: " + generateQuarterlyValue.toString());

		String qtrName = generateQuarterlyValue.get(0).substring(4);

		// click product chooser tab
		planworkspace.clickProductChooserTab();
		// select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttributeLocal,
				AutoConfigPlannerWorkSpace.lineClassAttributeLocal, AutoConfigPlannerWorkSpace.spotLengthAttribute)
				.clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(product).clickAddSelectedProductLocal();
		
		// enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(product, dayPart, spotValue, 2);
		// Save Workspace
		planworkspace.clickSaveWorkSpace();

		workSpacebook.clickonBookChooser();

		workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
		// apply Book for Selected product
		workSpacebook.applyBookForSelectedProductSelectAll(product, AutoConfigPlannerWorkSpace.bookAug21, qtrName);

		workSpacebook.clickApply();

		
		try {
			//Select Line Info filter
			planworkspace.lineInfoFilterSelectAll();
			//get Line info value
			String lineclassValue=planworkspace.verifyLineInfo(product, "1", AutoConfigPlannerWorkSpace.lineClassLineInfo);
			String commercialValue=planworkspace.verifyLineInfo(product, "1", AutoConfigPlannerWorkSpace.commercialTypeInfo);
			String spotlengthValue=planworkspace.verifyLineInfo(product, "1", AutoConfigPlannerWorkSpace.spotLengthInfo);
			getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.lineClassAttributeLocal, lineclassValue,"line class value not same");
			getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.commTypeAttributeLocal, commercialValue,"comm type  not same");
			getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.spotLengthAttribute, spotlengthValue,"spotLength value not same");
			
			planworkspace.editLines(product,1,AutoConfigPlannerWorkSpace.commTypeAttributeLocalupdated,AutoConfigPlannerWorkSpace.lineClassAttributeLocalupdated,AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate);
			planworkspace.editLinesWarningmessage();
			
			//get Line info value
			String lineclassValueUpdated=planworkspace.verifyLineInfo(product, "1", AutoConfigPlannerWorkSpace.lineClassLineInfo);
			String commercialValueUpdated=planworkspace.verifyLineInfo(product, "1", AutoConfigPlannerWorkSpace.commercialTypeInfo);
			String spotlengthValueUpdated=planworkspace.verifyLineInfo(product, "1", AutoConfigPlannerWorkSpace.spotLengthInfo);
			getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.lineClassAttributeLocalupdated, lineclassValueUpdated,"line class value is not edited");
			getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.commTypeAttributeLocalupdated, commercialValueUpdated,"comm type  is not edited");
			getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate, spotlengthValueUpdated,"spotLength value is not edited");
			
			getCustomeVerification().assertAll(getSoftAssert());
			}
			
			finally {
			//Deselect all matrics from line info
			planworkspace.lineInfoFilterDeSelectAll();
			}
	}
	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
