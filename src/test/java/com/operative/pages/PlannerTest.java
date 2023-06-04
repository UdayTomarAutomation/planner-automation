/**
 * 
 */
package com.operative.pages;

import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Page;
import com.operative.base.utils.BasePage;

/**
 * @author upratap
 *
 */
public class PlannerTest  extends BasePage{
	SoftAssert getSoftAssert; 
	/**
	 * @param page
	 */
	public PlannerTest(Page page) {
		super(page);
		 this.getSoftAssert=new SoftAssert();
		// TODO Auto-generated constructor stub
	}
	
	public void valida()
	{
	getCustomeVerification().verifyFalse(getSoftAssert, true, "ddd");
	getCustomeVerification().assertAll(getSoftAssert);
	}

}
