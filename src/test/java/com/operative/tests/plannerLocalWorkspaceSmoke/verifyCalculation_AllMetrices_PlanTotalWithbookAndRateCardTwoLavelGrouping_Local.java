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


public class  verifyCalculation_AllMetrices_PlanTotalWithbookAndRateCardTwoLavelGrouping_Local extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="localSmokeSuitePlannerDataGrouping")
	public void verifyCalculationAllMetricesPlanTotalWithbookAndRateCardTwoLavelGrouping(String planClass,String station,String flightDates,String flightStartDate,String flightEndDate,
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
		Double prd1PlanTotalImps000 = Double.valueOf(cellImpsPrd1) * Double.valueOf(spotValue);
		
		//Get Cell Property RTG Value
		String cellRTGPrd1=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		Double prd1PlanTotalRTG = Double.valueOf(cellRTGPrd1) * Double.valueOf(spotValue);
		
		Logger.log("cellImpsPrd1 is==>>"+cellImpsPrd1+"\n cellRTGPrd1 is ==> "+cellRTGPrd1);
		Logger.log("prd1PlanTotalImps000 is==>>"+prd1PlanTotalImps000+"\n prd1PlanTotalRTG is ==> "+prd1PlanTotalRTG);
		
		 //click weekly spots value for product 2
		planworkspace.clickWeeklySpotsValueBasedOnRowId(product[1], dayPart2,2,1);
		
		//Get Cell Property imps Value
		String cellImpsPrd2=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		Double prd2PlanTotalImps000 = Double.valueOf(cellImpsPrd2) * Double.valueOf(spotValue);
		
		//Get Cell Property RTG Value
		String cellRTGPrd2=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		Double prd2PlanTotalRTG = Double.valueOf(cellRTGPrd2) * Double.valueOf(spotValue);
		
		Logger.log("cellImpsPrd2 is==>>"+cellImpsPrd2+"\n cellRTGPrd2 is ==> "+cellRTGPrd2);
		Logger.log("prdPlanTotalImps000 is==>>"+prd2PlanTotalImps000+"\n prd2PlanTotalRTG is ==> "+prd2PlanTotalRTG);
		
		
		//click weekly spots value for product 3
		planworkspace.clickWeeklySpotsValueBasedOnRowId(product[0], dayPart1,3,1);
		
		//Get Cell Property imps Value
		String cellImpsPrd3=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		Double prd3PlanTotalImps000 = Double.valueOf(cellImpsPrd3) * Double.valueOf(spotValue);
		
		//Get Cell Property RTG Value
		String cellRTGPrd3=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		Double prd3PlanTotalRTG = Double.valueOf(cellRTGPrd3) * Double.valueOf(spotValue);
		
		Logger.log("cellImpsPrd3 is==>>"+cellImpsPrd3+"\n cellRTGPrd3 is ==> "+cellRTGPrd3);
		Logger.log("prd3PlanTotalImps000 is==>>"+prd3PlanTotalImps000+"\n prd3PlanTotalRTG is ==> "+prd3PlanTotalRTG);
		
		//click weekly spots value for product 3
		planworkspace.clickWeeklySpotsValueBasedOnRowId(product[1], dayPart2,4,1);
		
		//Get Cell Property imps Value
		String cellImpsPrd4=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps).replace(",", "").replace("$", "");
		Double prd4PlanTotalImps000 = Double.valueOf(cellImpsPrd4) * Double.valueOf(spotValue);
		
		//Get Cell Property RTG Value
		String cellRTGPrd4=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellrtg).replace(",", "").replace("$", "");	
		Double prd4PlanTotalRTG = Double.valueOf(cellRTGPrd4) * Double.valueOf(spotValue);
		
		Logger.log("cellImpsPrd4 is==>>"+cellImpsPrd4+"\n cellRTGPrd4 is ==> "+cellRTGPrd4);
		Logger.log("prd4PlanTotalImps000 is==>>"+prd4PlanTotalImps000+"\n prd3PlanTotalRTG is ==> "+prd4PlanTotalRTG);

		//Navigate to Plan Total Tab
		planworkspace.navigateToPlanTotalTab();
		
	    //Select columns on workspace
	    planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.qtrNetRate+";"+AutoConfigPlannerWorkSpace.qtr000+";"+AutoConfigPlannerWorkSpace.qtrGrossCPM+";"+AutoConfigPlannerWorkSpace.qtrNetCPM+";"+AutoConfigPlannerWorkSpace.qtrGrossCPP+";"+AutoConfigPlannerWorkSpace.qtrNetCPP+";"+AutoConfigPlannerWorkSpace.qtrGRP);
	  	
	  //get rate value in total qtr
  		String grossRateValuePlanTotalPrd1=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.ratetotalQtr,1).replace(",", "").replace("$", "");
  		String grossRateValuePlanTotalPrd2=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
  				dayPart2, AutoConfigPlannerWorkSpace.ratetotalQtr,2).replace(",", "").replace("$", "");
  		String grossRateValuePlanTotalPrd3=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.ratetotalQtr,3).replace(",", "").replace("$", "");
  		String grossRateValuePlanTotalPrd4=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
  				dayPart2, AutoConfigPlannerWorkSpace.ratetotalQtr,4).replace(",", "").replace("$", "");
  		
  		Logger.log("grossRateValuePlanTotalPrd1 "+grossRateValuePlanTotalPrd1+"\n grossRateValuePlanTotalPrd2 "+grossRateValuePlanTotalPrd2+"\n grossRateValuePlanTotalPrd3 "+grossRateValuePlanTotalPrd3+"\n grossRateValuePlanTotalPrd4"+grossRateValuePlanTotalPrd4);
  		
  		String netRateValuePlanTotalPrd1=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.netRatetotalQtr,1).replace(",", "").replace("$", "");
  		String netRateValuePlanTotalPrd2=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
  				dayPart2, AutoConfigPlannerWorkSpace.netRatetotalQtr,2).replace(",", "").replace("$", "");
  		String netRateValuePlanTotalPrd3=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.netRatetotalQtr,3).replace(",", "").replace("$", "");
  		String netRateValuePlanTotalPrd4=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
  				dayPart2, AutoConfigPlannerWorkSpace.netRatetotalQtr,4).replace(",", "").replace("$", "");
  		
  		Logger.log("netRateValuePlanTotalPrd1 "+netRateValuePlanTotalPrd1+"\n netRateValuePlanTotalPrd2 "+netRateValuePlanTotalPrd2+"\n netRateValuePlanTotalPrd3 "+netRateValuePlanTotalPrd3+"\n netRateValuePlanTotalPrd4"+netRateValuePlanTotalPrd4);
  		
  		String imps000ValuePlanTotalPrd1=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.impstotalQtr,1).replace(",", "").replace("$", "");
  		String imps000ValuePlanTotalPrd2=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.impstotalQtr,2).replace(",", "").replace("$", "");
  		String imps000ValuePlanTotalPrd3=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.impstotalQtr,3).replace(",", "").replace("$", "");
  		String imps000ValuePlanTotalPrd4=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
  				dayPart1, AutoConfigPlannerWorkSpace.impstotalQtr,4).replace(",", "").replace("$", "");
  		
  		Logger.log("imps000ValuePlanTotalPrd1 "+imps000ValuePlanTotalPrd1+"\n imps000ValuPlanTotalPrd2 "+imps000ValuePlanTotalPrd2+"\n imps000ValuePlanTotalPrd3 "+imps000ValuePlanTotalPrd3+"\n imps000ValuePlanTotalPrd4"+imps000ValuePlanTotalPrd4);
  		
  		String cpmValuePlanTotalPrd1=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.cpmtotalQtr,1).replace(",", "").replace("$", "");
  		String cpmValuePlanTotalPrd2=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.cpmtotalQtr,2).replace(",", "").replace("$", "");
  		String cpmValuePlanTotalPrd3=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.cpmtotalQtr,3).replace(",", "").replace("$", "");
  		String cpmValuePlanTotalPrd4=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.cpmtotalQtr,4).replace(",", "").replace("$", "");
		
  		Logger.log("cpmValuePlanTotalPrd1 "+cpmValuePlanTotalPrd1+"\n cpmValuePlanTotalPrd2 "+cpmValuePlanTotalPrd2+"\n cpmValuePlanTotalPrd3 "+cpmValuePlanTotalPrd3+"\n cpmValuePlanTotalPrd4"+cpmValuePlanTotalPrd4);
  		
		String netCpmValuePlanTotalPrd1=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.netCpmtotalQtr,1).replace(",", "").replace("$", "");
		String netCpmValuePlanTotalPrd2=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.netCpmtotalQtr,2).replace(",", "").replace("$", "");
		String netCpmValuePlanTotalPrd3=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.netCpmtotalQtr,3).replace(",", "").replace("$", "");
		String netCpmValuePlanTotalPrd4=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.netCpmtotalQtr,4).replace(",", "").replace("$", "");
		
		Logger.log("netCpmValuePlanTotalPrd1 "+netCpmValuePlanTotalPrd1+"\n netCpmValuePlanTotalPrd2 "+netCpmValuePlanTotalPrd2+"\n netCpmValuePlanTotalPrd3 "+netCpmValuePlanTotalPrd3+"\n netCpmValuePlanTotalPrd4"+netCpmValuePlanTotalPrd4);
		
		String grossCppValuePlanTotalPrd1=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.grossCpptotalQtr,1).replace(",", "").replace("$", "");
		String grossCppValuePlanTotalPrd2=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.grossCpptotalQtr,2).replace(",", "").replace("$", "");
		String grossCppValuePlanTotalPrd3=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.grossCpptotalQtr,3).replace(",", "").replace("$", "");
		String grossCppValuePlanTotalPrd4=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.grossCpptotalQtr,4).replace(",", "").replace("$", "");
		
		Logger.log("grossCppValuePlanTotalPrd1 "+grossCppValuePlanTotalPrd1+"\n grossCppValuePlanTotalPrd2 "+grossCppValuePlanTotalPrd2+"\n grossCppValuePlanTotalPrd3 "+grossCppValuePlanTotalPrd3+"\n grossCppValuePlanTotalPrd4"+grossCppValuePlanTotalPrd4);
		
		String netCppValuePlanTotalPrd1=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.netCpptotalQtr,1).replace(",", "").replace("$", "");
		String netCppValuePlanTotalPrd2=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.netCpptotalQtr,2).replace(",", "").replace("$", "");
		String netCppValuePlanTotalPrd3=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.netCpptotalQtr,3).replace(",", "").replace("$", "");
		String netCppValuePlanTotalPrd4=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.netCpptotalQtr,4).replace(",", "").replace("$", "");
  		
		Logger.log("netCppValuePlanTotalPrd1 "+netCppValuePlanTotalPrd1+"\n netCppValuePlanTotalPrd2 "+netCppValuePlanTotalPrd2+"\n netCppValuePlanTotalPrd3 "+netCppValuePlanTotalPrd3+"\n netCppValuePlanTotalPrd4"+netCppValuePlanTotalPrd4);
		
		String grpValuePlanTotalPrd1=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.grptotalQtr,1).replace(",", "").replace("$", "");
		String grpValuePlanTotalPrd2=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.grptotalQtr,2).replace(",", "").replace("$", "");
		String grpValuePlanTotalPrd3=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[0], 
				dayPart1, AutoConfigPlannerWorkSpace.grptotalQtr,3).replace(",", "").replace("$", "");
		String grpValuePlanTotalPrd4=planworkspace.getAggregateTotalValuePlanTotalBasedOnRowID(product[1], 
				dayPart2, AutoConfigPlannerWorkSpace.grptotalQtr,4).replace(",", "").replace("$", "");
  		
		Logger.log("grpValuePlanTotalPrd1 "+grpValuePlanTotalPrd1+"\n grpValuePlanTotalPrd2 "+grpValuePlanTotalPrd2+"\n grpValuePlanTotalPrd3 "+grpValuePlanTotalPrd3+"\n grpValuePlanTotalPrd4"+grpValuePlanTotalPrd4);
		
		try {
	    	
		    planworkspace.selectGrouping(1,AutoConfigPlannerWorkSpace.groupByDayPart);

			planworkspace.selectGrouping(1,AutoConfigPlannerWorkSpace.groupByProduct);
			
			//Get Grouped metrics value at Product level
			String grossrateValueAtProduct1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[0],AutoConfigPlannerWorkSpace.ratetotalQtr);
			String netrateValueAtProduct1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[0],AutoConfigPlannerWorkSpace.netRatetotalQtr);
			String imps000ValueAtProduct1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[0],AutoConfigPlannerWorkSpace.impstotalQtr);
			String grossCPMValueAtProduct1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[0],AutoConfigPlannerWorkSpace.cpmtotalQtr);
			String netCPMValueAtProduct1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[0],AutoConfigPlannerWorkSpace.netCpmtotalQtr);
			String grossCPPValueAtProduct1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[0],AutoConfigPlannerWorkSpace.grossCpptotalQtr);
			String netCPPValueAtProduct1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[0],AutoConfigPlannerWorkSpace.netCpptotalQtr);
			String grpValueAtProduct1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[0],AutoConfigPlannerWorkSpace.grptotalQtr);
			
			Logger.log("grossrateValueAtProduct1GroupPinedRowsPlanTotal: "+grossrateValueAtProduct1GroupPinedRowsPlanTotal+"\n netrateValueAtProduct1GroupPinedRowsPlanTotal: "+netrateValueAtProduct1GroupPinedRowsPlanTotal+"\n imps000ValueAtProduct1GroupPinedRowsPlanTotal: "+imps000ValueAtProduct1GroupPinedRowsPlanTotal+
					"\n grossCPMValueAtProduct1GroupPinedRowsPlanTotal: "+grossCPMValueAtProduct1GroupPinedRowsPlanTotal+"\n netCPMValueAtProduct1GroupPinedRowsPlanTotal: "+netCPMValueAtProduct1GroupPinedRowsPlanTotal+"\n grossCPPValueAtProduct1GroupPinedRowsPlanTotal: "+grossCPPValueAtProduct1GroupPinedRowsPlanTotal+
					"\n netCPPValueAtProduct1GroupPinedRowsPlanTotal: "+netCPPValueAtProduct1GroupPinedRowsPlanTotal+"\n grpValueAtProduct1GroupPinedRowsPlanTotal: "+grpValueAtProduct1GroupPinedRowsPlanTotal);
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossrateValueAtProduct1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossRateValuePlanTotalPrd1)+Double.valueOf(grossRateValuePlanTotalPrd3))),"Product1 rate at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netrateValueAtProduct1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netRateValuePlanTotalPrd1)+Double.valueOf(netRateValuePlanTotalPrd3))),"Product1 net rate at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(imps000ValueAtProduct1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(prd1PlanTotalImps000+prd3PlanTotalImps000)),"Product1 000 at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPMValueAtProduct1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct1GroupPinedRowsPlanTotal)/ (prd1PlanTotalImps000+prd3PlanTotalImps000))),"Product1 gross CPM at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPMValueAtProduct1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct1GroupPinedRowsPlanTotal)/ (prd1PlanTotalImps000+prd3PlanTotalImps000))),"Product1 net CPM at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPPValueAtProduct1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct1GroupPinedRowsPlanTotal)/ Double.valueOf(grpValueAtProduct1GroupPinedRowsPlanTotal))),"Product1 gross CPP at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPPValueAtProduct1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct1GroupPinedRowsPlanTotal)/ Double.valueOf(grpValueAtProduct1GroupPinedRowsPlanTotal))),"Product1 net CPP at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grpValueAtProduct1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(prd1PlanTotalRTG + prd2PlanTotalRTG)),"Product1 grp at secondary grouped row is not expected");

			
			String grossrateValueAtProduct2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[1],AutoConfigPlannerWorkSpace.ratetotalQtr);
			String netrateValueAtProduct2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[1],AutoConfigPlannerWorkSpace.netRatetotalQtr);
			String imps000ValueAtProduct2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[1],AutoConfigPlannerWorkSpace.impstotalQtr);
			String grossCPMValueAtProduct2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[1],AutoConfigPlannerWorkSpace.cpmtotalQtr);
			String netCPMValueAtProduct2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[1],AutoConfigPlannerWorkSpace.netCpmtotalQtr);
			String grossCPPValueAtProduct2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[1],AutoConfigPlannerWorkSpace.grossCpptotalQtr);
			String netCPPValueAtProduct2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[1],AutoConfigPlannerWorkSpace.netCpptotalQtr);
			String grpValueAtProduct2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(product[1],AutoConfigPlannerWorkSpace.grptotalQtr);
			
			Logger.log("grossrateValueAtProduct2GroupPinedRowsPlanTotal: "+grossrateValueAtProduct2GroupPinedRowsPlanTotal+"\n netrateValueAtProduct2GroupPinedRowsPlanTotal: "+netrateValueAtProduct2GroupPinedRowsPlanTotal+"\n imps000ValueAtProduct2GroupPinedRowsPlanTotal: "+imps000ValueAtProduct2GroupPinedRowsPlanTotal+
					"\n grossCPMValueAtProduct2GroupPinedRowsPlanTotal: "+grossCPMValueAtProduct2GroupPinedRowsPlanTotal+"\n netCPMValueAtProduct2GroupPinedRowsPlanTotal: "+netCPMValueAtProduct2GroupPinedRowsPlanTotal+"\n grossCPPValueAtProduct2GroupPinedRowsPlanTotal: "+grossCPPValueAtProduct2GroupPinedRowsPlanTotal+
					"\n netCPPValueAtProduct2GroupPinedRowsPlanTotal: "+netCPPValueAtProduct2GroupPinedRowsPlanTotal+"\n grpValueAtProduct2GroupPinedRowsPlanTotal: "+grpValueAtProduct2GroupPinedRowsPlanTotal);
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossrateValueAtProduct2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossRateValuePlanTotalPrd2)+Double.valueOf(grossRateValuePlanTotalPrd4))),"Product2 rate at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netrateValueAtProduct2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netRateValuePlanTotalPrd2)+Double.valueOf(netRateValuePlanTotalPrd4))),"Product2 net rate at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(imps000ValueAtProduct2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(prd2PlanTotalImps000+prd4PlanTotalImps000)),"Product2 000 at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPMValueAtProduct2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct2GroupPinedRowsPlanTotal)/ (prd2PlanTotalImps000+prd4PlanTotalImps000))),"Product2 gross CPM at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPMValueAtProduct2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct2GroupPinedRowsPlanTotal)/ (prd2PlanTotalImps000+prd4PlanTotalImps000))),"Product2 net CPM at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPPValueAtProduct2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct2GroupPinedRowsPlanTotal)/ Double.valueOf(grpValueAtProduct2GroupPinedRowsPlanTotal))),"Product2 gross CPP at secondary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPPValueAtProduct2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct2GroupPinedRowsPlanTotal)/ Double.valueOf(grpValueAtProduct2GroupPinedRowsPlanTotal))),"Product2 net CPP at secondary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grpValueAtProduct2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(prd2PlanTotalRTG + prd4PlanTotalRTG)),"Product2 grp at secondary gouped row is not expected");

			/*
			 * Verify primary grouping pinged rows values
			 */
			String grossrateValueAtDaypart1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart1,AutoConfigPlannerWorkSpace.ratetotalQtr);
			String netrateValueAtDaypart1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart1,AutoConfigPlannerWorkSpace.netRatetotalQtr);
			String imps000ValueAtDaypart1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart1,AutoConfigPlannerWorkSpace.impstotalQtr);
			String grossCPMValueAtDaypart1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart1,AutoConfigPlannerWorkSpace.cpmtotalQtr);
			String netCPMValueAtDaypart1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart1,AutoConfigPlannerWorkSpace.netCpmtotalQtr);
			String grossCPPValueAtDaypart1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart1,AutoConfigPlannerWorkSpace.grossCpptotalQtr);
			String netCPPValueAtDaypart1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart1,AutoConfigPlannerWorkSpace.netCpptotalQtr);
			String grpValueAtDaypart1GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart1,AutoConfigPlannerWorkSpace.grptotalQtr);
			
			Logger.log("grossrateValueAtDaypart1GroupPinedRowsPlanTotal: "+grossrateValueAtDaypart1GroupPinedRowsPlanTotal+"\n netrateValueAtDaypart1GroupPinedRowsPlanTotal: "+netrateValueAtDaypart1GroupPinedRowsPlanTotal+"\n imps000ValueAtDaypart1GroupPinedRowsPlanTotal: "+imps000ValueAtDaypart1GroupPinedRowsPlanTotal+
					"\n grossCPMValueAtDaypart1GroupPinedRowsPlanTotal: "+grossCPMValueAtDaypart1GroupPinedRowsPlanTotal+"\n netCPMValueAtDaypart1GroupPinedRowsPlanTotal: "+netCPMValueAtDaypart1GroupPinedRowsPlanTotal+"\n grossCPPValueAtDaypart1GroupPinedRowsPlanTotal: "+grossCPPValueAtDaypart1GroupPinedRowsPlanTotal+
					"\n netCPPValueAtDaypart1GroupPinedRowsPlanTotal: "+netCPPValueAtDaypart1GroupPinedRowsPlanTotal+"\n grpValueAtDaypart1GroupPinedRowsPlanTotal: "+grpValueAtDaypart1GroupPinedRowsPlanTotal);
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossrateValueAtDaypart1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossRateValuePlanTotalPrd1)+Double.valueOf(grossRateValuePlanTotalPrd3))),"Daypart1 rate at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netrateValueAtDaypart1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netRateValuePlanTotalPrd1)+Double.valueOf(netRateValuePlanTotalPrd3))),"Daypart1 net rate at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(imps000ValueAtDaypart1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(prd1PlanTotalImps000+prd3PlanTotalImps000)),"Daypart1 000 at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPMValueAtDaypart1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct1GroupPinedRowsPlanTotal)/ (prd1PlanTotalImps000+prd3PlanTotalImps000))),"Daypart1 gross CPM at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPMValueAtDaypart1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct1GroupPinedRowsPlanTotal)/ (prd1PlanTotalImps000+prd3PlanTotalImps000))),"Daypart1 net CPM at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPPValueAtDaypart1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtProduct1GroupPinedRowsPlanTotal)/ (prd1PlanTotalRTG + prd3PlanTotalRTG))),"Daypart1 gross CPP at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPPValueAtDaypart1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtProduct1GroupPinedRowsPlanTotal)/ ((prd1PlanTotalRTG + prd3PlanTotalRTG)))),"Daypart1 net CPP at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grpValueAtDaypart1GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(prd1PlanTotalRTG + prd3PlanTotalRTG)),"Daypart1 grp at primary grouped row is not expected");

			
			String grossrateValueAtDaypart2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart2,AutoConfigPlannerWorkSpace.ratetotalQtr);
			String netrateValueAtDaypart2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart2,AutoConfigPlannerWorkSpace.netRatetotalQtr);
			String imps000ValueAtDaypart2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart2,AutoConfigPlannerWorkSpace.impstotalQtr);
			String grossCPMValueAtDaypart2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart2,AutoConfigPlannerWorkSpace.cpmtotalQtr);
			String netCPMValueAtDaypart2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart2,AutoConfigPlannerWorkSpace.netCpmtotalQtr);
			String grossCPPValueAtDaypart2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart2,AutoConfigPlannerWorkSpace.grossCpptotalQtr);
			String netCPPValueAtDaypart2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart2,AutoConfigPlannerWorkSpace.netCpptotalQtr);
			String grpValueAtDaypart2GroupPinedRowsPlanTotal= planworkspace.getMetricsValueAtGroupRowsPlanTotal(dayPart2,AutoConfigPlannerWorkSpace.grptotalQtr);
			
			Logger.log("grossrateValueAtDaypart2GroupPinedRowsPlanTotal: "+grossrateValueAtDaypart2GroupPinedRowsPlanTotal+"\n netrateValueAtDaypart2GroupPinedRowsPlanTotal: "+netrateValueAtDaypart2GroupPinedRowsPlanTotal+"\n imps000ValueAtDaypart2GroupPinedRowsPlanTotal: "+imps000ValueAtDaypart2GroupPinedRowsPlanTotal+
					"\n grossCPMValueAtDaypart2GroupPinedRowsPlanTotal: "+grossCPMValueAtDaypart2GroupPinedRowsPlanTotal+"\n netCPMValueAtDaypart2GroupPinedRowsPlanTotal: "+netCPMValueAtDaypart2GroupPinedRowsPlanTotal+"\n grossCPPValueAtDaypart2GroupPinedRowsPlanTotal: "+grossCPPValueAtDaypart2GroupPinedRowsPlanTotal+
					"\n netCPPValueAtDaypart2GroupPinedRowsPlanTotal: "+netCPPValueAtDaypart2GroupPinedRowsPlanTotal+"\n grpValueAtDaypart2GroupPinedRowsPlanTotal: "+grpValueAtDaypart2GroupPinedRowsPlanTotal);
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossrateValueAtDaypart2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossRateValuePlanTotalPrd2)+Double.valueOf(grossRateValuePlanTotalPrd4))),"Daypart2 rate at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netrateValueAtDaypart2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netRateValuePlanTotalPrd2)+Double.valueOf(netRateValuePlanTotalPrd4))),"Daypart2 net rate at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(imps000ValueAtDaypart2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(prd2PlanTotalImps000+prd4PlanTotalImps000)),"Daypart2 000 at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPMValueAtDaypart2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtDaypart2GroupPinedRowsPlanTotal)/ (prd2PlanTotalImps000+prd4PlanTotalImps000))),"Daypart2 gross CPM at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPMValueAtDaypart2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtDaypart2GroupPinedRowsPlanTotal)/ (prd2PlanTotalImps000+prd4PlanTotalImps000))),"Daypart2 net CPM at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grossCPPValueAtDaypart2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(grossrateValueAtDaypart2GroupPinedRowsPlanTotal)/ Double.valueOf(grpValueAtDaypart2GroupPinedRowsPlanTotal))),"Daypart2 gross CPP at primary grouped row is not expected");
			
			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(netCPPValueAtDaypart2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(Double.valueOf(netrateValueAtDaypart2GroupPinedRowsPlanTotal)/ Double.valueOf(grpValueAtDaypart2GroupPinedRowsPlanTotal))),"Daypart2 net CPP at primary grouped row is not expected");

			getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(grpValueAtDaypart2GroupPinedRowsPlanTotal), getBasePage().
					format2Digi(String.valueOf(prd2PlanTotalRTG+prd4PlanTotalRTG)),"Daypart2 grp at primary grouped row is not expected");


			
			
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
