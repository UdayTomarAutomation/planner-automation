package com.operative.pages.connectAOS;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.aos.configs.AutoGlobalConstants;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;

import io.qameta.allure.Step;

public class ConnectMappingsPage extends BasePage {
	SoftAssert getSoftAssert;

	/**
	 * @param page
	 * @author Akshay Rathi
	 */
	public ConnectMappingsPage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert = new SoftAssert();
		// TODO Auto-generated constructor stub
	}

	final private String aosHamburgerMenu = "//button[@id='header_top_menu_icon']";
	final private String connectHyperLink = "//li/a[contains(text(),'Connect')]";
	final private String salesHyperLink = "//a[@href='/unifiedplanner']";
	
	final private String  mdlSettings   = "//a[contains(@class,'nav-link') and contains(text(),'Module Settings')]";
	final private String expandConnect = "//span[text()='Connect']//..//parent::div//span[contains(@class,'tree-toggler')]";
	final private String mappingTab = "//span[text()='Mappings']";
	final private String defaultPartnerValue = "//p-dropdown[@id ='undefined_dropdown']//span[contains(@class,'input')]/span";
	final private String arrowPartnerDropdown = "//p-dropdown[@id ='undefined_dropdown']//span[contains(@class , 'ui-dropdown-trigger')]";
	private String partnerListValue = "//li//span[contains(@title,'%s')]";
	private String selectTab = "//li//span[text()='%s']";
	private String selectbtn = "//button//span[text()='%s'] ";
	final private String txtBoxSearchProduct ="//input[@placeholder=' Search Product']";
	final private String txtBoxSearchTarget = "//input[@placeholder='Search']";
	private String chkBoxSelectProduct = "//div[@col-id='productName']//span[text()='%s']//parent::div//parent::div//parent::div[contains(@class,'ag-row')]//div[@col-id='0']//input";
	final private String btnAddSelected  = "//span[text()='Add Selected']";
	final private String txtBoxSearchExposedProduct = "//oa-input[@id='connectFilterProduct_exposedProductName']//input";
	private String getdetailsOfExposedProduct= "//td[contains(@id,'exposedProductName')]//span[contains(text(),'%s')]";
	private String btnApplyClear = "//div[contains(@class,'filter-buttons')]//button[not(@disabled)]//span[text()='%s']";
	private String btnunexposedProduct = "//td[contains(@id,'exposedExternalProductName')]//span[contains(text(),'%s')]//ancestor::tr//td[contains(@class,'action-column')]//em[contains(@class,'delete')]";
	final private String NoProductRecordAfterUnexposing = "//table[@class='undefined']/tbody[@class='ui-table-tbody']/tr/td[text()='No records found' and @colspan = '4']";
	private String selectTopName ="//li[@class='listbox-item ng-star-inserted' and contains(text(),'%s')]";
	private String addTop ="//td[contains(text(),'%s')]/parent::tr//span[contains(@class,'ui-radiobutton-icon ui-clickable')]";
	final private String NoTargetRecordAfterUnexposing ="//op-dynamic-grid[@id='connect-target-grid']//td[text()='No records found']";
	final private String includeTop ="//span[normalize-space()='Include']";
	final private String btnApply ="//button[@class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']//span[@class='ui-button-text ui-clickable'][normalize-space()='Apply']";
	final private String txtBoxSearchExposedTarget ="//input[@aria-label='Exposed Target']";
	private String btnunexposedTarget = "//td[contains(@id,'exposedTargetName_')]//span[contains(text(),'%s')]//ancestor::tr//td[contains(@class,'action-column')]//em[contains(@class,'delete')]";
	private String getDetailsofExposedTarget ="//td[contains(@id,'exposedTargetName')]//span[contains(text(),'%s')]";

	
	//final private String newDealLink = "//button[@id='header_top_menu_icon']";

	@Step("Navigate to connect")
	public Page navigateToConnect() {
		click(aosHamburgerMenu);
		Page connectPage = getPageBrowser().waitForPopup(() -> {
			getPageBrowser().click(connectHyperLink);
		});
		Logger.log("Navigate to connect");
		return connectPage;
	}

	@Step("close supply connect")
	public void closeSupplyConnect(Page connectPage) {
		connectPage.close();
		Logger.log("Navigate to connect");

	}

	@Step("Navigate to Sales")
	public void navigateToSales() {
		click(aosHamburgerMenu);
		click(salesHyperLink);
		Logger.log("Navigate to Sales");
	}
	
	@Step("Navigate to module settings")
	public void navigateTomoduleSettings() {
		try {
			clickwithWait(mdlSettings,1000);
			Logger.log("Navigate to module settings");
		} catch (final Exception e) {
			Logger.log("failed to navigate to moule settings");
		}
	}
	
	@Step("Expand Connect")
	public void expandConnect() {
		try {
			clickwithWait(expandConnect,1000);
			Logger.log("Expanded connect successfully");
		} catch (final Exception e) {
			Logger.log("failed to Expand connect successfully");
		}
	}
	
	@Step("Navigate to mapping page")
	public void navigateTomappingPage() {
		try {
			clickwithWait(mappingTab,1000);
			Logger.log("Navigate to mapping page");
		} catch (final Exception e) {
			Logger.log("failed to navigate to mapping page");
		}
	}
	
	
	@Step("select partner value")
	public void selectPartnerValue(String partnerName) {
		try {
			if(getText(defaultPartnerValue).equalsIgnoreCase(partnerName))
			  {
				  Logger.log("Selected "+partnerName+" value from the dropdown.");
			  }
			  else
			  {
				  click(arrowPartnerDropdown);
				  String partnervalue = partnerListValue.replace("%s",partnerName);
				  clickwithWait(partnervalue,1000);
				  Logger.log("Selected "+partnerName+" value from the dropdown.");  
			  }
		} catch (final Exception e) {
			Logger.log("failed to select partner value");
		}
	}
	
	@Step("Navigate to specific Tab like Exposed Product,Target,Mapping")
	public void navigateTospecificTab(String tabName) {
		try {
			String tabNameValue = selectTab.replace("%s",tabName);
			clickwithWait(tabNameValue,1000);
			Logger.log("Navigate to Exposed Product or Exposed Target or Account Mapping Tab successfully");
		} catch (final Exception e) {
			Logger.log("failed to navigate to Exposed Product or Exposed Target or Account Mapping Tab");
		}
	}
	
	@Step("click to specific button like Select Product or Select Target")
	public void clickOnSpecificButton(String btnName) {
		try {
			String btnValue = selectbtn.replace("%s",btnName);
			clickwithWait(btnValue,1000);
			Logger.log("clicked to Select Product or Select Target button successfully");
		} catch (final Exception e) {
			Logger.log("failed to clicked to Select Product or Select Target button");
		}
	}
	
	
	@Step("Expose Product from AOS")
	public void exposeProductfromAOS(String productName) {
		try {
			//click(txtBoxSearchProduct);
			type(txtBoxSearchProduct,productName);
			String selectExposedProductchkbox = chkBoxSelectProduct.replace("%s",productName);
			clickwithWait(selectExposedProductchkbox,1000);
			clickwithWait(btnAddSelected,1000);
			Logger.log("Exposed Product successfully");
		} catch (final Exception e) {
			Logger.log("failed to Exposed Product Successfully");
		}
	}
	
	@Step("Filter and verify Expose Product ")
	public boolean filterAndVerifyExposedProduct(String productName,String btnApply) {
		try {
			
			type(txtBoxSearchExposedProduct,productName);
			String applyBtn = btnApplyClear.replace("%s", btnApply);
			clickwithWait(applyBtn,1000);
			String detailsOfExposedProduct = getdetailsOfExposedProduct.replace("%s", productName);
			waitForSelectorVisible(detailsOfExposedProduct, 4000);
			boolean isDisplayed= false;
			if (isElementPresent(detailsOfExposedProduct)) {
				isDisplayed=true;
				Logger.log("Exposed Product is displayed in AOS exposed product grid Successfully");
				return isDisplayed;
			}else {
				return isDisplayed;
			}
		} catch (final Exception e) {
			
			Logger.log("Exposed Product is not displayed in AOS exposed product grid Successfully");
			return false;
		}
	}
	
	@Step("Filter and verify Expose Product ")
	public boolean filterAndVerifyUnExposedProduct(String productName,String btnClear,String btnApply) {
		try {
			
			
			String unexposedProducticon =btnunexposedProduct.replace("%s", productName);
			clickwithWait(unexposedProducticon,1000);
			String clearBtn = btnApplyClear.replace("%s", btnClear);
			clickwithWait(clearBtn,1000);
			type(txtBoxSearchExposedProduct,productName);
			String applyBtn = btnApplyClear.replace("%s", btnApply);
			clickwithWait(applyBtn,1000);
			waitForSelectorVisible(NoProductRecordAfterUnexposing,4000);
			boolean isDisplayed= false;
			if (isElementPresent(NoProductRecordAfterUnexposing)) {
				isDisplayed=true;
				Logger.log("Exposed Product is unexposed from AOS exposed product grid Successfully");
				return isDisplayed;
			}else {
				return isDisplayed;
			}
		} catch (final Exception e) {
			
			Logger.log("Exposed Product is not unexposed from AOS exposed product grid Successfully");
			return false;
		}
	}
	
	@Step("Expose Target from AOS")
	public void exposeTargetfromAOS(String tName, String TopName) {
		try {
			//click(txtBoxSearchProduct);
			type(txtBoxSearchTarget,tName);
			String selectExposedTargetchkbox = selectTopName.replace("%s",tName);
			clickwithWait(selectExposedTargetchkbox,1000);
			clickwithWait(addTop.replace("%s", TopName), 1000);
			clickwithWait(includeTop,1000);
			clickwithWait(btnApply, 1000);
			Logger.log("Exposed Target successfully");
		} catch (final Exception e) {
			Logger.log("Failed to Exposed Target Successfully");
		}
	}
	
	@Step("Filter and verify Expose Target ")
	public boolean filterAndVerifyExposedTarget(String tName,String topName,String btnApply) {
		try {
			
			type(txtBoxSearchExposedTarget,tName);
			String applyBtn = btnApplyClear.replace("%s", btnApply);
			clickwithWait(applyBtn,6000);
			String detailsOfExposedTop = getDetailsofExposedTarget.replace("%s", tName);
			waitForSelectorVisible(detailsOfExposedTop, 8000);
			boolean isDisplayed= false;
			if (isElementPresent(detailsOfExposedTop)) {
				isDisplayed=true;
				Logger.log("Exposed Target is displayed in AOS exposed Targets grid Successfully");
				return isDisplayed;
			}else {
				return isDisplayed;
			}
		} catch (final Exception e) {
			
			Logger.log("Exposed Target is not displayed in AOS exposed Targets grid Successfully");
			return false;
		}
	}
	
	@Step("Filter and verify UnExpose Target ")
	public boolean filterAndVerifyUnExposedTarget(String tName, String topName,String btnClear,String btnApply) {
		try {
			
			
			String unexposedProducticon =btnunexposedTarget.replace("%s", tName);
			clickwithWait(unexposedProducticon,3000);
			String clearBtn = btnApplyClear.replace("%s", btnClear);
			clickwithWait(clearBtn,5000);
			type(txtBoxSearchExposedTarget,tName);
			String applyBtn = btnApplyClear.replace("%s", btnApply);
			clickwithWait(applyBtn,6000);
			waitForSelectorVisible(NoTargetRecordAfterUnexposing,8000);
			boolean isDisplayed= false;
			if (isElementPresent(NoTargetRecordAfterUnexposing)) {
				isDisplayed=true;
				Logger.log("Exposed  Target is unexposed from AOS exposed Targets grid Successfully");
				return isDisplayed;
			}else {
				return isDisplayed;
			}
		} catch (final Exception e) {
			
			Logger.log("Exposed Target is not unexposed from AOS exposed Targets grid Successfully");
			return false;
		}
	}


}