/**
 * 
 */
package com.operative.pages.plannerworkspace;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import org.elasticsearch.common.inject.Key;
import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.ElementHandle.ScrollIntoViewIfNeededOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ClickOptions;
import com.microsoft.playwright.Page.WaitForSelectorOptions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;
import com.operative.base.utils.pojo.PlannerDateTime.QuarterDTO;

import io.qameta.allure.Step;

/**
 * @author upratap
 *
 */
public class PlannerWorkBookPage extends BasePage {
	SoftAssert getSoftAssert;
	/**
	 * @param getpageBrowser
	 */
	public PlannerWorkBookPage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert=new SoftAssert();
		// TODO Auto-generated constructor stub
	}
	
	
	Locator quickRefreshToastMsg =getpageBrowser.locator("//div[contains(@class,'ui-toast-detail') and contains(.,'Refresh workspace rates successfully completed.')]");
	Locator workspaceTab =getpageBrowser.locator("text=Workspace");
	Locator constraintDateRangeInputBox =getpageBrowser.locator("//p-calendar[contains(@id,'constraintDateRange')]//input");
	
	final private String tools="button:has-text('Tools')";
	final private String bookchooserbutton = "//span[text()='Book Chooser']";
	private final String nextButton = "//p-dialog[@id]//button[text()='Next']";
	private final String selectEstimateTab = "//span[normalize-space(text())='Select Estimates']";
	private final String selectBookCheckbox= "//div[contains(@class,'ui-chkbox-box ui-widget ui-corner-all ui-state-default') and @role='checkbox']";
	private final String applybutton ="//button[text()='Apply']";
	private final String demoDropDown =" //p-dropdown[@defaultlabel='Demo']//span";
	private final String backButton = "//p-dialog[@id]//button[text()='Back']";
	final private String quickrefresh="//span[contains(text(),'Quick Refresh')]";
	final private String  yesquickRef="text=Yes";
	final private String  keepRateIndexRadioButton="//label[normalize-space(text())='Keep the deal level overridden rate index value.']//parent::p-radiobutton";
	final private String  overrideRateIndexRadioButton="//label[normalize-space(text())='Restore the rate index and modify the deal level overridden value.']//parent::p-radiobutton";
	final private String  unitIndexOverrideButton="text=Unit Index Override";
	final private String  unitIndexSavebutton="//spotlength-dialog//button//span[normalize-space(text())='Save']";
	final private String  unitIndexOverrideHeading="//span[text()='Unit Index Override']";
	final private String  editProductAttributes="//span[normalize-space(text())='Edit Product Attributes']";
	private final String constaintApplybutton ="//button//span[text()='Apply']";
	private final String removeUnitsPopUpMsg ="//span[normalize-space(text())='There are units on line(s) you are removing. Do you wish to continue?']";
	final private String popUpYesButton="text=Yes";
	final private String editProductAttributeHeading="//span[normalize-space(text())='Edit Product Attribute']";
	final private String calenderIcon="//button[contains(@class,'ui-datepicker')]//span[contains(@class,'ui-button-icon')]";
	final private String constraintDateRangeInput="//p-calendar[contains(@id,'constraintDateRange')]//input";
	final private String plannerHeaderTooltip = "//div[@tooltipstyleclass='headerTooltip']";
	final private String othertools="text=Other Tools";
	

	
	
	@Step("click on Book Chooser")
	public PlannerWorkBookPage clickonBookChooser() {
		getpageBrowser.waitForSelector(tools,
				new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
	    click(tools);
	    click(bookchooserbutton);
	    Logger.log("clicked book chooser");
	    return this;
	  }

	@Step("select Books")
	public PlannerWorkBookPage selectedBooks(String bookNames) {
	    Arrays.stream(bookNames.split(";")).forEach(books -> click("//td[contains(text(),'"
	        + books + "')]/preceding-sibling::td//div[contains(@class,'chkbox-box')]"));
	    Arrays.stream(bookNames.split(";")).forEach(books -> Logger.log("select book name===" + books));
	    return this;
	  }
	
	@Step("click on Next button")
	  public PlannerWorkBookPage clickNext() {
	    click(nextButton);
	    if (isElementPresent(backButton)) {
		click(backButton);	
		}
	    click(nextButton);
	    Logger.log("click on next buttton");
	    return this;
	  }
	
	@Step("click on Back button")
	  public PlannerWorkBookPage clickBackButton() {
	    click(backButton);
	    Logger.log("click on back buttton");
	    return this;
	  }
	
	@Step("Apply Books for selected quarters")
	  public PlannerWorkBookPage applyBookForSelectedProductSelectAll(String productName,
		      String bookName, String qtrName) throws InterruptedException {
		getpageBrowser.waitForSelector(selectEstimateTab,
				new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		    clickwithWait(selectEstimateTab,6000.0);
		    click("//span[@title='" + productName + "' or contains(.,'" + productName
		        + "')]/../following-sibling::div//app-books-ellipsis//i");
		    // click on book
		    Locator bookNameLocator=getpageBrowser.locator("//span[@class='ui-multiselect-label ui-corner-all' and text()='" + bookName + "']");
		    bookNameLocator.hover();
		    click(selectBookCheckbox);
		    Logger.log(" select product name " + productName + " click bookName===>" + bookName + " apply qtr ==> All Quarters");
		    return this;
		  }
	
	
	@Step("Apply Books for selected products")
	  public PlannerWorkBookPage applyBookForMultipleProductSelectAll(String productName,
		      String productLineNumber, String bookName, String qtrName) throws InterruptedException {
		getpageBrowser.waitForSelector(selectEstimateTab,
				new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		    //click(selectEstimateTab);
		    clickwithWait(selectEstimateTab, 3000.00);
		    click("((//app-selected-books//span[normalize-space(text())='"+productLineNumber+"']//following::div[normalize-space(text())='"+productName+"'])[1]//following::div//app-books-ellipsis//i)[1]");
		    // click on book
		    Locator bookNameLocator=getpageBrowser.locator("//span[@class='ui-multiselect-label ui-corner-all' and text()='" + bookName + "']");
		    bookNameLocator.hover();
		    click(selectBookCheckbox);
		    Logger.log(" select product name " + productName + " click bookName===>" + bookName + " apply qtr ==> All Quarters");
		    return this;
		  }
	  
	@Step("Click Apply Button")
	  public PlannerWorkBookPage clickApply() {
		    click(applybutton);
		    clickwithWait(plannerHeaderTooltip,9000.00);
		    Logger.log("click apply button");
		    return this;
		  }
	
	

	@Step("Change Demo Value")
	  public PlannerWorkBookPage ChangeDemo(String DemoValue) {
		    click(demoDropDown);
		    click(" //p-dropdownitem//span[normalize-space(text())='"+DemoValue+"']");
		    clickwithWait(plannerHeaderTooltip,9000.00);
		    Logger.log(DemoValue + " value selected");
		    return this;
		  }
	
	
	@Step("Click Quick Refresh Rate card Local/Books")
	public PlannerWorkBookPage  openQuickRefreshBooks()
	{   
		click(tools);
		click(quickrefresh);
	    getpageBrowser.waitForSelector(yesquickRef);
	    return this;
	}
	
	@Step("Keep overridden rating index in Quick Refresh Rate card Local/Books")
	public PlannerWorkBookPage  keepOverriddenUnitInedexQuickRefresh()
	{   
		click(keepRateIndexRadioButton);
		Logger.log("Keep overridden rating index in Quick Refresh");
	    return this;
	}
	
	@Step("Keep overridden rating index in Quick Refresh Rate card Local/Books")
	public PlannerWorkBookPage overriddenUnitInedexQuickRefresh()
	{   
		click(overrideRateIndexRadioButton);
		Logger.log("Override overridden rating index in Quick Refresh");
	    return this;
	}
	
	@Step("click on Quick Refresh Rate card Local/Books")
	public PlannerWorkBookPage  clickQuickRefreshBooks()
	{   
		
	    click(yesquickRef);
	    getpageBrowser.waitForSelector("//div[contains(text(),'Refresh workspace rates')]", new
		  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		Logger.log("click on Quick Refresh Rate card");
		quickRefreshToastMsg.isVisible();
		clickwithWait(plannerHeaderTooltip,9000.00);
		Logger.log("Refresh workspace rates successfully completed");
	return this;
	}
	
	@Step("update unit index")
	public PlannerWorkBookPage  updateUnitIndex(String unitLength,int unitIndex,String data)
	{   
		click(tools);
		click(unitIndexOverrideButton);
		Locator unitIndexdata=getpageBrowser.locator("(//td[normalize-space(text())='"+unitLength+"']//following::td)["+unitIndex+"]//div//input");
		unitIndexdata.fill(data);
		click(unitIndexOverrideHeading);
		click(unitIndexSavebutton);
		clickwithWait(plannerHeaderTooltip,9000.00);
		getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
		getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
		Logger.log("Rate index value changes");
		return this;
	}
	
	 @Step("Add constraint or Edit Product Attributes")
		public PlannerWorkBookPage  addConstraintOrEditProductAttributes(int productLineNum, String dateRange) {
		click("//div[contains(@class,'line_id_"+productLineNum+"')]//app-product-ellipsis//i");
		getpageBrowser.locator(othertools).first().hover();
		click(editProductAttributes);
		click(calenderIcon);
		constraintDateRangeInputBox.clear();
		type(constraintDateRangeInput,dateRange);
		getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
		click(editProductAttributeHeading);
		click(constaintApplybutton);
		getpageBrowser.waitForSelector(removeUnitsPopUpMsg,
				new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		click(popUpYesButton);
		getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
		Logger.log("Constraint Added or Product Attribute Edited");
		clickwithWait(plannerHeaderTooltip,9000.00);
	    return this;
		}
	 
	 
}
