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
 * @author uday madan
 *
 */
public class VerifyProduct_EditLine_ThreeMatrix extends BaseTest {
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="smokeSuitePlannerData")
	public void VerifyProductEditLineThreeMatrix(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String RatingStream,String spotsvalue,String Ratecard,String Product1,String Product2,String Product3) {
		PlannerHeaderPage  planHeaderPage=new PlannerHeaderPage(getPageBrowser());
		//creating Plan Header
		planHeaderPage.creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//Select Rating Stream
		planHeaderPage.selectRatingStream(RatingStream);
		//Add Ratecard
		planHeaderPage.selectRateCardName(Ratecard);
		//save Plan Header
		planHeaderPage.clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttribute, 
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(Product1).clickAddClose();
		try {
		//Select Line Info filter
		planworkspace.lineInfoFilterSelectAll();
		//get Line info value
		String lineclassValue=planworkspace.verifyLineInfo(Product1, "1", AutoConfigPlannerWorkSpace.lineClassLineInfo);
		String commercialValue=planworkspace.verifyLineInfo(Product1, "1", AutoConfigPlannerWorkSpace.commercialTypeInfo);
		String spotlengthValue=planworkspace.verifyLineInfo(Product1, "1", AutoConfigPlannerWorkSpace.spotLengthInfo);
		getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.lineClassAttribute, lineclassValue,"line class value not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.commTypeAttribute, commercialValue,"comm type  not same");
		getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.spotLengthAttribute, spotlengthValue,"spotLength value not same");
		
		planworkspace.editLines(Product1,1,AutoConfigPlannerWorkSpace.commTypeAttributeUpdate,AutoConfigPlannerWorkSpace.lineClassAttributeUpdate,AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate);
		planworkspace.editLinesWarningmessage();
		
		//get Line info value
		String lineclassValueUpdated=planworkspace.verifyLineInfo(Product1, "1", AutoConfigPlannerWorkSpace.lineClassLineInfo);
		String commercialValueUpdated=planworkspace.verifyLineInfo(Product1, "1", AutoConfigPlannerWorkSpace.commercialTypeInfo);
		String spotlengthValueUpdated=planworkspace.verifyLineInfo(Product1, "1", AutoConfigPlannerWorkSpace.spotLengthInfo);
		getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.lineClassAttributeUpdate, lineclassValueUpdated,"line class value is not edited");
		getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.commTypeAttributeUpdate, commercialValueUpdated,"comm type  is not edited");
		getCustomeVerification().verifyEquals(getSoftAssert(), AutoConfigPlannerWorkSpace.spotLengthAttributeUpdate, spotlengthValueUpdated,"spotLength value is not edited");
		
		getCustomeVerification().assertAll(getSoftAssert());
		}
		
		finally {
		//Deselect all matrics from line info
		planworkspace.lineInfoFilterDeSelectAll();
		}

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
