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
public class VerifyRefreshRateCard_WS extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="refreshRateCardWorkSpace")
	public void verifyRefreshRateCardWorkSpace(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String spotsvalue,String cellRatevalue) 
	{
		PlannerHeaderPage planHeader=new PlannerHeaderPage(getPageBrowser());
		//creating Plan Header
		planHeader.creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//select Rate Card Name
		planHeader.selectRateCardName(AutoConfigPlannerWorkSpace.ratecardName);
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
		planworkspace.addProductInChooserWindow(AutoConfigPlannerWorkSpace.productRc_1).clickAddClose();
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(AutoConfigPlannerWorkSpace.productRc_1,
				AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,1);
		//click weekly spots value
	     planworkspace.clickWeeklySpotsValue(AutoConfigPlannerWorkSpace.productRc_1, 
	    		 AutoConfigPlannerWorkSpace.dayPartDisplay, 1);
		//enter rate in cell Properties
		planworkspace.enterCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate, cellRatevalue);
		//save workspace
		planworkspace.clickSaveWorkSpace();
		//click Quick Refresh
		planworkspace.clickQuickRefresh();
		//click weekly spots value
	     planworkspace.clickWeeklySpotsValue(AutoConfigPlannerWorkSpace.productRc_1, 
	    		 AutoConfigPlannerWorkSpace.dayPartDisplay, 1);
		//get cell Properties value
		String rateCellProperties=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate);
		getCustomeVerification().verifyNotEquals(getSoftAssert(), rateCellProperties, cellRatevalue, "rate cell value is same after Quick Refresh");
		getCustomeVerification().assertAll(getSoftAssert());
		

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
