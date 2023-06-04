/**
 * 
 */
package com.operative.tests.plannerWorkspaceSmoke;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.dataprovider.PlannerWorkSpaceDataprovider;
import com.operative.aos.configs.AutoConfigPlannerWorkSpace;
import com.operative.base.utils.BaseTest;
import com.operative.pages.LoginPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;

/**
 * @author upratap
 *
 */
public class VerifyProductCreateSimilarLine extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="productCreateSimilarLine")
	public void verifyProductCreateSimilarLine(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue) 
	{
		PlannerHeaderPage planHeader=new PlannerHeaderPage(getPageBrowser());
		//creating Plan Header
		planHeader.creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
	    //save Plan Header
		planHeader.clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttribute, 
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		//add product in chooser
		planworkspace.addProductInChooserWindow(AutoConfigPlannerWorkSpace.productworkSpace).clickAddClose();
		//createSimilarLines
		planworkspace.createSimilarLines(AutoConfigPlannerWorkSpace.productworkSpace, 1);
		int productcount=planworkspace.productCountWorkSpace();
		getCustomeVerification().verifyEquals(getSoftAssert(), productcount, 2,"product count not same");
		getCustomeVerification().assertAll(getSoftAssert());
		

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
