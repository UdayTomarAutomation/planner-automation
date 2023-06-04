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


public class  verifyCalculation_AllMetrices_PeriodViewWithbookAndRateCardTwoLavelGrouping_Local extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="localSmokeSuitePlannerDataGrouping")
	public void verifyCalculationAllMetricesPeriodViewWithbookAndRateCardTwoLavelGrouping(String planClass,String station,String flightDates,String flightStartDate,String flightEndDate,
			String advertiser,String buyingAgency,String accountExecutive,String calenderValue,String startPeriodValue,String endPeriodValue,String demo,String dayPart1,String dayPart2, String marketPlace,
			String ratingStream,String products,String ratecard, String spotValue) throws ParseException, InterruptedException
	{
		PlannerHeaderPage  planHeaderPage=new PlannerHeaderPage(getPageBrowser());
		BasePage getBasePage = new BasePage(getPageBrowser());
		final PlannerWorkBookPage workSpacebook = new PlannerWorkBookPage(getPageBrowser());
		
		String planName =  AutoConfigPlannerHeader.linearPlanName + getBasePage.getRandomNumber();
		String product[] = products.split(";");
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
				dayPart1,spotValue,1,1);
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(product[1],
				dayPart2,spotValue,2,1);
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(product[0],
				dayPart1,spotValue,3,1);
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(product[1],
				dayPart2,spotValue,4,1);
		
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
	    workSpacebook.clickBackButton().clickNext();
	    workSpacebook.applyBookForMultipleProductSelectAll(product[0],"3",
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);
	    workSpacebook.clickBackButton().clickNext();
	    workSpacebook.applyBookForMultipleProductSelectAll(product[1],"4",
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);
	    workSpacebook.clickApply();
	    
	  //click weekly spots value for product 1
		planworkspace.clickWeeklySpotsValueBasedOnRowId(product[0], dayPart1,1,1);
		
		//Get Cell Property imps Value
		String cellImpsPrd1=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		Double prd1QtrTotalImps000 = Double.valueOf(cellImpsPrd1) * Double.valueOf(spotValue);
		
		//Get Cell Property RTG Value
		String cellRTGPrd1=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		Double prd1QtrTotalRTG = Double.valueOf(cellRTGPrd1) * Double.valueOf(spotValue);
		
		Logger.log("cellImpsPrd1 is==>>"+cellImpsPrd1+"\n cellRTGPrd1 is ==> "+cellRTGPrd1);
		Logger.log("prd1QtrTotalImps000 is==>>"+prd1QtrTotalImps000+"\n prd1QtrTotalRTG is ==> "+prd1QtrTotalRTG);
		
		 //click weekly spots value for product 2
		planworkspace.clickWeeklySpotsValueBasedOnRowId(product[1], dayPart2,2,1);
		
		//Get Cell Property imps Value
		String cellImpsPrd2=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		Double prd2QtrTotalImps000 = Double.valueOf(cellImpsPrd2) * Double.valueOf(spotValue);
		
		//Get Cell Property RTG Value
		String cellRTGPrd2=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		Double prd2QtrTotalRTG = Double.valueOf(cellRTGPrd2) * Double.valueOf(spotValue);
		
		Logger.log("cellImpsPrd2 is==>>"+cellImpsPrd2+"\n cellRTGPrd2 is ==> "+cellRTGPrd2);
		Logger.log("prd2QtrTotalImps000 is==>>"+prd2QtrTotalImps000+"\n prd2QtrTotalRTG is ==> "+prd2QtrTotalRTG);
		
		
		//click weekly spots value for product 3
		planworkspace.clickWeeklySpotsValueBasedOnRowId(product[0], dayPart1,3,1);
		
		//Get Cell Property imps Value
		String cellImpsPrd3=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		Double prd3QtrTotalImps000 = Double.valueOf(cellImpsPrd3) * Double.valueOf(spotValue);
		
		//Get Cell Property RTG Value
		String cellRTGPrd3=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		Double prd3QtrTotalRTG = Double.valueOf(cellRTGPrd3) * Double.valueOf(spotValue);
		
		Logger.log("cellImpsPrd3 is==>>"+cellImpsPrd3+"\n cellRTGPrd3 is ==> "+cellRTGPrd3);
		Logger.log("prd3QtrTotalImps000 is==>>"+prd3QtrTotalImps000+"\n prd3QtrTotalRTG is ==> "+prd3QtrTotalRTG);
		
		//click weekly spots value for product 3
		planworkspace.clickWeeklySpotsValueBasedOnRowId(product[1], dayPart2,4,1);
		
		//Get Cell Property imps Value
		String cellImpsPrd4=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		Double prd4QtrTotalImps000 = Double.valueOf(cellImpsPrd4) * Double.valueOf(spotValue);
		
		//Get Cell Property RTG Value
		String cellRTGPrd4=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		Double prd4QtrTotalRTG = Double.valueOf(cellRTGPrd4) * Double.valueOf(spotValue);
		
		Logger.log("cellImpsPrd4 is==>>"+cellImpsPrd4+"\n cellRTGPrd4 is ==> "+cellRTGPrd4);
		Logger.log("prd4QtrTotalImps000 is==>>"+prd4QtrTotalImps000+"\n prd3QtrTotalRTG is ==> "+prd4QtrTotalRTG);

		planworkspace.navigateToPlanTotalTab();
		planworkspace.navigateToPeriodViewTab();
		//Select columns on workspace
	    planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.qtrNetRate+";"+AutoConfigPlannerWorkSpace.qtr000+";"+
		AutoConfigPlannerWorkSpace.qtrGrossCPM+";"+AutoConfigPlannerWorkSpace.qtrNetCPM+";"+AutoConfigPlannerWorkSpace.qtrGrossCPP+";"+
	    AutoConfigPlannerWorkSpace.qtrNetCPP+";"+AutoConfigPlannerWorkSpace.qtrGRP);
	
	   
		
	  	//get rate value in total qtr
  		String grossRateValueQtrPrd1=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.ratetotalQtr,1).replace(",", "").replace("$", "");
  		String grossRateValueQtrPrd2=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
  				dayPart2, AutoConfigPlannerWorkSpace.ratetotalQtr,2).replace(",", "").replace("$", "");
  		String grossRateValueQtrPrd3=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.ratetotalQtr,3).replace(",", "").replace("$", "");
  		String grossRateValueQtrPrd4=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
  				dayPart2, AutoConfigPlannerWorkSpace.ratetotalQtr,4).replace(",", "").replace("$", "");
  		
  		Logger.log("grossRateValueQtrPrd1 "+grossRateValueQtrPrd1+"\n grossRateValueQtrPrd2 "+grossRateValueQtrPrd2+"\n grossRateValueQtrPrd3 "+grossRateValueQtrPrd3+"\n grossRateValueQtrPrd4"+grossRateValueQtrPrd4);
  		
  		 String netRateValueQtrPrd1=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
   				dayPart1, AutoConfigPlannerWorkSpace.netRatetotalQtr,1).replace(",", "").replace("$", "");
   		String netRateValueQtrPrd2=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
   				dayPart2, AutoConfigPlannerWorkSpace.netRatetotalQtr,2).replace(",", "").replace("$", "");
   		String netRateValueQtrPrd3=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
   				dayPart1, AutoConfigPlannerWorkSpace.netRatetotalQtr,3).replace(",", "").replace("$", "");
   		String netRateValueQtrPrd4=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
   				dayPart2, AutoConfigPlannerWorkSpace.netRatetotalQtr,4).replace(",", "").replace("$", "");
   		
   		Logger.log("netRateValueQtrPrd1 "+netRateValueQtrPrd1+"\n netRateValueQtrPrd2 "+netRateValueQtrPrd2+"\n netRateValueQtrPrd3 "+netRateValueQtrPrd3+"\n netRateValueQtrPrd4"+netRateValueQtrPrd4);
  		
  		String imps000ValueQtrPrd1=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.impstotalQtr,1).replace(",", "").replace("$", "");
  		String imps000ValueQtrPrd2=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.impstotalQtr,2).replace(",", "").replace("$", "");
  		String imps000ValueQtrPrd3=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.impstotalQtr,3).replace(",", "").replace("$", "");
  		String imps000ValueQtrPrd4=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.impstotalQtr,4).replace(",", "").replace("$", "");
  		
  		Logger.log("imps000ValueQtrPrd1 "+imps000ValueQtrPrd1+"\n imps000ValueQtrPrd2 "+imps000ValueQtrPrd2+"\n imps000ValueQtrPrd3 "+imps000ValueQtrPrd3+"\n imps000ValueQtrPrd4"+imps000ValueQtrPrd4);
  		
  		String cpmValueQtrPrd1=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.cpmtotalQtr,1).replace(",", "").replace("$", "");
  		String cpmValueQtrPrd2=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.cpmtotalQtr,2).replace(",", "").replace("$", "");
  		String cpmValueQtrPrd3=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.cpmtotalQtr,3).replace(",", "").replace("$", "");
  		String cpmValueQtrPrd4=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.cpmtotalQtr,4).replace(",", "").replace("$", "");
		
  		Logger.log("cpmValueQtrPrd1 "+cpmValueQtrPrd1+"\n cpmValueQtrPrd2 "+cpmValueQtrPrd2+"\n cpmValueQtrPrd3 "+cpmValueQtrPrd3+"\n cpmValueQtrPrd4"+cpmValueQtrPrd4);
  		
		String netCpmValueQtrPrd1=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.netCpmtotalQtr,1).replace(",", "").replace("$", "");
		String netCpmValueQtrPrd2=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.netCpmtotalQtr,2).replace(",", "").replace("$", "");
		String netCpmValueQtrPrd3=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.netCpmtotalQtr,3).replace(",", "").replace("$", "");
		String netCpmValueQtrPrd4=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.netCpmtotalQtr,4).replace(",", "").replace("$", "");
		
		Logger.log("netCpmValueQtrPrd1 "+netCpmValueQtrPrd1+"\n netCpmValueQtrPrd2 "+netCpmValueQtrPrd2+"\n netCpmValueQtrPrd3 "+netCpmValueQtrPrd3+"\n netCpmValueQtrPrd4"+netCpmValueQtrPrd4);
		
		String grossCppValueQtrPrd1=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.grossCpptotalQtr,1).replace(",", "").replace("$", "");
		String grossCppValueQtrPrd2=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.grossCpptotalQtr,2).replace(",", "").replace("$", "");
		String grossCppValueQtrPrd3=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.grossCpptotalQtr,3).replace(",", "").replace("$", "");
		String grossCppValueQtrPrd4=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.grossCpptotalQtr,4).replace(",", "").replace("$", "");
		
		Logger.log("grossCppValueQtrPrd1 "+grossCppValueQtrPrd1+"\n grossCppValueQtrPrd2 "+grossCppValueQtrPrd2+"\n grossCppValueQtrPrd3 "+grossCppValueQtrPrd3+"\n grossCppValueQtrPrd4"+grossCppValueQtrPrd4);
		
		String netCppValueQtrPrd1=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.netCpptotalQtr,1).replace(",", "").replace("$", "");
		String netCppValueQtrPrd2=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.netCpptotalQtr,2).replace(",", "").replace("$", "");
		String netCppValueQtrPrd3=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.netCpptotalQtr,3).replace(",", "").replace("$", "");
		String netCppValueQtrPrd4=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.netCpptotalQtr,4).replace(",", "").replace("$", "");
  		
		Logger.log("netCppValueQtrPrd1 "+netCppValueQtrPrd1+"\n netCppValueQtrPrd2 "+netCppValueQtrPrd2+"\n netCppValueQtrPrd3 "+netCppValueQtrPrd3+"\n netCppValueQtrPrd4"+netCppValueQtrPrd4);
		
		String grpValueQtrPrd1=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.grptotalQtr,1).replace(",", "").replace("$", "");
		String grpValueQtrPrd2=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.grptotalQtr,2).replace(",", "").replace("$", "");
		String grpValueQtrPrd3=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.grptotalQtr,3).replace(",", "").replace("$", "");
		String grpValueQtrPrd4=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.grptotalQtr,4).replace(",", "").replace("$", "");
  		
		Logger.log("grpValueQtrPrd1 "+grpValueQtrPrd1+"\n grpValueQtrPrd2 "+grpValueQtrPrd2+"\n grpValueQtrPrd3 "+grpValueQtrPrd3+"\n grpValueQtrPrd4"+grpValueQtrPrd4);
		
		try {
	    	
		    planworkspace.selectGrouping(1,AutoConfigPlannerWorkSpace.groupByDayPart);

			planworkspace.selectGrouping(1,AutoConfigPlannerWorkSpace.groupByProduct);
			
			//Get Grouped metrics value at Product level
			String grossrateValueAtProduct1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[0],AutoConfigPlannerWorkSpace.ratetotalQtr);
			String netrateValueAtProduct1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[0],AutoConfigPlannerWorkSpace.netRatetotalQtr);
			String imps000ValueAtProduct1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[0],AutoConfigPlannerWorkSpace.impstotalQtr);
			String grossCPMValueAtProduct1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[0],AutoConfigPlannerWorkSpace.cpmtotalQtr);
			String netCPMValueAtProduct1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[0],AutoConfigPlannerWorkSpace.netCpmtotalQtr);
			String grossCPPValueAtProduct1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[0],AutoConfigPlannerWorkSpace.grossCpptotalQtr);
			String netCPPValueAtProduct1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[0],AutoConfigPlannerWorkSpace.netCpptotalQtr);
			String grpValueAtProduct1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[0],AutoConfigPlannerWorkSpace.grptotalQtr);
			
			Logger.log("grossrateValueAtProduct1GroupPinedRowsQtr: "+grossrateValueAtProduct1GroupPinedRowsQtr+"\n netrateValueAtProduct1GroupPinedRowsQtr: "+netrateValueAtProduct1GroupPinedRowsQtr+"\n imps000ValueAtProduct1GroupPinedRowsQtr: "+imps000ValueAtProduct1GroupPinedRowsQtr+
					"\n grossCPMValueAtProduct1GroupPinedRowsQtr: "+grossCPMValueAtProduct1GroupPinedRowsQtr+"\n netCPMValueAtProduct1GroupPinedRowsQtr: "+netCPMValueAtProduct1GroupPinedRowsQtr+"\n grossCPPValueAtProduct1GroupPinedRowsQtr: "+grossCPPValueAtProduct1GroupPinedRowsQtr+
					"\n netCPPValueAtProduct1GroupPinedRowsQtr: "+netCPPValueAtProduct1GroupPinedRowsQtr+"\n grpValueAtProduct1GroupPinedRowsQtr: "+grpValueAtProduct1GroupPinedRowsQtr);
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossrateValueAtProduct1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossRateValueQtrPrd1)+Double.valueOf(grossRateValueQtrPrd3))),"Product1 rate at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netrateValueAtProduct1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netRateValueQtrPrd1)+Double.valueOf(netRateValueQtrPrd3))),"Product1 net rate at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(imps000ValueAtProduct1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(prd1QtrTotalImps000+prd3QtrTotalImps000)),"Product1 000 at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPMValueAtProduct1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct1GroupPinedRowsQtr)/ (prd1QtrTotalImps000+prd3QtrTotalImps000))),"Product1 gross CPM at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPMValueAtProduct1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct1GroupPinedRowsQtr)/ (prd1QtrTotalImps000+prd3QtrTotalImps000))),"Product1 net CPM at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPPValueAtProduct1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct1GroupPinedRowsQtr)/ Double.valueOf(grpValueAtProduct1GroupPinedRowsQtr))),"Product1 gross CPP at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPPValueAtProduct1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct1GroupPinedRowsQtr)/ Double.valueOf(grpValueAtProduct1GroupPinedRowsQtr))),"Product1 net CPP at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grpValueAtProduct1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(prd1QtrTotalRTG + prd2QtrTotalRTG)),"Product1 grp at secondary grouped row is not expected");

			
			String grossrateValueAtProduct2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[1],AutoConfigPlannerWorkSpace.ratetotalQtr);
			String netrateValueAtProduct2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[1],AutoConfigPlannerWorkSpace.netRatetotalQtr);
			String imps000ValueAtProduct2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[1],AutoConfigPlannerWorkSpace.impstotalQtr);
			String grossCPMValueAtProduct2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[1],AutoConfigPlannerWorkSpace.cpmtotalQtr);
			String netCPMValueAtProduct2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[1],AutoConfigPlannerWorkSpace.netCpmtotalQtr);
			String grossCPPValueAtProduct2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[1],AutoConfigPlannerWorkSpace.grossCpptotalQtr);
			String netCPPValueAtProduct2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[1],AutoConfigPlannerWorkSpace.netCpptotalQtr);
			String grpValueAtProduct2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(product[1],AutoConfigPlannerWorkSpace.grptotalQtr);
			
			Logger.log("grossrateValueAtProduct2GroupPinedRowsQtr: "+grossrateValueAtProduct2GroupPinedRowsQtr+"\n netrateValueAtProduct2GroupPinedRowsQtr: "+netrateValueAtProduct2GroupPinedRowsQtr+"\n imps000ValueAtProduct2GroupPinedRowsQtr: "+imps000ValueAtProduct2GroupPinedRowsQtr+
					"\n grossCPMValueAtProduct2GroupPinedRowsQtr: "+grossCPMValueAtProduct2GroupPinedRowsQtr+"\n netCPMValueAtProduct2GroupPinedRowsQtr: "+netCPMValueAtProduct2GroupPinedRowsQtr+"\n grossCPPValueAtProduct2GroupPinedRowsQtr: "+grossCPPValueAtProduct2GroupPinedRowsQtr+
					"\n netCPPValueAtProduct2GroupPinedRowsQtr: "+netCPPValueAtProduct2GroupPinedRowsQtr+"\n grpValueAtProduct2GroupPinedRowsQtr: "+grpValueAtProduct2GroupPinedRowsQtr);
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossrateValueAtProduct2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossRateValueQtrPrd2)+Double.valueOf(grossRateValueQtrPrd4))),"Product2 rate at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netrateValueAtProduct2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netRateValueQtrPrd2)+Double.valueOf(netRateValueQtrPrd4))),"Product2 net rate at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(imps000ValueAtProduct2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(prd2QtrTotalImps000+prd4QtrTotalImps000)),"Product2 000 at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPMValueAtProduct2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct2GroupPinedRowsQtr)/ (prd2QtrTotalImps000+prd4QtrTotalImps000))),"Product2 gross CPM at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPMValueAtProduct2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct2GroupPinedRowsQtr)/ (prd2QtrTotalImps000+prd4QtrTotalImps000))),"Product2 net CPM at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPPValueAtProduct2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct2GroupPinedRowsQtr)/ Double.valueOf(grpValueAtProduct2GroupPinedRowsQtr))),"Product2 gross CPP at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPPValueAtProduct2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct2GroupPinedRowsQtr)/ Double.valueOf(grpValueAtProduct2GroupPinedRowsQtr))),"Product2 net CPP at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grpValueAtProduct2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(prd2QtrTotalRTG + prd4QtrTotalRTG)),"Product2 grp at secondary gouped row is not expected");

			/*
			 * Verify primary grouping pinged rows values
			 */
			String grossrateValueAtDaypart1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart1,AutoConfigPlannerWorkSpace.ratetotalQtr);
			String netrateValueAtDaypart1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart1,AutoConfigPlannerWorkSpace.netRatetotalQtr);
			String imps000ValueAtDaypart1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart1,AutoConfigPlannerWorkSpace.impstotalQtr);
			String grossCPMValueAtDaypart1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart1,AutoConfigPlannerWorkSpace.cpmtotalQtr);
			String netCPMValueAtDaypart1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart1,AutoConfigPlannerWorkSpace.netCpmtotalQtr);
			String grossCPPValueAtDaypart1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart1,AutoConfigPlannerWorkSpace.grossCpptotalQtr);
			String netCPPValueAtDaypart1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart1,AutoConfigPlannerWorkSpace.netCpptotalQtr);
			String grpValueAtDaypart1GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart1,AutoConfigPlannerWorkSpace.grptotalQtr);
			
			Logger.log("grossrateValueAtDaypart1GroupPinedRowsQtr: "+grossrateValueAtDaypart1GroupPinedRowsQtr+"\n netrateValueAtDaypart1GroupPinedRowsQtr: "+netrateValueAtDaypart1GroupPinedRowsQtr+"\n imps000ValueAtDaypart1GroupPinedRowsQtr: "+imps000ValueAtDaypart1GroupPinedRowsQtr+
					"\n grossCPMValueAtDaypart1GroupPinedRowsQtr: "+grossCPMValueAtDaypart1GroupPinedRowsQtr+"\n netCPMValueAtDaypart1GroupPinedRowsQtr: "+netCPMValueAtDaypart1GroupPinedRowsQtr+"\n grossCPPValueAtDaypart1GroupPinedRowsQtr: "+grossCPPValueAtDaypart1GroupPinedRowsQtr+
					"\n netCPPValueAtDaypart1GroupPinedRowsQtr: "+netCPPValueAtDaypart1GroupPinedRowsQtr+"\n grpValueAtDaypart1GroupPinedRowsQtr: "+grpValueAtDaypart1GroupPinedRowsQtr);
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossrateValueAtDaypart1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossRateValueQtrPrd1)+Double.valueOf(grossRateValueQtrPrd3))),"Daypart1 rate at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netrateValueAtDaypart1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netRateValueQtrPrd1)+Double.valueOf(netRateValueQtrPrd3))),"Daypart1 net rate at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(imps000ValueAtDaypart1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(prd1QtrTotalImps000+prd3QtrTotalImps000)),"Daypart1 000 at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPMValueAtDaypart1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct1GroupPinedRowsQtr)/ (prd1QtrTotalImps000+prd3QtrTotalImps000))),"Daypart1 gross CPM at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPMValueAtDaypart1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct1GroupPinedRowsQtr)/ (prd1QtrTotalImps000+prd3QtrTotalImps000))),"Daypart1 net CPM at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPPValueAtDaypart1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct1GroupPinedRowsQtr)/ (prd1QtrTotalRTG + prd3QtrTotalRTG))),"Daypart1 gross CPP at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPPValueAtDaypart1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct1GroupPinedRowsQtr)/ ((prd1QtrTotalRTG + prd3QtrTotalRTG)))),"Daypart1 net CPP at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grpValueAtDaypart1GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(prd1QtrTotalRTG + prd3QtrTotalRTG)),"Daypart1 grp at primary grouped row is not expected");

			
			String grossrateValueAtDaypart2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart2,AutoConfigPlannerWorkSpace.ratetotalQtr);
			String netrateValueAtDaypart2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart2,AutoConfigPlannerWorkSpace.netRatetotalQtr);
			String imps000ValueAtDaypart2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart2,AutoConfigPlannerWorkSpace.impstotalQtr);
			String grossCPMValueAtDaypart2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart2,AutoConfigPlannerWorkSpace.cpmtotalQtr);
			String netCPMValueAtDaypart2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart2,AutoConfigPlannerWorkSpace.netCpmtotalQtr);
			String grossCPPValueAtDaypart2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart2,AutoConfigPlannerWorkSpace.grossCpptotalQtr);
			String netCPPValueAtDaypart2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart2,AutoConfigPlannerWorkSpace.netCpptotalQtr);
			String grpValueAtDaypart2GroupPinedRowsQtr= planworkspace.getMetricsValueAtGroupRowsQtr(dayPart2,AutoConfigPlannerWorkSpace.grptotalQtr);
			
			Logger.log("grossrateValueAtDaypart2GroupPinedRowsQtr: "+grossrateValueAtDaypart2GroupPinedRowsQtr+"\n netrateValueAtDaypart2GroupPinedRowsQtr: "+netrateValueAtDaypart2GroupPinedRowsQtr+"\n imps000ValueAtDaypart2GroupPinedRowsQtr: "+imps000ValueAtDaypart2GroupPinedRowsQtr+
					"\n grossCPMValueAtDaypart2GroupPinedRowsQtr: "+grossCPMValueAtDaypart2GroupPinedRowsQtr+"\n netCPMValueAtDaypart2GroupPinedRowsQtr: "+netCPMValueAtDaypart2GroupPinedRowsQtr+"\n grossCPPValueAtDaypart2GroupPinedRowsQtr: "+grossCPPValueAtDaypart2GroupPinedRowsQtr+
					"\n netCPPValueAtDaypart2GroupPinedRowsQtr: "+netCPPValueAtDaypart2GroupPinedRowsQtr+"\n grpValueAtDaypart2GroupPinedRowsQtr: "+grpValueAtDaypart2GroupPinedRowsQtr);
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossrateValueAtDaypart2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossRateValueQtrPrd2)+Double.valueOf(grossRateValueQtrPrd4))),"Daypart2 rate at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netrateValueAtDaypart2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netRateValueQtrPrd2)+Double.valueOf(netRateValueQtrPrd4))),"Daypart2 net rate at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(imps000ValueAtDaypart2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(prd2QtrTotalImps000+prd4QtrTotalImps000)),"Daypart2 000 at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPMValueAtDaypart2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtDaypart2GroupPinedRowsQtr)/ (prd2QtrTotalImps000+prd4QtrTotalImps000))),"Daypart2 gross CPM at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPMValueAtDaypart2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtDaypart2GroupPinedRowsQtr)/ (prd2QtrTotalImps000+prd4QtrTotalImps000))),"Daypart2 net CPM at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPPValueAtDaypart2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtDaypart2GroupPinedRowsQtr)/ Double.valueOf(grpValueAtDaypart2GroupPinedRowsQtr))),"Daypart2 gross CPP at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPPValueAtDaypart2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtDaypart2GroupPinedRowsQtr)/ Double.valueOf(grpValueAtDaypart2GroupPinedRowsQtr))),"Daypart2 net CPP at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grpValueAtDaypart2GroupPinedRowsQtr), getBasePage().
					format2Digi(String.valueOf(prd2QtrTotalRTG+prd4QtrTotalRTG)),"Daypart2 grp at primary grouped row is not expected");


			
			
	    }
	    finally {
			//Deselect Grouping
			planworkspace.deselectGrouping();
			planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.defaultWorksapceColumn);
			getCustomeVerification().assertAll(getSoftAssert());
		}
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
