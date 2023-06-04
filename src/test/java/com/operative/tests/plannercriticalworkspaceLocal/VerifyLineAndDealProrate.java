/**
 * 
 */
package com.operative.tests.plannercriticalworkspaceLocal;

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
 * @author uday
 *
 */
public class VerifyLineAndDealProrate extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerWorkSpaceDataprovider.class, dataProvider = "localSmokeSuitePlannerData")
	public void verifyLineAndDealProrate(String planClass,String station,String flightDates,String flightStartDate,String flightEndDate,
			String advertiser,String buyingAgency,String accountExecutive,String calenderValue,String startPeriodValue,String endPeriodValue,String demo,String dayPart,String marketPlace,
			String ratingStream,String product,String ratecard, String spotValue)
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
		//Add Ratecard
		planHeaderPage.selectRateCardName(ratecard);
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
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(product, dayPart, spotValue, 1);
		// Save Workspace
		planworkspace.clickSaveWorkSpace();

		workSpacebook.clickonBookChooser();

		workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
		// apply Book for Selected product
		workSpacebook.applyBookForSelectedProductSelectAll(product, AutoConfigPlannerWorkSpace.bookAug21, qtrName);
		workSpacebook.clickApply();
		
		//get rate value in total qtr before prorate
		String grossRateValueQtrBeforeProrate=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
		String percentRCValueQtrBeforeProrate=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentRCQtr).replace("$", "").replace("$", "").replace("%", "");
		
		Logger.log("grossRateValueQtrBeforeProrate is==> "+grossRateValueQtrBeforeProrate+"\n percentRCValueQtrBeforeProrate is==>> "+percentRCValueQtrBeforeProrate);
		
		String prorateValue=String.valueOf(Double.valueOf(percentRCValueQtrBeforeProrate)*2);
		Logger.log("prorateValue is==> "+prorateValue);
		
		//click on deal Prorate
		planworkspace.dealProrate(AutoConfigPlannerWorkSpace.dealProratepercentRC, prorateValue);

		//get rate value in total qtr before prorate
		String grossRateValueQtrAfterProrate=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
		String percentRCValueQtrAfterProrate=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.percentRCQtr).replace("$", "").replace("$", "").replace("%", "");
		
		Logger.log("grossRateValueQtrAfterProrate is==> "+grossRateValueQtrAfterProrate+"\n percentRCValueQtrAfterProrate is==>> "+percentRCValueQtrAfterProrate);
		

		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(Double.valueOf(grossRateValueQtrBeforeProrate)*2)), getBasePage().
				format2Digi(grossRateValueQtrAfterProrate),"Gross Rate is not changed after prorate");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(Double.valueOf(percentRCValueQtrBeforeProrate)*2)), getBasePage().
				format2Digi(percentRCValueQtrAfterProrate),"%RC is not changed after prorate");
		
		planworkspace.clicklineProrate(product, 1);
		
		planworkspace.enterlineProrateValue(AutoConfigPlannerWorkSpace.ratetotalQtr, String.valueOf(Double.valueOf(percentRCValueQtrBeforeProrate)*4));

		//get gross unit rate
		String grossRateValueAfterLineProrate=planworkspace.getAggregateTotalValueQtr(product, 
				dayPart, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueAfterLineProrate), getBasePage().
				format2Digi(String.valueOf(Double.valueOf(percentRCValueQtrBeforeProrate)*4*Integer.valueOf(spotValue))),"Gross Rate (A) is not changed after prorate");
		
		getCustomeVerification().assertAll(getSoftAssert());
	}
	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
	
}
