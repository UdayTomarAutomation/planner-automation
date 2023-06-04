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

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.ScrollIntoViewIfNeededOptions;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
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
public class PlannerWorkSpacePage extends BasePage {
	SoftAssert getSoftAssert;
	/**
	 * @param getpageBrowser
	 */
	public PlannerWorkSpacePage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert=new SoftAssert();
		// TODO Auto-generated constructor stub
	}
	Locator workspaceTab =getpageBrowser.locator("text=Workspace");
	Locator productChooser =getpageBrowser.locator("text=Product Chooser");
	Locator planTotalTab =getpageBrowser.locator("//app-workspace-tab-view//span[normalize-space(text())='Plan Total']");
	Locator periodViewTab =getpageBrowser.locator("//app-workspace-tab-view//span[normalize-space(text())='Period View']");	
	Locator weeklyTotalsPanelButton =getpageBrowser.locator("//div[contains(@class,'summary-panel-button')]");	
	Locator weeklyTotalsCheckedColumns = getpageBrowser.locator("//div[contains(@class,'ng-trigger-animation')]//li//div[contains(@class,'active')]");
	Locator quickRefreshToastMsg =getpageBrowser.locator("//div[contains(@class,'ui-toast-detail') and contains(.,'Refresh workspace rates successfully completed.')]");
	Locator lineInfoCheckBoxWithoutAnyMetricSelection=getpageBrowser.locator("(//div[@class='ag-wrapper ag-input-wrapper ag-checkbox-input-wrapper']//input[contains(@aria-label,'Press SPACE to toggle visibility (hidden)')])[1]");
    Locator lineInfoCheckBoxWithSomeMetricSelection=getpageBrowser.locator("//div[contains(@class,'ag-checkbox-input-wrapper ag-indeterminate')]//input[contains(@aria-label,'Press SPACE to toggle visibility (hidden)')]");
    Locator lineInfoCheckBoxWithAllMetricSelection=getpageBrowser.locator("//span[normalize-space(text())='Line Info']/..//div[@class='ag-wrapper ag-input-wrapper ag-checkbox-input-wrapper ag-checked']");
    Locator weeklySpotSelectQuadView=getpageBrowser.locator("//p-multiselect[@defaultlabel='View Metrics']//div[contains(@class,'ui-multiselect-trigger')]");
    Locator productInputFiled = getpageBrowser.locator("input[placeholder=' Search Product']");
    Locator ratecardRefreshToastMsg =getpageBrowser.locator("//div[contains(@class,'ui-toast-detail') and contains(.,'Refresh ratecard has been completed.')]");
    Locator quarterTotalExpandButton = getpageBrowser.locator("//app-quaterly-plans-header//i[contains(@class,'fas fa-caret-right')]");
    Locator expandUnitMetric = getpageBrowser.locator("//app-quarter-unit-metrics-filter//i[contains(@class,'fas fa-caret-right')]");
    Locator planTotalExpandButton = getpageBrowser.locator("//app-plan-total-quarter-header//i[contains(@class,'fas fa-caret-right')]");
    Locator workspaceColumnFilter = getpageBrowser.locator("//div[contains(text(),'Totals')]/..//div[contains(@class,'menu-column-chooser')]//i");
    Locator allColumnsCheckBox = getpageBrowser.locator("(//div[contains(@class,'ag-wrapper ag-input-wrapper ag-checkbox-input')]//input[contains(@aria-label,'toggle visibility') or contains(@aria-label,'Plan')])[1]");
    Locator allColumnsCheckBoxCheckedQuarterly = getpageBrowser.locator("(//div[contains(@class,'ag-wrapper ag-input-wrapper ag-checkbox-input')]//input[contains(@aria-label,'toggle visibility') or contains(@aria-label,'Plan')])[1]");
    Locator selectSelectionsDrop = getpageBrowser.locator("//p-multiselect[@defaultlabel='Select Sections']//span[contains(@class,'pi-caret-down')]");
    Locator checkunitMetrics = getpageBrowser.locator("//li[contains(@class,'state-highlight')]//span[text()='Unit Metrics']");
    Locator unitMetricsCheckbox = getpageBrowser.locator("//span[text()='Unit Metrics']");
    Locator unitMetricFilter = getpageBrowser.locator("//div[contains(text(),' Unit Metrics ')]/..//div[contains(@class,'menu-column-chooser')]//i");
    Locator closeColumnFilter = getpageBrowser.locator("//span[@class='ag-icon ag-icon-columns']");
    Locator constraintDateRangeInputBox =getpageBrowser.locator("//p-calendar[contains(@id,'constraintDateRange')]//input");
	
	final private String tools="button:has-text(\"Tools\")";
	final private String closeicon="//p-multiselect[contains(@id,'commType')]//span[contains(@class,'icon-close')]";
	final private String applyproduct="button:has-text(\"Apply\")";
	final private String searchProduct="input[placeholder=' Search Product']";
	final private String addCloseButton="//span[text()='Add and Close']";
	final private String saveWorkSpace="text=Save";
	final private String weeklyspotedit="//app-complex-cell-input/input";
	final private String quickrefresh="//span[contains(text(),'Quick Refresh')]";
	final private String  yesquickRef="text=Yes";
	final private String createSimilarLine="text=Create Similar Line(s)";
	final private String lines="text=Line(s)";
	final private String othertools="text=Other Tools";
	final private String applyCreateSimilar="//span[text()='Create Similar Line(s)']/../..//span[text()='Apply']";
	final private String  productCount ="//div[@class='product-container prodSummary']";
	final private String  qtrtotaledit="//app-numeric-cell-editor//input";
	final private String removeUnits="text=Remove Units";
	final public String units="text=Unit(s)";
	final private String  lineProrates="text=Edit Rates and Ratings";
	final private String  applylineProrate="text=Apply";
	final private String columnFilterWeeklyTotals = "//div[contains(@col-id,'weeklyproductSummary')]//div[contains(@class,'menu-column-chooser')]//i";
	final private String editLine="text=Edit Line(s)";
	final private String lineInfoFilter="//div[contains(@id,'lineInfoFilter')]//i";
	public String  yesButton="text=Yes";
	final private String unitDetailsText="//div[text()='Unit Details']";
	
	final private String dealProrate="//span[contains(text(),'Prorate')]";
	final private String groupBy="//app-group-by-dropdown//button[@label='Group By']//span[contains(@class,'button-icon')]";
	final private String deselectGrouping="//div[contains(@class,'dropdown-clearable')]//i[contains(@class,'clear-icon')]";
	
	final private String remix="//span[contains(text(),'Remix')]";
	final private String unitDetails="//span[contains(text(),'Unit Details')]";
	final private String unitDetailsEditor="//app-grid-editor//input";
	final private String unitDetailsHeader="//span[text()='Apply']";
	final private String rateCardRefresh="//span[contains(text(),'Refresh Ratecard')]";
	final private String  refreshButton="//span[contains(text(),'Refresh')]";
	final private String  dealProrateInputField="//input[contains(@class,'inputtext')]";
	final private String  loadingPage="//span[text()='Loading...']";
	final private String  editCommTypeField="//p-dropdown[contains(@id,'commercial')]";
	final private String  editLineClassField="//p-dropdown[contains(@id,'lineclass')]";
	final private String  editUnitLengthField="//p-dropdown[contains(@id,'spotlength')]";
	final private String  editLineWarningMsg="//span[@class='ui-confirmdialog-message']";
	final private String  cellProperty3DotsIcon="//app-cell-properties[@class='weekCellProp']//i[@class='fa fa-ellipsis-v']";
	final private String  appluBtn="//span[text()='Apply']";
	final private String workspaceProgressmsg="(//div[text()='Please wait. Workspace update is in progress'])[1]";
	final private String plantotalcol="//span[text()='Plan Total: ']";
	final private String productColumn="//p[text()='Product']";
	final private String addSelectedProductButton="//span[text()='Add Selected Products']";
	final private String clickRemoveLineButton = "//span[contains(text(),'Remove Line(s)')]";
	final private String removeLineAllPeriod = "//span[contains(text(),'All Periods')]";
	final private String yesRemoveLine = "//button[contains(@class,'button')]//span[text()='Yes']";
	final private String lockUnlockLine = "//span[contains(text(),'Lock/Unlock Line(s)')]";
	final private String removeLineDisabled = "//div[contains(@class, 'option-disabled')]//span[text()='Remove Line(s)']";
	final private String editLineDisabled = "//div[contains(@class, 'option-disabled')]//span[text()='Edit Line(s)']";
	final private String lineProrateDisabled = "//div[contains(@class, 'option-disabled')]//span[text()='Edit Rates and Ratings']";
	final private String createSimilarLineEnabled = "//div[not(contains(@class,'ag-menu-option-disabled'))]//span[text()='Create Similar Line(s)']";
	final private String removeUnitsEnabled = "//div[not(contains(@class,'ag-menu-option-disabled'))]//span[text()='Remove Units']";
	final private String copyUnitsEnabled = "//div[not(contains(@class,'ag-menu-option-disabled'))]//span[text()='Copy Units']";
	final private String notesEnabled = "//div[not(contains(@class,'ag-menu-option-disabled'))]//span[text()='Notes']";
	final private String viewEditLineageCommentEnabled = "//div[not(contains(@class,'ag-menu-option-disabled'))]//span[text()='View/Edit Lineage Comment']";
	final private String ellipsisOfCellProp = "(//i[@class='fa fa-ellipsis-v'])[1]/../..";
	final private String removeUnitsCellProperties = "//div[text()='Remove Units']";
	final private String editProductAttribute = "//span[text()='Edit Product Attributes']";
	final private String EditProductAttributeLabel = "//span[text()='Edit Product Attribute']";
	final private String editAttributeApplyBtn = "//button[contains(@class,'ui-button ui-widget')]//span[text()='Apply']";
	final private String editProductAttributeHeading="//span[normalize-space(text())='Edit Product Attribute']";
	final private String calenderIcon="//button[contains(@class,'ui-datepicker')]//span[contains(@class,'ui-button-icon')]";
	final private String constraintDateRangeInput="//p-calendar[contains(@id,'constraintDateRange')]//input";
	final private String editProdAttriStartTimeDropdown= "//op-cc-new-timepicker[@placeholder='Start Time']//button";
	final private String editProdAttriEndTimeDropdown= "//op-cc-new-timepicker[@placeholder='End Time']//button";
    final public String plannerHeaderTooltip = "//div[@tooltipstyleclass='headerTooltip']";
    final private String lineInfoCheckBox = "//span[normalize-space(text())='Line Info']/..//div[contains(@class,'ag-wrapper ag-input-wrapper')]";
    final private String lineInfoCheckBoxWithAllMetricSelected="//span[normalize-space(text())='Line Info']/..//div[@class='ag-wrapper ag-input-wrapper ag-checkbox-input-wrapper ag-checked']";
    final private String lineInfoCheckBoxWithSomeMetricSelected= "//div[contains(@class,'ag-checkbox-input-wrapper ag-indeterminate')]//input[contains(@aria-label,'Press SPACE to toggle visibility (hidden)')]";
    final private String unitDetailsrateratings="//span[text()='Rates & Ratings']";
    final private String editRateandRating="//span[text()='Edit Rate and Ratings']";
    final private String lineInfoRightIcon="//div[contains(text(),'Line Info')]/..//i[contains(@class,'right')]";
    final private String lineInfoleftIcon="//div[contains(text(),'Line Info')]/..//i[contains(@class,'left')]";
    
	
    
    
    // Get Random Number with 3 Digits
	  public String randomNumber() {
	    final int max = 40;
	    final int min = 6;
	    final int diff = max - min;
	    final Random rn = new Random();
	    int i = rn.nextInt(diff + 1);
	    i += min;
	    return Integer.toString(i);
	  }


	@SuppressWarnings("deprecation")
	@Step("Navigate To WorkSpace Tab")
	public PlannerWorkSpacePage navigateToWorkSpaceTab()
	{
		getpageBrowser.waitForNavigation(() -> { // Waits for the next navigation
			workspaceTab.click(); // Triggers a navigation after a timeout
		});
		
		//getpageBrowser.waitForLoadState(LoadState.LOAD);
		/*
		 * getpageBrowser.waitForSelector(unitMetrics, new
		 * Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		 */
		 
		Logger.log("Navigate To WorkSpace Tab");
		return this;
	}

	@Step("click on Product chooser Tab")
	public PlannerWorkSpacePage  clickProductChooserTab()
	{
		click(tools);
		productChooser.click();
//		if (isElementPresent(closeicon)) {
//			click(closeicon);
//		}
		getpageBrowser.click("//op-product-chooser//span[normalize-space(text())='Linear']",
				new Page.ClickOptions().setDelay(9000.00));
		click(closeicon);
		getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
		getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
		Logger.log("click on Product Chooser Tab");
		return this;
	}


	@Step("Select Line Iteam Attributes")
	public PlannerWorkSpacePage selectLineItemAttributes(String commType, String lineClass,
			String unitLength) {
		
		if (!commType.isEmpty()) {
			multiSelectValuesSelection(AutoConfigPlannerWorkSpace.commType, commType);
		}

		if (!lineClass.isEmpty()) {
			multiSelectValuesSelection(AutoConfigPlannerWorkSpace.lineClass, lineClass);

		}
		if (!lineClass.isEmpty()) {
			multiSelectValuesSelection(AutoConfigPlannerWorkSpace.unitLength, unitLength);

		}

		return this;
	}

	@Step("click on apply product")
	public PlannerWorkSpacePage clickApplyProductChooser()
	{
		click(applyproduct);
		Logger.log("click on Apply Product");
		return this;	
	}

	@Step(" select value form MultiDropdown")
	public void multiSelectValuesSelection(String fieldName, String dataValues)
	{
		
		waitForSelectorHidden("(//span[@class='icon-loader'])[2]", 60000);
		String dataValue[]=dataValues.split(";");
		isElementPresent("//p-multiselect[contains(@id,'"+fieldName+"')]//span[contains(@class,'down')]");
		click("//p-multiselect[contains(@id,'"+fieldName+"')]//span[contains(@class,'down')]");
        
        for (int i = 0; i < dataValue.length; i++) {
        waitForSelectorVisible("//li[contains(@class,'multiselect-item')]//span[text()='" + dataValue[i] + "']/..",50000);	
        click("//li[contains(@class,'multiselect-item')]//span[text()='" + dataValue[i] + "']/..");    
        Logger.log("Selected "+fieldName+"==="+dataValue[i]);
        }

	}
	@Step("Add product in Product chooser window")
	public PlannerWorkSpacePage  addProductInChooserWindow(String productName)
	{
		getpageBrowser.locator(searchProduct).press("Backspace");
		fill(searchProduct, productName);
		click("(//span[contains(text(),'"+productName+"')]/../../../..//input)[1]");
		Logger.log("select product in Product chooser==>>"+productName);
		return this;
	}

	@Step("click on add close")
	public PlannerWorkSpacePage  clickAddClose()
	{
	click(addCloseButton);
	getpageBrowser.waitForSelector(workspaceProgressmsg,
			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(40000));
	clickwithWait(plannerHeaderTooltip, 9000.00);
	Logger.log("click on add close");
    return this;
	}

    
	 @Step("Enter Work Space data in Weekly Spots")
	    public  String []  enterWorkSpaceDataInWeeklySpots(String productName,String dayPartName,String spotsValue)
	    {
	        getpageBrowser.waitForSelector(workspaceProgressmsg,
	                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(30000));
	        getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(50000));
	        String [] workSpaceSpotData=null;
	        getpageBrowser.waitForSelector("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]",
	        new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(50000));
	        getpageBrowser.waitForSelector("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]//div[contains(@class,'disabled')]",
	        new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
	        Locator weeklyspot=getpageBrowser.locator("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]");
	        Logger.log("weeklyspot :"+weeklyspot.count());
	        workSpaceSpotData = new String[weeklyspot.count()];
	        for (int i = 0; i < weeklyspot.count(); i++) {
	            //weeklyspot.nth(i).hover();
	            //weeklyspot.nth(i).dblclick();
	            weeklyspot.nth(i).waitFor(new Locator.WaitForOptions().setTimeout(60000));
	            weeklyspot.nth(i).click(new Locator.ClickOptions().setForce(true));
	            weeklyspot.nth(i).isEditable();
	            type(weeklyspotedit, spotsValue);
	            workSpaceSpotData[i] = spotsValue;

	            Logger.log("enter WorkSpace weekly spots===>>"+workSpaceSpotData[i]);
	        }
	        click(plantotalcol);
	        return workSpaceSpotData;
	        }
	
	
	@Step("Enter Work Space data in Weekly Spots by index based")
	public  PlannerWorkSpacePage  enterWorkSpaceDataInWeeklySpotsIndex(String productName,String dayPartName,String spotsValue,int index)
	{

		getpageBrowser.waitForSelector("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]//span)["+index+"]",
				new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		Locator weeklyspot=getpageBrowser.locator("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]//div[@class='input-placeholder ng-star-inserted'])["+index+"]");
		 weeklyspot.waitFor(new Locator.WaitForOptions().setTimeout(90000));
		weeklyspot.click();
		getpageBrowser.
		type(weeklyspotedit, spotsValue);
//		click("//span[@title='"+productName+"' or contains(.,'"+productName+"')]");
		click(plannerHeaderTooltip);
		Logger.log("enter WorkSpace weekly spots===>>"+spotsValue);

		return this;
	}
	
	@Step("click  Weekly Spots")
	public  PlannerWorkSpacePage  clickWeeklySpotsValue(String productName,String dayPartName,int index)
	{

		getpageBrowser.waitForSelector("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]//span)["+index+"]",
				new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		Locator weeklyspot=getpageBrowser.locator("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]//div[@class='input-placeholder ng-star-inserted'])["+index+"]");
		//weeklyspot.dblclick();
		weeklyspot.click();
		Logger.log("<<===click weekly spots value===>>");

		return this;
	}
	
	@Step("click on Save WorkSpace page")
	public PlannerWorkSpacePage clickSaveWorkSpace() {
	clickwithWait(saveWorkSpace,5000.00);
	Logger.log("Save WorkSpace Page");
	getpageBrowser.waitForSelector("//div[contains(@class,'ui-toast-detail') and contains(.,'Workspace saved successfully')]",
			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
	return this;
	}
	
	@Step("Enter Work Space data in Weekly Spots")
	public  String[]  getWorkSpaceDataInWeeklySpots(String productName,String dayPartName,int rowId)
	{
		String[] weeklyspotvalue = null;
		getpageBrowser.waitForSelector("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//span",
		new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		Locator weeklyspot=getpageBrowser.locator("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//span");
		weeklyspotvalue = new String[weeklyspot.count()];
		for (int i = 0; i < weeklyspot.count(); i++) {
			weeklyspotvalue[i]=weeklyspot.nth(i).textContent();
			Logger.log("get the weekly spot data===>>" + weeklyspotvalue[i]);
			}
		return weeklyspotvalue;

	}
	
	@Step("enter cell Properties value")
	public PlannerWorkSpacePage enterCellPropertiesValue(String columnName,String cellValue)
	{
		Locator cellpro=getpageBrowser.locator("app-cell-properties[class='weekCellProp'] input[col-id='"+columnName+"']");
		cellpro.dblclick();
        cellpro.type(cellValue);
	   Logger.log("enter cell properties "+columnName+"====>>"+cellValue);
	return this;
	}
	
	@Step("get total qtr value")
	public String getAggregateTotalValueQtr(String product,String daypart,String columnName)
	{
	getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(90000));
	getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
	Locator totalQtr=getpageBrowser.locator("//span[@title='"+product+"' or contains(.,'"+product+"')]/../..//span[@title='"+daypart+"']"
				+ "//ancestor::div[contains(@class,'ag-body-viewport')]//div[@col-id='"+columnName+"']");
	String qtrtotalvalue=totalQtr.textContent();
	Logger.log("get total Aggregate qtr "+columnName+" value==>>"+qtrtotalvalue);
	return qtrtotalvalue;	
	}
	
	@Step("click on Quick Refresh Rate card")
	public PlannerWorkSpacePage  clickQuickRefresh()
	{   
		click(tools);
		click(quickrefresh);
	    getpageBrowser.waitForSelector(yesquickRef);
	    click(yesquickRef);
	    getpageBrowser.waitForSelector("//div[contains(text(),'Refresh workspace rates')]", new
		  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		Logger.log("click on Quick Refresh Rate card");
		quickRefreshToastMsg.isVisible();
		Logger.log("Refresh workspace rates successfully completed");
	return this;
	}

	@Step("get cell Properties value")
	public String getCellPropertiesValue(String columnName)
	{
		getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
		getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
		Locator cellpro=getpageBrowser.locator("app-cell-properties[class='weekCellProp'] input[col-id='"+columnName+"']");
		getpageBrowser.waitForSelector("app-cell-properties[class='weekCellProp'] input[col-id='"+columnName+"']", new
				  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		String cellPropertiesvalue=cellpro.getAttribute("title");
	   Logger.log("get cell properties "+columnName+"====>>"+cellPropertiesvalue);
	return cellPropertiesvalue;
	}
	
	
	@Step("create the SimilarLines")
	public PlannerWorkSpacePage  createSimilarLines(String productName,int index)
	{
		click("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../../../../../..//div//app-product-ellipsis//i");
		mousehover(lines);
		click(createSimilarLine);
		getpageBrowser.waitForSelector(applyCreateSimilar, new
				  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
		click(applyCreateSimilar);
		clickwithWait(plannerHeaderTooltip,9000.00);
		getpageBrowser.waitForLoadState(LoadState.LOAD);
		Logger.log("create SimilarLines in workspace");
		return this;
	}
	
	@Step("get the Product count")
	public int  productCountWorkSpace()
	{
		
    getpageBrowser.waitForSelector(loadingPage, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).
	setTimeout(50000));
//    getpageBrowser.waitForSelector(productnumber, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
    getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
    int productcount=getpageBrowser.locator(productCount).count();	
	Logger.log("get the product count==>>"+productcount);
	return productcount;
	}

	@Step("enter workspace data in Aggregate qtr total")
	public PlannerWorkSpacePage enterAggregateTotalValueQtr(String productName,String dayPart,String columnvalue,String value,int index)
	{
		getpageBrowser.waitForSelector("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPart+"']//ancestor::div[contains(@class,'ag-body-viewport')]//div[@row-id='"+index+"']//div[@col-id='"+columnvalue+"']", new
				  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(70000));
		getpageBrowser.locator("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPart+"']//ancestor::div[contains(@class,'ag-body-viewport')]//div[@row-id='"+index+"']//div[@col-id='"+columnvalue+"']").scrollIntoViewIfNeeded();
		click("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPart+"']//ancestor::"
				+ "div[contains(@class,'ag-body-viewport')]//div[@row-id='"+index+"']//div[@col-id='"+columnvalue+"']");
	    type(qtrtotaledit, value);
		//click("//span[@title='"+productName+"' or contains(.,'"+productName+"')]");
		//click(plantotalcol);
	    click(productColumn);
		Logger.log("enter workspace data dayPart "+columnvalue+"=====>>"+value);
		return this;
		
	}
	
	@Step("Remove weekly spots Units ")
	public PlannerWorkSpacePage  removeUnits(String productName,int index)
	{
		
		click("//div[contains(@class,'line_id_"+index+"')]//app-product-ellipsis//i");
		getpageBrowser.locator(units).first().hover();
		click(removeUnits);
		click(yesButton);
		clickwithWait(plannerHeaderTooltip,9000.00);
		Logger.log("remove Spots units");
		return this;
	}
	
	@Step("click on  Line(s) Prorate")
	public PlannerWorkSpacePage  clicklineProrate(String productName,int index)
	{
		click("//div[contains(@class,'line_id_"+index+"')]//app-product-ellipsis//i");
		mousehover(lines);
		click(lineProrates);
		Logger.log("click on line Prorates");
		return this;
	}
	
	@Step("enter cell Properties value")
	public PlannerWorkSpacePage enterlineProrateValue(String columnName,String lineProrateValue) 
	{
		
			Locator linepro=getpageBrowser.locator("//span[text()='Edit Rates and Ratings']/../..//input[@col-id='"+columnName+"']");
			getpageBrowser.click("//span[text()='Edit Rates and Ratings']/../..//input[@col-id='"+columnName+"']",
					new Page.ClickOptions().setDelay(9000));
		     linepro.last().click();
		     getpageBrowser.click(lineProrates, new Page.ClickOptions().setDelay(9000));
//		     linepro.last().press("Backspace");
//		     linepro.press("Delete");
		     linepro.last().dblclick();
    		linepro.last().type(lineProrateValue);
    		click("//span[text()='Edit Rates and Ratings']/../..//div[contains(text(),'Unit $$')]");
    		click(applylineProrate);
    		getpageBrowser.waitForSelector(loadingPage, new
					  Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setStrict(true).setTimeout(80000));
    		clickwithWait(plannerHeaderTooltip, 9000.00);
           Logger.log("enter line Prorate "+columnName+"====>>"+lineProrateValue);
			     	
	return this;
	}
	
	@Step("Navigate To Plan Total Tab")
	public PlannerWorkSpacePage navigateToPlanTotalTab()
	{
		getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(60000));
		planTotalTab.click();
		getpageBrowser.waitForLoadState();
		Logger.log("Navigate To  Plan Total Tab");
		return this;
	}
	
	@Step("get total Plan Total tab value")
	public String getAggregateTotalValuePlanTotalTab(String product,String daypart,String columnName)
	{
		Locator totalQtr=getpageBrowser.locator("//span[@title='"+product+"' or contains(.,'"+product+"')]/../..//span[@title='"+daypart+"']"
				+ "//ancestor::div[contains(@class,'ag-body-viewport')]//div[@col-id='"+columnName+"']");
	String qtrtotalvalue=totalQtr.textContent();
	Logger.log("get total Aggregate Plan Total "+columnName+" value==>>"+qtrtotalvalue);
	return qtrtotalvalue;	
	}
	
	@Step("Navigate To Period View Tab")
	public PlannerWorkSpacePage navigateToPeriodViewTab()
	{
		periodViewTab.click();
		clickwithWait(plannerHeaderTooltip, 9000.00);
		Logger.log("Navigate To Period View Tab");
		return this;
	}
	
	@Step("Open workspace Weekly Pannel")
	public PlannerWorkSpacePage clickWeeklyTotalsPanel(){
		if (weeklyTotalsPanelButton.isEnabled()){
	    weeklyTotalsPanelButton.click();
		}
	    Logger.log("clicked on weekly totals panel linear weekly");
	    return this;
	  }
	
	  @Step("Remove columns in workspace Weekly Pannel")
	  public PlannerWorkSpacePage removeFilterSelectionInWeeklyTotalsWorkspace(){
	    clickWeeklyTotalsPanel();
	   
	    if(getpageBrowser.isEnabled(columnFilterWeeklyTotals)) {
	    clickwithWait(columnFilterWeeklyTotals,4000.00);
	    }
	    else {
	    	getpageBrowser.waitForSelector(columnFilterWeeklyTotals,
	    			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
	    	click(columnFilterWeeklyTotals);
	    }
	    Logger.log("Number of checked columns on Weekly Pannels are: "+weeklyTotalsCheckedColumns.count());
	    for (int i = weeklyTotalsCheckedColumns.allTextContents().size(); i >= 1; i--) {
	    	click("(//div[contains(@class,'ng-trigger-animation')]//li//div[contains(@class,'active')])["+ i + "]");
	    }
	    if(getpageBrowser.isEnabled(columnFilterWeeklyTotals)) {
		    click(columnFilterWeeklyTotals);
		    }
	    else {
	    	getpageBrowser.waitForSelector(columnFilterWeeklyTotals,
	    			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
	    	click(columnFilterWeeklyTotals);
	    }
	    clickWeeklyTotalsPanel();
	    return this;
	  }
	  
	  @Step("Add columns in workspace Weekly Pannel")
	  public PlannerWorkSpacePage selectColumnsInWeeklyTotalsWorkspace(String columns){
	    final String[] column = columns.split(";");
	    clickWeeklyTotalsPanel();
	    getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(50000));
	    if(getpageBrowser.isEnabled(columnFilterWeeklyTotals)) {
		    clickwithWait(columnFilterWeeklyTotals,4000.00);
		    }
	    for (final String col : column) {
	    	try {
	    	click("//span[text()='" + col + "']/..//div[contains(@class,'chkbox-box')]");
	    	}
	    	catch(TimeoutError e) {
	    		 Logger.log("TimeOutError: "+e);
	    		click(columnFilterWeeklyTotals);
	    		click("//span[text()='" + col + "']/..//div[contains(@class,'chkbox-box')]");
	    	}
	    }
	    if(getpageBrowser.isEnabled(columnFilterWeeklyTotals)) {
		    click(columnFilterWeeklyTotals);
		    }
	    clickWeeklyTotalsPanel();
	    return this;
	  }
	  
	  @Step("Reselect columns in workspace Weekly Pannel")
	  public PlannerWorkSpacePage resetFilterInWeeklyTotalsWorkspace(String columns){
	    removeFilterSelectionInWeeklyTotalsWorkspace();
	    getpageBrowser.waitForLoadState();
	    selectColumnsInWeeklyTotalsWorkspace(columns);
	    return this;
	  }
	  
	  @Step("Edit Lines")
		public PlannerWorkSpacePage  editLines(String productName,int index,String commTypeNew,String lineClassNew,String unitLengthNew)
		{
			click("//span[@title='"+productName+"' or contains(.,'"+productName+"')]//following::div//app-product-ellipsis//i");
			getpageBrowser.locator(lines).first().hover();
			click(editLine);
			if (!commTypeNew.isEmpty()) {
			click(editCommTypeField);
			click("//p-dropdownitem//li[@aria-label='"+commTypeNew+"']");
			}
			
			if (!lineClassNew.isEmpty()) {
				click(editLineClassField);
				click("//p-dropdownitem//li[@aria-label='"+lineClassNew+"']");
				}
			if (!unitLengthNew.isEmpty()) {
				click(editUnitLengthField);
				click("//p-dropdownitem//li[@aria-label='"+unitLengthNew+"']");
				}
			
			click(appluBtn);
			getpageBrowser.waitForSelector(loadingPage, new
			  Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
			Logger.log("Line Edited in workspace");
			return this;
		}
	  
	  @Step("get Edit Lines message")
		public String  editLinesWarningmessage() {
		  Locator msg=getpageBrowser.locator(editLineWarningMsg);
		  Logger.log("Warning Message displayed on Workspace is: "+msg.textContent());
		  click(yesButton);
		  return msg.textContent();
	  }
	  
	  @Step("Line Info Filter Select All Metrics")
	  public PlannerWorkSpacePage lineInfoFilterSelectAll(){
		  if (isElementPresent(lineInfoRightIcon)) {
			click(lineInfoRightIcon);
		}
		  else if (isElementPresent(lineInfoleftIcon)) {
			  clickwithWait(lineInfoleftIcon,4000.00);
			click(lineInfoRightIcon);
		}
		  getpageBrowser.locator(lineInfoFilter).scrollIntoViewIfNeeded();
		  click(lineInfoFilter);
		  getpageBrowser.waitForLoadState();
		    if(lineInfoCheckBoxWithoutAnyMetricSelection.isVisible()) {
		    	lineInfoCheckBoxWithoutAnyMetricSelection.click();
		    }
		    else if(lineInfoCheckBoxWithSomeMetricSelection.isVisible()) {
		    	lineInfoCheckBoxWithSomeMetricSelection.click();
		    }
		    click("//span[contains(@class,'ag-icon-columns')]");
		    return this;
		  }
	  
	  @Step("Line Info Filter De Select All Metrics")
	  public PlannerWorkSpacePage lineInfoFilterDeSelectAll(){
	      getpageBrowser.locator(lineInfoFilter).scrollIntoViewIfNeeded();
		    clickwithWait(lineInfoFilter,4000.00);
		    getpageBrowser.waitForSelector(lineInfoCheckBox,
	    			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		    if(lineInfoCheckBoxWithSomeMetricSelection.isVisible()) {
		    	lineInfoCheckBoxWithSomeMetricSelection.click();
		    	clickwithWait(lineInfoCheckBoxWithSomeMetricSelected,3000.00);		    	
		    }
		    else if(lineInfoCheckBoxWithAllMetricSelection.isVisible()) {
		    	clickwithWait(lineInfoCheckBoxWithAllMetricSelected,3000.00);
		    }
		    clickwithWait("//span[contains(@class,'ag-icon-columns')]",3000.00);
		    return this;
		  }
	  
	  @Step("Verify Line info ")
	  public String verifyLineInfo(String productName,String lineNumber,String colfiltervalue){
		  getpageBrowser.waitForSelector(loadingPage, new
				  Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
		  clickwithWait(plannerHeaderTooltip,9000.00);
		  String filtervalue="";
		  if (!colfiltervalue.isEmpty()) {
			  filtervalue=getText("//div[@col-id='lineNo']//app-product-line-menu//span[contains(.,'"+lineNumber+"')]//following::"
			  		+ "div//span[@title='"+productName+"' or contains(.,'"+productName+"')]//following::div[@col-id='"+colfiltervalue+"']/span//span");
			  Logger.log("get LinrInfo Filter "+colfiltervalue+" value===>>"+filtervalue);
		  }
	 return filtervalue;
	  }
	  
	  @Step("Get Weeks from Start and End date ")
	  public Map<String, List<String>> getWeeksBetweenDatesWorkspaceInQuarter(String startDate,
		      String endDate) throws ParseException {
		    final DateTimeFunctions dateTimeFunction = new DateTimeFunctions();
		    final Map<String, List<String>> WeekDatesMap = new HashMap<>();
		    final List<QuarterDTO> weeks = dateTimeFunction.getQuartersBetweenDates(startDate, endDate);
		    for (final QuarterDTO week : weeks) {
		      final int qtrweek = week.getWeeksOfQuarter().size();
		      final List<String> weekList = new ArrayList<>();
		      for (int j = 0; j < qtrweek; j++) {
		        weekList.add(week.getWeeksOfQuarter().get(j).getStartDate().toString());
		      }
		      WeekDatesMap.put(week.getValue(), weekList);
		    }
		    return WeekDatesMap;
		  }
	  
	  @Step("Get Weekly values from Weekly Pannel ")
	  public String[] getWeeklyTotalValue(String fieldName, List<String> weeks){
	    boolean flag = false;
	    // click on weekly view
	    clickWeeklyTotalsPanel();
	    // check field is checked or not
	    flag = isElementPresent("//ag-grid-angular[contains(@id,'weeklySection')]//div[text()='" + fieldName + "']");
	    if (flag == false) {
	      clickWeeklyTotalsPanel();
	      selectColumnsInWeeklyTotalsWorkspace(fieldName);
	      clickWeeklyTotalsPanel();
	    }
	    Logger.log("weeks.size = " + weeks.size());
	    final String[] weeksValue = new String[weeks.size()];
	    for (int i = 0; i < weeks.size(); i++) {
	              
	        Locator weeklyField=getpageBrowser.locator("//ag-grid-angular[contains(@id,'weeklySection')]//div[text()='" +fieldName + "']/..//div[contains(@col-id,'" + weeks.get(i).replace("-", "") + "')]//span//div");
	        weeksValue[i] =weeklyField.textContent().replace(",", "").replace("$", "");
	     
	        Logger.log("get weeklyTotal Value " + fieldName + "===" + weeksValue[i]);
	    }
	    clickWeeklyTotalsPanel();
	    return weeksValue;
	  }
	  
	  @Step("Navigate To Plan Total Tab")
		public PlannerWorkSpacePage switchQuarters(String quarterValue) throws InterruptedException{
		  String updatedQuarterValue=quarterValue.substring(0, 4)+" - "+quarterValue.substring(4, 6);
			click("//app-loading-overlay//span[contains(@id,'"+updatedQuarterValue+"')]");
			getpageBrowser.waitForSelector(loadingPage, new
					  Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
			Logger.log("Navigate To "+ quarterValue+ " quarter");
			return this;
		}
	  
	  @Step("Calculate total weekly value")
	  public String calculateWeeklyTotalValue(String[] weeksMetricValues){
		  Double val=0.00;
		  for (String metricsValue:weeksMetricValues) {
			  val=val + Double.valueOf(metricsValue);
		  }
		  return String.valueOf(val);
		  
	  }
	  
	  @Step("add columns in workspace Quad View")
	  public PlannerWorkSpacePage addColumnsWeekInQuadView(String columns) {
		    final String[] column = columns.split(";");
		    weeklySpotSelectQuadView.click();
		    
		    for (final String col : column) {
		    	Locator quadViewMetrics = getpageBrowser.locator("//span[text()='"+col+"']/..//div[contains(@class,'chkbox-box') and contains(@class,'ui-state-active')]");
		    	if(quadViewMetrics.isVisible()) {
		    		Logger.log(col+" is already selected");	
		    	}
		    	else {
		      click("//span[text()='" + col + "']/..//div[contains(@class,'chkbox-box')]");
		      Logger.log(col+" is selected");	
		    	}
		    }
		    weeklySpotSelectQuadView.click();
		    return this;
		  }
	  
	  @Step(" Remove columns from WorkSpace Quad View")
	  public PlannerWorkSpacePage removeColumnsWeekInQuadView(String columns) {
	    final String[] column = columns.split(";");
	    weeklySpotSelectQuadView.click();
	    for (final String col : column) {
	      if (isElementPresent("//span[text()='" + col + "']/..//div[contains(@class,'ui-state-active')]") == true) {
	        click("//span[text()='" + col + "']/..//div[contains(@class,'ui-state-active')]");
	      }
	    }
	    weeklySpotSelectQuadView.click();
	    return this;
	  }
	  
	  @Step("get quad view metrics value")
		public String[] getQuadViewMetricsValue(String productName,String dayPartName,int metricsIndex)
		{
		  String [] workSpaceSpotData=null;
			getpageBrowser.waitForSelector("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]//span",
			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			Locator weeklyspot=getpageBrowser.locator("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]//div[@class='input-placeholder ng-star-inserted']");
			workSpaceSpotData = new String[weeklyspot.count()];
			for (int i = 0; i < weeklyspot.count(); i++) {
				Locator metrics=getpageBrowser.locator("((//div[contains(@class,'weekly')]//app-complex-cell//div[@class='input-placeholder ng-star-inserted'])["+(i+1)+"]/../..//div//div[contains(@class,'cell-value editable-field')])["+metricsIndex+"]");
	            String metricsValue=metrics.textContent().replace(",", "").replace("$", "");
			   Logger.log("get cell properties====>>"+metricsValue);
				workSpaceSpotData[i] = metricsValue;
				Logger.log("Metrics Values are ===>>"+workSpaceSpotData[i]);
				}
		return workSpaceSpotData;
		}

	  @Step("get Cell Propert Metrics Values")
		public String[] getCellPropertyMetricsValues(String productName,String dayPartName, String columnName)
		{
		  String [] workSpaceSpotData=null;
			getpageBrowser.waitForSelector("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]//span",
			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			Locator weeklyspot=getpageBrowser.locator("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//ancestor::div//div[contains(@class,'weekly-units')]");
			workSpaceSpotData = new String[weeklyspot.count()];
			for (int i = 0; i < weeklyspot.count(); i++) {
				weeklyspot.nth(i).click();
				Locator cellpro=getpageBrowser.locator("app-cell-properties[class='weekCellProp'] input[col-id='"+columnName+"']");
				getpageBrowser.waitForSelector("app-cell-properties[class='weekCellProp'] input[col-id='"+columnName+"']", new
						  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
				String cellPropertiesvalue=cellpro.getAttribute("title");
			   Logger.log("get cell properties "+columnName+"====>>"+cellPropertiesvalue);
				workSpaceSpotData[i] = cellPropertiesvalue;
				Logger.log("Cell Property "+ columnName+ " Values are ===>>"+workSpaceSpotData[i]);
				}
		return workSpaceSpotData;
		}
	  
	  @Step("click on Deal prorate")
		public PlannerWorkSpacePage  dealProrate(String metricName, String dealProrateValue) 
		{   
			click(tools);
			click(dealProrate);
			if (isElementPresent("//input[@value='"+metricName+"' and @type='radio']//following::div[@aria-checked='true']") == true) {
				Logger.log(metricName+" radio button is already selected");
		      }
			else {
				click("//input[@value='"+metricName+"' and @type='radio']//following::div[@aria-checked='true']");
				Logger.log(metricName+" radio button is selected");
			}
			Locator inputFiled = getpageBrowser.locator(dealProrateInputField);
			inputFiled.last().click();
			for (int i=0;i<6;i++) {
			inputFiled.press("Backspace");
			}
			inputFiled.type(dealProrateValue);
			click(applylineProrate);
			clickwithWait(plannerHeaderTooltip,9000.00);
			getpageBrowser.waitForSelector(loadingPage, new
					  Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
		   Logger.log("enter Deal Prorate "+metricName+"====>>"+dealProrateValue);
			
			return this;
		}
	  
	  @Step("Add multiple product in Product chooser window")
		public PlannerWorkSpacePage  addMultipleProductInChooserWindowIndexBased(String productNames,String index)
		{
		  final String[] productName = productNames.split(";");
		    for (final String prdName : productName) {
		    	getpageBrowser.keyboard().press("Control+A");
		    	productInputFiled.fill(prdName);
		    	click("(//span[normalize-space(text())='"+prdName+"']/../../../..//input)['"+index+"']");
		    }

			return this;
		}
	  
	  @Step("Enter Work Space data in Weekly Spots by index based Multiple Row")
		public  PlannerWorkSpacePage  enterWorkSpaceDataInWeeklySpotsRowAndIndex(String productName,String dayPartName,String spotsValue,int rowId,int index)
		{

			getpageBrowser.waitForSelector("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//span)["+index+"]",
					new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setStrict(true).setTimeout(90000));
			Locator weeklyspot=getpageBrowser.locator("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//div[@class='input-placeholder ng-star-inserted'])["+index+"]");
			weeklyspot.waitFor(new Locator.WaitForOptions().setTimeout(90000));
			weeklyspot.click();
			getpageBrowser.
			type(weeklyspotedit, spotsValue);
			click("//span[@title='"+productName+"' or contains(.,'"+productName+"')]");
			Logger.log("enter WorkSpace weekly spots===>>"+spotsValue);

			return this;
		}
	  
	  @Step("get total qtr value for multiple product")
		public String getAggregateTotalValueQtrBasedOnRowID(String product,String daypart,String columnName,int rowId)
		{
		  
		Locator totalQtr=getpageBrowser.locator("//span[@title='"+product+"' or contains(.,'"+product+"')]/../..//span[@title='"+daypart+"']//ancestor::div[contains(@class,'ag-body-viewport')]//div[contains(@class,'line_id_"+rowId+"')]//div[@col-id='"+columnName+"']");
		totalQtr.scrollIntoViewIfNeeded();
		String qtrtotalvalue=totalQtr.textContent();
		Logger.log("get total Aggregate qtr "+columnName+" value==>>"+qtrtotalvalue);
		return qtrtotalvalue;	
		}
	  
	  @Step("select grouping")
		public PlannerWorkSpacePage selectGrouping(int groupingLevel, String filedname )
		{
			click(groupBy);
			click("(//div[contains(@class,'group-by__dropdown')]//span[contains(.,'Grouping By')])["+groupingLevel+"]");
			click("//p-dropdownitem//li[@aria-label='"+filedname+"']");
			click(groupBy);
			clickwithWait(plannerHeaderTooltip,9000.00);
		Logger.log("Level "+groupingLevel+ " Grouping with "+filedname+ "is completed" );
		return this;	
		}
	  
	  @Step("deselect grouping")
		public PlannerWorkSpacePage deselectGrouping()
		{
			click(groupBy);
			getpageBrowser.waitForSelector(deselectGrouping,
					new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			click(deselectGrouping);
			click(groupBy);
		Logger.log("Grouping is deselected" );
		return this;	
		}
	  
	  @Step("click  Weekly Spots based on row index")
		public  PlannerWorkSpacePage  clickWeeklySpotsValueBasedOnRowId(String productName,String dayPartName,int rowId,int index)
		{

		  getpageBrowser.waitForSelector("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//span)["+index+"]",
					new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			Locator weeklyspot=getpageBrowser.locator("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//div[@class='input-placeholder ng-star-inserted'])["+index+"]");
			weeklyspot.click();
			Logger.log("<<===click weekly spots value===>>");

			return this;
		}
	  
	  @Step("Get Metrics values on Grouping rows on Quarter Tab")
			public  String  getMetricsValueAtGroupRowsQtr(String groupByFieldName, String columnName)
			{

			  	Locator groupByField=getpageBrowser.locator("//span[contains(.,'"+groupByFieldName+" Total:')]//ancestor::div[@role='row']");
			  	String rowIndex=groupByField.getAttribute("row-index");
			  	Locator groupByFieldValue=getpageBrowser.locator("//div[@role='row' and @row-index='"+rowIndex+"']//div[@col-id='"+columnName+"']//span");
			  	String metricValue=groupByFieldValue.first().textContent().replace("$", "").replace(",", "").replace("%", "");
			  	
				Logger.log("After grouping Metric value for "+columnName+ " is: ===>" + metricValue);

				return metricValue;
			}
	  
	  @Step("Get Metrics values on Grouping rows on Plan Tab")
		public  String  getMetricsValueAtGroupRowsPlanTotal(String groupByFieldName, String columnName){
            Locator groupByField=getpageBrowser.locator("//span[contains(.,'"+groupByFieldName+" Total:')]//ancestor::div[@role='row']");
		  	String rowIndex=groupByField.getAttribute("row-index");
		  	Locator groupByFieldValue=getpageBrowser.locator("//div[@role='row' and @row-index='"+rowIndex+"']//div[@col-id='"+columnName+"']");
		  	String metricValue=groupByFieldValue.textContent().replace("$", "").replace(",", "").replace("%", "");
		  	
			Logger.log("After grouping Metric value for "+columnName+ " is: ===>" + metricValue);

			return metricValue;
		}
	  
	  @Step("click on Remix")
		public PlannerWorkSpacePage  openRemixPannel()
		{   
			click(tools);
			click(remix);
			Logger.log("Remix button clicked");
			return this;
		}
	  
	  @Step("Enter data in Last Year Weights Section")
		public PlannerWorkSpacePage  enterLastYearWeights(String rowId,String daypart,String columnName,String Value)
		{ 
		  if(isElementPresent("//div[@row-index='"+rowId+"']//div[text()='"+daypart+"']/following-sibling::div[@col-id='"+columnName+"']")) {
		  getpageBrowser.waitForSelector("//div[@row-index='"+rowId+"']//div[text()='"+daypart+"']/following-sibling::div[@col-id='"+columnName+"']", 
				  new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		  click("//div[@row-index='"+rowId+"']//div[text()='"+daypart+"']/following-sibling::div[@col-id='"+columnName+"']");
		  }
		  else {
			  click("//div[@row-index='"+rowId+"']//div[@col-id='"+columnName+"']");
		  }
		  type(unitDetailsEditor,Value);
		  click(plannerHeaderTooltip);
		  Logger.log("Value entered in "+columnName+" for "+daypart);
		  return this;
		}
	  
	  @Step("Get data in Last Year Weights Section")
		public String  getLastAndCurrentYearWeights(String rowId,String daypart,String columnName)
		{   
		  String val="";
		  if(isElementPresent("//div[@row-index='"+rowId+"']//div[text()='"+daypart+"']/following-sibling::div[@col-id='"+columnName+"']")){
		   val=getText("//div[@row-index='"+rowId+"']//div[text()='"+daypart+"']/following-sibling::div[@col-id='"+columnName+"']");
		  }
		  else {
			  val=getText("//div[@row-index='"+rowId+"']//div[@col-id='"+columnName+"']");
		  }
		  Logger.log("Value for "+columnName+" is "+val);
		  return val;
		}
	  
	  @Step("Open Unit Details Window")
		public Page  clickOnUnitDetails()
		{  
		  getpageBrowser.waitForSelector(unitDetails,
	                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
		 
         Page unitDetailsPage=getpageBrowser.waitForPopup( () -> {
			  click(unitDetails);
		  }) ;
         unitDetailsPage.waitForLoadState();
		  Logger.log("Unit Details button clicked");
		  return unitDetailsPage;
		}
	  
	  @Step("Get metrics values from Unit Details Page based on index mention as (Index 0-gross rate) ,(Index 1-net Rate) ,(Index2-imps),(Index 4-gross Cpm) ,(Index 9-net cpm),(Index 6-%rc)")
	  public String getMetricsValuesFromUnitDetails(int rowId,String productName, int indexvalue, Page unitDetails) {
		  String val;
		  if (indexvalue==0) {
			  Locator spotlocator= unitDetails.locator("//div[@row-id='"+(rowId-1)+"']//div[text()='"+productName+"']/..//div[@col-id='valueField']/span/span");
			   val=spotlocator.first().textContent().replace("$", "").replace(",", "").replace("%", "");  
		}
		  else
		  {
			  unitDetails.click(unitDetailsText, new Page.ClickOptions().setDelay(3000.00));
			  Locator spotlocator= unitDetails.locator("//div[@row-id='"+(rowId-1)+"']//div[text()='"+productName+"']/..//div[@col-id='valueField_"+indexvalue+"']/span/span");
			   val=spotlocator.first().textContent().replace("$", "").replace(",", "").replace("%", "");  
		  }
		  
		 
		  Logger.log("Value get from Unit Details page is: "+val);
		  return val;
		  }
	  

	  @Step("enter metrics values from Unit Details Page based on index mention as (Index 4-gross rate) ,(Index 5-net Rate) ,(Index6-imps),(Index 8-gross Cpm) ,(Index 9-net cpm),(Index 10-%rc)")
	  public void enterPriceBySpotValue(int rowValue,String productName, String value,String colName, Page unitDetails) {
		   
		  unitDetails.locator("//div[contains(@row-index,'"+(rowValue-1)+"')]//div[text()='"+productName+"']/..//i").first().click();
		  unitDetails.click(editRateandRating);
		  unitDetails.locator("//input[@col-id='"+colName+"']").dblclick();
		  unitDetails.locator("//input[@col-id='"+colName+"']").press("Backspace");;
		  unitDetails.type("//input[@col-id='"+colName+"']",value);
		  unitDetails.click(unitDetailsrateratings);
		  unitDetails.click(unitDetailsHeader);
	  }

	  @Step("click on Refresh Ratecard")
		public PlannerWorkSpacePage  openRefreshRatecardPage()
		{   
		    click(tools);
			click(rateCardRefresh);
			Logger.log("Refresh Ratecard button clicked");
			return this;
		}
	  
	    @Step("Update details on Refresh Ratecard")
		public PlannerWorkSpacePage  refreshRatecard(String columnName)
		{   
			click("//p-radiobutton[@label='"+columnName+"' and @name='constant']");
			click(refreshButton);
			getpageBrowser.waitForSelector(loadingPage, new
					  Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
			Logger.log("Refresh Ratecard  using: "+columnName + " as constant");
			getpageBrowser.waitForSelector("//div[contains(@class,'ui-toast-detail') and contains(.,'Refresh ratecard has been completed.')]",
					new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			return this;
		}
	    
	    @Step("Copy weekly units from weekly spot")
		public  PlannerWorkSpacePage  copypasteWeeklyUnitsFromWeeklyCell(String productName,String dayPartName,int rowId,int index, String action)
		{

			getpageBrowser.waitForSelector("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//span)["+index+"]",
					new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			Locator weeklyspot=getpageBrowser.locator("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')])["+index+"]");
			weeklyspot.click();
			
			Locator cellproperty3dotIcon=getpageBrowser.locator(cellProperty3DotsIcon);
			cellproperty3dotIcon.scrollIntoViewIfNeeded();
			cellproperty3dotIcon.focus();
			cellproperty3dotIcon.click();
			Locator actionTobePerform=getpageBrowser.locator("//app-cell-properties[@class='weekCellProp']//div[contains(@class,'spot-menu')]//div[normalize-space (text ())='"+action+"']");
			actionTobePerform.focus();
			actionTobePerform.click();
			cellproperty3dotIcon.scrollIntoViewIfNeeded();
			cellproperty3dotIcon.focus();
			cellproperty3dotIcon.click();
			clickwithWait(plannerHeaderTooltip,9000.00);
			Logger.log(action+" action performed");
			return this;
		}
	    
	    @Step("get weekly spot")
	    public  String  getWeeklySpotsIndexbased(String productName,String dayPartName,int rowId,int index )
		{
			getpageBrowser.waitForSelector("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//span)["+index+"]",
			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			Locator weeklyspot=getpageBrowser.locator("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//span)["+index+"]");
			String weeklyspotvalue = weeklyspot.innerText();
			Logger.log("get the weekly spot data at index "+index +" ===>>" + weeklyspotvalue);
			return weeklyspotvalue;
		}
	    
	    @Step("Copy paste remove weekly units from product line")
		public  PlannerWorkSpacePage  copypasteWeeklyUnitsFroProductLine(String productName,String dayPartName,int rowId, String action)
		{

			getpageBrowser.waitForSelector("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]//span",
					new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			click("(//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../../../../../..//div//app-product-ellipsis//i)["+rowId+"]");
			getpageBrowser.locator(units).hover();
			Locator actionTobePerform=getpageBrowser.locator("//span[normalize-space (text ())='"+action+"']");
			actionTobePerform.focus();
			actionTobePerform.click();
			if (action.equalsIgnoreCase(AutoConfigPlannerWorkSpace.removeUnits)) {
				click(yesButton);
			}
			clickwithWait(plannerHeaderTooltip,9000.00);
			Logger.log(action+" action performed");
			return this;
		}
	    
	    @Step("Enter Work Space data in Weekly Spots based on rows")
		public  String []  enterWorkSpaceDataInWeeklySpotsRowBased(String productName,String dayPartName,String spotsValue,int rowId)
		{
			getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(60000));
			getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
			String [] workSpaceSpotData=null;
			getpageBrowser.waitForSelector("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]",
			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			Locator weeklyspot=getpageBrowser.locator("//span[@title='"+productName+"' or contains(.,'"+productName+"')]/../..//span[@title='"+dayPartName+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')]");
			workSpaceSpotData = new String[weeklyspot.count()];
			for (int i = 0; i < weeklyspot.count(); i++) {
				//weeklyspot.nth(i).
				weeklyspot.nth(i).focus();
				weeklyspot.nth(i).click();
				type(weeklyspotedit, spotsValue);
				workSpaceSpotData[i] = spotsValue;
//				click("//span[@title='"+productName+"' or contains(.,'"+productName+"')]");
				Logger.log("enter WorkSpace weekly spots===>>"+workSpaceSpotData[i]);
				}
			return workSpaceSpotData;

		}
	    
    @Step("Add columns in workspace")    
    public PlannerWorkSpacePage reselectClmnsWorkspaceQrtly(String columns)
	      throws InterruptedException {
    	getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
    	getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
	    if (quarterTotalExpandButton.isVisible()) {
	    	quarterTotalExpandButton.click();
	    	Logger.log("clicked on quarterTotalExpandButton");
	    }
	    if (expandUnitMetric.isVisible()) {
	    	expandUnitMetric.click();
	    	Logger.log("clicked on expand for unit metric");
	    }
	    if (planTotalExpandButton.isVisible()) {
	    	planTotalExpandButton.click();
	    	Logger.log("clicked on expand for Plan total");
	    }
	    workspaceColumnFilter.click();
	    allColumnsCheckBox.click();
	    allColumnsCheckBoxCheckedQuarterly.waitFor(new Locator.WaitForOptions().setTimeout(90000));
	   allColumnsCheckBoxCheckedQuarterly.click();
	    Arrays.stream(columns.split(";")).forEach(column -> {
	    	getpageBrowser.getByPlaceholder("Search...").fill(column);
	    	click("//span[text()='" + column + "']//parent::div//input[contains(@type,'checkbox')]");
	    	});
		Arrays.stream(columns.split(";")).forEach(column -> Logger.log("select column name===" + column));
		getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
		getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
		closeColumnFilter.click();
	    return this;
	  }
    
    
    
    @Step("Add columns in unit Metrics")    
    public PlannerWorkSpacePage reselectClmninUnitMetric(String columns)
	      throws InterruptedException {
    	getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
    	getpageBrowser.waitForLoadState(LoadState.NETWORKIDLE);
	    if (quarterTotalExpandButton.isVisible()) {
	    	quarterTotalExpandButton.click();
	    	Logger.log("clicked on quarterTotalExpandButton");
	    }
	    if (expandUnitMetric.isVisible()) {
	    	expandUnitMetric.click();
	    	Logger.log("clicked on expand for unit metric");
	    }
	    if (planTotalExpandButton.isVisible()) {
	    	planTotalExpandButton.click();
	    	Logger.log("clicked on expand for Plan total");
	    }

	    if (selectSelectionsDrop.isVisible()) {
	    	selectSelectionsDrop.click();
	    	if(checkunitMetrics.isVisible() == false) {
	    		unitMetricsCheckbox.click();
	    	}
	    	Logger.log("clicked on expand for Plan total");
	    }


	    unitMetricFilter.click();
	    allColumnsCheckBox.click();
	    if (allColumnsCheckBoxCheckedQuarterly.isVisible()) {
	    	allColumnsCheckBoxCheckedQuarterly.click();
	    }
	    Arrays.stream(columns.split(";")).forEach(column -> click("//span[text()='" + column + "']//parent::div//input[contains(@type,'checkbox')]"));
		Arrays.stream(columns.split(";")).forEach(column -> Logger.log("select column name===" + column));
      
		closeColumnFilter.click();
	    return this;
	  }

    @Step("click on add close for local")
	public PlannerWorkSpacePage  clickAddSelectedProductLocal() {
	click(addSelectedProductButton);
	getpageBrowser.waitForSelector(workspaceProgressmsg,
			new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
	clickwithWait(plannerHeaderTooltip,9000.00);
	Logger.log("click on add close");
    return this;
	}

    @Step("remove line")
	public PlannerWorkSpacePage  removeLine(String productName,int index)
	{
    	click("//div[contains(@class,'line_id_"+index+"')]//app-product-ellipsis//i");
    	getpageBrowser.locator(lines).first().hover();
		click(clickRemoveLineButton);
		getpageBrowser.waitForSelector(removeLineAllPeriod, new
				  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
		click(removeLineAllPeriod);
		getpageBrowser.waitForSelector(yesRemoveLine, new
				  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
		click(yesRemoveLine);
		getpageBrowser.waitForLoadState(LoadState.LOAD);
		clickwithWait(plannerHeaderTooltip,9000.00);
		Logger.log("removed line in workspace");
		return this;
	}
		
    @Step("Lock/Unlock Line")
	public PlannerWorkSpacePage  lockUnlockLine(String productName,String productLineNumber)
	{	
    	click("//div[contains(@class,'line_id_"+productLineNumber+"')]//app-product-ellipsis//i");
    	getpageBrowser.locator(lines).first().hover();
		getpageBrowser.waitForSelector(lockUnlockLine, new
				  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
		click(lockUnlockLine);
		clickwithWait(plannerHeaderTooltip,9000.00);
		Logger.log("Lock/Unlock line clicked");
		return this;
	}
    
    @Step("Verify Lock/Unlock Line")
    public boolean verifyLineIsLocked(String productLineNumber) {
    	Locator constraintDateRangeInputBox =getpageBrowser.locator("//div[@id='line-no']//span[contains(text(),'" + productLineNumber + "')]//i[contains(@class,'pi-lock')]");
        boolean isLineLocked = constraintDateRangeInputBox.isVisible();
        return isLineLocked;
      }
    
    // verify Add Constraint, Remove Line(s), Edit Line(s), Line(s) Prorate options are disable after
    // lock the line
    @Step("Verify Options are disabled after Lock the Line")
    public boolean verifyOptionsDisabledAfterLineIsLocked(String productName,
        String productLineNumber) throws InterruptedException {
      boolean areOptionsDisabled = false;
      click("//div[contains(@class,'line_id_"+productLineNumber+"')]//app-product-ellipsis//i");
      getpageBrowser.locator(lines).first().hover();
      if (isElementPresent(removeLineDisabled)
          && isElementPresent(editLineDisabled) && isElementPresent(lineProrateDisabled)) {
        areOptionsDisabled = true;
        click(plannerHeaderTooltip);
      }
      return areOptionsDisabled;
    }
    
  //verify Create Similar Line(s), Remove / Copy / Paste Units, Notes, View/Edit Lineage Comment
    // options are enable after unlock the line
    @Step("Verify Options are enabled after unlock the Line")
    public boolean verifyOptionsEnabledAfterLineIsUnlocked(String productName, String productLineNumber)
        throws InterruptedException {
    	 boolean areOptionsDisabled = false;
    	 click("//div[contains(@class,'line_id_"+productLineNumber+"')]//app-product-ellipsis//i");
    	 getpageBrowser.locator(lines).first().hover();
         if (isElementPresent(createSimilarLineEnabled) && isElementPresent(editLine) && isElementPresent(lineProrates)) {
           areOptionsDisabled = true;
         }
         click(plannerHeaderTooltip);
         return areOptionsDisabled;
    }
    
    @Step("Remove Weekly units")
    public PlannerWorkSpacePage clickRemoveUnits() {
      click(ellipsisOfCellProp);
      getpageBrowser.locator(removeUnitsCellProperties).scrollIntoViewIfNeeded();
      click(removeUnitsCellProperties);
      clickwithWait(plannerHeaderTooltip,9000.00);
      Logger.log("click on RemoveLink cell properties");
      return this;
    }
    
    @Step("Mult select DOW")
    public void multiSelectValuesSelectionDow(String fieldName, String dataValues) {
        final String[] data = dataValues.split(";");
        getpageBrowser.waitForSelector("//p-multiselect[@id='" + fieldName+"']/..//span[contains(@class,'ui-multiselect-label ui-corner-all')]", new
				  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
        click("//p-multiselect[@id='" + fieldName + "']/..//span[contains(@class,'ui-multiselect-label ui-corner-all')]");
        Logger.log("Clicked " + fieldName + " multiselect");
        click("//div[contains(@class,'ui-multiselect-header')]//div[contains(@class,'ui-state-default') or contains(@classs,'ui-state-default ui-state-active')]");
        click("//div[contains(@class,'ui-multiselect-header')]//div[contains(@class,'ui-state-default') or contains(@classs,'ui-state-default ui-state-active')]");
        for (final String datum : data) {
        	 getpageBrowser.waitForSelector("//li[contains(@class,'multiselect-item')]//span[text()='" + datum + "']/..", new
   				  Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
          click("//li[contains(@class,'multiselect-item')]//span[text()='" + datum + "']/..");
          Logger.log("Selected " + datum);
        }
      }
    
    @Step("Edit Product Attributes")
    public PlannerWorkSpacePage editProductAttributes(int productLineNum,String dow, String startTime, String endTime, String startEndDate)
    	      throws InterruptedException {
    	click("//div[contains(@class,'line_id_"+productLineNum+"')]//app-product-ellipsis//i");
    	getpageBrowser.locator(othertools).first().hover();
    	    click(editProductAttribute);
    	    if (!dow.isEmpty()) {
    	      multiSelectValuesSelectionDow("EditAttributeDow",
    	          dow);
    	      getpageBrowser.waitForSelector(workspaceProgressmsg,
    					new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(30000));
    	      click(EditProductAttributeLabel);
    	    }
    	    if(!startTime.isEmpty()) {
    	    	click(editProdAttriStartTimeDropdown);
    	    	click("//li//span[normalize-space(text())='"+startTime+"']");
    	    }
    	    if(!endTime.isEmpty()) {
    	    	click(editProdAttriEndTimeDropdown);
    	    	click("//li//span[normalize-space(text())='"+endTime+"']");
    	    }
    	    if(!startEndDate.isEmpty()) {
    	    	click(calenderIcon);
    			constraintDateRangeInputBox.clear();
    			type(constraintDateRangeInput,startEndDate);
    			getpageBrowser.waitForLoadState(LoadState.LOAD, new Page.WaitForLoadStateOptions().setTimeout(80000));
    			click(editProductAttributeHeading);
    	    }
    	    click(editAttributeApplyBtn);
    	    click(yesButton);
    	    clickwithWait(plannerHeaderTooltip,9000.00);
    	    return this;
    	  }
    
    @Step("Get value from line info section")
    public String getValueFromLineInfoSection(String productName, int row, String LineInfoColId) {
        String value = "";
        if (isElementPresent("//span//div[text()='" + productName + "']/ancestor::div[contains(@class,'line_id_"+ row + "')]//div[@col-id='" + LineInfoColId + "']//div//span"))
          value = getText("//span//div[text()='" + productName + "']/ancestor::div[contains(@class,'line_id_"+ row + "')]//div[@col-id='" + LineInfoColId + "']//div//span");
        return value;

      }
    
    @Step("get total Plan Total value for multiple product")
	public String getAggregateTotalValuePlanTotalBasedOnRowID(String product,String daypart,String columnName,int rowId)
	{
		Locator totalQtr=getpageBrowser.locator("//span[@title='"+product+"' or contains(.,'"+product+"')]/../..//span[@title='"+daypart+"']//ancestor::div[contains(@class,'ag-body-viewport')]//div[contains(@class,'line_id_"+rowId+"')]//div[@col-id='"+columnName+"']");
	String qtrtotalvalue=totalQtr.textContent();
	Logger.log("get total Aggregate qtr "+columnName+" value==>>"+qtrtotalvalue);
	return qtrtotalvalue;	
	}
    
    @Step("Verify cell disability")
	public Boolean verifyWeeklyCell(String product,String daypart,int rowId, int index, String hoverText)
	{
		Locator weekDisabledCell=getpageBrowser.locator("(//span[@title='"+product+"' or contains(.,'"+product+"')]/../..//span[@title='"+daypart+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')])["+index+"]//div[@class='input-placeholder input-placeholder__disabled']");
		Logger.log("val  ==>>"+weekDisabledCell.isVisible());
		Boolean cellDisable=false;
		if(weekDisabledCell.isVisible()) {
			weekDisabledCell.focus();
//			weekDisabledCell.click();
			weekDisabledCell.hover();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getpageBrowser.hover("((//span[@title='"+product+"' or contains(.,'"+product+"')]/../..//span[@title='"+daypart+"']//following::div[contains(@class,'line_id_"+rowId+"')]//div[contains(@class,'weekly-units')])["+index+"]//app-complex-cell)[1]");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(isElementPresent("//app-tooltip-component//span[normalize-space(text())='"+hoverText+"']")) {
				cellDisable=true;	
			}
			
		}
	Logger.log("weekly cell disable and howing msg on hover is  ==>>"+cellDisable);
	return cellDisable;	
	}
    
    @Step("Browser Zooom In")
	public PlannerWorkSpacePage browserZoomIn()
	{
    	getpageBrowser.keyboard().press("Control+Shift+Equal");
    	getpageBrowser.keyboard().press("Control+Shift+Equal");
    	getpageBrowser.keyboard().press("Control+Shift+Equal");
    	return this;
	}
    
    @Step("Browser Zooom out")
	public PlannerWorkSpacePage browserZoomOut()
	{
    	getpageBrowser.keyboard().press("Control+Minus");
    	getpageBrowser.keyboard().press("Control+Minus");
    	getpageBrowser.keyboard().press("Control+Minus");
    	return this;
	}
  
}
