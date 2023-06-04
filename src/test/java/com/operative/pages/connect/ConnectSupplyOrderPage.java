package com.operative.pages.connect;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;

import io.qameta.allure.Step;

public class ConnectSupplyOrderPage extends BasePage {
	SoftAssert getSoftAssert;

	/**
	 * @param page
	 */
	public ConnectSupplyOrderPage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert = new SoftAssert();
		// TODO Auto-generated constructor stub
	}

	private final String supplyTab = "//span[text()='Supply Orders']";
	private final String waitForpaginationload = "//div[contains(@id,'supplyOrdersList')]//div[(@role='progressbar') and not(contains(@style,'display: none;'))]//div[text() ='Loading...']";
	private final String partnerSalesOrderIdtxtField = "//div[contains(@id,'supplyOrdersPanel')]//input[@name='partnerSOId']";
	private final String btnApplyFilter = "//span[@id='button-1096-btnInnerEl']";
	private final String waitForPageLoad = "//div[(@id='loadmask-1664') and  not(contains(@style,'display: none;'))]";
	private final String btnSave = "//div[contains(@id,'supplyOrderDetailsPanel')]//span[text()='Save']";
	private final String btnSaveSync = "//span[text()='Save & Sync']";
	private final String sliCount = "//td[contains(@class,'auto_connect_supplysligrid_sliid')]/..";
	private final String connectStatus = "//div[contains(@class,'x-form-display-field') and text()= 'Order Submitted']";
	private final String btnRefresh = "//div[contains(@id,'supplyOrderDetailsPanel')]//a[contains(@class,'auto_connect_supplyorderdetail_refreshbtn')]";
	private final String saveSyncsuccessfulMsg = "//div[contains(@id,'messagebox-1001-msg') and contains(text(),'Order sync initiated')]";
	private final String btnOKafterSaveSync = "//span[contains(@id,'button-1005-btnWrap')]";
	private final String OrderProcessingStatus = "//div[contains(@id,'supplyOrderDetailsPanel')]//div[contains(@class,'x-form-display-field') and text()= '%s']";
    private final String orderProcessingStatusUnique = "//div[contains(@class,'orderProcessingStatus')]//div[contains(@class,'x-form-display-field')]/div";
    private final String getPartnerSalesOrderID = "//div[contains(@class,'partnersalesorderid')]//div[contains(@class,'x-form-display-field')]/div";
    private final String getAOSDealID = "//div[contains(@id,'supplyOrderDetailsPanel')]//div[contains(@class,'orderdetail_orderid')]/div/div";
	
	@Step("Navigate to Supply Order Tab")
	public void navigateToSupplyTab(Page connectNavPage) {
		try {
			// connectNavPage.waitForSelector(waitForpaginationload, new
			// Page.WaitForSelectorOptions()
			// .setState(WaitForSelectorState.HIDDEN).setStrict(true).setTimeout(15000));
			connectNavPage.waitForSelector(supplyTab, new Page.WaitForSelectorOptions()
					.setState(WaitForSelectorState.VISIBLE).setStrict(true).setTimeout(25000));
			connectNavPage.click(supplyTab);
			Logger.log("Navigate to Supply Order");
		} catch (final Exception e) {
			Logger.log("failed to navigate to Supply Order");
		}
	}

	@Step("Search a Proposal in Supply Connect which has been pushed from Demand connect")
	public void searchProposal(String orderId) {

		try {
			waitForSelectorHidden(waitForpaginationload, 15000);
			click(partnerSalesOrderIdtxtField);
			type(partnerSalesOrderIdtxtField, orderId.trim());
			click(btnApplyFilter);
			waitForSelectorHidden(waitForPageLoad, 10000);
			Logger.log("Proposal Searched successfully in supply connect");
			

		} catch (final Exception e) {
			Logger.log("failed to serched the Proposal in supply connect");

		}
	}

	@Step("Open a Proposal in Supply Connect which has been pushed from Demand connect")
	public boolean openProposal(String planName) {
		boolean isDisplayed = false;
		try {

			final String openOrder = "//table[contains(@id,'tableview')]/tbody/tr/td/div[text()='%s']";
			final String openPushedOrder = openOrder.replace("%s", planName);
			click(openPushedOrder);
			Logger.log("Proposal opened successfully in supply connect");
			return isDisplayed;
		} catch (final Exception e) {
			Logger.log("failed to Open the Proposal in supply connect");
			return isDisplayed;
		}
	}

	@Step("Get Line ID ")
	public boolean getLineId(String productName, String lineId) {
		boolean isDisplayed = false;
		try {
			String partnerLineItemId = "//div[text()='" + productName
					+ "']/parent::td/../../tr/td[contains(@class,'sliid')and contains(@class,'gridcolumn')]/div[text()='"
					+ lineId + "']";
			isDisplayed = isElementPresent(partnerLineItemId);
			Logger.log("Line is displayed" + lineId);
			return isDisplayed;
		} catch (final Exception e) {

			return isDisplayed;
		}
	}

	@Step("Click on save button")
	public void clickSaveBtn() {

		try {
			click(btnSave);
			mousehover(btnSaveSync);
			click(btnSaveSync);
			int i=0;
			while (!isElementPresent(saveSyncsuccessfulMsg )&& i<=3) {
				mousehover(btnOKafterSaveSync);
				click(btnOKafterSaveSync);
				click(btnSave);
				mousehover(btnSaveSync);
				click(btnSaveSync);
				i++;
				Logger.log("Save and sync is successful After second try");
			}  
				mousehover(btnOKafterSaveSync);
				click(btnOKafterSaveSync);
				Logger.log("Save and sync is successful");
			
		} catch (final Exception e) {

			Logger.log("Save and sync is not successful");
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

	@Step("check the proposal status")
	public boolean getConnectStatus() {
		boolean isDisplayed = false;
		try {
			isDisplayed = getpageBrowser.locator(connectStatus).isVisible();
			return isDisplayed;
		} catch (final Exception e) {
			Logger.log("failed to check the Proposal status");
			return isDisplayed;
		}
	}
	
	@Step("Get Line Push Status ")
	public String getPushStatus(String productName) {
		try {
			final String pushStatus = getText("//div[text()='" + productName
					+ "']/parent::td/../../tr/td[contains(@class,'pushstatus')]/div");
			Logger.log("Push Status is" + pushStatus);
			return pushStatus;
		} catch (final Exception e) {

			return "not able to fetch Push Status";
		}
	}
	
	@Step("check the order processing status")
	public boolean getorderProcessionStatus(String status) {
		boolean isDisplayed = false;
		try {
			String opStatus = OrderProcessingStatus.replace("%s", status);
			isDisplayed = getpageBrowser.locator(opStatus).isVisible();
			return isDisplayed;
		} catch (final Exception e) {
			Logger.log("failed to check the order Processing status");
			return isDisplayed;
		}
	}
	
	@Step("wait till order get synced to AOS")
	public String waitForOrdertoSyncinAOS() {
		String getStatus=null;
		
			 getStatus = getText(orderProcessingStatusUnique);
			Logger.log("Status is displayed " +getStatus);
			while(!getStatus.equalsIgnoreCase("Complete"))
			{
				if (getStatus.equalsIgnoreCase("Complete with Errors"))
				{
				break;
				}
				clickwithWait(btnRefresh, 20000.00);
				getStatus = getText(orderProcessingStatusUnique);
			}
			  return getStatus;
			
		 
	}
	
	@Step("Get  order ID or AOS Deal ID ")
	public String getSupplyOrderID() {
		try {
			final String aosDealId = getText(getAOSDealID);
			Logger.log("Aos Deal ID or  Order ID  is" + aosDealId);
			return aosDealId;
		} catch (final Exception e) {

			return "not able to fetch AOS Deal ID or  Order ID";
		}
	}
	
	
	@Step("Get Account details of Supply connect ")
	public String getAccountDetails(String accName) {
		try {
			String account = "auto_connect_orderdetail_%s";
			String accountName = account.replace("%s",accName);
			String accountDetails = getText("//div[contains(@id,'supplyOrderDetailsPanel')]//div[contains(@class,'"+accountName+"')]//div[@role='textbox']");
			Logger.log("fetch account details of supply connect=====> "+accName+" " + accountDetails.trim());
			return accountDetails.trim();
		} catch (final Exception e) {

			return "not able to fetch account details of supply connect =====> "+accName+" ";
		}
	}



}