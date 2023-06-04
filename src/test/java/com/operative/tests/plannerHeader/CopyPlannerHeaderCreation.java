package com.operative.tests.plannerHeader;


import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerHeaderDataprovider;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;



public class CopyPlannerHeaderCreation extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerHeaderDataprovider.class,dataProvider="PlanCreation")
	public void copyPlannerHeaderCreation(String dealName,String planClass,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,String demovalue) 
	{
		PlannerHeaderPage planHeader=new PlannerHeaderPage(getPageBrowser());
		//creating Plan Header
		planHeader.creationPlanHeader(dealName,planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//save Plan Header
		planHeader.clickSaveHeader();
		//click on copy Header
		planHeader.clickCopyHeader(AutoConfigPlannerWorkSpace.copyHeader);
		//click Deal List
		planHeader.clickDealList();
		//verify Plan Name in Deal List
		boolean  planName=planHeader.verifyPlanNameInDealList(dealName+" - Copy");
		getCustomeVerification().verifyTrue(getSoftAssert(), planName, "planName Copy is not displaying");
		
		}

	@AfterTest
	  public void afterTest() {
      getbrowsercontext().close();
	  getPageBrowser().close();
	  }


}
