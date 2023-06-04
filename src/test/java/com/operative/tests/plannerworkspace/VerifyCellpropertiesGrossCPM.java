/**
 * 
 */
package com.operative.tests.plannerworkspace;

import java.text.DecimalFormat;

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
public class VerifyCellpropertiesGrossCPM  extends BaseTest{
	@BeforeTest
	public void beforeTest() {
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
	}

	@Test(dataProviderClass=PlannerWorkSpaceDataprovider.class,dataProvider="cellpropertiesGrossCPM")
	public void verifyCellpropertiesGrossCPM(String dealName,String advName,String agencyName,String accountExecute,String calendarvalue,
			String startPeriodValue,String endPeriodValue,String dealYearValue,String marketPlaceValue,String flightvalue,String channelValue,
			String demovalue,String spotsvalue,String cellRatevalue,String cellimpsvalue)
	{
		//creating Plan Header
		new PlannerHeaderPage(getPageBrowser()).creationPlanHeader(dealName,AutoConfigPlannerWorkSpace.planClass,advName,agencyName,accountExecute, 
				calendarvalue, startPeriodValue, endPeriodValue, dealYearValue, marketPlaceValue, flightvalue,channelValue,demovalue);
		//save Plan Header
		new PlannerHeaderPage(getPageBrowser()).clickSaveHeader();
		//navigate to WorkSpace
		PlannerWorkSpacePage  planworkspace=new PlannerWorkSpacePage(getPageBrowser()).navigateToWorkSpaceTab();
		//click product chooser tab
		planworkspace.clickProductChooserTab();
		//select Line Item Attribute in product chooser window
		planworkspace.selectLineItemAttributes(AutoConfigPlannerWorkSpace.commTypeAttribute, 
				AutoConfigPlannerWorkSpace.lineClassAttribute, AutoConfigPlannerWorkSpace.spotLengthAttribute).clickApplyProductChooser();
		planworkspace.addProductInChooserWindow(AutoConfigPlannerWorkSpace.productworkSpace).clickAddClose();
		//enter workspace
		planworkspace.enterWorkSpaceDataInWeeklySpotsIndex(AutoConfigPlannerWorkSpace.productworkSpace,
				AutoConfigPlannerWorkSpace.dayPartDisplay,spotsvalue,1);
		//click weekly spots value
		planworkspace.clickWeeklySpotsValue(AutoConfigPlannerWorkSpace.productworkSpace, AutoConfigPlannerWorkSpace.dayPartDisplay, 1);
		//enter cell properties in imps 
		planworkspace.enterCellPropertiesValue(AutoConfigPlannerWorkSpace.cellImps, cellimpsvalue);
		//enter cell properties in rate 
		planworkspace.enterCellPropertiesValue(AutoConfigPlannerWorkSpace.cellRate, cellRatevalue);
		//calculation gross cpm
		Double grosscpmQtr=Double.parseDouble(cellRatevalue)/Double.parseDouble(cellimpsvalue);
		//save workspace
		planworkspace.clickSaveWorkSpace();
		//get gross cpm value in cell properties
		String grossCpm=planworkspace.getCellPropertiesValue(AutoConfigPlannerWorkSpace.grossCpm);
		 DecimalFormat f = new DecimalFormat("##.00");
		getCustomeVerification().verifyEquals(getSoftAssert(), "$"+f.format(Double.parseDouble(grossCpm)),  "$"+f.format(grosscpmQtr),
				"Gross cpm cell properties not same");
		getCustomeVerification().assertAll(getSoftAssert());
	

	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
