package com.operative.pages.o1sales;
import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.Logger;

import io.qameta.allure.Step;

public class OrderPropertiesPage extends BasePage {
	SoftAssert getSoftAssert;
	
	/**
	 * @param page
	 */
	public OrderPropertiesPage(Page page) {
		super(page);
		this.getSoftAssert = new SoftAssert();
		// TODO Auto-generated constructor stub
	}
}