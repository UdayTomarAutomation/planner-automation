/**
 * 
 */
package com.operative.tests.plannerworkspace;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;

/**
 * @author upratap
 *
 */
public class VerifyRemoveUnitsWorkSpace  extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="removeUnitsWorkSpace")
	public void verifyRemoveUnitsWorkSpace(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String spotsValue) 
	{
		PlannerHeaderPage planHeader=new PlannerHeaderPage(getPageBrowser());
		//creating Plan Header
		planHeader.creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
	    //save Plan Header
		planHeader.clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttribute, 
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		//add product in chooser
		planworkspace.addProductInChooserWindow(AutoConfigPlannerWorkSpace.productworkSpace).clickAddClose();
		//enter the WorkSpace weekly value
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(AutoConfigPlannerWorkSpace.productworkSpace,
				AutoConfigPlannerWorkSpace.dayPartDisplay, spotsValue, 1);
		//remove Spots weekly
		planworkspace.removeUnits(AutoConfigPlannerWorkSpace.productworkSpace, 1);
		//save workspace
		planworkspace.clickSaveWorkSpace();
		//enter weekly Data
		String weeklydata[]=planworkspace.getWorkSpaceDataInWeeklySpots(AutoConfigPlannerWorkSpace.productworkSpace, 
				AutoConfigPlannerWorkSpace.dayPartDisplay,1);
	    getCustomeVerification().verifyEquals(getSoftAssert(), weeklydata[0], "0","weekly spot not same");
		getCustomeVerification().assertAll(getSoftAssert());
		

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
