package com.operative.base.utils;

import java.nio.file.Paths;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import com.operative.aos.configs.AutoGlobalConstants;
import com.operative.verification.utils.CustomeVerification;

import io.qameta.allure.Step;

/**
 * @author upratap
 *
 */
public class BaseTest {

	private Page pagebrowser;
	private BasePage basePage;
	BrowserContext browsercontext;
	SoftAssert softAssert;
	CustomeVerification customeVerification;

	public Page getPageBrowser() {
		return pagebrowser;
	}

	public BrowserContext getbrowsercontext() {
		return browsercontext;
	}

	@Step("lanuch Aos Application")
	public void launchFoxApplication() {
		

		Browser browser = new BrowserCreationFactory().createBrowser();
		browsercontext = new BrowserCreationFactory().bowserContext(browser);
		pagebrowser = new BrowserCreationFactory().getPage(browsercontext);
		Logger.log("Launched " + AutoGlobalConstants.browserType + " browser");
		Logger.log("Environment : " + AutoGlobalConstants.environmentUrl);
	}

	@Step("lanuch O1 Application")
	public void launchO1OperativeApplication() {
		

		Browser browser = new BrowserCreationFactory().createBrowser();
		browsercontext = new BrowserCreationFactory().bowserContext(browser);
		pagebrowser = new BrowserCreationFactory().getPageO1Operative(browsercontext);
		Logger.log("Launched " + AutoGlobalConstants.browserType + " browser");
		Logger.log("Environment : " + AutoGlobalConstants.environmentUrlO1Operative);
	}

	public BasePage getBasePage() {
		if (basePage == null) {
			basePage = new BasePage(getPageBrowser());
		}
		return basePage;
	}

	@BeforeMethod
	public void beforeMethod() {
		this.softAssert = new SoftAssert();
	}

	// get SoftAssert object
	public SoftAssert getSoftAssert() {
		return softAssert;
	}

	// get CustomeVerification object
	public CustomeVerification getCustomeVerification() {
		if (customeVerification == null) {
			customeVerification = new CustomeVerification();
		}
		return customeVerification;
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult itr) {

		final String testName = itr.getMethod().getMethodName();
		Logger.log("============Test Results Run==========================");
		Logger.log("Test : " + testName + " Test Status : " + itr.getStatus());
		Logger.log("=======================================================");

		if (AutoGlobalConstants.traceViewer != null) {
			getbrowsercontext().tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));
		}

		if (ITestResult.FAILURE == itr.getStatus()) {
			captureScreenshot(itr.getName());
		} else {
			Logger.log("trace viewer not on");
		}
	}

	/**
	 * Method Used to take ScreenShot For Failed Test Case
	 *
	 * @param driver
	 * @param screenshotname
	 */
	public String captureScreenshot(String screenshotname) {
		String path = System.getProperty("user.dir") + "/Screenshot/" + screenshotname + System.currentTimeMillis()
				+ ".png";
		getPageBrowser().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		Logger.log("<<====Screenshot Taken====>>");
		return path;
	}

	/*
	 * @BeforeSuite()
	 * 
	 * @Parameters("datafile") public void beforeSuite(@Optional String fileName) {
	 * if (new File("./Screenshot/").exists()) { try { FileUtils.cleanDirectory(new
	 * File("./Screenshot/")); } catch (final IOException e) {
	 * Logger.log("screen shot folder is missing"); e.printStackTrace(); } } }
	 */

}
