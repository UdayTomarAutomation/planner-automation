package com.operative.tests.plannercriticalworkspace;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
import com.google.common.base.Joiner;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.DateTimeFunctions;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;
/**
 * @author Uday 
 *
 */


public class  GroupingLatestTwoLevelRemoveUnitAndCheckPinnedRowCalculationForIndividualGrouping_IncludingPlanTotal extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuitePlannerData")
	public void QtrUpdateGrossRate000GrossCPMAndcheckQtyWeeklyCalculationplanTotalAndUpdate(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String RatingStream,String spotsvalue,String Ratecard,String Product1,String Product2,String Product3) throws ParseException, InterruptedException {
		PlannerHeaderPage  planHeaderPage=new PlannerHeaderPage(getPageBrowser());
		//creating Plan Header
		planHeaderPage.creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
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
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate).clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(Product2).clickAddClose();
	
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,1,1);
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(Product2,AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,2,1);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//click weekly spots value for Product 1
		planworkspace.clickWeeklySpotsValueBasedOnRowId(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay,1, 1);
		
		//Get Cell Property Rate Value Product 1
		String cellRatePrd1=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value Product 1
		String cellImpsPrd1=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		Logger.log("Cell Rate for Product 1 is: "+cellRatePrd1+"\n Cell imps for Product 1 is:"+cellImpsPrd1);
		
		//click weekly spots value for Product 2
		planworkspace.clickWeeklySpotsValueBasedOnRowId(Product2, AutoConfigPlannerWorkSpace.dayPartDisplay,2, 1);
		
		//Get Cell Property Rate Value Product 2
		String cellRatePrd2=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value Product 1
		String cellImpsPrd2=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		Logger.log("Cell Rate for Product 2 is: "+cellRatePrd2+"\n Cell imps for Product 2 is:"+cellImpsPrd2);
		
		String expectedPrd1RateQtr=String.valueOf(Double.valueOf(cellRatePrd1)*Double.valueOf(spotsvalue));
		String expectedPrd1ImpsQtr=String.valueOf(Double.valueOf(cellImpsPrd1)*Double.valueOf(spotsvalue));
		String expectedPrd1CPMQtr = String.valueOf(Double.valueOf(expectedPrd1RateQtr)/Double.valueOf(expectedPrd1ImpsQtr));
		
		String expectedPrd2RateQtr=String.valueOf(Double.valueOf(cellRatePrd2)*Double.valueOf(spotsvalue));
		String expectedPrd2ImpsQtr=String.valueOf(Double.valueOf(cellImpsPrd2)*Double.valueOf(spotsvalue));
		String expectedPrd2CPMQtr = String.valueOf(Double.valueOf(expectedPrd2RateQtr)/Double.valueOf(expectedPrd2ImpsQtr));
		
		
		
		/*
		 * Select primary and secondary grouping and verify metrics values at pinged rows
		 */
		try {
		planworkspace.selectGrouping(1,AutoConfigPlannerWorkSpace.groupByProduct);
		Thread.sleep(5000);
		planworkspace.selectGrouping(1,AutoConfigPlannerWorkSpace.groupByUnitLength);
		
		String prd1RateValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product1,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd1ImpsValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product1,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd1GrossCPMValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product1,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd1RateValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd1ImpsValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd1GrossCPMValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd2RateValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product2,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd2ImpsValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product2,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd2GrossCPMValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product2,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd2RateValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd2ImpsValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd2GrossCPMValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.grossCpm);
		
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1RateValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1RateQtr),"Product 1 rate at primary gouped row is not expected");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1ImpsValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1ImpsQtr),"Product 1 imp at primary gouped row is not expected");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1GrossCPMValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1CPMQtr),"Product 1 cpm at primary gouped row is not expected");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1RateValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1RateQtr),"Product 1 rate at secondary gouped row is not expected");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1ImpsValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1ImpsQtr),"Product 1 imp at secondary gouped row is not expected");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1GrossCPMValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1CPMQtr),"Product 1 cpm at secondary gouped row is not expected");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2RateValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2RateQtr),"Product 2 rate at primary gouped row is not expected");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2ImpsValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2ImpsQtr),"Product 2 imp at primary gouped row is not expected");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2GrossCPMValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2CPMQtr),"Product 2 cpm at primary gouped row is not expected");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2RateValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2RateQtr),"Product 2 rate at secondary gouped row is not expected");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2ImpsValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2ImpsQtr),"Product 2 imp at secondary gouped row is not expected");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2GrossCPMValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2CPMQtr),"Product 2 cpm at secondary gouped row is not expected");
		/*
		 * Verify metrics values for Grouped rows on Plan Total Tab
		 */
		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		
		String prd1RateValuePriGroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product1,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd1ImpsValuePriGroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product1,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd1GrossCPMValuePriGroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product1,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd1RateValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd1ImpsValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd1GrossCPMValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd2RateValuePriGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product2,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd2ImpsValuePriGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product2,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd2GrossCPMValuePriGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product2,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd2RateValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd2ImpsValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd2GrossCPMValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.grossCpm);
		
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1RateValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1RateQtr),"Product 1 rate at primary gouped row is not expected on Plan Total Tab");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1ImpsValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1ImpsQtr),"Product 1 imp at primary gouped row is not expected on Plan Total Tab");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1GrossCPMValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1CPMQtr),"Product 1 cpm at primary gouped row is not expected on Plan Total Tab");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1RateValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1RateQtr),"Product 1 rate at secondary gouped row is not expected on Plan Total Tab");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1ImpsValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1ImpsQtr),"Product 1 imp at secondary gouped row is not expected on Plan Total Tab");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1GrossCPMValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1CPMQtr),"Product 1 cpm at secondary gouped row is not expected on Plan Total Tab");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2RateValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2RateQtr),"Product 2 rate at primary gouped row is not expected on Plan Total Tab");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2ImpsValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2ImpsQtr),"Product 2 imp at primary gouped row is not expected on Plan Total Tab");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2GrossCPMValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2CPMQtr),"Product 2 cpm at primary gouped row is not expected on Plan Total Tab");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2RateValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2RateQtr),"Product 2 rate at secondary gouped row is not expected on Plan Total Tab");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2ImpsValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2ImpsQtr),"Product 2 imp at secondary gouped row is not expected on Plan Total Tab");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2GrossCPMValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2CPMQtr),"Product 2 cpm at secondary gouped row is not expected on Plan Total Tab");
		
		/*
		 * Navigate back to Quarter Tab , remove units and verify metrics values
		 */
		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPeriodViewTab();
		
		//Deselect Grouping
		planworkspace.deselectGrouping();
		
		String spotsvalueNew=String.valueOf(Integer.valueOf(spotsvalue)/2);
		//Remove/changed Units
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalueNew,1,1);
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(Product2,AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalueNew,2,1);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//click weekly spots value for Product 1
		planworkspace.clickWeeklySpotsValueBasedOnRowId(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay,1, 1);
		
		//Get Cell Property Rate Value Product 1
		String cellRateAfterUnitsChangedPrd1=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value Product 1
		String cellImpsAfterUnitsChangedPrd1=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		Logger.log("Cell Rate After Units Changed for Product 1 is: "+cellRateAfterUnitsChangedPrd1+"\n Cell imps After Units Changed for Product 1 is:"+cellImpsAfterUnitsChangedPrd1);
		
		//click weekly spots value for Product 2
		planworkspace.clickWeeklySpotsValueBasedOnRowId(Product2, AutoConfigPlannerWorkSpace.dayPartDisplay,2, 1);
		
		//Get Cell Property Rate Value Product 2
		String cellRateAfterUnitsChangedPrd2=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate).replace(",", "").replace("$", "");

		//Get Cell Property imps Value Product 1
		String cellImpsAfterUnitsChangedPrd2=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		
		Logger.log("Cell Rate After Units Changed for Product 2 is: "+cellRateAfterUnitsChangedPrd2+"\n Cell imps After Units Changed for Product 2 is:"+cellImpsAfterUnitsChangedPrd2);
		
		String expectedPrd1RateAfterUnitsChangedQtr=String.valueOf(Double.valueOf(cellRateAfterUnitsChangedPrd1)*Double.valueOf(spotsvalueNew));
		String expectedPrd1ImpsAfterUnitsChangedQtr=String.valueOf(Double.valueOf(cellImpsAfterUnitsChangedPrd1)*Double.valueOf(spotsvalueNew));
		String expectedPrd1CPMAfterUnitsChangedQtr = String.valueOf(Double.valueOf(expectedPrd1RateAfterUnitsChangedQtr)/Double.valueOf(expectedPrd1ImpsAfterUnitsChangedQtr));
		
		String expectedPrd2RateAfterUnitsChangedQtr=String.valueOf(Double.valueOf(cellRateAfterUnitsChangedPrd2)*Double.valueOf(spotsvalueNew));
		String expectedPrd2ImpsAfterUnitsChangedQtr=String.valueOf(Double.valueOf(cellImpsAfterUnitsChangedPrd2)*Double.valueOf(spotsvalueNew));
		String expectedPrd2CPMAfterUnitsChangedQtr = String.valueOf(Double.valueOf(expectedPrd2RateAfterUnitsChangedQtr)/Double.valueOf(expectedPrd2ImpsAfterUnitsChangedQtr));
		
		

		/*
		 * Select primary and secondary grouping and verify metrics values at pinged rows
		 */
		planworkspace.selectGrouping(1,AutoConfigPlannerWorkSpace.groupByProduct);
		Thread.sleep(5000);
		planworkspace.selectGrouping(1,AutoConfigPlannerWorkSpace.groupByUnitLength);
		
		String prd1RateAfterUnitsChangedValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product1,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd1ImpsAfterUnitsChangedValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product1,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd1GrossCPMAfterUnitsChangedValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product1,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd1RateAfterUnitsChangedValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd1ImpsAfterUnitsChangedValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd1GrossCPMAfterUnitsChangedValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd2RateAfterUnitsChangedValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product2,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd2ImpsAfterUnitsChangedValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product2,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd2GrossCPMAfterUnitsChangedValuePriGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(Product2,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd2RateAfterUnitsChangedValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd2ImpsAfterUnitsChangedValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd2GrossCPMAfterUnitsChangedValueSecGroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.grossCpm);
		
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1RateAfterUnitsChangedValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1RateAfterUnitsChangedQtr),"Product 1 rate at primary gouped row is not expected After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1ImpsAfterUnitsChangedValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1ImpsAfterUnitsChangedQtr),"Product 1 imp at primary gouped row is not expected After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1GrossCPMAfterUnitsChangedValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1CPMAfterUnitsChangedQtr),"Product 1 cpm at primary gouped row is not expected After Units Changed");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1RateAfterUnitsChangedValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1RateAfterUnitsChangedQtr),"Product 1 rate at secondary gouped row is not expected After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1ImpsAfterUnitsChangedValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1ImpsAfterUnitsChangedQtr),"Product 1 imp at secondary gouped row is not expected After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1GrossCPMAfterUnitsChangedValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd1CPMAfterUnitsChangedQtr),"Product 1 cpm at secondary gouped row is not expected After Units Changed");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2RateAfterUnitsChangedValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2RateAfterUnitsChangedQtr),"Product 2 rate at primary gouped row is not expected After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2ImpsAfterUnitsChangedValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2ImpsAfterUnitsChangedQtr),"Product 2 imp at primary gouped row is not expected After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2GrossCPMAfterUnitsChangedValuePriGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2CPMAfterUnitsChangedQtr),"Product 2 cpm at primary gouped row is not expected After Units Changed");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2RateAfterUnitsChangedValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2RateAfterUnitsChangedQtr),"Product 2 rate at secondary gouped row is not expected After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2ImpsAfterUnitsChangedValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2ImpsAfterUnitsChangedQtr),"Product 2 imp at secondary gouped row is not expected After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2GrossCPMAfterUnitsChangedValueSecGroupPinedRowsQtr), getBasePage().
				format2Digi(expectedPrd2CPMAfterUnitsChangedQtr),"Product 2 cpm at secondary gouped row is not expected After Units Changed");
		/*
		 * Verify metrics values for Grouped rows on Plan Total Tab
		 */
		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		
		String prd1RateAfterUnitsChangedValuePriGroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product1,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd1ImpsAfterUnitsChangedValuePriGroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product1,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd1GrossCPMAfterUnitsChangedValuePriGroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product1,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd1RateAfterUnitsChangedValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd1ImpsAfterUnitsChangedValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd1GrossCPMAfterUnitsChangedValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttribute,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd2RateAfterUnitsChangedValuePriGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product2,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd2ImpsAfterUnitsChangedValuePriGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product2,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd2GrossCPMAfterUnitsChangedValuePriGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(Product2,AutoConfigPlannerWorkSpace.grossCpm);
		
		String prd2RateAfterUnitsChangedValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.ratetotalQtr);
		String prd2ImpsAfterUnitsChangedValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.impstotalQtr);
		String prd2GrossCPMAfterUnitsChangedValueSecGroupPinedRowsPlanTotal = planworkspace.getMetricsValueAtGroupRowsPlanTotal(AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate,AutoConfigPlannerWorkSpace.grossCpm);
		
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1RateAfterUnitsChangedValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1RateAfterUnitsChangedQtr),"Product 1 rate at primary gouped row is not expected on Plan Total Tab After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1ImpsAfterUnitsChangedValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1ImpsAfterUnitsChangedQtr),"Product 1 imp at primary gouped row is not expected on Plan Total Tab After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1GrossCPMAfterUnitsChangedValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1CPMAfterUnitsChangedQtr),"Product 1 cpm at primary gouped row is not expected on Plan Total Tab After Units Changed");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1RateAfterUnitsChangedValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1RateAfterUnitsChangedQtr),"Product 1 rate at secondary gouped row is not expected on Plan Total Tab After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1ImpsAfterUnitsChangedValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1ImpsAfterUnitsChangedQtr),"Product 1 imp at secondary gouped row is not expected on Plan Total Tab After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd1GrossCPMAfterUnitsChangedValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd1CPMAfterUnitsChangedQtr),"Product 1 cpm at secondary gouped row is not expected on Plan Total Tab After Units Changed");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2RateAfterUnitsChangedValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2RateAfterUnitsChangedQtr),"Product 2 rate at primary gouped row is not expected on Plan Total Tab After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2ImpsAfterUnitsChangedValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2ImpsAfterUnitsChangedQtr),"Product 2 imp at primary gouped row is not expected on Plan Total Tab After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2GrossCPMAfterUnitsChangedValuePriGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2CPMAfterUnitsChangedQtr),"Product 2 cpm at primary gouped row is not expected on Plan Total Tab After Units Changed");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2RateAfterUnitsChangedValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2RateAfterUnitsChangedQtr),"Product 2 rate at secondary gouped row is not expected on Plan Total Tab After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2ImpsAfterUnitsChangedValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2ImpsAfterUnitsChangedQtr),"Product 2 imp at secondary gouped row is not expected on Plan Total Tab After Units Changed");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(prd2GrossCPMAfterUnitsChangedValueSecGroupPinedRowsPlanTotal), getBasePage().
				format2Digi(expectedPrd2CPMAfterUnitsChangedQtr),"Product 2 cpm at secondary gouped row is not expected on Plan Total Tab After Units Changed");
		}
		finally {
		//Deselect Grouping
		planworkspace.deselectGrouping();
		getCustomeVerification().assertAll(getSoftAssert());
		}

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
