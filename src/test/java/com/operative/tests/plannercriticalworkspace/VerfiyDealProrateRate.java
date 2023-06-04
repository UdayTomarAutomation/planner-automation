/**
 * 
 */
package com.operative.tests.plannercriticalworkspace;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;

/**
 * @author uday 
 *
 */
public class VerfiyDealProrateRate  extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuitePlannerData")
	public void DealProrateRate(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String RatingStream,String spotsvalue,String Ratecard,String Product1,String Product2,String Product3) throws InterruptedException
	{
		PlannerHeaderPage  planHeaderPage=new PlannerHeaderPage(getPageBrowser());
		//creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//Select Rating Stream
		planHeaderPage.selectRatingStream(RatingStream);
		//Add Ratecard
		planHeaderPage.selectRateCardName(Ratecard);
		//save Plan Header
		planHeaderPage.clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttribute, 
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(Product1).clickAddClose();
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(Product1,
				AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,1);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//get rate value in total qtr before prorate
		String grossRateValueQtrBeforeProrate=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
		String percentRCValueQtrBeforeProrate=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.percentRCQtr).replace("$", "").replace("$", "").replace("%", "");
		
		Logger.log("grossRateValueQtrBeforeProrate is==> "+grossRateValueQtrBeforeProrate+"\n percentRCValueQtrBeforeProrate is==>> "+percentRCValueQtrBeforeProrate);
		
		String prorateValue=String.valueOf(Double.valueOf(percentRCValueQtrBeforeProrate)*2);
		Logger.log("prorateValue is==> "+prorateValue);
		
		//click on line Prorate
		planworkspace.dealProrate(AutoConfigPlannerWorkSpace.dealProratepercentRC, prorateValue);
		Thread.sleep(5000);
		//get rate value in total qtr before prorate
		String grossRateValueQtrAfterProrate=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr).replace("$", "").replace(",", "");
		String percentRCValueQtrAfterProrate=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.percentRCQtr).replace("$", "").replace("$", "").replace("%", "");
		
		Logger.log("grossRateValueQtrAfterProrate is==> "+grossRateValueQtrAfterProrate+"\n percentRCValueQtrAfterProrate is==>> "+percentRCValueQtrAfterProrate);
		

		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(Double.valueOf(grossRateValueQtrBeforeProrate)*2)), getBasePage().
				format2Digi(grossRateValueQtrAfterProrate),"Gross Rate is not changed after prorate");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(Double.valueOf(percentRCValueQtrBeforeProrate)*2)), getBasePage().
				format2Digi(percentRCValueQtrAfterProrate),"%RC is not changed after prorate");
		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
