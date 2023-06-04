package com.operative.tests.plannerHeader;


import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.dataprovider.PlannerHeaderDataprovider;
import com.microsoft.playwright.ElementHandle;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;

/**
 * @author upratap
 *
 */

public class PlannerHeaderCreation extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerHeaderDataprovider.class,dataProvider="PlanCreation")
	public void planCreation(String dealName,String planClass,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,String demovalue) 
	{
		 //getPageBrowser().onRequest(request -> System.out.println(">> " + request.method() + " " + request.url()));
	     //getPageBrowser().onResponse(response -> System.out.println("<<" + response.status() + " " + response.url()));
		
			/*
			 * getPageBrowser().click(
			 * "//op-cc-combobox[@id='topPanelDemo_advertisers']//span[contains(@class,'down-arrow')]"
			 * ); List<ElementHandle>lst=getPageBrowser().querySelectorAll(
			 * "//input[@id='topPanelDemo_advertisers_input']/../..//ul//li"); for (int i =
			 * 0; i < lst.size(); i++) { lst.get(i).scrollIntoViewIfNeeded(); String
			 * value=lst.get(i).textContent(); System.out.println("============"+value); }
			 */
		//creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(dealName,planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//save Plan Header
		   new PlannerHeaderPage(getPageBrowser()).clickSaveHeader();
		   
		   //getPageBrowser().mouse().wheel(0, 150000);
		//select division
		//new PlannerHeaderPage(getPageBrowser()).selectDivision_SubDivision("division_dropdown", "Sports");
	
		/*
		 * new PlannerHeaderPage(getPageBrowser()).clickAdvertiserdotsPopUp();
		 * 
		 * new PlannerHeaderPage(getPageBrowser()).selectAdvertiserValueFromWindowPopup(
		 * "Advertiser", "LINKEDIN"); new
		 * PlannerHeaderPage(getPageBrowser()).selectAdvertiserValueFromWindowPopup(
		 * "Category", "Auto_Primary121359308918070"); new
		 * PlannerHeaderPage(getPageBrowser()).
		 * selectAdvertiserValueFromWindowPopup("Secondary Category",
		 * "Auto_Secondary12135930"); new PlannerHeaderPage(getPageBrowser()).
		 * selectAdvertiserValueFromWindowPopup("Default Brand", "GEN"); new
		 * PlannerHeaderPage(getPageBrowser()).clicksaveAdvertiserPopupWindow();
		 */
		   
		/*
		 * new PlannerHeaderPage(getPageBrowser()).clickRateCarddotsPopUp(); new
		 * PlannerHeaderPage(getPageBrowser()).selectRateCardValueFromRCWindowPopup(
		 * "AddRatecard","Donot USE;DV_MARCH",0);
		 * 
		 * new PlannerHeaderPage(getPageBrowser()).selectRateCardValueFromRCWindowPopup(
		 * "Delete","",2);
		 */
		/*
		 * new PlannerHeaderPage(getPageBrowser()).clickRatingStreamDotsPopUp();
		 * 
		 * new
		 * PlannerHeaderPage(getPageBrowser()).selectRatingStreamValueFromWindowPopup(
		 * "C7", "Fox Deportes", "C3", "", "");
		 */
		   
		   
		System.out.println("=============");
}

	@AfterTest
	  public void afterTest() {
      getbrowsercontext().close();
	  getPageBrowser().close();
	  }


}
