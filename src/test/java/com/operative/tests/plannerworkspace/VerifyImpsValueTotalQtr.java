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
public class VerifyImpsValueTotalQtr  extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="ImpsValueTotalQtr")
	public void verifyImpsValueTotalQtr(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String spotsvalue,String impstotal) 
	{
		//creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//save Plan Header
		new PlannerHeaderPage(getPageBrowser()).clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttribute, 
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(AutoConfigPlannerWorkSpace.productworkSpace).clickAddClose();
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(AutoConfigPlannerWorkSpace.productworkSpace,
				AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,1);
		//enter imps value in total
		planworkspace.enterAggregateTotalValueQtr(AutoConfigPlannerWorkSpace.productworkSpace, 
				AutoConfigPlannerWorkSpace.dayPartDisplay, AutoConfigPlannerWorkSpace.impstotalQtr,impstotal, 0);
		//save workspace
		planworkspace.clickSaveWorkSpace();
		//get imps value in Aggregate total
		String impsTotalValue=planworkspace.getAggregateTotalValueQtr(AutoConfigPlannerWorkSpace.productworkSpace, AutoConfigPlannerWorkSpace.dayPartDisplay, 
				AutoConfigPlannerWorkSpace.impstotalQtr);
		getCustomeVerification().verifyEquals(getSoftAssert(), getBasePage().format2Digi(impsTotalValue), getBasePage().format2Digi(impstotal),"total imps value not match");
		
		getCustomeVerification().assertAll(getSoftAssert());
		

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
