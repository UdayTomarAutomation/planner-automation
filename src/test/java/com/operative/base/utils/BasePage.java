package com.operative.base.utils;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.operative.base.database.utils.MySQLUtilities;
import com.operative.verification.utils.CustomeVerification;
/**
 * @author upratap
 *
 */
public class BasePage {

	protected Page getpageBrowser;
	CustomeVerification customeVerification;

	

	public Page getPageBrowser() {
		return getpageBrowser;
	}

	public BasePage(Page getpageBrowser) {
		this.getpageBrowser = getpageBrowser;

	}
	
	

	public void click(String locator) {
		getpageBrowser.click(locator);
	}

	public void type(String  locator, String value) {
		getpageBrowser.type(locator, value);

	}

	public void fill(String  locator, String value) {
		getpageBrowser.fill(locator, value);
	}
	
	public void checkByLabel(String fieldName) {
		getpageBrowser.getByLabel(fieldName).check();
	}


	public String getText(String  locator) {
		return getpageBrowser.textContent(locator);
	}

	public String getAttribute(String  locator,String name) {
		return getpageBrowser.getAttribute(locator, name);
	}

	public void mousehover(String  locator) {
		getpageBrowser.hover(locator);
	}

	public void doubleclick(String  locator) {
		getpageBrowser.dblclick(locator);;
	}

	public void dragAndDrop(String source, String  target)
	{
		getpageBrowser.dragAndDrop(source, target);
	}

	public void typeSetDely(String  locator, String value,double wait) {
		getpageBrowser.type(locator, value,new Page.TypeOptions().setDelay(wait));
	}

	public boolean isElementPresent(String  selector) {
		boolean value;
		try {
			getpageBrowser.isEnabled(selector);
			value = true;
		} catch (final TimeoutError e) {
			value = false;
		}
		return value;
	}
	
	
	public void clickwithWait(String  locator,double wait) {
		getpageBrowser.click(locator,new Page.ClickOptions().setDelay(wait));
		
	}
	
	public void waitForSelectorVisible(String selector,double wait)
	{
		getpageBrowser.waitForSelector(selector,
				new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(wait));
	}
	
	public void waitForSelectorHidden(String selector,double wait)
	{
		getpageBrowser.waitForSelector(selector,
				new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setStrict(true).setTimeout(wait));
	}
	
	public String format2Digi(String value) {
		return new DecimalFormat("#0.00").format(Double.parseDouble(value));
	}


	public CustomeVerification getCustomeVerification() {
		if (customeVerification == null) {
			customeVerification = new CustomeVerification();
		}
		return customeVerification;
	}
	
	
	public String[][] evaluateQuery(String sQuery, Integer iMode) throws RuntimeException {
		String[][] ResultArray = null;
		try {
			switch (iMode) {
			case 1: // Evaluate query to get the data
				ResultArray = new MySQLUtilities().executeQueryAndGetResults(sQuery, true);
				break;
			case 2:// Insert
				new MySQLUtilities().insertQuery(sQuery);
				System.out.println("Insert Query Executed");
				break;
			case 3:// Update
				new MySQLUtilities().updateQuery(sQuery);
				System.out.println("Update Query Executed");
				break;

			case 4:// StoredProcedure
				new MySQLUtilities().executeProcedure(sQuery);
				System.out.println("Executed Stored Procedure call");
				break;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return ResultArray;
	}
	public String getRandomNumber() {
		final String chars = "345678910";
		String ret = "";
		final int length = chars.length();
		for (int i = 0; i < length; i++) {
			ret += chars.split("")[(int) (Math.random() * (length - 1))];
		}
		return ret;
	}
	
	public String convertDatemmddyyToddmmyyy(String myDate) throws ParseException
	{
		Logger.log("currently Date format is ddmmyyyy >>>> " +myDate);
		Date date = new SimpleDateFormat("mm/dd/yyyy").parse(myDate);
		SimpleDateFormat inDate = new SimpleDateFormat("dd/mm/yyyy");
		String date1  = inDate.format(date);
		Logger.log("Date format is changed from ddmmyyyy to mmddyyyy >>>> " +date1);
		return date1;
	}
}


