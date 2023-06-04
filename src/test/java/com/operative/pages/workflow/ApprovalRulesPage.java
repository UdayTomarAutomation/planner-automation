package com.operative.pages.workflow;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Page;
import com.operative.base.utils.BasePage;

public class ApprovalRulesPage extends BasePage {
	SoftAssert getSoftAssert;
	public ApprovalRulesPage(Page getpageBrowser) {
		super(getpageBrowser);
		this.getSoftAssert=new SoftAssert();
		// TODO Auto-generated constructor stub
	}

	final private String moduleSettingsTab="//span[text()=' Module Settings ']";
	final private String expandPlanning="//span[text()='Planning']/../../span[contains(@class,'ui-tree-toggler')]";
	final private String approvalRulesTab="//span[text()='Approval Rules']";
	final private String addButton="//span[text()='Add']";
	final private String nameTab="//label[text()=' Name ']/../input";
	final private String notesTab="//div[text()='Note(s)']/..//textarea";
	final private String fieldNameTab="//div[contains(@class,'condField')]";
	final private String fieldNameDropdown="//input[contains(@class,'ui-dropdown-filter')]";
	final private String operatorTab="//op-cc-dropdown[contains(@class,'operatorDrpdown')]";
	final private String operatorDropdown="//input[contains(@class,'ui-dropdown-filter')]";
	final private String valueTab="//div[@class='occ-dropdown']";
	final private String valueDropdown="//occ-dropdown-form";
	final private String editActionBtn="//button[@title='Edit']";
	final private String approverStatusTab="//p-multiselect[contains(@id,'apprStatus_multiselect')]";
	final private String approverStatusDropdown="//div[contains(@class,'ui-multiselect-filter-container')]//input[contains(@class,'ui-widget')]";
	final private String approverTypeTab="//op-cc-dropdown[contains(@id,'apprType')]";
	final private String approverTypeDropdown="//input[contains(@class,'ui-dropdown-filter')]";
	final private String approversTab="//input[@id='id_Approvers_input']";
	final private String saveActionBtn="//p-button[@class='saveActionDialogBtn']";
	final private String saveBtn="//span[text()='Save']";


	public ApprovalRulesPage navigateToApprovalRules() {
		click(moduleSettingsTab);
		click(expandPlanning);
		click(approvalRulesTab);
		return this;
	}

	public ApprovalRulesPage clickAddButton() {
		click(addButton);
		return this;
	}

	public ApprovalRulesPage createNewApprovalRule(String name, String notes, String fieldname,
			String operator, String value, String approvalStatus, String approvalType, String approver) {
		click(nameTab);
		type(nameTab, name);
		click(notesTab);
		type(notesTab, notes);
		click(fieldNameTab);
		type(fieldNameDropdown, fieldname);
		click("//span[text()='"+fieldname+"']");
		click(operatorTab);
		type(operatorDropdown, operator);
		click("//span[text()='"+operator+"']");
		click(valueTab);
		type(valueDropdown, value);
		click("//span[text()='"+value+"']/..//p-checkbox");
		click(editActionBtn);
		click(approverStatusTab);
		type(approverStatusDropdown, value);
		click("//span[text()='"+approvalStatus+"']");
		click(approverTypeTab);
		type(approverTypeDropdown, value);
		click("//span[text()='"+approvalType+"']");
		click(approversTab);
		click("//span[text()='"+approver+"']");
		click(saveActionBtn);
		return this;
	}
	
	public ApprovalRulesPage clickSaveButton() {
		click(saveBtn);
		return this;
	}



	//span[text()='Deal > Advertiser']

	//span[text()='Equals']

	//span[text()='1800 Got Junk']/..//p-checkbox

	//span[text()='Pending Revision Approvals']

	//span[text()='Required']

	//span[text()='Madhumita Velayutham']
}
