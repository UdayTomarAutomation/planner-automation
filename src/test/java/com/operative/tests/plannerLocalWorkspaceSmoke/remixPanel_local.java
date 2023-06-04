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
 * @author uday madan
 *
 */
public class remixPanel_local  extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuiteRemixPlannerData_Local")
	public void remixpanel_Local(String planClass,String station,String flightDates,String flightStartDate,String flightEndDate,
			String advertiser,String buyingAgency,String accountExecutive,String calenderValue,String startPeriodValue,String endPeriodValue,String demo,String dayParts,String marketPlace,
			String ratingStream,String products,String ratecard, String spotValue,String lywRates,String lywCPMs) throws InterruptedException, ParseException
	{
		PlannerHeaderPage  planHeaderPage=new PlannerHeaderPage(getPageBrowser());
		BasePage getBasePage = new BasePage(getPageBrowser());
		final PlannerWorkBookPage workSpacebook = new PlannerWorkBookPage(getPageBrowser());
		
		String planName =  AutoConfigPlannerHeader.linearPlanName + getBasePage.getRandomNumber();
		String product[] = products.split(";");
		String dayPart[] = dayParts.split(";");
		String[] lywRate=lywRates.split(";");
		String[] lywCPM=lywCPMs.split(";");
		
		//creating Plan Header
		planHeaderPage.creationLocalPlanHeader(planName,planClass,advertiser,buyingAgency,accountExecutive, 
				calenderValue, startPeriodValue, endPeriodValue, marketPlace, flightDates, station,demo);
		//Select Rating Stream
		planHeaderPage.selectRatingStream(ratingStream);
		//Add Ratecard
		planHeaderPage.selectRateCardName(ratecard);
		//save Plan Header
		planHeaderPage.clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		//click product chooser tab
		// get all quarters and weeks
	    final List<String> generateQuarterlyValue =
	        new DateTimeFunctions().generateQuarterlySequence(flightStartDate, flightEndDate);
	    
	    Logger.log("Quarter values: " + generateQuarterlyValue.toString());

	    String qtrName = generateQuarterlyValue.get(0).substring(4);
		
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttributeLocal, 
				AutoConfigPlannerWorkSpace.lineClassAttributeLocal, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		planworkspace.addMultipleProductInChooserWindowIndexBased(product[0]+";"+product[1],"1").clickAddSelectedProductLocal();
		
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttributeLocal, 
				AutoConfigPlannerWorkSpace.lineClassAttributeLocal, AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate).clickApplyProductChooser();
		planworkspace.addMultipleProductInChooserWindowIndexBased(product[0]+";"+product[1],"1").clickAddSelectedProductLocal();
		
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(product[0],
				dayPart[0],spotValue,1,1);
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(product[1],
				dayPart[1],spotValue,2,1);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		workSpacebook.clickonBookChooser();

	    workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
	     
	    // apply Book for Selected product
	    workSpacebook.applyBookForMultipleProductSelectAll(product[0],"1",
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);
	    workSpacebook.clickBackButton().clickNext();
	    workSpacebook.applyBookForMultipleProductSelectAll(product[1],"2",
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);
	    workSpacebook.clickApply();
		
		//open remix pannel
		planworkspace.openRemixPannel();
		
		//Enter Rate and CPM for Last Year Weights
		planworkspace.enterLastYearWeights("1",dayPart[1],AutoConfigPlannerWorkSpace.lywRate,lywRate[0]);
		planworkspace.enterLastYearWeights("1",dayPart[1],AutoConfigPlannerWorkSpace.lywCpm,lywCPM[0]);
		planworkspace.enterLastYearWeights("2",dayPart[0],AutoConfigPlannerWorkSpace.lywRate,lywRate[1]);
		planworkspace.enterLastYearWeights("2",dayPart[0],AutoConfigPlannerWorkSpace.lywCpm,lywCPM[1]);
		
		String expectedlyImps000Daytime = String.valueOf(Double.valueOf(lywRate[0])/ Double.valueOf(lywCPM[0]));
		String expectedlyImps000Primetime = String.valueOf(Double.valueOf(lywRate[1])/ Double.valueOf(lywCPM[1]));
		
		//Get Imps Value from Remix section
		String lyImps000Daytime=planworkspace.getLastAndCurrentYearWeights("1",dayPart[1],AutoConfigPlannerWorkSpace.lywImps).replace(",", "").replace("$", "");
		String lyImps000Primetime=planworkspace.getLastAndCurrentYearWeights("2",dayPart[0],AutoConfigPlannerWorkSpace.lywImps).replace(",", "").replace("$", "");
		
		//Compaire actual and expected Imps000 for dayparts in Remix pannel
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lyImps000Daytime), getBasePage().
				format2Digi(String.valueOf(expectedlyImps000Daytime)),"Last Year imps value for Daytime daypart is not matching");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lyImps000Primetime), getBasePage().
				format2Digi(String.valueOf(expectedlyImps000Primetime)),"Last Year imps value for primetime daypart is not matching");
		
		//Get Current Year Weights
		String cyImps000Daytime=planworkspace.getLastAndCurrentYearWeights("1",dayPart[1],AutoConfigPlannerWorkSpace.cywImps).replace(",", "").replace("$", "");
		String cyImps000Primetime=planworkspace.getLastAndCurrentYearWeights("2",dayPart[0],AutoConfigPlannerWorkSpace.cywImps).replace(",", "").replace("$", "");
		
		String cyRateDaytime=planworkspace.getLastAndCurrentYearWeights("1",dayPart[1],AutoConfigPlannerWorkSpace.cywRate).replace(",", "").replace("$", "");
		String cyRatePrimetime=planworkspace.getLastAndCurrentYearWeights("2",dayPart[0],AutoConfigPlannerWorkSpace.cywRate).replace(",", "").replace("$", "");
		
		String cyCPMDaytime=planworkspace.getLastAndCurrentYearWeights("1",dayPart[1],AutoConfigPlannerWorkSpace.cywCpm).replace(",", "").replace("$", "");
		String cyCPMPrimetime=planworkspace.getLastAndCurrentYearWeights("2",dayPart[0],AutoConfigPlannerWorkSpace.cywCpm).replace(",", "").replace("$", "");
		
		//Compaire current and last Year values in Remix pannel
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lyImps000Daytime), getBasePage().
				format2Digi(String.valueOf(cyImps000Daytime)),"Current and Last Year imps value for Daytime daypart is not matching");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lyImps000Primetime), getBasePage().
				format2Digi(String.valueOf(cyImps000Primetime)),"Current and Last Year imps value for Primetime daypart is not matching");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lywRate[0]), getBasePage().
				format2Digi(String.valueOf(cyRateDaytime)),"Current and Last Year rate value for Daytime daypart is not matching");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lywRate[1]), getBasePage().
				format2Digi(String.valueOf(cyRatePrimetime)),"Current and Last Year rate value for Primetime daypart is not matching");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lywCPM[0]), getBasePage().
				format2Digi(String.valueOf(cyCPMDaytime)),"Current and Last Year cpm value for Daytime daypart is not matching");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(lywCPM[1]), getBasePage().
				format2Digi(String.valueOf(cyCPMPrimetime)),"Current and Last Year cpm value for Primetime daypart is not matching");
		
				
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
