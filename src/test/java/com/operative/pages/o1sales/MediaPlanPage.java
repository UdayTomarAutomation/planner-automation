package com.operative.pages.o1sales;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;

import io.qameta.allure.Step;

public class MediaPlanPage extends BasePage {
	SoftAssert getSoftAssert;

	/**
	 * @author akrathi
	 */
	public MediaPlanPage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert = new SoftAssert();
		// TODO Auto-generated constructor stub
	}

	final private String mediaPlanTab = "//span[text()='Media Plan']";
	final private String addLineItems = "//span[text()='Add Line Item']";
	final private String TypePrdNameTextBox = "//input[contains(@class,'auto_sales_mptab_productname')]";
	final private String AddProductPlus = "//img[contains(@class,'icon-plus-hover')]";
	final private String AddProductSuccess = "//img[contains(@class,'icon-success')]";
	final private String clickCloseIcon = "//input[contains(@class,'auto_sales_mptab_productname')]/parent::div//span";
	final private String saveDealChanges = "//table[contains(@class,'auto_sales_mptab_save')]//button[text()='Save']";
	final private String LineAddedOnGrid = "//td[contains(@class,'productName')]/div[text()='%s']";
	final private String productDisplay = "//div[@class='x-grid3-body']//span[text()= '%s']";
	final private String lineIdNotReflected = "//table[@class='x-grid3-row-table']//td//div[text()='Shay-Prod-2']/parent::td/../td[contains(@class,'td-id')]/div[text()='-']";
	final private String lineItemNamePath ="//input[@name='name' and contains(@class,'auto_sales_mptab_name')]";
	final private String pricingTabPath = "//div[contains(@class, 'op-sub-tabs')]//span[text()='Pricing']";
	final private String costMethodPath ="//input[@name='costMethod']/parent:: div";
	final private String startDatePath ="//input[@name='startDate']";
	final private String endDatePath = "//input[@name='endDate']";
	final private String quantityPath = "//input[@name='quantity']";
	final private String netUnitCostPath = "//input[@name= 'unitCost']";
	final private String netUnitCostField = "//input[@name= 'unitCostDisplayField']";
	final private String addedvalue ="//input[@name='addedValue']";
	final private String makegood ="//input[@name='makegood']";
    final private String totalNetCost = "//div[contains(@class,'auto_sales_view_totalnet')]//div[@class='op-form-value']/div";
	final private String MPTargetingTab="//div[contains(@class, 'op-sub-tabs')]//span[text()='Targeting']";
   
	@Step("Navigate to media plan")
	public void navigateTomediaPlan() {
		try {
			click(mediaPlanTab);
			Logger.log("Navigate to Media Plan");
		} catch (final Exception e) {
			Logger.log("failed to navigate to Media Plan");
		}
	}

	@Step("Add Line Items")
	public void addlineitem(String str) {
		try {
			click(addLineItems);
			String productName[] = str.split(";");
			for (String prd : productName) {
				{

					clickwithWait(TypePrdNameTextBox, 2000);
					type(TypePrdNameTextBox, prd);
					String productDiplayed = productDisplay.replaceAll("%s", prd);
					waitForSelectorVisible(productDiplayed, 3000);
					click(AddProductPlus);
					click(clickCloseIcon);
					Logger.log("Line Item Added ====>" + prd);
				}
			}

		} catch (final Exception e) {
			Logger.log("Not able to add line items");
		}

	}

	@Step("save the changes of media plan")
	public void saveTheDetails() {
		try {
			clickwithWait(saveDealChanges, 4000);
			Logger.log("Save the details of Deal");
		} catch (final Exception e) {
			Logger.log("unable to Save the details of Deal");
		}
	}

	@Step("O1 Line added successfully")
	public boolean isLineAddedDisplayedOnGrid(String Name) {

		String productName[] = Name.split(";");
		for (String prd : productName) {
			String LineDisplayedOnGrid = LineAddedOnGrid.replace("%s", prd);
			Logger.log("Line/product name is" + LineDisplayedOnGrid);
			if (!getpageBrowser.isVisible(LineDisplayedOnGrid)) {
				return false;
			}
		}
		return true;

	}

	@Step("Get cost method")
	public String getCostMethod(String productName) {
		try {
		final String costMethod = getText("//table[@class='x-grid3-row-table']//td//div[text()='"+productName+"']/parent::td/following-sibling::td[contains(@class,'td-costTypeName')]");
		Logger.log("Cost Method is" + costMethod);
		return costMethod;
	}catch (final Exception e) {
		
		return "not able to fetch cost method";
	}}
	
	@Step("Get Net Unit cost")
	public String getNetUnitCost(String productName) {
		try {
		final String netUnitCost = getText("//table[@class='x-grid3-row-table']//td//div[text()='"+productName+"']/parent::td/following-sibling::td[contains(@class,'td-unitCost ')]");
		Logger.log("Nt unit cost Method is" + netUnitCost);
		return netUnitCost.trim();
	}catch (final Exception e) {
		
		return "not able to fetch Net unit cost ";
	}}
	
	@Step("Get Net cost")
	public String getNetCost(String productName) {
		try {
		final String netCost = getText("//table[@class='x-grid3-row-table']//td//div[text()='"+productName+"']/parent::td/following-sibling::td[contains(@class,'td-cost ')]");
		Logger.log("Nt  cost  is" + netCost);
		return netCost.trim();
	}catch (final Exception e) {
		
		return "not able to fetch Net unit cost ";
	}}
	
	@Step("Get Quantity")
	public String getQuantity(String productName) {
		try {
		final String quantity = getText("//table[@class='x-grid3-row-table']//td//div[text()='"+productName+"']/parent::td/following-sibling::td[contains(@class,'td-quantity')]");
		Logger.log("Quantity is" + quantity);
		return quantity.trim();
	}catch (final Exception e) {
		
		return "not able to fetch quantity ";
	}}
	
	@Step("Get Start Date")
	public String getStartDate(String productName) {
		try {
		final String startDate = getText("//table[@class='x-grid3-row-table']//td//div[text()='"+productName+"']/parent::td/following-sibling::td[contains(@class,'td-startDate')]");
		Logger.log("Start Date is" + startDate);
		return startDate;
	}catch (final Exception e) {
		
		return "not able to fetch Get start date ";
	}}
	
	@Step("Get End Date")
	public String getEndDate(String productName) {
		try {
		final String endDate = getText("//table[@class='x-grid3-row-table']//td//div[text()='"+productName+"']/parent::td/following-sibling::td[contains(@class,'td-endDate')]");
		Logger.log("End Date is" + endDate);
		return endDate;
	}catch (final Exception e) {
		
		return "not able to fetch Get End date  ";
	}}
	
	@Step("Get Line ID ")
	public String getLineId(String productName) {
		try {
			waitForSelectorHidden("//table[@class='x-grid3-row-table']//td//div[text()='"+productName+"']/parent::td/../td[contains(@class,'td-id')]/div[text()='-']", 5000);
		final String lineID = getText("//table[@class='x-grid3-row-table']//td//div[text()='"+productName+"']/parent::td/../td[contains(@class,'td-id')]/div");
		Logger.log("Line ID is" + lineID);
		return lineID;
	}catch (final Exception e) {
		
		return "not able to fetch Get Line ID ";
	}}
	
	@Step("Get Total Net cost of proposal ")
	public String getTotalNetCostOfProposal() {
		try {
		clickwithWait(totalNetCost,15000);
		final String totalNetCostDeal = getText(totalNetCost);
		String totalNetCost = totalNetCostDeal.replaceAll("[\\s\\u00A0]+$", "");
		Logger.log("Total Net Cost is" + totalNetCost);
		return totalNetCost.trim();
	}catch (final Exception e) {
		
		return "not able to fetch total net cost of Proposal or Deal";
	}}
	
	@Step("Adding multiple field in Line Item")
	public void addMultipleFieldOnLineItem(String lineItemName,String lineId ,String lineItemNameUpdate,String costMethodupdate, String quantity,
			String netUnitCost, String startDate, String endDate, String addValue, String makeGood, String LItargetWithOps) {
		

		getpageBrowser.getByText(lineId).click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
		getpageBrowser.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Advanced Edit")).click();
		
		
		if(!lineItemNameUpdate.isEmpty()) {
			try {
				clickwithWait(lineItemNamePath, 1000);
				fill(lineItemNamePath, lineItemNameUpdate);
				Logger.log("Updated Line Item Name is : " +lineItemNameUpdate);
			}catch (final Exception e) {
				Logger.log("Not able to update Line Item Name.");
			}
		}		
		if(!costMethodupdate.isEmpty()) {
			try {
				getpageBrowser.getByLabel("Cost Method").click();
				getpageBrowser.getByText(costMethodupdate).click();
				Logger.log("Cost Method is :" + costMethodupdate);
			}catch (final Exception e) {
				
				Logger.log("Not able to update cost method");
			}
		}
		if(!startDate.isEmpty()) {
			try {
				clickwithWait(startDatePath, 500);
				fill(startDatePath, startDate);
				Logger.log("Start Date is : " + startDate);
			}catch(final Exception e) {
				Logger.log("Not able to update Start Date");
			}
		}
		if(!endDate.isEmpty()) {
			try {
				clickwithWait(endDatePath, 500);
				fill(endDatePath, endDate);
				Logger.log("End Date is : " +endDate);
			}catch(final Exception e) {
				Logger.log("Not able to update End Date");
			}
		}
		if(!netUnitCost.isEmpty()) {
			try {
				click(pricingTabPath);
				clickwithWait(netUnitCostField, 1000);
				type(netUnitCostPath,netUnitCost);
				Logger.log("Net Unit Cost is : " +netUnitCost);
				
			}catch(final Exception e) {
				Logger.log("Not able to update Net Unit Cost.");
			}
		}
		if(!quantity.isEmpty()) {
			try {
				
				clickwithWait(quantityPath,1000);
				type(quantityPath, quantity);
				Logger.log("Quantity is: " + quantity);
			}catch (final Exception e) {
				Logger.log("Not able to update Quantity.");
			}
		}
		if(addValue.contains("Yes")) {
			try {
				getpageBrowser.getByLabel("Treat as Added Value").check();
				Logger.log("Treat as Added Value is Selected.");
				
			}catch(final Exception e) {
				Logger.log("Not able to select Added Value.");
			}
		}
		if(makeGood.contains("Yes")) {
			try {
				getpageBrowser.getByLabel("Treat as Makegood").check();
				Logger.log("Make Good Value is Selected.");
				
			}catch(final Exception e) {
				Logger.log("Not able to select Make Good Value.");
			}
		}
		
		if (!LItargetWithOps.isEmpty()) {
			try {
				click(MPTargetingTab);
				String tWithOp[] = LItargetWithOps.split("&");

				for (String targetWithOp : tWithOp) {

					String targetOpName[] = targetWithOp.split(":");
					String targetName = targetOpName[0];
					String tName = "//div[@name='" + targetName
							+ "']//button[contains(@class,'auto_multi_select_field_add_new_item')]";
					clickwithWait(tName, 1000);
					String tOpName[] = targetOpName[1].split(",");
					for (int t = 0; t <= tOpName.length - 1; t++) {
						clickwithWait("//div[text()='" + tOpName[t] + "']/preceding-sibling::img", 1000);
					}
				}
				Logger.log("Target is added with Targeting options");
			} catch (final Exception e) {
				Logger.log(" Not able to add Targets on LineItem");
			}

		}

		getpageBrowser.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply")).click();
		
	}

}