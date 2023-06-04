package com.operative.tests.plannerworkspace;


import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerHeaderDataprovider;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.PageObjectManager;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;



public class EnterWeeklyspotsInAllweekWorkSpace extends PageObjectManager{

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerHeaderDataprovider.class,dataProvider="PlanCreation")
	public void enterWeeklyspotsInAllweekWorkSpace(String dealName,String planClass,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,String demovalue) 
	{
		//creating Plan Header
		getPlannerHeaderPage().creationPlanHeader(dealName,planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//save Plan Header
		getPlannerHeaderPage().clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttribute, 
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(AutoConfigPlannerWorkSpace.productworkSpace).clickAddClose();
		//enter workspace
		String weeklyspot[]=planworkspace.enterWorkSpaceDataInWeeklySpots(AutoConfigPlannerWorkSpace.productworkSpace,
				AutoConfigPlannerWorkSpace.dayPartDisplay,"20");
		//save WorkSpace
		planworkspace.clickSaveWorkSpace();
        //get weekly spot for all week
		String getweeklyspot[]=planworkspace.getWorkSpaceDataInWeeklySpots(AutoConfigPlannerWorkSpace.productworkSpace,
				AutoConfigPlannerWorkSpace.dayPartDisplay,1);
		getCustomeVerification().verifyArrayEquals(getSoftAssert(), weeklyspot, getweeklyspot, "not same weekly spot");
		getCustomeVerification().assertAll(getSoftAssert());


	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}


}
