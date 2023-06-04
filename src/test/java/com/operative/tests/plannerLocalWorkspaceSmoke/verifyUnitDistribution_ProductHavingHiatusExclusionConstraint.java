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
public class verifyUnitDistribution_ProductHavingHiatusExclusionConstraint  extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="localSmokeSuitePlannerUnitsDistribution")
	public void verifyUnitDistributionProductHavingHiatusExclusionConstraint(String planClass,String station,String flightDates,String flightStartDate,String flightEndDate,
			String advertiser,String buyingAgency,String accountExecutive,String calenderValue,String startPeriodValue,String endPeriodValue,String demo,String dayPart,String marketPlace,
			String ratingStream,String products,String ratecard, String totalUnits, String editedDateRange) throws InterruptedException, ParseException
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
//		planHeaderPage.selectRateCardName(ratecard);
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
		planworkspace.addMultipleProductInChooserWindowIndexBased(product[0]+";"+product[1]+";"+product[2],"1").clickAddSelectedProductLocal();
		
		//select colmns
		planworkspace.reselectClmnsWorkspaceQrtly(AutoConfigPlannerWorkSpace.defaultWorksapceColumn);
		
		workSpacebook.clickonBookChooser();

	    workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
	     
	    // apply Book for Selected product
	    workSpacebook.applyBookForMultipleProductSelectAll(product[0],"1",
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);
	    workSpacebook.clickBackButton().clickNext();
	    workSpacebook.applyBookForMultipleProductSelectAll(product[1],"2",
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);
	    workSpacebook.clickBackButton().clickNext();
	    workSpacebook.applyBookForMultipleProductSelectAll(product[2],"3",
	    		AutoConfigPlannerWorkSpace.bookAug21, qtrName);
	    workSpacebook.clickApply();
		
	   //enter updated rate value on qtr field
	    planworkspace.enterAggregateTotalValueQtr(product[2], 
				dayPart, AutoConfigPlannerWorkSpace.unitstotalQtr,totalUnits,2);
		planworkspace.enterAggregateTotalValueQtr(product[0], 
				dayPart, AutoConfigPlannerWorkSpace.unitstotalQtr,totalUnits,0);
		planworkspace.enterAggregateTotalValueQtr(product[1], 
				dayPart, AutoConfigPlannerWorkSpace.unitstotalQtr,totalUnits,1);
		
		
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		String weeklyUnitsPrd1[] = planworkspace.getWorkSpaceDataInWeeklySpots(product[0],dayPart,1);
		String weeklyUnitsPrd2[] = planworkspace.getWorkSpaceDataInWeeklySpots(product[1],dayPart,2);
		String weeklyUnitsPrd3[] = planworkspace.getWorkSpaceDataInWeeklySpots(product[2],dayPart,3);
		
		//Verify units distributed for product 1
		for (int i=0;i<weeklyUnitsPrd1.length;i++) {
		getCustomeVerification().verifyFalse(getSoftAssert(), weeklyUnitsPrd1[i].equals("0"),"Weekly units is not distributed for product 1");
		}
		
		//Verify units distributed for product 2
		for (int i=0;i<weeklyUnitsPrd2.length;i++) {
		getCustomeVerification().verifyFalse(getSoftAssert(), weeklyUnitsPrd2[i].equals("0"),"Weekly units is not distributed for product 2");
		}
		
		//Verify units distributed for product 3
		for (int i=0;i<weeklyUnitsPrd3.length;i++) {
		getCustomeVerification().verifyFalse(getSoftAssert(), weeklyUnitsPrd3[i].equals("0"),"Weekly units is not distributed for product 3");
		}
		
		Integer totalWeeklyUnitsProduct1 = 0 ;
		Integer totalWeeklyUnitsProduct2 = 0;
		Integer totalWeeklyUnitsProduct3 = 0;
		//Total units for products
		for (int i=0;i<weeklyUnitsPrd1.length;i++) {
			totalWeeklyUnitsProduct1=totalWeeklyUnitsProduct1 + Integer.valueOf(weeklyUnitsPrd1[i]);
			}
		
		for (int i=0;i<weeklyUnitsPrd2.length;i++) {
			totalWeeklyUnitsProduct2=totalWeeklyUnitsProduct2 + Integer.valueOf(weeklyUnitsPrd2[i]);
			}
		
		for (int i=0;i<weeklyUnitsPrd3.length;i++) {
			totalWeeklyUnitsProduct3=totalWeeklyUnitsProduct3 + Integer.valueOf(weeklyUnitsPrd3[i]);
			}
		
		Logger.log("totalWeeklyUnitsProduct1: "+totalWeeklyUnitsProduct1+"\n totalWeeklyUnitsProduct2: "+totalWeeklyUnitsProduct2+"\n totalWeeklyUnitsProduct3: "+totalWeeklyUnitsProduct3);
		
		getCustomeVerification().verifyEquals(getSoftAssert(), totalWeeklyUnitsProduct1, Integer.valueOf(totalUnits),"Weekly units for product 1 is incorrect");
		getCustomeVerification().verifyEquals(getSoftAssert(), totalWeeklyUnitsProduct2, Integer.valueOf(totalUnits),"Weekly units for product 2 is incorrect");
		getCustomeVerification().verifyEquals(getSoftAssert(), totalWeeklyUnitsProduct3, Integer.valueOf(totalUnits),"Weekly units for product 3 is incorrect");
		
		//Verify Weekly cell having product Haitus
		getCustomeVerification().verifyTrue(getSoftAssert(),planworkspace.verifyWeeklyCell(product[0],dayPart, 2, 1, AutoConfigPlannerWorkSpace.productHiatus),"Product 2 weekly spot 1 is not having product haitus");
		getCustomeVerification().verifyTrue(getSoftAssert(),planworkspace.verifyWeeklyCell(product[0],dayPart, 3, 4, AutoConfigPlannerWorkSpace.productHiatus),"Product 3 weekly spot 4 is not having product haitus");
		getCustomeVerification().verifyTrue(getSoftAssert(),planworkspace.verifyWeeklyCell(product[0],dayPart, 3, 5, AutoConfigPlannerWorkSpace.productHiatus),"Product 3 weekly spot 5 is not having product haitus");
		
		//Edit Product Attributes and check weekly units
		workSpacebook.addConstraintOrEditProductAttributes(1, editedDateRange);
		getCustomeVerification().verifyFalse(getSoftAssert(),planworkspace.verifyWeeklyCell(product[0],dayPart, 1, 1, AutoConfigPlannerWorkSpace.lineConstraint),"Product 1 weekly spot 1 is disable");
		getCustomeVerification().verifyFalse(getSoftAssert(),planworkspace.verifyWeeklyCell(product[0],dayPart, 1, 2, AutoConfigPlannerWorkSpace.lineConstraint),"Product 1 weekly spot 2 is disable");
		getCustomeVerification().verifyFalse(getSoftAssert(),planworkspace.verifyWeeklyCell(product[0],dayPart, 1, 3, AutoConfigPlannerWorkSpace.lineConstraint),"Product 1 weekly spot 3 is disable");
		getCustomeVerification().verifyTrue(getSoftAssert(),planworkspace.verifyWeeklyCell(product[0],dayPart, 1, 4, AutoConfigPlannerWorkSpace.lineConstraint),"Product 1 weekly spot 4 is enable");
		getCustomeVerification().verifyTrue(getSoftAssert(),planworkspace.verifyWeeklyCell(product[0],dayPart, 1, 5, AutoConfigPlannerWorkSpace.lineConstraint),"Product 1 weekly spot 5 is enable");
		//getCustomeVerification().verifyTrue(getSoftAssert(),planworkspace.verifyWeeklyCell(product[0],dayPart, 1, 6, AutoConfigPlannerWorkSpace.lineConstraint),"Product 1 weekly spot 6 is enable");
		
		String weeklyUnitsPrd1AfterAddingConstraint[] = planworkspace.getWorkSpaceDataInWeeklySpots(product[0],dayPart,1);
		
		Integer totalWeeklyUnitsProduct1AfterAddingConstraint = 0;
		//Total units for products
		for (int i=0;i<weeklyUnitsPrd1AfterAddingConstraint.length;i++) {
			totalWeeklyUnitsProduct1AfterAddingConstraint=totalWeeklyUnitsProduct1AfterAddingConstraint + Integer.valueOf(weeklyUnitsPrd1AfterAddingConstraint[i]);
			}
		
		String grossRateValueQtrPrd1=planworkspace.getAggregateTotalValueQtrBasedOnRowID(product[0], 
  				dayPart, AutoConfigPlannerWorkSpace.unitstotalQtr,1).replace(",", "").replace("$", "");
		
		getCustomeVerification().verifyEquals(getSoftAssert(), totalWeeklyUnitsProduct1AfterAddingConstraint, Integer.valueOf(grossRateValueQtrPrd1),"Weekly units for product 1 is incorrect after adding constraint");
		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
