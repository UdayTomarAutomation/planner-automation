package com.operative.pages.o1sales;

import java.util.List;

import javax.swing.Popup;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;
import com.operative.pages.plannerheader.PlannerHeaderPage;

import io.qameta.allure.Step;

public class SalesOrderAdOpsPage extends BasePage {
	SoftAssert getSoftAssert;

	/**
	 * @author akrathi
	 */
	public SalesOrderAdOpsPage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert = new SoftAssert();
		// TODO Auto-generated constructor stub
	}

	final private String btnNew = "//button[text()='New']";
	final private String dealNametext = "//input[contains(@class,'auto_sales_new_name')]";
	final private String dropDownValueO1SalesHeader = "//div[contains(@class,'x-combo-list') and contains(@style,'visibility: visible;')]//div[contains(@class,'x-combo-list-item')]";
	final private String btnsaveAndContinueEditing = "//button[text()='Save and Continue Editing']";
	final private String isDealPresent = "//label[contains(@class,'item-ticket') and text()='%s']";
	final private String dropDownSalesOrder = "//input[@name='salesOrderType' and contains(@class, 'auto_sales_new_ordertype')]//../img";
	final private String dropDownAdvertiser = "//input[@name='advertiser' and contains(@class,'auto_sales_new_advertiser')]//../img";
	final private String dropDownAgency = "//input[@name='advertiser' and contains(@class,'auto_sales_new_agency')]//../img";
	final private String orderId = "//div[contains(@class,'auto_sales_view_salesorderid')]//div[@class='op-form-value']";
	final private String connectHyperLink = "//button[text()='Connect']";
	final private String getAdvertiserDetails ="//div[contains(@class,'auto_sales_view_advertiser')]/div[@class='op-form-value']/div";
	final private String getAgencyDetails ="//div[contains(@class,'auto_sales_view_buyingagency')]/div[@class='op-form-value']/div";
 
	/**
	 * create so with maximum number for parameter
	 */
	public void createSalesOrderAndGetSalesOrderId(String orderName, String orderAdvertizer, String primarySalesPerson,
			String orderAgency, String salesStage, String orderOwner, String orderBelongs, String currency,
			String rateCard, String orderType, String salesContract) {
		waitForSelectorVisible(btnNew, 60000);
		click(btnNew);
		if (!orderOwner.isEmpty()) {
			clickComboItem("owner", orderOwner);
		}
		clickComboItem("primarySalesperson", primarySalesPerson);
		clickComboItem("organizationTeam", orderBelongs);
		type(dealNametext, orderName);
		clickComboItem("advertiser", orderAdvertizer);
		if (!salesStage.isEmpty()) {
			clickComboItem("salesStage", salesStage);
		}

		if (!currency.isEmpty()) {
			clickComboItem("currency", currency);
		}

		if (!orderAgency.isEmpty()) {
			clickComboItem("agency", orderAgency);
		}

		if (!rateCard.isEmpty()) {
			clickComboItem("ratecard", rateCard);
		}
		if (!orderType.isEmpty()) {
			clickComboItem("salesOrderType", orderType);
		}
		if (!salesContract.isEmpty()) {
			clickComboItem("salesContract", salesContract);
		}

		clickwithWait(btnsaveAndContinueEditing, 1000);

	}

	@Step("drowp down Value Select")
	public void clickComboItem(String Name, String target) {
		try {
			if (Name.equals("primarySalesperson") || Name.equals("owner") || Name.equals("ratecard")
					|| Name.equals("currency") || Name.equals("salesStage") || Name.equals("salesContract")
					|| Name.equals("organizationTeam")) {
				clickwithWait("//input[@name='" + Name + "']//../img", 1000);
			} else if (Name.equals("advertiser")) {
				clickwithWait(dropDownAdvertiser, 1000);
			} else if (Name.equals("salesOrderType")) {

				clickwithWait(dropDownSalesOrder, 1000);

			} else {
				clickwithWait(dropDownAgency, 1000);
			}

			waitForSelectorVisible(dropDownValueO1SalesHeader, 2000);
			final Locator comboItems = getpageBrowser.locator(dropDownValueO1SalesHeader);
			List<String> value = comboItems.allTextContents();
			System.out.println(value);

			for (String var : value) {
				if (var.equalsIgnoreCase(target)) {
					Logger.log(var + "---Matched---" + target);
					click("//div[contains(@class,'x-combo-list-item') and text() ='" + var + "' ]");
					break;
				} else {
				}
			}

		} catch (final Exception e) {
			Logger.log("failed due to +" + e + " ");
		}

	}

	@Step("O1 sales order header created")
	public boolean isDealDisplayed(String Name) {
		try {
			String salesOrderPresent = isDealPresent.replace("%s", Name);
			Logger.log("Deal name is" + salesOrderPresent);
			waitForSelectorVisible(salesOrderPresent, 25000);
			if (getpageBrowser.isVisible(salesOrderPresent)) {
				return true;
			} else {
				return false;
			}

		} catch (final Exception e) {
			Logger.log("failed due to +" + e + " ");
			return false;
		}

	}

	@Step("Get sales order ID")
	public String getOrderId() {
		try {
			Logger.log("get order id after open the order");
			final String id = getText(orderId);
			Logger.log("so id:---------" + id);
			return id;
		} catch (final Exception e) {
			return "Unable to fetch order ID";

		}
	}
	
	
	@Step("Get Advertiser Details of O1 ")
	public String getAdvDetailsOfProposal() {
		try {
		
		final String AdvDetails = getText(getAdvertiserDetails);
		String getAdvdetails = AdvDetails.replaceAll("[\\s\\u00A0]+$","");
		//String getAdvdetails = AdvDetails.replaceAll("[^\\x00-\\x7F]","");
		//String getAdvdetails = AdvDetails.replaceAll(new String("Â".getBytes("UTF-8"), "UTF-8"), "");
		Logger.log("Get Advertiser Details is" + getAdvdetails);
		return getAdvdetails.trim();
	}catch (final Exception e) {
		
		return "not able to get advertiser details";
	}}
	
	@Step("Get Agency Details of O1 ")
	public String getAgcDetailsOfProposal() {
		try {
		
		final String AdvDetails = getText(getAgencyDetails);
		String getAdvdetails = AdvDetails.replaceAll("[\\s\\u00A0]+$", "");
		//String getAdvdetails = AdvDetails.replaceAll("[^\\x00-\\x7F]","");
		Logger.log("Get Agency Details is" + getAdvdetails);
		return getAdvdetails.trim();
	}catch (final Exception e) {
		
		return "not able to get agency details";
	}}

	@Step("Navigate to connect")
	public Page navigateToConnect() {

		Page connectPage = getPageBrowser().waitForPopup(() -> {
			getPageBrowser().click(connectHyperLink);
		});
		Logger.log("Navigate to connect");
		return connectPage;
	}

}