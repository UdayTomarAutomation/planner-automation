/**
 * 
 */
package com.operative.pages.plannerworkspace;

import java.util.List;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;

import io.qameta.allure.Step;

/**
 * @author ajay_bhave
 *
 */
public class PlannerDigitalWorkspacePage extends BasePage {
	SoftAssert getSoftAssert;

	public PlannerDigitalWorkspacePage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert = new SoftAssert();
	}

	final private String tools = "//span[text()='Tools']";
	final private String productChooser = "//span[text()='Product Chooser']";
	final private String workspaceTab = "//div//span[contains(text(),'Workspace')]";
	final private String saveButton = "//span[text()='Save']";
	final private String generalTab = "//span[text()='General']";
	final private String financeSummaryTab = "//span[text()='Finance Summary']";
	final private String packageButton = "//span[contains(text(),'Packages')]";
	final private String productSearchField = "//input[@placeholder=' Search Product']";
	final private String packageSearchField = "//input[@placeholder=' Search Package']";
	final private String addSelectedButton = "//span[text()='Add Selected']";
	final private String headerButton = "//div[@role='navigation']//span[contains(text(),'Header')]";
	final private String advanceEditOption = "//div[@class='ag-menu ag-ltr ag-popup-child']//span[contains(text(),'Advanced Edit')]";
	final private String advEditApplyButton = "//div[contains(@class,'sidebar-container-advanced-edit')]//span[text()='Apply']";
	final private String advEditCancelButton = "//div[contains(@class,'sidebar-container-advanced-edit')]//span[text()='Cancel']";
	final private String amountTermInputField = "//input[contains(@class,'ui-dropdown-filte')]";
	final private String unitTermInputField = " //input[contains(@class,'ui-dropdown-filte')]";
	final private String selectedTabName = "//div[@class='grid-tabview']//a[@aria-selected='true']/span";
	final private String summaryPanel = "//div[@id='summary-panel']//div[contains(@class,'summary-panel-button')]";
	final private String summaryPanelExpanded = "//div[contains(@class,'summary-panel-button summary-panel-button-left')]";
	final private String getNumberofLines = "//div[@class='ag-pinned-left-cols-container']/div";
	final private String totalNetCostDeal = "//div[@class='digital-total-net-cost']/span[@class='total-col-info']";
	final private String pricingTab = "//span[text()='Pricing']";
	final private String sidebarClose = "//a[@class='ui-sidebar-close']";
	final private String cancelTbutton = "//p-button[@class='op-button-outline']//button//span[normalize-space()='Cancel']";

	

	@Step("Navigate To WorkSpace Tab")
	public PlannerDigitalWorkspacePage navigateToWorkSpaceTab() {
		getpageBrowser.waitForNavigation(() -> { // Waits for the next navigation
			click(workspaceTab); // Triggers a navigation after a timeout
		});

		Logger.log("Navigate To WorkSpace Tab");
		return this;
	}

	@Step("Navigate To Finance Summary Tab")
	public PlannerDigitalWorkspacePage navigateToFinanceSummaryTab() {
		click(financeSummaryTab);
		Logger.log("Navigate to Finance Summary Tab");
		return this;
	}

	@Step("Navigate to header")
	public void navigateToHeader() {
		click(headerButton);
		Logger.log("Navigate to Header");
	}

	@Step("Add Product To Digital Workspace")
	public void addDigitalProduct(String productName) {
		String[] products = productName.split(";");

		click(tools);
		click(productChooser);
		for (String prd : products) {
			click(productSearchField);
			getPageBrowser().keyboard().press("Control+A");
			getPageBrowser().keyboard().press("Delete");
			type(productSearchField, prd);
			String productOption = "//div[text()='" + prd
					+ "']/preceding-sibling::div//div[contains(@class,'ag-input-field')]";
			click(productOption);
			Logger.log("Selected Product is " + prd);
		}

		click(addSelectedButton);

	}

	@Step("Add Package To Digital Workspace")
	public void addPackageInChooserDigital(String packageNames) {
		String[] packages = packageNames.split(";");

		click(tools);
		click(productChooser);
		click(packageButton);
		for (String pkg : packages) {
			click(packageSearchField);
			getPageBrowser().keyboard().press("Control+A");
			getPageBrowser().keyboard().press("Delete");
			type(packageSearchField, pkg);
			String pkgOption = "//span[text()='" + pkg
					+ "']/ancestor::div[@role='gridcell']//preceding-sibling::div//input[contains(@aria-label,'Toggle Row Selection')]";
			click(pkgOption);
			Logger.log("Selected Packages is " + pkg);
		}
		click(addSelectedButton);

	}

	@Step("Save Digital Workspace")
	public void saveDigitalWorkspace() {
		click(saveButton);
		Logger.log("save workspace");
	}

	@Step("Remove line from workspace")
	public void removeLineFromWorkspace(String productName) {
		// removeLineItemBaseOnLineNumber
	}

	@Step("Enter product values in workspace grid")
	public void editDigitalProductLineTextField(String fieldName, String value, int rowNum) {
		String field = "(//div[contains(@role,'gridcell') and contains(@col-id,'" + fieldName + "')])[" + rowNum
				+ "]/span";
		String inputField = "//div[contains(@role,'gridcell') and contains(@col-id,'" + fieldName + "')]//input";
		click(field);
		getPageBrowser().keyboard().press("Control+A");
		getPageBrowser().keyboard().press("Delete");
		type(inputField, value);
		Logger.log("Entered " + value + " in " + fieldName + " field");
	}

	@Step("Get Text Field value from the workspace")
	public String getDigitalProductLineTextFieldvalue(String fieldName, int rowNum) {
		String value;
		String inputField = "(//div[contains(@role,'gridcell') and contains(@col-id,'" + fieldName + "')])[" + rowNum
				+ "]/span";
		value = getText(inputField).replace(",", "");
		Logger.log("Value for the field " + fieldName + " value");
		return value;
	}

	@Step("Get Line Number")
	public String getProductLineNo(String productName) {
		String productLineNo = "//div[contains(text(),'" + productName
				+ "')]/ancestor::div[@role='gridcell']/..//div[@id='line-no']/span";
		final String lineNo = getText(productLineNo);
		Logger.log("The line number for product  " + productName + " is " + lineNo);
		return lineNo;

	}

	@Step("Expand the Package Line Item base on Line No. ")
	public void expandPackageLineItem(String getLineNo) {
		String expandButton = "//div[@id='line-no']//span[text()='" + getLineNo
				+ "']/ancestor::div[@role='row']//span[@class='ag-group-contracted' and @ref='eContracted']//i";
		click(expandButton);
		Logger.log("Expanded Package Line : " + getLineNo);
	}

	@Step("get package child lines list")
	public List<String> getPackageChildLineList(String getLineNo) {
		String childlLines = "//div[@id='line-no']//span[contains(text()," + "'" + getLineNo
				+ ".')]/ancestor::div[@role='row']//div[@col-id='productName']//div[@class='digital-product-name']";

		List<String> packageChildLines = getPageBrowser().locator(childlLines).allTextContents();
		return packageChildLines;
	}

	@Step("Navigate to Advance Edit Page and validate Advance Edit page")
	public void navigateToAdvanceEditPage(String lineNo) {
		String kababIcon = "//div[@id='line-no']//span[text()='" + lineNo
				+ "']/ancestor::div[@role='gridcell']/..//div[@id='line-menu']//i";
		click(kababIcon);
		click(advanceEditOption);
		Logger.log("advance edit opened");

	}

	@Step("Update Amount Terms Of the lines in finance Summary tab with given value")
	public void updateAmountTermsInFinanceSummaryTab(String value, int index) {
		String amountTermDropdown = "(//div[@col-id='suggestedAmountTerm' and contains(@class,'op-filter-dropdown')]"
				+ "//div[@id='digital-custom-dropdown'])[" + index + "]";
		String amountTermValue = "//li[contains(@class,'ui-dropdown-item')]/span[text()='" + value + "']";
		click(amountTermDropdown);
		type(amountTermInputField, value);
		click(amountTermValue);
		Logger.log("Selected Amount term value " + value);
	}

	@Step("To Update unit Terms Of the lines in finance Summary tab with given value")
	public void updateUnitTermsInFinanceSummaryTab(String value, int index) {
		String unitTermDropdown = "(//div[@col-id='suggestedUnitTerm' and contains(@class,'op-filter-dropdown')]//div[@id='digital-custom-dropdown'])["
				+ index + "]";
		String unitTermsValue = "//li[contains(@class,'ui-dropdown-item')]/span[text()='" + value + "']";
		click(unitTermDropdown);
		type(unitTermInputField, value);
		click(unitTermsValue);
		Logger.log("Selected Unit term value " + value);

	}

	@Step("Get Unit Terms Value From Finance Summary Tab")
	public String getUnitTermsValue(int index) {
		String unitTermField = "(//div[@col-id='suggestedUnitTerm' and contains(@class,'op-filter-dropdown')]//div[@id='digital-custom-dropdown'])["
				+ index + "]//span";
		String value = getText(unitTermField);
		Logger.log("Unit Term Value for index " + index + " is " + value);
		return value;

	}

	@Step("Get Amount Terms Value From Finance Summary Tab")
	public String getAmountTermsValue(int index) {
		String amountTermField = "(//div[@col-id='suggestedAmountTerm' and contains(@class,'op-filter-dropdown')]//div[@id='digital-custom-dropdown'])["
				+ index + "]//span";
		String value = getText(amountTermField);
		Logger.log("Amount Term Value for index " + index + " is " + value);
		return value;
	}

	@Step("Get Selected Tab Name")
	public String getSelectedTabInWorkspace() {
		String value = getText(selectedTabName);
		Logger.log("Selected Tab Name is " + value);
		return value;
	}

	@Step("Open Summary Panel in Workspace")
	public void expandSummaryPanel() {
		click(summaryPanel);
		Logger.log("Summary Panel expended");
	}

	@Step("close Summary Panel in Workspace")
	public void collapseSummaryPanel() {
		click(summaryPanelExpanded);
		Logger.log("Summary Panel Collapsed");
	}
	
	@Step("Get Total  Line Number")
	public int getTotalNOofLine() {
		try {
			final Locator comboItems = getpageBrowser.locator(getNumberofLines);
			List<String> value = comboItems.allTextContents();
			Logger.log("Number of line is" + value.size());
			return value.size();
		} catch (final Exception e) {

			return 0;
		}
	}
	
	@Step("Line with correct name is visible in workspace")
	public boolean isLinePresent(String PrdName) {
		boolean isDisplayed=false;
		try {
			String lineName = "//span[(text()='" +PrdName+ "')]/../../..//div[@id='line-menu']/i";
			waitForSelectorVisible(lineName, 10000);
			isDisplayed = isElementPresent(lineName);
			return isDisplayed;
		} catch (final Exception e) {

			return isDisplayed;
		}
	}
	
	@Step("Get Line Values of Line Items")
	public String getValuesOfLineItems(String lineName,String colName) {
      	
		String rowIndex=getAttribute("//span[text()='"+lineName+"']/../../../.", "row-index");
		String value = getText("//div[@row-index='"+rowIndex+"']//div[@col-id='"+colName+"']/span");
		Logger.log("value of '"+colName+"' for line '"+lineName+"' " + value);
		return value.trim();
	}
	
	@Step("Get ReadOnly Line Values of Line Items")
	public String getReadOnlyValuesOfLineItems(String lineName,String colName) {
      	
		String rowIndex=getAttribute("//span[text()='"+lineName+"']/../../../.", "row-index");
		String value = getText("//div[@row-index='"+rowIndex+"']//div[@col-id='"+colName+"']");
		Logger.log("value of '"+colName+"' for line '"+lineName+"' " + value);
		return value.trim();
	}

	@Step("Get Total Net cost")
	public String getTotalNetCostOnDeal() {

		String totalLineCost = getText(totalNetCostDeal);
		Logger.log("value of Total Net Line Cost on AOS deal is" + totalLineCost);
		return totalLineCost;
	}
	
	@Step("click on Advance Edit as per line name")
	public void clickOnKebabMenu(String PrdName) {

		try {
			String kebabMenu = "//span[(text()='" + PrdName + "')]/../../..//div[@id='line-menu']/i";
			click(kebabMenu);
			click(advanceEditOption);
			Logger.log(" able to clcik on advanceEditOption ");
		} catch (final Exception e) {

			Logger.log("Not able to clcik on advanceEditOption ");
		}
	}

	@Step("Check the checkbox of lineclass")
	public boolean isLineClassCheckboxChecked(String lineClassName) {
		boolean isDisplayed = false;
		try {
			String isCheckedlineClassName = "//input[@type='checkbox' and @id = '" + lineClassName + "']";
			isDisplayed = getpageBrowser.isChecked(isCheckedlineClassName);
			Logger.log(lineClassName+" is checked/unchecked >>>>>" +isDisplayed);
			return isDisplayed;
		} catch (final Exception e) {

			return isDisplayed;
		}
	}

	@Step("Navigate to pricing tab")
	public void navigateToPricingTab() {

		try {
			click(pricingTab);
			Logger.log("navigated to pricing Tab");
		} catch (final Exception e) {

			Logger.log("not able to navigate to pricing Tab");
		}
	}

	@Step("close side bar")
	public void closeSideBar() {

		try {
			click(sidebarClose);
			Logger.log("closed the sideBar");
		} catch (final Exception e) {

			Logger.log("Not able to closed the sidebar");
		}
	}
	
	@Step("Cancel Target tab Button")
	public void cancelTargetabButton() {

		try {
			click(cancelTbutton);
			Logger.log("closed the Target tab");
		} catch (final Exception e) {

			Logger.log("Not able to closed the Target Tab");
		}
	}
	
	@Step("Get Targetting Summary")
	public boolean targetSummary(String lineName, String mtops) {
		boolean isDisplayed = false;
		try {
			click("//span[(text()='" + lineName + "')]/../../..//i[contains(@class,' icon-targets')]");
			Logger.log("navigated to targetting Summary");
			String tWithOp[] = mtops.split("&");

			for (String targetWithOp : tWithOp) {

				String targetOpName[] = targetWithOp.split(":");
				String targetName = targetOpName[0];
				String targetxpath = "//span[@class='target-label' and contains(@title, '" + targetName + "')]";
				String searchT = getText(targetxpath).trim();

				if (targetName.equals(searchT)) {
					click(targetxpath);
					String tOpName[] = targetOpName[1].split(",");
					for (int top = 0; top <= tOpName.length - 1; top++) {
						String isVisibleTopXPath = "//span[@title='" + targetName
								+ "']/parent::label/parent::div/following-sibling::div/ul/li/span[text()='"
								+ tOpName[top] + "']";
						isDisplayed = isElementPresent(isVisibleTopXPath);
					}
				}

			}

		} catch (final Exception e) {

			Logger.log("not able to navigate to Tar");
			return false;
		}
		return isDisplayed;
	}

}
