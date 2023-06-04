package com.operative.base.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.file.Paths;
import java.util.Objects;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.operative.aos.configs.AutoGlobalConstants;

import io.qameta.allure.Step;

/**
 * @author upratap
 *
 */
public class BrowserCreationFactory {
	Page page;
	Browser browser;
	BrowserContext browsercontext;

	ThreadLocal<Browser>tbrowser=new ThreadLocal<>();
	ThreadLocal<BrowserContext>tbrowserContext=new ThreadLocal<>();
	ThreadLocal<Page>tpage=new ThreadLocal<>();

	public Browser createBrowser() {
		try {
			Playwright playwright = Playwright.create();
			if (AutoGlobalConstants.browserType.equalsIgnoreCase(AutoGlobalConstants.chrome)) {
				LaunchOptions launchOptions=new LaunchOptions();
				launchOptions.setChannel("chrome");
				launchOptions.setHeadless(false);
				//launchOptions.setSlowMo(500);
				//launchOptions.setIgnoreDefaultArgs(Arrays.asList("--hide-scrollbars"));
				//launchOptions.setArgs(Arrays.asList("--window-size=1280,720"));
				if (AutoGlobalConstants.devTools!=null) {
					launchOptions.setDevtools(Boolean.parseBoolean(AutoGlobalConstants.devTools));
				}
				if (Objects.isNull(browser)) {
					tbrowser.set(playwright.chromium().launch(launchOptions));
					browser = tbrowser.get();	
				}



			}
			else if (AutoGlobalConstants.browserType.equalsIgnoreCase(AutoGlobalConstants.chromium)) { 
				browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			}
			else if (AutoGlobalConstants.browserType.equalsIgnoreCase(AutoGlobalConstants.firefox)) {
				browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));

			}

			else if (AutoGlobalConstants.browserType.equalsIgnoreCase(AutoGlobalConstants.webkit)) {
				browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
			}

		}
		catch (final Exception e) {
			System.out.println(" Caught Exception in the launching "+AutoGlobalConstants.browserType+" browser" + e);
		}
		return browser;
	}

	@Step("Retun the  BowserContext")
	public BrowserContext  bowserContext(Browser browser)
	{

		if (AutoGlobalConstants.recordTestScripts.equalsIgnoreCase(AutoGlobalConstants.recordTestScriptsYes)) {
			browsercontext =browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/")));	
		}
		else if (AutoGlobalConstants.traceViewer!=null) {
			browsercontext = browser.newContext();
			browsercontext.tracing().start(new Tracing.StartOptions()
					.setScreenshots(true)
					.setSnapshots(true));
		}
		else {
			if (Objects.isNull(browsercontext)) {
				Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
				int width=(int)screen.getWidth();
				int height=(int)screen.getHeight();
				Logger.log("Window width==>>"+String.valueOf(width));
				Logger.log("Window height==>>"+String.valueOf(height));
				//tbrowserContext.set(browser.newContext(new Browser.NewContextOptions().setViewportSize(1536, 722)));
				tbrowserContext.set(browser.newContext(new Browser.NewContextOptions().setViewportSize(width, 722)));
				tbrowserContext.get().clearCookies();
				browsercontext=tbrowserContext.get();
			}

		}

		return browsercontext;
	}

	@Step("get the Page for Lanuching URL")
	public Page  getPage(BrowserContext browserContext)
	{
		if (Objects.isNull(page)) {
			tpage.set(browserContext.newPage());
			tpage.get().navigate(AutoGlobalConstants.environmentUrl);
			Logger.logResponse("Env Url==>>", tpage.get().url());
			if (AutoGlobalConstants.setDefaultTimeout!=null) {
				Logger.log("Override the SetDefaultTimeout===>>"+AutoGlobalConstants.setDefaultTimeout);
				tpage.get().setDefaultTimeout(Double.parseDouble(AutoGlobalConstants.setDefaultTimeout));
			}
		}
		page =tpage.get();

		return page;
	}

	@Step("get the Page for Lanuching URL O1Operative")
	public Page  getPageO1Operative(BrowserContext browserContext)
	{
		if (Objects.isNull(page)) {
			tpage.set(browserContext.newPage());
			tpage.get().navigate(AutoGlobalConstants.environmentUrlO1Operative);
			Logger.logResponse("Env Url==>>", tpage.get().url());
			if (AutoGlobalConstants.setDefaultTimeout!=null) {
				Logger.log("Override the SetDefaultTimeout===>>"+AutoGlobalConstants.setDefaultTimeout);
				tpage.get().setDefaultTimeout(Double.parseDouble(AutoGlobalConstants.setDefaultTimeout));
			}
		}
		page =tpage.get();
		return page;
	}


}
