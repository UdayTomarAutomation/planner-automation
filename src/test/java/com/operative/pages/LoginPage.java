package com.operative.pages;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Page;
import com.operative.aos.configs.AutoGlobalConstants;
import com.operative.base.utils.BasePage;

import io.qameta.allure.Step;

/**
 * @author upratap
 *
 */
public class LoginPage extends BasePage {
	SoftAssert softAssert;

	/**
	 * @param getpageBrowser
	 */
	public LoginPage(Page getpageBrowser) {
		super(getpageBrowser);
		this.softAssert = new SoftAssert();
		// TODO Auto-generated constructor stub
	}

	String username = "//input[@placeholder='User Name']";
	String password = "//input[@placeholder='Password']";
	String singIn = "//input[@value='Log in']";

	@Step("==>>login in AOS Application")
	public LoginPage loginApplication() {
		 fill(username, AutoGlobalConstants.userName);
		 type(password, AutoGlobalConstants.passWord);
		 //page.type(password, AutoGlobalConstants.passWord, new Page.TypeOptions().setDelay(100))
		 click(singIn);
		return this;
	}
	
	@Step("==>>login in AOS Application")
	public LoginPage loginApplication(String inputusername, String inputpassword) {
		fill(username, inputusername);
		type(password, inputpassword);
		click(singIn);
		return this;
	}
	
	@Step("==>>login in O1 Application")
	public LoginPage loginApplicationO1Operative() {
        fill(username, AutoGlobalConstants.userNameO1Operative);
        type(password, AutoGlobalConstants.passWordO1Operative);
		//page.type(password, AutoGlobalConstants.passWord, new Page.TypeOptions().setDelay(100));
		click(singIn);
		return this;
	}
	
	
	

}
