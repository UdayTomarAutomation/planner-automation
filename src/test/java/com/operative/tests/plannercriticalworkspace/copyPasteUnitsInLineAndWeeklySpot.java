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


public class  copyPasteUnitsInLineAndWeeklySpot extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuitePlannerData")
	public void copyPasteUnitsInLineWeeklySpot(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
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
		
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		String weeklyCellvalue1=planworkspace.getWeeklySpotsIndexbased(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay, 1, 1);
		//Copy units from 1st row 1st cell
		planworkspace.copypasteWeeklyUnitsFromWeeklyCell(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,1,1,AutoConfigPlannerWorkSpace.copyUnits);
		
		//paste unit into 1st row 2nd cell
		planworkspace.copypasteWeeklyUnitsFromWeeklyCell(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,1,2,AutoConfigPlannerWorkSpace.pasteUnits);
		
		String weeklyCellvalue2=planworkspace.getWeeklySpotsIndexbased(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay, 1, 2);
		
		
		getCustomeVerification().verifyEquals(getSoftAssert(),weeklyCellvalue1, weeklyCellvalue2,"Weekly spot value is not copied and paste for row 1");
		
		//paste unit into 2nd row 2nd cell
		planworkspace.copypasteWeeklyUnitsFromWeeklyCell(Product2,AutoConfigPlannerWorkSpace.dayPartDisplay,2,2,AutoConfigPlannerWorkSpace.pasteUnits);
				
		String weeklyCellvalue2Row2=planworkspace.getWeeklySpotsIndexbased(Product1, AutoConfigPlannerWorkSpace.dayPartDisplay, 2, 2);
		
		getCustomeVerification().verifyEquals(getSoftAssert(),weeklyCellvalue1, weeklyCellvalue2Row2,"Weekly spot value is not copied and paste for row 2 cell 2");
		
		//Remove units from weekly spot 2 row 2
		planworkspace.copypasteWeeklyUnitsFromWeeklyCell(Product2,AutoConfigPlannerWorkSpace.dayPartDisplay,2,2,AutoConfigPlannerWorkSpace.removeUnits);
		
		String weeklyCellvalue2Row2UnitRemove=planworkspace.getWeeklySpotsIndexbased(Product2, AutoConfigPlannerWorkSpace.dayPartDisplay, 2, 2);
		
		getCustomeVerification().verifyEquals(getSoftAssert(),weeklyCellvalue2Row2UnitRemove, "0","Weekly spot value is not removed from row 2 cell 2");
		
		//Copy units from row1 to row 2
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowBased(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,1);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		String weeklyUnitsProduct1[] =planworkspace.getWorkSpaceDataInWeeklySpots(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay, 1);
		
		//Copy units from product line 1
		planworkspace.copypasteWeeklyUnitsFroProductLine(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,1,AutoConfigPlannerWorkSpace.copyUnits);
		
		//Paste units from product line 2
		planworkspace.copypasteWeeklyUnitsFroProductLine(Product2,AutoConfigPlannerWorkSpace.dayPartDisplay,2,AutoConfigPlannerWorkSpace.pasteUnits);
		
		String weeklyUnitsProduct2[] =planworkspace.getWorkSpaceDataInWeeklySpots(Product2,AutoConfigPlannerWorkSpace.dayPartDisplay, 2);
		
		for(int i=0;i<weeklyUnitsProduct1.length;i++) {
		getCustomeVerification().verifyEquals(getSoftAssert(),weeklyUnitsProduct1[i], weeklyUnitsProduct2[i],"Weekly spot value are not copied from product line 1 to product line 2");
		}
		
		//Remove units from product line 1
		planworkspace.copypasteWeeklyUnitsFroProductLine(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay,1,AutoConfigPlannerWorkSpace.removeUnits);
		String weeklyUnitsProduct1AfterRemove[] =planworkspace.getWorkSpaceDataInWeeklySpots(Product1,AutoConfigPlannerWorkSpace.dayPartDisplay, 1);		
		
		for(int i=0;i<weeklyUnitsProduct1AfterRemove.length;i++) {
			getCustomeVerification().verifyEquals(getSoftAssert(),weeklyUnitsProduct1AfterRemove[i], "0","Weekly spot value are not removed from product line 1");
			}
		
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
