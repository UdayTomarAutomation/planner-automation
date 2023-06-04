package com.operative.tests.plannerWorkspaceSmoke;

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
 * @author Uday Madan
 *
 */


public class  QtrUpdate_GrossRate_000_GrossCPM_And_checkQty_WeeklyCalculation_planTotal_And_Update extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuitePlannerDataWeekly")
	public void QtrUpdateGrossRate000GrossCPMAndcheckQtyWeeklyCalculationplanTotalAndUpdate(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String flightStartDate,String flightEndDate,String channelValue,
			String demovalue,String RatingStream,String spotsvalue,String Ratecard,String Product1) throws ParseException  {
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
		
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpots(Product1,
				AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		// get all quarters and weeks
	    final List<String> generateQuarterlyValue =
	        new DateTimeFunctions().generateQuarterlySequence(flightStartDate, flightEndDate);
	    final Map<String, List<String>> weeksMap = planworkspace.getWeeksBetweenDatesWorkspaceInQuarter(flightStartDate, flightEndDate);
	    final List<String> weeeksDates = weeksMap.get(generateQuarterlyValue.get(0));
	    Logger.log(weeeksDates.toString());
	    
		
		//get rate value in total qtr
		String grossRateValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr);
		
		String impsValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr);
		
		String cpmValueQtr=planworkspace.getAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm);
		
		Logger.log("grossRateValueQtr is==> "+grossRateValueQtr+"\n impsValueQtr is==>>"+impsValueQtr+"\n cpmValueQtr is ==> "+cpmValueQtr);
		
		/*
		 * Open Weekly Pannel And verify Metrics values
		 * 			
		 */
		
		planworkspace.resetFilterInWeeklyTotalsWorkspace(AutoConfigPlannerWorkSpace.weeklyColumnsGrossRate+";"+AutoConfigPlannerWorkSpace.weeklyColumnsGrossCPM+";"+AutoConfigPlannerWorkSpace.weeklyColumns000Imps);	
		
		final String weeklyRateTotal[] =planworkspace.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyTotalRateColumn,weeeksDates);
		final String weeklyImps000Total[] =planworkspace.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyTotalImps000Column,weeeksDates);
		final String weeklyGrossCPMTotal[] =planworkspace.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyTotalCPMColumn,weeeksDates);
		
		Logger.log("Weekly Rate is: "+Joiner.on(",").join(weeklyRateTotal)+"\n Weekly Imps000 is: "+Joiner.on(",").join(weeklyImps000Total)+"\n Weekly CPM is: "+Joiner.on(",").join(weeklyGrossCPMTotal));
		
		String totalWeeklyRate=planworkspace.calculateWeeklyTotalValue(weeklyRateTotal);
		String totalWeeklyImps000=planworkspace.calculateWeeklyTotalValue(weeklyImps000Total);
		String totalWeeklyGrossCPM=String.valueOf(Double.valueOf(totalWeeklyRate)/Double.valueOf(totalWeeklyImps000));
		
		Logger.log("Total Weekly Rate is: "+totalWeeklyRate+"\n Total Weekly Imps000 is: "+totalWeeklyImps000+"\n Total Weekly CPM is: "+totalWeeklyGrossCPM);
		
		
		//Verify Weekly CPM value
		for (int i=0;i<weeklyGrossCPMTotal.length;i++) {
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(Double.valueOf(weeklyRateTotal[i])/Double.valueOf(weeklyImps000Total[i]))), getBasePage().
				format2Digi(String.valueOf(weeklyGrossCPMTotal[i].replace(",", "").replace("$", ""))),"Weekly CPM value is not correct");
		}
		
		//Verify Gross Rate, IMPS and CPM Weekly Pannel
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(totalWeeklyRate.replace(",", "").replace("$", ""))),"rate total qtr and Weekly total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(totalWeeklyImps000.replace(",", "").replace("$", ""))),"imps total qtrand Weekly total is not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtr.replace(",", "").replace("$", "")), getBasePage().
				format2Digi(String.valueOf(totalWeeklyGrossCPM.replace(",", "").replace("$", ""))),"cpm total qtr and Weekly total is not same");


		
		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		
		//get total values from Plan Total Tab
		String grossValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr);
		
		String impsValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr);
		
		String cpmValuePlanTotalTab=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm);
		
		Logger.log("grossValuePlanTotalTab is==> "+grossValuePlanTotalTab+"\n impsValuePlanTotalTab is==>>"+impsValuePlanTotalTab+"\n cpmValuePlanTotalTab is ==> "+cpmValuePlanTotalTab);
		
		//Verify Gross Rate, IMPS and CPM Plan Total values
				getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtr.replace(",", "").replace("$", "")), getBasePage().
						format2Digi(String.valueOf(grossValuePlanTotalTab.replace(",", "").replace("$", ""))),"rate total qtr and plan total is not same");
				getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtr.replace(",", "").replace("$", "")), getBasePage().
						format2Digi(String.valueOf(impsValuePlanTotalTab.replace(",", "").replace("$", ""))),"imps total qtr and plan total is not same");
				getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtr.replace(",", "").replace("$", "")), getBasePage().
						format2Digi(String.valueOf(cpmValuePlanTotalTab.replace(",", "").replace("$", ""))),"cpm total qtr and plan total is not same");
	
	
	/*
	 * Update Quarters rate value and veriy calculation on Qtr and Plan Total Tab			
	 */
				
		//Navigate to Period View Tab
        planworkspace.navigateToPeriodViewTab();
            	
		String qtrRateUpdatedvalue=String.valueOf(Double.valueOf(grossRateValueQtr.replace(",", "").replace("$", ""))*2);
		Logger.log("qtrRateUpdatedvalue is==> "+qtrRateUpdatedvalue);
		
		//enter updated rate value on qtr field
		planworkspace.enterAggregateTotalValueQtr(Product1, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr,qtrRateUpdatedvalue,0);
		
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		
		//get rate value in total qtr
				String grossRateValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(Product1, 
						AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr);
				
				String impsValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(Product1, 
						AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr);
				
				String cpmValueQtrUpdated=planworkspace.getAggregateTotalValueQtr(Product1, 
						AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm);
				
				Logger.log("grossRateValueQtrUpdated is==> "+grossRateValueQtrUpdated+"\n impsValueQtrUpdated is==>>"+impsValueQtrUpdated+"\n cpmValueQtrUpdated is ==> "+cpmValueQtrUpdated);
				
				/*
				 * Open Weekly Pannel And verify Metrics values
				 * 			
				 */
												
				final String weeklyRateTotalUpdated[] =planworkspace.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyTotalRateColumn,weeeksDates);
				final String weeklyImps000TotalUpdated[] =planworkspace.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyTotalImps000Column,weeeksDates);
				final String weeklyGrossCPMTotalUpdated[] =planworkspace.getWeeklyTotalValue(AutoConfigPlannerWorkSpace.weeklyTotalCPMColumn,weeeksDates);
				
				Logger.log("Weekly Updated Rate is: "+Joiner.on(",").join(weeklyRateTotalUpdated)+"\n Weekly Updated Imps000 is: "+Joiner.on(",").join(weeklyImps000TotalUpdated)+"\n Weekly Updated CPM is: "+Joiner.on(",").join(weeklyGrossCPMTotalUpdated));
				
				String totalWeeklyRateUpdated=planworkspace.calculateWeeklyTotalValue(weeklyRateTotalUpdated);
				String totalWeeklyImps000Updated=planworkspace.calculateWeeklyTotalValue(weeklyImps000TotalUpdated);
				String totalWeeklyGrossCPMUpdated=String.valueOf(Double.valueOf(totalWeeklyRateUpdated)/Double.valueOf(totalWeeklyImps000Updated));
				
				Logger.log("Total Updated Weekly Rate is: "+totalWeeklyRateUpdated+"\n Total Updated Weekly Imps000 is: "+totalWeeklyImps000Updated+"\n Total Updated Weekly CPM is: "+totalWeeklyGrossCPMUpdated);
				
				//Verify Weekly CPM value after qtr Gross rate update
				for (int i=0;i<weeklyGrossCPMTotalUpdated.length;i++) {
				getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(String.valueOf(Double.valueOf(weeklyRateTotalUpdated[i])/Double.valueOf(weeklyImps000TotalUpdated[i]))), getBasePage().
						format2Digi(String.valueOf(weeklyGrossCPMTotalUpdated[i].replace(",", "").replace("$", ""))),"Weekly CPM value is not correct after qtr Gross rate update");
				}
				
				//Verify Gross Rate, IMPS and CPM Weekly Pannel
				getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
						format2Digi(String.valueOf(totalWeeklyRateUpdated.replace(",", "").replace("$", ""))),"rate total qtr and Weekly total is not same after gross rate update");
				getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
						format2Digi(String.valueOf(totalWeeklyImps000Updated.replace(",", "").replace("$", ""))),"imps total qtrand Weekly total is not same after gross rate update");
				getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
						format2Digi(String.valueOf(totalWeeklyGrossCPMUpdated.replace(",", "").replace("$", ""))),"cpm total qtr and Weekly total is not same after gross rate update");

				
				
				//Navigate to Plan Total Tab
				planworkspace.navigateToPlanTotalTab();
				
				//get total values from Plan Total Tab
				String grossValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
						AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.ratetotalQtr);
				
				String impsValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
						AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr);
				
				String cpmValuePlanTotalTabUpdated=planworkspace.getAggregateTotalValuePlanTotalTab(Product1, 
						AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.grossCpm);
				
				Logger.log("grossValuePlanTotalTabUpdated is==> "+grossValuePlanTotalTabUpdated+"\n impsValuePlanTotalTabUpdated is==>>"+impsValuePlanTotalTabUpdated+"\n cpmValuePlanTotalTabUpdated is ==> "+cpmValuePlanTotalTabUpdated);
				
				//Verify Gross Rate, IMPS and CPM Plan Total values
						getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossRateValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
								format2Digi(String.valueOf(grossValuePlanTotalTabUpdated.replace(",", "").replace("$", ""))),"rate total qtr and plan total is not same after updating gross rate value");
						getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
								format2Digi(String.valueOf(impsValuePlanTotalTabUpdated.replace(",", "").replace("$", ""))),"imps total qtr and plan total is not same after updating gross rate value");
						getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(cpmValueQtrUpdated.replace(",", "").replace("$", "")), getBasePage().
								format2Digi(String.valueOf(cpmValuePlanTotalTabUpdated.replace(",", "").replace("$", ""))),"cpm total qtr and plan total is not same after updating gross rate value");
			
		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
