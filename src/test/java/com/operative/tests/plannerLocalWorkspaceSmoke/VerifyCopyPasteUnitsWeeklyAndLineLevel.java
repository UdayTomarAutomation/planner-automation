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
 * @author mansi_vyas
 *
 */
public class VerifyCopyPasteUnitsWeeklyAndLineLevel extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass = PlannerWorkSpaceDataprovider.class, dataProvider = "copyPasteUnitsInLineAndWeekly")
	public void verifyCopyPasteUnitsWeeklyAndLineLevel(String planClass, String station,
			String flightDates, String flightStartDate, String flightEndDate, String advertiser, String buyingAgency,
			String accountExecutive, String calenderValue, String startPeriodValue, String endPeriodValue, String demo,
			String dayPart, String marketPlace, String ratingStream, String product, String spotValue, String product2)
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
		planworkspace.addMultipleProductInChooserWindowIndexBased(product+";"+product2,"1").clickAddSelectedProductLocal();
		
		// enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(product, dayPart, spotValue, 1,1);
//		planworkspace.enterWorkSpaceDataInWeeklySpotsRowAndIndex(product2, dayPart, spotValue,2, 1);
		
		// Save Workspace
		planworkspace.clickSaveWorkSpace();

		workSpacebook.clickonBookChooser();

		workSpacebook.selectedBooks(AutoConfigPlannerWorkSpace.bookAug21+";"+AutoConfigPlannerWorkSpace.bookJul21).clickNext();
		// apply Book for Selected product
		workSpacebook.applyBookForSelectedProductSelectAll(product, AutoConfigPlannerWorkSpace.bookAug21, qtrName);
		workSpacebook.clickBackButton().clickNext();
		workSpacebook.applyBookForSelectedProductSelectAll(product2, AutoConfigPlannerWorkSpace.bookAug21, qtrName);

		workSpacebook.clickApply();
		
		String weeklyCellvalue1=planworkspace.getWeeklySpotsIndexbased(product, dayPart, 1, 1);
		//Copy units from 1st row 1st cell
		planworkspace.copypasteWeeklyUnitsFromWeeklyCell(product,dayPart,1,1,AutoConfigPlannerWorkSpace.copyUnits);
		
		//paste unit into 1st row 2nd cell
		planworkspace.copypasteWeeklyUnitsFromWeeklyCell(product,dayPart,1,2,AutoConfigPlannerWorkSpace.pasteUnits);
		
		String weeklyCellvalue2=planworkspace.getWeeklySpotsIndexbased(product,dayPart, 1, 2);
		
		
		getCustomeVerification().verifyEquals(getSoftAssert(),weeklyCellvalue1, weeklyCellvalue2,"Weekly spot value is not copied and paste for row 1");
		
		//paste unit into 2nd row 2nd cell
		planworkspace.copypasteWeeklyUnitsFromWeeklyCell(product2,dayPart,2,2,AutoConfigPlannerWorkSpace.pasteUnits);
				
		String weeklyCellvalue2Row2=planworkspace.getWeeklySpotsIndexbased(product, dayPart, 2, 2);
		
		getCustomeVerification().verifyEquals(getSoftAssert(),weeklyCellvalue1, weeklyCellvalue2Row2,"Weekly spot value is not copied and paste for row 2 cell 2");
		
		//Remove units from weekly spot 2 row 2
		planworkspace.copypasteWeeklyUnitsFromWeeklyCell(product2,dayPart,2,2,AutoConfigPlannerWorkSpace.removeUnits);
		
		String weeklyCellvalue2Row2UnitRemove=planworkspace.getWeeklySpotsIndexbased(product2, dayPart, 2, 2);
		
		getCustomeVerification().verifyEquals(getSoftAssert(),weeklyCellvalue2Row2UnitRemove, "0","Weekly spot value is not removed from row 2 cell 2");
		
		//Copy units from row1 to row 2
		planworkspace.enterWorkSpaceDataInWeeklySpotsRowBased(product,dayPart,spotValue,1);
		//Save Workspace
		planworkspace.clickSaveWorkSpace();
		String weeklyUnitsProduct1[] =planworkspace.getWorkSpaceDataInWeeklySpots(product,dayPart, 1);
		
		//Copy units from product line 1
		planworkspace.copypasteWeeklyUnitsFroProductLine(product,dayPart,1,AutoConfigPlannerWorkSpace.copyUnits);
		
		//Paste units from product line 2
		planworkspace.copypasteWeeklyUnitsFroProductLine(product2,dayPart,2,AutoConfigPlannerWorkSpace.pasteUnits);
		
		String weeklyUnitsProduct2[] =planworkspace.getWorkSpaceDataInWeeklySpots(product2,dayPart, 2);
		
		for(int i=0;i<weeklyUnitsProduct1.length;i++) {
		getCustomeVerification().verifyEquals(getSoftAssert(),weeklyUnitsProduct1[i], weeklyUnitsProduct2[i],"Weekly spot value are not copied from product line 1 to product line 2");
		}
		
		//Remove units from product line 1
		planworkspace.copypasteWeeklyUnitsFroProductLine(product,dayPart,1,AutoConfigPlannerWorkSpace.removeUnits);
		String weeklyUnitsProduct1AfterRemove[] =planworkspace.getWorkSpaceDataInWeeklySpots(product,dayPart, 1);		
		
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
