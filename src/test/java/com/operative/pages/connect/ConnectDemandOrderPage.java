package com.operative.pages.connect;

import java.util.List;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;
import com.operative.pages.o1sales.SalesOrderAdOpsPage;

import io.qameta.allure.Step;

public class ConnectDemandOrderPage extends BasePage {
	SoftAssert getSoftAssert;
	private Page connectPage;

	/**
	 * @author akrathi
	 */
	public ConnectDemandOrderPage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert = new SoftAssert();
		// TODO Auto-generated constructor stub
	}

	private final String demandTab = "//span[text()='Demand Orders']";
	private final String btnCreateProposal = "//span[text()='Create Proposal']";
	private final String btnSubmit = "//span[text()='Submit']";
	private final String orderIdtxtField = "//div[contains(@class,'auto_connect_syncdemandorder_orderid')]//input[@name='orderId']";
	private final String waitForpaginationload = "//div[contains(@id,'demandOrdersList')]//div[(not(contains(@style,'display: none;'))) and contains(@data-componentid,'loadmask')]";
	private final String waitforFetchingDetailsLoad = "//div[text()='Fetching order details..']";
	private final String waitForProposalIdDisplay = "//div[contains(@class,'auto_connect_proposalsummary_proposalid')]/div/div";
	private final String connectStatus = "//div[contains(@class,'x-form-display-field') and text()= 'Order Submitted']";
	private final String sliCount = "//td[contains(@class,'auto_connect_demandsligrid_sliid')]/..";
    private final String totalNetCostProposal = "//div[contains(@id,'OrderDetailsPanel')]//div[contains(@class,'auto_connect_proposalsummary_netcost')]/div[contains(@class,'x-form-item-body')]/div";
	
	@Step("Navigate to Demand Order Tab")
	public void navigateToDemandTab(Page connectNavPage) {
		try {
			connectNavPage.waitForSelector(demandTab, new Page.WaitForSelectorOptions()
					.setState(WaitForSelectorState.VISIBLE).setStrict(true).setTimeout(25000));
			connectNavPage.click(demandTab);
			Logger.log("Navigate to Demand Order");
		} catch (final Exception e) {
			Logger.log("failed to navigate to Demand Order");
		}
	}

	@Step("Create a Proposal in Demand Connect")
	public boolean createProposal(String orderId, Page connectNavPage) {
		try {

			connectNavPage.waitForSelector(waitForpaginationload, new Page.WaitForSelectorOptions()
					.setState(WaitForSelectorState.HIDDEN).setStrict(true).setTimeout(45000));
			connectNavPage.click(orderIdtxtField);
			connectNavPage.type(orderIdtxtField, orderId);
			connectNavPage.click(btnCreateProposal);
			Logger.log("Proposal Created successfully");
			connectNavPage.waitForSelector(waitforFetchingDetailsLoad, new Page.WaitForSelectorOptions()
					.setState(WaitForSelectorState.HIDDEN).setStrict(true).setTimeout(45000));
			connectNavPage.waitForSelector(waitForProposalIdDisplay, new Page.WaitForSelectorOptions()
					.setState(WaitForSelectorState.VISIBLE).setStrict(true).setTimeout(7000));
			return true;
		} catch (final Exception e) {
			Logger.log("failed to create Proposal");
			return false;
		}
	}

	@Step("click on submit button")
	public void clickSubmit(Page connectNavPage) {
		try {
//			connectNavPage.waitForSelector(btnSubmit, new Page.WaitForSelectorOptions()
//					.setState(WaitForSelectorState.VISIBLE).setStrict(true).setTimeout(3000));
			connectNavPage.hover(btnSubmit);
			connectNavPage.click(btnSubmit);
			Logger.log("clicked on submit ");
		} catch (final Exception e) {
			Logger.log("failed to click on submit");
		}
	}

	@Step("check the proposal status")
	public boolean getConnectStatus(Page connectNavPage) {
		try {

			connectNavPage.waitForSelector(connectStatus, new Page.WaitForSelectorOptions()
					.setState(WaitForSelectorState.VISIBLE).setStrict(true).setTimeout(45000));
			if (isElementPresent(connectStatus))
				return true;
			else
				return false;
		} catch (final Exception e) {
			Logger.log("failed to check the Proposal status");
			return false;
		}
	}

	@Step("Get cost method")
	public String getCostMethod(String productName) {
		try {

			final String costMethod = getText("//div[text()='" + productName
					+ "']/parent::td/following-sibling::td[contains(@class,'costmethod')]/div");
			Logger.log("Cost Method is" + costMethod);
			return costMethod;
		} catch (final Exception e) {

			return "not able to fetch cost method";
		}
	}

	@Step("Get Net Unit cost")
	public String getNetUnitCost(String productName) {
		try {
			final String netUnitCost = getText("//div[text()='" + productName
					+ "']/parent::td/following-sibling::td[contains(@class,'netunitcost')]/div");
			Logger.log("Nt unit cost is" + netUnitCost);
			return netUnitCost;
		} catch (final Exception e) {

			return "not able to fetch Net unit cost ";
		}
	}
	
	
	@Step("Get Net cost")
	public String getNetCost(String productName) {
		try {
			final String netCost = getText("//div[text()='" + productName
					+ "']/parent::td/following-sibling::td[contains(@class,'netcost')]/div");
			Logger.log("Nt unit cost is" + netCost);
			return netCost;
		} catch (final Exception e) {

			return "not able to fetch Net unit cost ";
		}
	}


	@Step("Get Quantity")
	public String getQuantity(String productName) {
		try {
			final String quantity = getText("//div[text()='" + productName
					+ "']/parent::td/following-sibling::td[contains(@class,'_quantity')]/div");
			Logger.log("Quantity is" + quantity);
			return quantity;
		} catch (final Exception e) {

			return "not able to fetch Quantity ";
		}
	}

	@Step("Get Start Date")
	public String getStartDate(String productName) {
		try {
			final String startDate = getText("//div[text()='" + productName
					+ "']/parent::td/following-sibling::td[contains(@class,'startdate')]/div");
			Logger.log("Start Date is" + startDate);
			return startDate;
		} catch (final Exception e) {

			return "not able to fetch Get start date ";
		}
	}

	@Step("Get End Date")
	public String getEndDate(String productName) {
		try {
			final String endDate = getText("//div[text()='" + productName
					+ "']/parent::td/following-sibling::td[contains(@class,'enddate')]/div");
			Logger.log("End Date is" + endDate);
			return endDate;
		} catch (final Exception e) {

			return "not able to fetch Get End date  ";
		}
	}

	@Step("Get Line ID ")
	public String getLineId(String productName) {
		try {
			final String lineID = getText("//div[text()='" + productName
					+ "']/parent::td/../../tr/td[contains(@class,'sliid')and contains(@class,'gridcolumn')]/div");
			Logger.log("Line ID is" + lineID);
			return lineID;
		} catch (final Exception e) {

			return "not able to fetch Line ID  ";
		}
	}
	
	@Step("Get Total Net Cost of Proposal ")
	public String getTotalNetCostOfProposal() {
		try {
			final String totalNetCost = getpageBrowser.locator(totalNetCostProposal).first().textContent();
			Logger.log("Total Net Cost of Proposal" + totalNetCost.trim());
			return totalNetCost.trim();
		} catch (final Exception e) {

			return "not able to fetch total net cost of  proposal  ";
		}
	}
	
	
	@Step("Get Account details of demand connect ")
	public String getAccountDetails(String accName) {
		try {
			String account = "auto_connect_orderdetail_%s";
			String accountName = account.replace("%s",accName);
			String accountDetails = getText("//div[contains(@id,'OrderDetailsPanel')]//div[contains(@class,'"+accountName+"')]//div[@role='textbox']");
			Logger.log("fetch account details of demand connect =====> "+accName+" " + accountDetails.trim());
			return accountDetails.trim();
		} catch (final Exception e) {

			return "not able to fetch account details of demand connect =====> "+accName+" ";
		}
	}
	
	@Step("Get Total Net Cost of Proposal ")
	public boolean isCheckboxChecked(String lineClass,String prodName) {
		  boolean isChecked= false;
		try {
			final String lineClassCheckbox = "//div[text()='"+prodName+"']/parent::td/following-sibling::td[contains(@class,'"+lineClass+"')]/div/input[@type='checkbox']";
			isChecked =getpageBrowser.isChecked(lineClassCheckbox);
			Logger.log("checkbox of"+prodName+"Whose"+lineClass+"isChecked>>>"+isChecked );
			return isChecked;
		} catch (final Exception e) {

			return isChecked;
		}
	}

	@Step("Get Number of lines ")
	public int getNumberofLine() {
		try {
			final Locator comboItems = getpageBrowser.locator(sliCount);
			List<String> value = comboItems.allTextContents();
			Logger.log("Number of line is" + value.size());
			return value.size();
		} catch (final Exception e) {

			return 0;
		}
	}
	
	@Step("Get Targeting Summary")
	public String getTargetingSummary(String productName) {
		try {
			final String targets = getText("//div[text()='" + productName
					+ "']/parent::td/following-sibling::td[contains(@class,'targetingsummary')]/div");
			Logger.log("Targeting Summary is -> " + targets);

			return targets;
		} catch (final Exception e) {

			return "not able to fetch Targeting Summary  ";
		}
	}

}