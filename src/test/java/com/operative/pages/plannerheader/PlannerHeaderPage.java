/**
 * 
 */
package com.operative.pages.plannerheader;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.aos.configs.AutoConfigPlannerHeader;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;

import io.qameta.allure.Step;

/**
 * @author upratap
 *
 */
public class PlannerHeaderPage  extends BasePage{
	SoftAssert getSoftAssert; 
	/**
	 * @param page
	 */
	public PlannerHeaderPage(Page page) {
		super(page);
		this.getSoftAssert=new SoftAssert();
		// TODO Auto-generated constructor stub
	}
    final private String newDeal="//span[text()='New Deal']";
    final private String dealNametext="//label[contains(text(),'Deal Name')]//parent::div/input";
    final private String planclassfield="label[for='planClass']";
    final private String continuebutton="button:has-text('Continue')";
    final private String saveButton="button:has-text('Save')";
	final private String closeicon="a[class*='ui-toast-close-icon']";
	final private String cancelBtn = "//button[@id='cancelButton']";
	final private String dealList="//span[text()=' Deal List ']";
	final private String loadingImage ="//span[@class='ag-overlay-loading-center']";
	final private String copybutton="//span[text()='Copy']/../..//span[contains(@class,'down')]";
	final private String removeAdvPopupValue="//app-advertiser-popup//op-cc-paginated-dropdown//i[contains(@class,'clear-icon')]";
	final private String advPopupDown="//app-advertiser-popup//op-cc-paginated-dropdown//span[contains(@class,'down')]";
	final private String  advPopUpType="//div[contains(@class,'ui-dropdown-filter-container')]//input";
	final private String removeadvCategoryPopup="//p-dropdown[@id='advCategory']//i[contains(@class,'clear-icon')]";
	final private String advCategoryPopUpDown="//p-dropdown[@id='advCategory']//span[contains(@class,'chevron-down')]";
	final private String advSecCategoryPopDown="//p-dropdown[@id='advSecCategory']//span[contains(@class,'chevron-down')]";
	final private String advBrandPopDown="//p-dropdown[@id='advBrand']//span[contains(@class,'chevron-down')]";
	final private String saveAdvPopup="//app-advertiser-popup//span[text()='Save']";
	final private String advertiserdots="//label[contains(@for,'advertisers')]/../../../../../..//div[@class='dots-button']";
	final private String agencydots = "//app-agency[@class='ng-star-inserted']//app-dots-button";
	final private String rateCarddots="//app-ratecard//app-dots-button[@class='dot-icon']";
	final private String addRatecard="text=Add Ratecard";
	final private String inputRatecardPop="//occ-dropdown[contains(@class,'paddingClass')]//input";
	final private String saveRateCardPopUp="//app-ratecard-popup//span[text()='Save']";
	final private String editRCPop="text=Edit Ratecard";
	final private String ratingStreamdots="//app-rating-stream//div[@class='dots-button']";
	final private String ratingStreamDp="//p-dropdown[contains(@id,'ratingStream')]";
	final private String ratecard="//app-ratecard//input[contains(@class,'input-box')]";
	
	final private String filterclickapply = "//span[contains(text(),'Apply')]";
	final private String  stationTvicon ="//span[text()='Station']/../..//span[@class='icon-tv']";
	final private String searchStataion ="//div[contains(text(),'Available Station')]/..//input";
	final private String rightclick = "//i[contains(@class,'circle-righ')]";
	final private String applyStation = "//span[text()='Apply']/..";
	final private String calendarIcon = "//app-flight//div[@class='broadcast-calendar']//span[contains(@class,'icon-calendar ')]";
	final private String startDateFiled = "//p-inputmask[contains(@id,'start-date')]";
	final private String startDateInputFiled = "//p-inputmask[contains(@id,'start-date')]//input";
	final private String endDateFiled = "//p-inputmask[contains(@id,'end-date')]";
	final private String endDateInputFiled = "//p-inputmask[contains(@id,'end-date')]//input";
	final private String applyButton = "//span[normalize-space(text())='Apply']";
    final private String openDeal = "//div[@class='ag-center-cols-container']";
    final private String demoDots="//app-demo//app-dots-button[@class='dot-icon']";
    final private String editDemoDrop="//app-demo-popup//p-multiselect//span[contains(@class,'chevron-down')]";
    final private String editDemoText="//input[@role='textbox']";
    final private String secondarycheck="//div[contains(@class,'overlayAnimation')]//div[@role='checkbox']//span";
    final private String saveEditDemoPop="//app-demo-popup//button[@id='saveButton']//span";
	
    @Step("Create Plan Header with mandatory fields")
	public PlannerHeaderPage  creationPlanHeader(String dealName,String planclassValue,String advertiserName,String agencyValue,
			String accountExecuteValue,String calendarvalue,String startPeriodValue,String endPeriodValue,String dealYearValue,
			String marketPlaceValue,String flightvalue,String channelValue,String demovalue) 
	{  
    	getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(70000));
    	click(newDeal);
    	getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
		fill(dealNametext, dealName);
	    getpageBrowser.locator("select[selectedname='Select']").first().selectOption(new SelectOption().setLabel(planclassValue));
		getpageBrowser.keyboard().press("Enter");
	    doubleclick(planclassfield);
	    if (getpageBrowser.locator(continuebutton).isEnabled()) {
	    	getpageBrowser.locator(continuebutton).click();	
		}
	    
		dropdownValue("advertisers", advertiserName);
		dropdownValue("agencies", agencyValue);
		//dropdownValue("accountExecutives", accountExecuteValue);
		dropdownValue("calendar", calendarvalue);
		dropdownValue("startPeriod", startPeriodValue);
		dropdownValue("endPeriod", endPeriodValue);
		dropdownValue("dealYear", dealYearValue);
		dropdownValue("marketPlace", marketPlaceValue);
		dropdownValue("broadcastDate", flightvalue);
		dropdownValue("Channel", channelValue);
		dropdownValue("demo", demovalue);
		return this;
    }
    
    @Step("Save Plan Header Page")
    public PlannerHeaderPage clickSaveHeader() {
    	click(saveButton);
		/*
		 * if (isElementPresent(closeicon)) { click(closeicon); }
		 */
     Logger.log("save Paln Header Page");
		return this;
    }
    
    
    @Step("drowp down Value Select")
    public PlannerHeaderPage dropdownValue(String fieldName,String value)
    {
    	getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(70000));
    	if (fieldName.equals("advertisers") || fieldName.equals("agencies") || fieldName.equals("accountExecutives")) {
    		clickwithWait("//div[contains(@id,'"+fieldName+"')]//span[contains(@class,'chevron-down')]",2000.00);
        	type("//div[contains(@id,'float-paginateddd-" + fieldName+ "')]//ancestor::app-root//"
        			+ "following-sibling::div[contains(@class,'overlayAnimation')]//input[contains(@class,'filter')]",value);
    		click("//div[contains(@class,'overlayAnimation')]//ul//li//span[contains(text(),'" + value + "')]");
    		Logger.log("Selected "+fieldName+"===>>"+value);	
		}
    	else if (fieldName.equals("calendar") ||fieldName.equals("startPeriod") || fieldName.equals("endPeriod") || 
    			fieldName.equals("dealYear") || fieldName.equals("marketPlace") || fieldName.equals("demo")) {
    		if (fieldName.equals("startPeriod")) {
    			getpageBrowser.waitForSelector("//p-dropdown[contains(@id,'startPeriod')]/div[contains(@class,'disabled')]",new Page.WaitForSelectorOptions().
    					setState(WaitForSelectorState.HIDDEN)); 
    			click("//p-dropdown[contains(@id,'"+fieldName+"')]//span[contains(@class,'pi-chevron-down')]");	
    			fill("//p-dropdown[contains(@id,'"+fieldName+"')]//ancestor::app-root//following-sibling::div[contains(@class,'overlayAnimation')]//input", value);
    			click("//span[contains(text(),'"+value+"')]");
    			Logger.log("Selected "+fieldName+"===>>"+value);
			}
    		
    	click("//p-dropdown[contains(@id,'"+fieldName+"')]//span[contains(@class,'pi-chevron-down')]");	
		fill("//p-dropdown[contains(@id,'"+fieldName+"')]//ancestor::app-root//following-sibling::div[contains(@class,'overlayAnimation')]//input", value);
		click("//span[contains(text(),'"+value+"')]");
		Logger.log("Selected "+fieldName+"===>>"+value);
    		
		}
    	
    	else if (fieldName.equals("broadcastDate")) {
    		getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(70000));
        	getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
        	final String[] dates = value.split("-");
        	click(calendarIcon);
        	click(startDateFiled);
        	type(startDateInputFiled, dates[0]);
        	click(endDateFiled);
        	type(endDateInputFiled, dates[1]);
        	click(applyButton);
    		Logger.log("Selected "+fieldName+"===>>"+value);
		}
    	else {
			click("//span[contains(text(),'"+fieldName+"')]//parent::label");
			click("//span[text()='"+value+"']");
			click(dealNametext);
			Logger.log("Selected "+fieldName+"===>>"+value);
		}
    	
    	return this;
    }
    
    @Step("Click Deal List")
    public PlannerHeaderPage clickDealList()
    {
    	getpageBrowser.waitForSelector(loadingImage, new
    			  Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
    	click(dealList);
    	Logger.log("click on Deal List");
		return this;
    }
    
    @Step("verify PlanName in Deal List")
    public boolean  verifyPlanNameInDealList(String planName)
    {
    	Boolean planNamedeal=isElementPresent("//div[text()='"+planName+"']");
    	Logger.log("Displaying planName "+planName+"==>>"+planNamedeal);
		return planNamedeal;
    	
    }
    
    @Step("select Rate crad Name in plan Header")
    public PlannerHeaderPage selectRateCardName(String ratecards)
    {

//    	click(ratecard);
    	clickwithWait(ratecard,5000.00);
    	String ratecard[]=ratecards.split(";");
    	for (int i = 0; i < ratecard.length; i++) {
    		fill("//app-ratecard//div[contains(@class,'input-container ui-float-label')]//input",ratecard[i]);
    		getpageBrowser.waitForSelector("//div[@class='ng-star-inserted']//*[@title='" + ratecard[i] +"']", 
    				new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(80000));
    		click("//span[@title='" + ratecard[i] + "']/..");
    		Logger.log("select Rate card Name===>>"+ratecard[i]);
    	}
    	click("//div[@class='workspace-data col']");
    	return this;
    }
    
    @Step("click on copy Header/copyAll")
    public PlannerHeaderPage clickCopyHeader(String filedValue)
    {
    	click(copybutton);
    	click("text="+filedValue+"");
    	Logger.log("click on copy "+filedValue+"");
		return this;
    	
    }
    
    @Step("Select the value form Divison and sub division")
    public PlannerHeaderPage selectDivision_SubDivision(String filedName,String value)
    {	
    getpageBrowser.waitForSelector("//p-dropdown[@id='"+filedName+"']//div[contains(@class,'disabled')]", new Page.WaitForSelectorOptions().
    					setState(WaitForSelectorState.HIDDEN));
    click("//p-dropdown[@id='"+filedName+"']//div[contains(@class,'corner-right')]");	
    type("//p-dropdown[contains(@id,'"+filedName+"')]//ancestor::app-root//following-sibling::div[contains(@class,'overlayAnimation')]//input", value);
	click("//span[text()='"+value+"']");
	
	Logger.log("Selected "+filedName+"===>>"+value);
	return this;	
    }
    
    @Step("click on advertiser dot button and open Advertiser window")
    public PlannerHeaderPage clickAdvertiserdotsPopUp()
    {
        click(advertiserdots);
        Logger.log("Advertiser Window Pop Up displaying");
		return this;
    }
    
    @Step("select Advertiser value from AdvertiserWindowPopup")
    public PlannerHeaderPage selectAdvertiserValueFromWindowPopup(String columnName, String value)
    {
    	switch (columnName) {
    	case "Advertiser":
    		if (isElementPresent(removeAdvPopupValue)) {
    			click(removeAdvPopupValue);
    		}
    		click(advPopupDown);
    		type(advPopUpType, value);
    		click("//span[contains(text(),'"+value+"')]");
    		Logger.log("Select "+columnName+" from Advertiser Pop Up===>>"+value);
    		break;

    	case "Category":
    		if (isElementPresent(removeadvCategoryPopup)) {
    			click(removeadvCategoryPopup);	
    		}
    		click(advCategoryPopUpDown);
    		getpageBrowser.locator("//span[text()='"+value+"']").first().click();
    		Logger.log("Select "+columnName+" from Advertiser Pop Up===>>"+value);
    		break;

    	case "Secondary Category":
    		click(advSecCategoryPopDown);;
    		getpageBrowser.waitForSelector("(//span[text()='"+value+"'])[2]", new Page.WaitForSelectorOptions().
    				setState(WaitForSelectorState.VISIBLE));
    		getpageBrowser.locator("//span[text()='"+value+"']").last().click();;
    		Logger.log("Select "+columnName+" from Advertiser Pop Up===>>"+value);
    		break;

    	case "Default Brand":
    		click(advBrandPopDown);
    		click("//span[contains(text(),'"+value+"')]");
    		Logger.log("Select "+columnName+" from Advertiser Pop Up===>>"+value);
    		break;
    	}
    	return this;
    }
    
    @Step("click on save button in Advertiser pop up window")
    public  PlannerHeaderPage  clicksaveAdvertiserPopupWindow()
    {
    	click(saveAdvPopup);
		return this;
    }

    
    @Step("click on Rate Card dot button and open Rate Card Pop up window")
    public PlannerHeaderPage clickRateCarddotsPopUp()
    {
        click(rateCarddots);
        Logger.log("Rate Card Window Pop Up displaying");
		return this;
    }

    @Step("select Rating Stream  in plan Header")
    public PlannerHeaderPage selectRatingStream(String ratingStream)
    {

    	click(ratingStreamDp);
    	click("//p-dropdownitem//li[@aria-label='"+ratingStream+"']");
    	Logger.log("selected Rating stream ===>>"+ratingStream);
    	return this;
    }
		
    @Step("select Rate Card value from Rate Card WindowPopup")
    public PlannerHeaderPage selectRateCardValueFromRCWindowPopup(String columnName,String values,int index)
    {
    	switch (columnName) {
    	case "AddRatecard":
    	String value[]=values.split(";");
    	click(addRatecard);
    	for (int i = 0; i < value.length; i++) {
      getpageBrowser.locator(inputRatecardPop).press("Backspace");
    		type(inputRatecardPop, value[i]);
        	click("text="+value[i]+"");	
        	click(editRCPop);
        	Logger.log("select Rate card value in RCPopup ===>>"+value[i]);
        	}
    	break;
    case "Delete":	
    	click("(//app-ratecard-popup//span[@id='delete-button'])["+index+"]");
    	Logger.log("Delete  Rate card value in RCPopup ===>>");
    	break;
    	}
    	return this;
  }
    
    @Step("click on save RateCard in RC pop up window")
    public PlannerHeaderPage clickSaveRateCardPopUpWindow()
    {
    	click(saveRateCardPopUp);
		return this;
    }
    
    @Step("click on RatingStream dot button and open RatingStream Pop up window")
    public PlannerHeaderPage clickRatingStreamDotsPopUp()
    {
        click(ratingStreamdots);
        Logger.log("RatingStream Window Pop Up displaying");
		return this;
    }
    
    
    @Step("select RatingStream value from  WindowPopup")
    public PlannerHeaderPage selectRatingStreamValueFromWindowPopup(String ratingStreamvalue,
    		String channelName,String channelRatingStream,String channelHHPosting,String channelDemoPosting)
    {
    	if (! ratingStreamvalue.isEmpty()) {
   
			click("//app-rating-stream-popup//p-dropdown[@id='_dropdown']//span[contains(@class,'chevron-down')]");
			click("text='"+ratingStreamvalue+"'");
		}
    	
    	if ( ! channelRatingStream.isEmpty()) {
			click("//label[text()[normalize-space()='"+channelName+"']]/../..//following-sibling::"
					+ "td//p-dropdown[@id='ratingStreamId']//span[contains(@class,'chevron-down')]");
			click("text='"+ratingStreamvalue+"'");
		}
    	
    	if ( !channelHHPosting.isEmpty()) {
			click("//label[text()[normalize-space()='"+channelName+"']]/../..//following-sibling::"
					+ "td//p-dropdown[@id='hhPostBuyId']//span[contains(@class,'chevron-down')]");
			click("text='"+ratingStreamvalue+"'");
		}
    	
    	if ( ! channelDemoPosting.isEmpty()) {
			click("//label[text()[normalize-space()='"+channelName+"']]/../..//following-sibling::"
					+ "td//p-dropdown[@id='hhPostBuyId']//span[contains(@class,'chevron-down')]");
			click("text='"+ratingStreamvalue+"'");
		}
    	
    	return this;
  }
    @Step("Get deal ID from master header")
	public String getAllMasterHeaderValue(String EntityName) {
		Boolean entity = isElementPresent("//span[contains(text(),'" + EntityName + "')]");
		Logger.log(EntityName + "--> is displayed from master header-->" + entity);
		String EntityValue = getText("//span[contains(text(),'" + EntityName + "')]");
		Logger.log(EntityName + "--> value displayed from master header is -->" + EntityValue);
		return EntityValue;
	}
	@Step("verify DealID in Deal List")
	public boolean verifyDealIdInDealList(String DealID) {
		Boolean dealId = isElementPresent("//div[text()='" + DealID + "']");
		Logger.log("Displaying DealID from listpage " + DealID + "==>>" + dealId);
		return dealId;
	}
	@Step("Apply filter for single select value for DealNAme")
	public PlannerHeaderPage filterBySingleSelectValue(String FilterEntityName, String FilterValue) {
		click("//label[contains(text(),'"+FilterEntityName+"')]//parent::div//input");
		Logger.log("clicked on filter ==>>" + FilterEntityName);
		type("//label[contains(text(),'"+FilterEntityName+"')]//parent::div//input", FilterValue);
		click(filterclickapply);
		Logger.log("Filtered applied for -->"+FilterEntityName+"-->"+ FilterValue);
		return this;
	}
	public PlannerHeaderPage filterApplyOrClear(String filterAction) {
		click("//span[contains(text(),'" + filterAction + "')]");
		Logger.log("Filter-->" + filterAction);
		return this;
	}
    @Step("Rating Stream popup present")
    public boolean isRatingStreamKebabPresent() {
      final boolean a = isElementPresent(ratingStreamdots);
      Logger.log("==Rating stream  popup up is present from UI-->" + a);
      return a;
    }
    @Step("get Selected values in PlanHeader for All entity")
    public String getSelectedValueFromPlanheader(String EntityName) {
      String value = "";
      value= getText("//p-dropdown[contains(@id, '"+EntityName+"')]//span[contains(@class,'ng-star-inserted')]//span");
      Logger.log(EntityName+"--> value from deal header-->"+value);
      return value;
    }
    
   final private String ratingStreamindeallevelfieldPopUp = 
		      "//app-rating-stream-popup/div//label//span[contains(text(),'Rating Stream')]/../..//div/span[contains(@class,'ui-dropdown-label')]";
   
    @Step("get deal level RS Selected values from Rating stream pop up")
    public String getRsHeaderLevelsValuesSelectedPopUp() {
      String delaLevelRsValuePopUp = "";
      delaLevelRsValuePopUp= getText(ratingStreamindeallevelfieldPopUp);
      Logger.log("header level RS value from RS pop up-->"+delaLevelRsValuePopUp);
      return delaLevelRsValuePopUp;
    }
    @Step("Click on apply From RS pop up") 
    public PlannerHeaderPage cliCKOnApplyFromRSpopUp() {
 	   click("//span[text()='Apply']/..");
 	   Logger.log("click on Apply button from RS pop up");
 	return this;
    }
    @Step("get Deal Name from master Header")
	public String getNameFromMasterHeader() {
		String value = "";
		value = getText("//div[@class='name']");
		Logger.log("Deal name from master header -->" + value);
		return value;
	}

	@Step("click on filtered deal")
	public PlannerHeaderPage clickOnFilteredDael(String dealFilterValue) {
		getpageBrowser.locator("text='" + dealFilterValue + "'").click();
		Logger.log("Clicked on deal -->" + dealFilterValue);
		return this;
	}

	@Step("get All Values selected from Adv pop up")
	public String getAdvertiserValueFromWindowPopup(String field) {
		String data = "";
		if (field.equals(AutoConfigPlannerHeader.advValue)) {
			data = getText(
					"//div[cgetAgontains(@aria-hidden,'false')]//op-cc-paginated-dropdown//label[contains(@class,'inputtext')]");
		} else if (isElementPresent(
				"//p-dropdown[@id='" + field + "']//span[contains(@class,'dropdown-label ui-inputtext')]")) {
			data = getText("//p-dropdown[@id='" + field + "']//span[contains(@class,'dropdown-label ui-inputtext')]");
		}
		Logger.log(field + "-->value from adv pop up is-->" + data);
		return data;
	}
	
	
	@Step("Create Local Plan Header with mandatory fields")
	public PlannerHeaderPage  creationLocalPlanHeader(String dealName,String planclassValue,String advertiserName,String agencyValue,
			String accountExecuteValue,String calendarvalue,String startPeriodValue,String endPeriodValue,
			String marketPlaceValue,String flightvalue,String stationValue,String demovalue) 
	{  
    	getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(90000));
    	getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
    	click(newDeal);
		type(dealNametext, dealName);
	    getpageBrowser.locator("select[selectedname='Select']").first().selectOption(new SelectOption().setLabel(planclassValue));
		getpageBrowser.keyboard().press("Enter");
	    doubleclick(planclassfield);
	    if (getpageBrowser.locator(continuebutton).isEnabled()) {
	    	getpageBrowser.locator(continuebutton).click();	
		}
	    
		dropdownValue("advertisers", advertiserName);
		dropdownValue("agencies", agencyValue);
		dropdownValue("accountExecutives", accountExecuteValue);
		dropdownValue("calendar", calendarvalue);
		dropdownValue("startPeriod", startPeriodValue);
		dropdownValue("endPeriod", endPeriodValue);
		dropdownValue("broadcastDate", flightvalue);
		dropdownValue("demo", demovalue);
		selectLocalStation(stationValue);
		return this;
    }
	
	public void selectLocalStation(String stationNames) {
	    final String stationName[] = stationNames.split(";");
	    click(stationTvicon);
	    for (final String stations : stationName) {
	      type(searchStataion, stations);
	      click("//div[text()='" + stations + "']");
	      click(rightclick);
	    }
	    click(applyStation);
	  }
	@Step("click on Deal Record and open the deal")
    public void opensearchDeal()
    {
    	click(openDeal);
		
    }
	
	public PlannerHeaderPage clickApplyOrClearIfenabled(String filterAction) {
		if(getpageBrowser.isEnabled("//span[contains(text(),'" + filterAction + "')]")) {
		click("//span[contains(text(),'" + filterAction + "')]");
		Logger.log("Filter-->" + filterAction);
		}
		else {
			Logger.log("button is already disabled on plan list page so we are good");
		}
		return this;
	}

	@Step("click on cancel buttonl")
	public void clickCancelButton() {
		click(cancelBtn);

	}

	@Step("click on agecny  dots")
	public void clickAgecnyDots() {
		click(agencydots);

	}

	@Step("check buying agency details")
	public boolean verifyBuyingAgencyValue(String agencyName) {
		boolean isDisplayed = false;
		if (isElementPresent("//div[@id='float-paginateddd-id']//label[contains(text(),'" + agencyName + "')]")) {
			isDisplayed = true;
			return isDisplayed;
		} else {
			return isDisplayed;
		}

	}


@Step("check Advertiser details")
public boolean verifyAdvertiserValue(String advName) {
	boolean isDisplayed = false;
	if (isElementPresent("//div[@id='float-paginateddd-id']//label[contains(text(),'" + advName + "')]/following-sibling::label")) {
		isDisplayed = true;
		return isDisplayed;
	} else {
		return isDisplayed;
	}

}
@Step("select Demo primary and Secondary from Demo pop")
public PlannerHeaderPage selectDemoPrimayAnd_secondary(String primarydemo,String secondaryDemo)
{
	
	click(demoDots);
	click(editDemoDrop);
	if (!primarydemo.isEmpty()) {
		type("//input[@role='textbox']", primarydemo);
		click("//span[text()='"+primarydemo+"']/..//following-sibling::div//span[contains(@class,'radiobutton')]");
		Logger.log("select the  primarydemo==>>"+primarydemo);
	}
	if (!secondaryDemo.isEmpty()) {
		type(editDemoText, secondaryDemo);
		click("//li[@aria-label='"+secondaryDemo+"']//div[@class='ui-chkbox ui-widget']");
		Logger.log("check the  secondaryDemo==>>"+secondaryDemo);
	}
	click(saveEditDemoPop);
	return null;
	
}


}




