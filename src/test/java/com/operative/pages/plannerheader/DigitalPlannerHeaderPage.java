/**
 * 
 */
package com.operative.pages.plannerheader;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;

import io.qameta.allure.Step;

/**
 * @author ajay_bhave
 *
 */
public class DigitalPlannerHeaderPage extends BasePage {
	SoftAssert getSoftAssert;
	
	/**
	 * @param page
	 */
	public DigitalPlannerHeaderPage(Page page) {
		super(page);
		this.getSoftAssert = new SoftAssert();
		// TODO Auto-generated constructor stub
	}

	final private String newDeal = "//span[text()='New Deal']";
	final private String digitalButton = "//span[@id='digital']";
	final private String linearButton = "//span[@id='linear']";
	final private String continueButton = "//span[text()='Continue']";
	final private String dealNameField = "//label[text()='Deal Name ']/preceding-sibling::input[contains(@class,'ui-inputtext')]";
	final private String orderTypeDropdown = "//label[contains(text(),'Order Type')]/following-sibling::select";
	final private String saveButton = "//span[text()='Save']";
	final private String versionpopup = "//span[@class='dots-button dot-icons']/span";
	final private String createVersionButton = "//span[contains(text(),'Create Version')]//parent::a";
	final private String newVersionINameField = "//input[contains(@id,'versionInput')]";
	final private String createNewVersionButton = "//span[text()='Create Version']/../..//span[contains(text(),'Save')]//parent::button";

	public DigitalPlannerHeaderPage createOnlyDigitalPlanner(String planName, String orderType, String flightDates,
			String advertiser, String buyingAgency, String primaryAccountExecutive, String calendar, String startperiod,
			String endperiod, String hiatusDate) {

		click(newDeal);
		type(dealNameField, planName);
		click(digitalButton);
		click(linearButton);
		getPageBrowser().locator(orderTypeDropdown).first().selectOption(orderType);
		click(continueButton);
		dropdownValue("advertisers", advertiser);
		dropdownValue("agencies", buyingAgency);
		dropdownValue("accountExecutives", primaryAccountExecutive);
		dropdownValue("calendar", calendar);
		dropdownValue("startPeriod", startperiod);
		dropdownValue("endPeriod", endperiod);
		dropdownValue("FlightDates", flightDates);
		return this;
	}

	@Step("drowp down Value Select")
	public DigitalPlannerHeaderPage dropdownValue(String fieldName, String value) {
		if (fieldName.equals("advertisers") || fieldName.equals("agencies") || fieldName.equals("accountExecutives")) {
			click("//div[contains(@id,'" + fieldName + "')]//span[contains(@class,'chevron-down')]");
			type("//div[contains(@id,'float-paginateddd-" + fieldName + "')]//ancestor::app-root//"
					+ "following-sibling::div[contains(@class,'overlayAnimation')]//input[contains(@class,'filter')]",
					value);
			click("//div[contains(@class,'overlayAnimation')]//ul//li//span[text()='" + value + "']");
			Logger.log("Selected " + fieldName + "===>>" + value);

		} else if (fieldName.equals("calendar") || fieldName.equals("startPeriod") || fieldName.equals("endPeriod")) {
			if (fieldName.equals("startPeriod")) {
				getpageBrowser.waitForSelector(
						"//p-dropdown[contains(@id,'startPeriod')]/div[contains(@class,'disabled')]",
						new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
				click("//p-dropdown[contains(@id,'" + fieldName + "')]//span[contains(@class,'pi-chevron-down')]");
				type("//p-dropdown[contains(@id,'calendar')]//ancestor::app-root//following-sibling::div[contains(@class,'overlayAnimation')]//input",
						value);
				click("//span[contains(text(),'" + value + "')]");
				Logger.log("Selected " + fieldName + "===>>" + value);
			}

			click("//p-dropdown[contains(@id,'" + fieldName + "')]//span[contains(@class,'pi-chevron-down')]");
			type("//p-dropdown[contains(@id,'calendar')]//ancestor::app-root//following-sibling::div[contains(@class,'overlayAnimation')]//input",
					value);
			click("//span[contains(text(),'" + value + "')]");
			Logger.log("Selected " + fieldName + "===>>" + value);

		} else if (fieldName.equals("FlightDates")) {
			if (!value.isEmpty()) {
				if (isElementPresent("//div[@class='broadcast-calendar-icon']//span[contains(@class,'icon-close')]")) {
					click("//div[@class='broadcast-calendar-icon']//span[contains(@class,'icon-close')]");
				}
				click("op-cc-broadcast-calendar[fieldname='" + fieldName + "'] input");
				type("op-cc-broadcast-calendar[fieldname='" + fieldName + "'] input", value);
				Logger.log("Selected " + fieldName + "===>>" + value);
			} else {
				Logger.log("Default Date as per start and end period ");
			}
		} else {
			click("//span[contains(text(),'" + fieldName + "')]//parent::label");
			click("//span[text()='" + value + "']");
			click(dealNameField);
			Logger.log("Selected " + fieldName + "===>>" + value);
		}

		return this;

	}

	@Step("Save Plan Header Page")
	public DigitalPlannerHeaderPage clickSaveHeader() {
		click(saveButton);

		Logger.log("save Digital Plan Header Page");
		return this;
	}

	@Step("Create New Version For the Deal")
	public void createNewVersion(String versionName) {
		click(versionpopup);
		click(createVersionButton);
		type(newVersionINameField, versionName);
		click(createNewVersionButton);
		Logger.log("New version created " + versionName);
	}

}
