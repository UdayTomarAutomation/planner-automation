package com.operative.pages.spotLight;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author tharun.kg
 *
 */
import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import com.mysql.cj.log.Log;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;

import io.qameta.allure.Step;

/**
 * @author tharun.kg contains general methods for spotlight
 */
public class SpotLightPage extends BasePage {
	SoftAssert getSoftAssert;

	public SpotLightPage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert = new SoftAssert();
		// TODO Auto-generated constructor stub
	}

	final private String hamburgerIcon = "//button[@id='header_top_menu_icon']/i";
	final private String spotLightSideMenuOption = "//a[contains(.,' Spotlight ')]";
	final private String taskListSideMenuOption = "//a[contains(.,' Task List ')]";
	final private String sideBarMenu = "//div[@class='sidebar show']";
	final private String spotlightGridTitleXpath = "//div[text()=\" Sales Attributes \"]";
	final private String spotLightGridSalesAttributesTitle = "//div[@class='custom-header-label' and contains(text(),' Sales Attributes ')]";
	final private String filterIconXpath = "//div[@class=\"menu-column-chooser\"]";
	final private String gridFIlterOptions = "//div[@class=\"ag-column-select-list ag-focus-managed\"]//span[@class='ag-column-select-column-label']";
	final private String checkedGridFilterOptions = "//div[contains(@class,'ag-checked')]/../../span[contains(@class,'column-label')]";
	final private String clickFiltersApplyButton = "//div[contains(@class,'filter-buttons')]/button[@id=\"apply\"]";
	final private String clickFiltersClearButton = "//button[@id='clear']";
	final private String gridColumnData = "//div[@class='ag-center-cols-container']//div[@col-id='channelId']";
	final private String supportOperationsButton = "//div[contains(@class,'header-right col')]/div/button[@class='op-avatar']";
	final private String userSettingsButton = "//div[@class='user-preference']/div/ul/li/span[text()='User Settings']";
	final private String userSettingsTitle = "//div[contains(@class,'user-settings-dialog')]/div[contains(@class,'ui-dialog-titlebar')]/span[text()='User Settings']";
	final private String userSettingsApplyButton = "//p-footer/button[contains(@class,'saveButton')]";
	final private String userSettingsCancelButton = "//span[text()='Cancel']/..";
	final private String bellIcon = "//button[@class='notify']/i[@class='icon-bell']";
	final private String closeFilterIcon = "//span[contains(@class,'ag-icon ag-icon-columns')]";
	final private String checkedCheckBoxInGridFIlter = "//div[contains(@class,'ag-virtual-list-container')]//div[contains(@class,'ag-checked')]/../../span[@class='ag-column-select-column-label']";
	final private String weekOfData = "//div[@class='ag-center-cols-container']/div//div[@col-id='weekId']";
	final private String weekOfDataFirstColumn = "//div[@class='ag-center-cols-container']/div[1]/div[@col-id='weekId']";
	final private String selectColumnDropdown = "//span[@class='ui-multiselect-trigger-icon ui-clickable ng-tns-c44-6 pi pi-chevron-down']";
	final private String selectColumnSearch = "//div[@class='ui-multiselect-filter-container ng-tns-c44-6 ng-star-inserted']/input[@class='ui-inputtext ui-widget ui-state-default ui-corner-all ng-tns-c44-6']";
	final private String searchbox = "//input[@class='ui-inputtext ui-widget ui-state-default ui-corner-all ng-tns-c44-6']";
	final private String columnChooserSearchBox = "//*[@id='id_multiselect']//input[@role='textbox']";
	final private String removeAllColumnsInGrid = "//span[@class='icon-close ng-star-inserted']";
	final private String filtersDropDown = "//span[@class='ui-multiselect-trigger-icon ui-clickable ng-tns-c44-9 pi pi-chevron-down']";
	final private String savedFiltersDropDown = "//span[@class='ui-dropdown-trigger-icon ui-clickable ng-tns-c48-7 pi pi-chevron-down']";
	final private String resetColumns = "//span[contains(text(),'Reset Columns')]";
	final private String refreshGrid = "//span[text()='Refresh']";
	final private String enableAllColumnsInGrid = "//div[contains(@class,'ui-multiselect-filter-container')]/../div[contains(@class,'ui-chkbox')]/div[@role='checkbox']";
	final private String spotlighturl = "https://aos-dev.operativeone.com/spotlight/#/spotlist";
	final private String taskListurl = "https://aos-dev.operativeone.com/unison/#/planner";
	final private String columnNamesInColumnCHooser = "//p-checkbox[@class='indent-1 ng-untouched ng-pristine ng-valid ng-star-inserted']/../span";
	final private String gridColumnBar = "//div[@ref='eCenterContainer']/div[@class='ag-header-row ag-header-row-column']";
	final private String gridColumnNames = "//span[@ref='eText']";
	final private String filterDropdownCheckBoxes="//ul[@class='menu-select']/div";

	@Step("Navigates to spotlight page")
	public SpotLightPage navigatetoSpotLight() {
		waitForPageLoadUsingNetwork();
		getPageBrowser().waitForSelector(hamburgerIcon);
		getPageBrowser().waitForSelector(spotLightSideMenuOption);
		click(spotLightSideMenuOption);
		Logger.log("clicked on spotlight menu");
		return this;
	}

	public SpotLightPage navigateToTaskList() {
		getPageBrowser().navigate(taskListurl);
		return this;
	}

	public SpotLightPage navigateToSpotlight() {
		getPageBrowser().navigate(spotlighturl);
		return this;
	}

	@Step("wait for page to load using NETWORKIDLE")
	public SpotLightPage waitForPageLoadUsingNetwork() {
		getPageBrowser().waitForLoadState(LoadState.NETWORKIDLE);
		return this;
	}

	@Step("wait for operative one home page to load")
	public SpotLightPage waitForOperativeOnePageLoad() {
		getPageBrowser().waitForSelector(hamburgerIcon);
		return this;
	}

	@Step("checking filters are present")
	public boolean verifyFilterIsPresent(String filter) {
		Logger.log("checking " + filter + " is present");
		boolean isFilterPresent = isElementPresent("//input[@id='" + filter + "']");
		if (isFilterPresent) {
			Logger.log(filter + " filter " + "is Present flag value " + isFilterPresent);
		} else {
			Logger.log(filter + " filter " + "is not Present flag value " + isFilterPresent);
		}
		return isFilterPresent;
	}

	@Step("check whether grid title is present")
	public boolean verifySpotlightGridSalesAttributeTitleHasLoaded() {
		boolean isgridTitleLoaded = isElementPresent(spotLightGridSalesAttributesTitle);
		Logger.log("checking grid title is loaded");
		return isgridTitleLoaded;
	}

	@Step("check given attribute is present in spotlight grid")
	public boolean checkGivenAttributesarePresentInGrid(String[] attributes) {
		boolean allAttributesPresent = true;
		Logger.log(" checking following attributes are present in grid ");
		for (String attribute : attributes) {
			boolean flag = isElementPresent("//span[text()='" + attribute + "']");
			Logger.log(flag + " ");
			if (flag) {
				Logger.log(attribute + " attribute present in grid ");
			} else {
				Logger.log(attribute + " attribute not present in grid ");
				allAttributesPresent = false;
				break;
			}
		}
		return allAttributesPresent;
	}

	@Step("click on filter icon in grid")
	public SpotLightPage clickOnFilterIcon() {
		click(filterIconXpath);
		Logger.log("clicked on filter");
		return this;
	}

	@Step("click on filter icon in grid")
	public SpotLightPage clickOnFilterIcon(String filterName) {
		Logger.log("trying to open " + filterName + " filter");
		click("//div[@class='custom-header-label' and text()='" + filterName
				+ "']/../div[@class='menu-column-chooser']/i");
		click(filterIconXpath);
		return this;
	}

	@Step("check the checkbox")
	public SpotLightPage checkTheCheckBox(String attributeName) {
		clickOnFilterIcon();
		String selectCheckBoxWithAttributeName = "//span[text()='" + attributeName
				+ "']/../div[contains(@class,'ag-column-select-checkbox')]/div[contains(@class,'ag-checkbox-input-wrapper')]/input[contains(@class,'ag-checkbox-input')]";
		Logger.log("check box checked " + getPageBrowser().isChecked(selectCheckBoxWithAttributeName));
		if (getPageBrowser().isChecked(selectCheckBoxWithAttributeName)) {
			Logger.log(attributeName + " is already checked");
		} else {
			getPageBrowser().locator(selectCheckBoxWithAttributeName).scrollIntoViewIfNeeded();
			getPageBrowser().check(selectCheckBoxWithAttributeName);
			Logger.log(attributeName + " is checked");
		}
		return this;
	}

	@Step("uncheck the check box")
	public SpotLightPage unCheckTheCheckBox(String attributeName) {
		clickOnFilterIcon();
		String selectCheckBoxWithAttributeName = "//span[text()='" + attributeName
				+ "']/../div[contains(@class,'ag-column-select-checkbox')]//input[contains(@class,'ag-checkbox-input')]";
		Logger.log("check box checked " + getPageBrowser().isChecked(selectCheckBoxWithAttributeName));
		if (getPageBrowser().isChecked(selectCheckBoxWithAttributeName)) {
			getPageBrowser().uncheck(selectCheckBoxWithAttributeName);
			Logger.log(attributeName + " is unchecked");
		} else {
			Logger.log(attributeName + " is already unchecked");
		}
		clickOnFilterIcon();
		return this;
	}

	@Step("get the grid attributes which are checked in filter")
	public String[] getCheckedFilterDropdownOptions() {
		Locator values = getPageBrowser().locator(checkedCheckBoxInGridFIlter);
		String[] checkedGridAttributeNames = new String[values.count()];
		for (int i = 0; i < values.count(); i++) {
			Logger.log(values.nth(i).textContent() + " " + i);
			checkedGridAttributeNames[i] = values.nth(i).textContent();
		}
		Logger.log(" no of attributes checked in filter " + checkedGridAttributeNames.length);
		return checkedGridAttributeNames;
	}

	@Step("click the filter down arrow")
	public SpotLightPage clickOnFilterDownArrow(String filterName) {
		click("//label[text()='" + filterName
				+ "']/../span[contains(@class,'pi pi-chevron-down combo-icon down-arrow')]");
		Logger.log(filterName + " filter is selected ");
		return this;
	}

	@Step("check the filter check box")
	public SpotLightPage checkTheFIlterCheckBox(String[] checkboxes) {
		for (String checkbox : checkboxes) {
			getPageBrowser().check("//span[text()='" + checkbox
					+ "']/..//div[contains(@class,'ui-chkbox-box ui-widget ui-corner-all ui-state-default')]");
			Logger.log(checkbox + " CHECKBOX IS CHECKED");
		}
		return this;
	}

	@Step("click on filters apply button")
	public SpotLightPage clickFiltersApplyButton() {
		boolean isDisabled = (boolean) getPageBrowser()
				.evaluate("document.getElementById('apply').hasAttribute('disabled')");
		if (isDisabled) {
			Logger.log("no filter are applied apply button is disabled");
		} else {
			click(clickFiltersApplyButton);
		}
		return this;
	}

	@Step("click on filters clear button")
	public SpotLightPage clickFiltersClearButton() {
		getPageBrowser().locator(clickFiltersClearButton);
		boolean isDisabled = (boolean) getPageBrowser()
				.evaluate("document.getElementById('clear').hasAttribute('disabled')");
		if (isDisabled) {
			Logger.log("no filter are applied clear button is disabled");
		} else {
			click(clickFiltersClearButton);
		}
		return this;
	}

	@Step("verify grid data based on filters")
	public boolean verifyGridDataBasedOnFiltersApplied(String gridColumnId, String[] checkBoxes) {
		Logger.log(gridColumnId + " grid column id " + checkBoxes + " checkboxes");
		boolean flag = true;
		waitForPageLoadUsingNetwork();
		getPageBrowser()
				.waitForSelector("//div[@class='ag-center-cols-container']//div[@col-id='" + gridColumnId + "']");
		Locator values = getPageBrowser()
				.locator("//div[@class='ag-center-cols-container']//div[@col-id='" + gridColumnId + "']");
		Logger.log("data entries" + values.count());
		String[] gridColumnData = new String[values.count()];
		for (int i = 0; i < values.count(); i++) {
			gridColumnData[i] = values.nth(i).textContent();
		}
		for (String data : gridColumnData) {
			Logger.log("data" + data + "--");
			flag = Arrays.asList(checkBoxes).contains(data);
			if (!flag) {
				Logger.log(data + "was not checked in " + gridColumnId + "but still present in grid");
				break;
			}
		}
		return flag;
	}

	@Step("click on support operations")
	public SpotLightPage clickOnSupportOpeations() {
		Logger.log("clicked on  support operations");
		click(supportOperationsButton);
		return this;
	}

	@Step("click on user settings")
	public SpotLightPage clickOnUserSetings() {
		Logger.log("clicked on user settings");
		click(userSettingsButton);
		return this;
	}

	@Step("verify user settings is visible")
	public SpotLightPage verifyUserSettingsIsVisible() {
		Logger.log("waiting for user settings title to be visible");
		getPageBrowser().waitForSelector(userSettingsTitle);
		waitForPageLoadUsingNetwork();
		boolean isUserSettingsVisible = isElementPresent(userSettingsTitle);
		if (isUserSettingsVisible) {
			Logger.log("user settings is visible");
		} else {
			Logger.log("user settings is not visible");
		}
		return this;
	}

	@Step("select drop down from custom settings in  user settings")
	public SpotLightPage clickOnCustomSettingsDropdown(String dropDownName) {
		Logger.log("trying to open " + dropDownName + " dropdown in custom settings");
		click("//label[contains(@class,'input-label ng-star-inserted')]/span[text()='" + dropDownName
				+ "']/../../p-dropdown/div/div[@role='button']/span");
		return this;
	}

	@Step("select option from date seperator dropdown in custom settings")
	public SpotLightPage selectOptionFromDateSeperatorDropdown(String dropDownOption) {
		Logger.log("trying to click on " + dropDownOption + " from date seprator");
		click("//li[@aria-label='" + dropDownOption + "']/..");
		Logger.log("clicked on " + dropDownOption + " from date seprator");
		return this;
	}

	@Step("click on user settings Apply Button")
	public SpotLightPage clickOnUseerSettingsApplyButton() {
		click(userSettingsApplyButton);
		Logger.log("clicked on usersettings apply button");
		return this;
	}

	@Step("click on user settings Cancel Button")
	public SpotLightPage clickOnUseerSettingsCancelButton() {
		click(userSettingsCancelButton);
		Logger.log("clicked on usersettings cancer button");
		return this;
	}

	@Step("refresh the web page")
	public SpotLightPage refreshTheCurrentWebPage() {
		Logger.log("refreshing the page");
		getPageBrowser().reload();
		return this;
	}

	@Step("check whether Date Seprator from user settings is applied to Week of column in grid")
	public boolean checkDateSeperatorIsAppliedToWeekOfColumnInGrid(String seperator) {
		boolean isApplied = true;
		getPageBrowser().waitForSelector(weekOfData);
		Locator values = getPageBrowser().locator(weekOfData);
		String[] gridColumnData = new String[values.count()];
		for (int i = 0; i < values.count(); i++) {
			gridColumnData[i] = values.nth(i).textContent();
		}
		for (String data : gridColumnData) {
			Logger.log("data" + data + "--");
			isApplied = data.contains(seperator);
			if (!isApplied) {
				Logger.log(data + "data seperator is not applied here");
				isApplied = false;
				break;
			}
		}
		return isApplied;
	}

	public String getDateSeperatorFromWeekColumn() {
		String data = getText(weekOfDataFirstColumn);
		Logger.log("week of " + data);
		String seperator = "";
		char[] JavaCharArray = { '-', '.', '/' };
		for (char c : JavaCharArray) {
			if (data.contains(String.valueOf(c))) {
				seperator = String.valueOf(c);
				Logger.log(" seperator " + seperator);
			}
		}
		return seperator;
	}

	@Step("verify bell icon for notification is present in top right corner")
	public boolean verifyBellIconPresent() {
		boolean isBellIconPresent = isElementPresent(bellIcon);
		if (isBellIconPresent) {
			Logger.log("bell icon is visible");
		} else {
			Logger.log("bell icon is not visible");
		}
		return isBellIconPresent;
	}

	@Step("select the select all attribues checkbox from grid filter")
	public void selectAllAttributesFromGridFilter(String filterName) {
		Logger.log("selecting all attributes of " + filterName);
		if (getPageBrowser().isChecked("//span[text()='" + filterName
				+ "']/../div[contains(@class,'ag-column-select-checkbox')]/div[contains(@class,'ag-checkbox-input-wrapper')]/input")) {
			Logger.log(filterName + " attributes is already checked");
			click(closeFilterIcon);
		} else {
			click("//span[text()='" + filterName
					+ "']/../div[contains(@class,'ag-column-select-checkbox')]/div[contains(@class,'ag-checkbox-input-wrapper')]/input");
		}
	}

	@Step("click on Select Column Dropdown")
	public SpotLightPage clickOnSelectColumnDropdown() {
		click(selectColumnDropdown);
		Logger.log("clicked on Select Column dropdown");
		return this;
	}

	@Step("click on filter")
	public SpotLightPage selectFilter(String filterName) {
		click("//label[@class='input-label ng-star-inserted']/span[text()='" + filterName + "']/../../div/span");
		return this;
	}

	@Step("get all filter Options")
	public String[] selectRandomFilterOption() {
		Locator values = getPageBrowser().locator(filterDropdownCheckBoxes);
		String[] options = new String[values.count()];
		for (int i = 0; i < values.count(); i++) {
			Logger.log(values.nth(i).textContent() + " " + i);
			options[i] = values.nth(i).textContent();
		}
		Logger.log(" no of options " + options.length);
		int randomNumber = new Random().nextInt(options.length);
		String checkbox = options[randomNumber];
		getPageBrowser().check("//span[text()='" + checkbox + "']/../p-checkbox/div/div[@role='checkbox']");
		return options;
	}

	@Step("check Select Column checkbox")
	public SpotLightPage checkSelectColumnCheckBox(String checkBoxName) {
		searchFieldName(checkBoxName);
		Logger.log("selecting " + checkBoxName + " checkbox from select Column dropdown");
		if (getPageBrowser().isChecked("//span[text()='" + checkBoxName + "']/..//div[@role='checkbox']")) {
			Logger.log(checkBoxName + " checkbox is already checked");
		} else {
			getPageBrowser().check("//span[text()='" + checkBoxName + "']/..//div[@role='checkbox']");
			Logger.log(checkBoxName + " checkbox has been checked");
			clearSelectColumnChooser();
		}
		return this;
	}

	@Step("type name in column chooser")
	public SpotLightPage searchFieldName(String fieldName) {
		type(columnChooserSearchBox, fieldName);
		return this;
	}

	@Step("clear name in column chooser")
	public SpotLightPage clearSelectColumnChooser() {
		getPageBrowser().locator(columnChooserSearchBox).clear();
		return this;
	}

	@Step("uncheck Select Column checkbox")
	public SpotLightPage unCheckSelectColumnCheckBox(String checkBoxName) {
		searchFieldName(checkBoxName);
		Logger.log("unchecking " + checkBoxName + " checkbox from select Column dropdown");
		if (getPageBrowser().isChecked("//span[text()='" + checkBoxName + "']/..//div[@role='checkbox']")) {
			getPageBrowser().uncheck("//span[text()='" + checkBoxName + "']/..//div[@role='checkbox']");
			Logger.log(checkBoxName + "checkbox has been unchecked");
		} else {

			Logger.log(checkBoxName + "checkbox is already unchecked");
		}
		clearSelectColumnChooser();
		return this;
	}

	public SpotLightPage makeGridEmpty() {
		//
		click(removeAllColumnsInGrid);
		waitForPageLoadUsingNetwork();

		return this;
	}

	public SpotLightPage resetGrid() {
		click(resetColumns);
		waitForPageLoadUsingNetwork();

		return this;
	}

	public SpotLightPage typeInFilterSearchFields(String filterName, String data) {
		click("//label[text()='" + filterName + "']/../input");

		type("//label[text()='" + filterName + "']/../input", data);
		return this;
	}

	public SpotLightPage clearFilterSearchField(String fieldName) {
		click("//label[text()='" + fieldName + "']/../span");
		return this;
	}

	public SpotLightPage clickOnFiltersDropdown() {
		click(filtersDropDown);

		return this;
	}

	@Step("get column data from grid")
	public String[] getGridColumnData(String columnName) {

		getPageBrowser().waitForSelector(weekOfData);
		Locator values = getPageBrowser().locator("//div[@col-id='" + columnName + "']");
		String[] gridColumnData = new String[values.count()];
		for (int i = 0; i < values.count(); i++) {
			gridColumnData[i] = values.nth(i).textContent();
		}
		return gridColumnData;
	}

	@Step("return grid column names present")
	public String[] getGridColumns() {
		getPageBrowser().waitForSelector(gridColumnBar);
		Locator values = getPageBrowser().locator(gridColumnNames);
		String[] columnsPresentInGrid = new String[values.count()];
		for (int i = 0; i < values.count(); i++) {
			Logger.log(values.nth(i).textContent() + ",");
			columnsPresentInGrid[i] = values.nth(i).textContent();
		}
		Logger.log(" no of attributes checked in filter " + columnsPresentInGrid.length);
		return columnsPresentInGrid;
	}

	@Step("enable filters ")
	public SpotLightPage enableFilters(String[] filterNames) {
		for (String filterName : filterNames) {
			if (isElementPresent("//span[text()='" + filterName
					+ "']/..//div[contains(@class,'ui-chkbox-box') and contains(@class,'ui-state-active')]")) {
				Logger.log(filterName + " checkbox is already checked");
			} else {
				getPageBrowser().click("//span[text()='" + filterName + "']/..//div[contains(@class,'ui-chkbox-box')]");
				Logger.log(filterName + " checkbox has been checked");
			}
		}
		return this;
	}

	@Step("disable filters ")
	public SpotLightPage disableFilters(String[] filterNames) {
		for (String filterName : filterNames) {
			if (isElementPresent("//span[text()='" + filterName
					+ "']/..//div[contains(@class,'ui-chkbox-box') and contains(@class,'ui-state-active')]")) {
				getPageBrowser().click("//span[text()='" + filterName + "']/..//div[contains(@class,'ui-chkbox-box')]");
				Logger.log(filterName + " checkbox has been unchecked");
			} else {

				Logger.log(filterName + " checkbox has alreadybeen checked");
			}
		}
		return this;
	}

	public SpotLightPage refreshTheGrid() {
		click(refreshGrid);
		return this;
	}

	public boolean checkTextFiltersArePresent(String[] filterNames) {
		boolean flag = true;
		for (String filterName : filterNames) {
			if (!(isElementPresent("//label[text()=' " + filterName + " ']"))) {
				flag = false;
				Logger.log("text filter " + filterName + " is not enabled");
			} else {
				Logger.log("text filter is " + filterName + " is present");
			}
		}
		return flag;
	}

	public boolean checkDropdownFiltersArePresent(String[] filterNames) {
		boolean flag = true;
		for (String filterName : filterNames) {
			if (!(isElementPresent("//label/span[text()='" + filterName + "']"))) {
				flag = false;
				Logger.log("dropdown filter is " + filterName + " is not enabled");
				break;
			} else {
				Logger.log("dropdown filter is " + filterName + " is present");
			}
		}
		return flag;
	}

	public SpotLightPage enableAllColumnsInGrid() {

		if (getPageBrowser().isChecked(enableAllColumnsInGrid)) {
			Logger.log("enable all columns has already been checked");
		} else {
			getPageBrowser().check(enableAllColumnsInGrid);
		}
		return this;
	}

	public SpotLightPage disableAllColumnsInGrid() {
		if (getPageBrowser().isChecked(enableAllColumnsInGrid)) {
			getPageBrowser().uncheck(enableAllColumnsInGrid);
		} else {
			Logger.log("enable all columns checkbox has been unchecked already");
		}
		return this;

	}
	
	public String [] returnColumnsInColumnChooser() {
		Locator values = getPageBrowser().locator(columnNamesInColumnCHooser);
		String[] gridColumnData = new String[values.count()];
		for (int i = 0; i < values.count(); i++) {
			gridColumnData[i] = values.nth(i).textContent();
			Logger.log(values.nth(i).textContent());
		}
		return gridColumnData;
	}
}
