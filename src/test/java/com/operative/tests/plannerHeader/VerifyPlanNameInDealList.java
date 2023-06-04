/**
 * 
 */
package com.operative.tests.plannerHeader;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.dataprovider.PlannerHeaderDataprovider;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;


/**
 * @author upratap
 *
 */
public class VerifyPlanNameInDealList extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerHeaderDataprovider.class,dataProvider="PlanCreation")
	public void verifyPlanNameInDealList(String dealName,String PlanClass,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue) 
	{
		PlannerHeaderPage  planHeader=new PlannerHeaderPage(getPageBrowser());
		//creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//save Plan Header
		planHeader.clickSaveHeader();
		//click Deal List
		planHeader.clickDealList();
		//verify Plan Name in Deal List
		boolean  planName=planHeader.verifyPlanNameInDealList(dealName);
		getCustomeVerification().verifyTrue(getSoftAssert(), planName, "planName is not displaying");
		getCustomeVerification().assertAll(getSoftAssert());

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
