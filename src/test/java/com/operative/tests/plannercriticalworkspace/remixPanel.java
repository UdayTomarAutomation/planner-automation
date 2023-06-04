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
public class remixPanel  extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuiteRemixPlannerData")
	public void remixpanel(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String RatingStream,String spotsvalue,String Ratecard,String Product1,String Product2,String Product3, String lywRates,String lywCPMs) throws InterruptedException
	{
		PlannerHeaderPage  planHeaderPage=new PlannerHeaderPage(getPageBrowser());
		String[] lywRate=lywRates.split(";");
		String[] lywCPM=lywCPMs.split(";");
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
		
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttribute, 
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(Product3).clickAddClose();
	
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,1,1);
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(Product3,AutoConfigPlannerWorkSpace.dayPartDisplayWeekend,spotsvalue,2,1);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//open remix pannel
		planworkspace.openRemixPannel();
		
		//Enter Rate and CPM for Last Year Weights
		planworkspace.enterLastYearWeights("1",AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.lywRate,lywRate[0]);
		planworkspace.enterLastYearWeights("1",AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.lywCpm,lywCPM[0]);
		planworkspace.enterLastYearWeights("2",AutoConfigPlannerWorkSpace.dayPartDisplayWeekend,AutoConfigPlannerWorkSpace.lywRate,lywRate[1]);
		planworkspace.enterLastYearWeights("2",AutoConfigPlannerWorkSpace.dayPartDisplayWeekend,AutoConfigPlannerWorkSpace.lywCpm,lywCPM[1]);
		
		String expectedlyImps000Prime = String.valueOf(Double.valueOf(lywRate[0])/ Double.valueOf(lywCPM[0]));
		String expectedlyImps000Weekend = String.valueOf(Double.valueOf(lywRate[1])/ Double.valueOf(lywCPM[1]));
		
		//Get Imps Value from Remix section
		String lyImps000Prime=planworkspace.getLastAndCurrentYearWeights("1",AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.lywImps).replace(",", "").replace("$", "");
		String lyImps000Weekend=planworkspace.getLastAndCurrentYearWeights("2",AutoConfigPlannerWorkSpace.dayPartDisplayWeekend,AutoConfigPlannerWorkSpace.lywImps).replace(",", "").replace("$", "");
		
		//Compaire actual and expected Imps000 for dayparts in Remix pannel
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lyImps000Prime), getBasePage().
				format2Digi(String.valueOf(expectedlyImps000Prime)),"Last Year imps value for Prime daypart is not matching");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lyImps000Weekend), getBasePage().
				format2Digi(String.valueOf(expectedlyImps000Weekend)),"Last Year imps value for weekend daypart is not matching");
		
		//Get Current Year Weights
		String cyImps000Prime=planworkspace.getLastAndCurrentYearWeights("1",AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.cywImps).replace(",", "").replace("$", "");
		String cyImps000Weekend=planworkspace.getLastAndCurrentYearWeights("2",AutoConfigPlannerWorkSpace.dayPartDisplayWeekend,AutoConfigPlannerWorkSpace.cywImps).replace(",", "").replace("$", "");
		
		String cyRatePrime=planworkspace.getLastAndCurrentYearWeights("1",AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.cywRate).replace(",", "").replace("$", "");
		String cyRateWeekend=planworkspace.getLastAndCurrentYearWeights("2",AutoConfigPlannerWorkSpace.dayPartDisplayWeekend,AutoConfigPlannerWorkSpace.cywRate).replace(",", "").replace("$", "");
		
		String cyCPMPrime=planworkspace.getLastAndCurrentYearWeights("1",AutoConfigPlannerWorkSpace.dayPartDisplay,AutoConfigPlannerWorkSpace.cywCpm).replace(",", "").replace("$", "");
		String cyCPMWeekend=planworkspace.getLastAndCurrentYearWeights("2",AutoConfigPlannerWorkSpace.dayPartDisplayWeekend,AutoConfigPlannerWorkSpace.cywCpm).replace(",", "").replace("$", "");
		
		//Compaire current and last Year values in Remix pannel
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lyImps000Prime), getBasePage().
				format2Digi(String.valueOf(cyImps000Prime)),"Current and Last Year imps value for Prime daypart is not matching");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lyImps000Weekend), getBasePage().
				format2Digi(String.valueOf(cyImps000Weekend)),"Current and Last Year imps value for weekend daypart is not matching");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lywRate[0]), getBasePage().
				format2Digi(String.valueOf(cyRatePrime)),"Current and Last Year rate value for Prime daypart is not matching");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lywRate[1]), getBasePage().
				format2Digi(String.valueOf(cyRateWeekend)),"Current and Last Year rate value for weekend daypart is not matching");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lywCPM[0]), getBasePage().
				format2Digi(String.valueOf(cyCPMPrime)),"Current and Last Year cpm value for Prime daypart is not matching");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lywCPM[1]), getBasePage().
				format2Digi(String.valueOf(cyCPMWeekend)),"Current and Last Year cpm value for weekend daypart is not matching");
		
				
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
