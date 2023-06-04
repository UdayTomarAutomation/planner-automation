/**
 * 
 */
package com.operative.tests.connect;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.dataprovider.ConnectDataprovider;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.operative.aos.configs.AutoConfigConnect;
import com.operative.aos.configs.AutoConfigDigitalPlannerWorkSpace;
import com.operative.aos.configs.AutoConfigPlannerHeader;
import com.operative.aos.configs.AutoGlobalConstants;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;
import com.operative.pages.LoginPage;
import com.operative.pages.connect.ConnectDemandOrderPage;
import com.operative.pages.connect.ConnectSupplyOrderPage;
import com.operative.pages.connectAOS.ConnectMappingsPage;
import com.operative.pages.o1sales.MediaPlanPage;
import com.operative.pages.o1sales.SalesOrderAdOpsPage;
import com.operative.pages.plannerheader.PlannerHeaderPage;
import com.operative.pages.plannerworkspace.PlannerDigitalWorkspacePage;
import com.operative.pages.plannerworkspace.PlannerWorkSpacePage;

import groovyjarjarantlr4.v4.codegen.model.SrcOp;

/**
 * @author SonaliKhakal
 *
 */
public class ConnectLineItemwithMultipleTargetOptions extends BaseTest {
	@BeforeTest
	public void beforeTest() throws InterruptedException {
		launchO1OperativeApplication();
		new LoginPage(getPageBrowser()).loginApplicationO1Operative();

	}

	@Test(dataProviderClass = ConnectDataprovider.class, dataProvider = "DealCreationWithTarget")
	public void verifyLineItemCreateWithMultipleTargetOptions(String orderName, String orderAdvertizer, String primarySalesPerson,
			String orderAgency, String salesStage, String orderOwner, String orderBelongs, String currency,
			String rateCard, String orderType, String salesContract, String productName, String CMUpd1,
			String startDt1, String endDt1,String quantityUdp1,String nucUpd1, String addedValueUpd1,
			String makegdUpd1,String LI1tWithOp,String MappedLI1tWithOp) throws ParseException {
		final String planName = orderName + getBasePage().getRandomNumber();
		SalesOrderAdOpsPage salesorderAdops = new SalesOrderAdOpsPage(getPageBrowser());

		new SalesOrderAdOpsPage(getPageBrowser()).createSalesOrderAndGetSalesOrderId(planName, orderAdvertizer,
				primarySalesPerson, orderAgency, salesStage, orderOwner, orderBelongs, currency, rateCard, orderType,
				salesContract);
		Assert.assertTrue(salesorderAdops.isDealDisplayed(planName));
		String soID = salesorderAdops.getOrderId();
		MediaPlanPage mediaPlanPage = new MediaPlanPage(getPageBrowser());
		mediaPlanPage.navigateTomediaPlan();
		mediaPlanPage.addlineitem(productName);
		mediaPlanPage.saveTheDetails();
		Assert.assertTrue(mediaPlanPage.isLineAddedDisplayedOnGrid(productName), "Line is not added from media plan");

		String costMethod = mediaPlanPage.getCostMethod(productName);

		String StartDate = mediaPlanPage.getStartDate(productName);
		String endDate = mediaPlanPage.getEndDate(productName);
		String qunty = mediaPlanPage.getQuantity(productName);
		String nuc = mediaPlanPage.getNetUnitCost(productName);
		String lineID = mediaPlanPage.getLineId(productName);

		Logger.log(costMethod +" , " + StartDate+" , " + endDate +" , "+ qunty+" , " + nuc+" , "+ lineID);
		String LINameUpd1 = productName+" Update";

		mediaPlanPage.addMultipleFieldOnLineItem(productName, lineID, LINameUpd1, CMUpd1,quantityUdp1, nucUpd1,
				startDt1, endDt1, addedValueUpd1, makegdUpd1,LI1tWithOp);
		mediaPlanPage.saveTheDetails();
		
		Page connectNavPage = salesorderAdops.navigateToConnect();
		ConnectDemandOrderPage connectDemandOrderPage = new ConnectDemandOrderPage(connectNavPage);
		connectDemandOrderPage.navigateToDemandTab(connectNavPage);
		Assert.assertTrue((connectDemandOrderPage.createProposal(soID, connectNavPage)), "Proposal is not created");
		connectDemandOrderPage.clickSubmit(connectNavPage);
		
		Assert.assertEquals(MappedLI1tWithOp, connectDemandOrderPage.getTargetingSummary(LINameUpd1),
				"Targeting Summary is not same in demand connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(lineID, connectDemandOrderPage.getLineId(LINameUpd1),
				"Line ID is not same in demand connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertTrue((connectDemandOrderPage.getConnectStatus(connectNavPage)), "status is not order submitted");
		getbrowsercontext().close();
		getPageBrowser().close();
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
		ConnectMappingsPage connectMappingsPage = new ConnectMappingsPage(getPageBrowser());
		Page supplyConnectPage = connectMappingsPage.navigateToConnect();
		ConnectSupplyOrderPage connectSupplyOrderPage = new ConnectSupplyOrderPage(supplyConnectPage);
		connectSupplyOrderPage.navigateToSupplyTab(supplyConnectPage);
		connectSupplyOrderPage.searchProposal(soID);
		connectSupplyOrderPage.openProposal(planName);
		ConnectDemandOrderPage connectDemandOrderPage1 = new ConnectDemandOrderPage(supplyConnectPage);
		
		Assert.assertEquals(costMethod, connectDemandOrderPage1.getCostMethod(LINameUpd1),
				"cost method is not same in supply connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(MappedLI1tWithOp, connectDemandOrderPage1.getTargetingSummary(LINameUpd1),
				"Targeting Summary is not same in supply connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertTrue((connectSupplyOrderPage.getConnectStatus()), "status is not order submitted");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(LINameUpd1),
				"Push is not pushed in supply connect before saveSync '" + LINameUpd1 + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getorderProcessionStatus(AutoConfigConnect.pendingOrderProcessingStatus),
				"Order Processing Status is not pending in supply connect before saveSync");	
		
		connectSupplyOrderPage.clickSaveBtn();
		
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(LINameUpd1),
				"Push is not inprogress in supply connect after saveSync '" + LINameUpd1 + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getorderProcessionStatus(AutoConfigConnect.inprogressProcessingStatus),
				"Order Processing Status is not inprogress in supply connect after saveSync");
		Assert.assertEquals(connectSupplyOrderPage.waitForOrdertoSyncinAOS(),AutoConfigConnect.completeOrderProcessingStatus,
				"Order Processing Status is not complete in supply connect after AOS saveSync'");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(LINameUpd1),
				"Push is not inprogress in supply connect after AOS saveSync '" + LINameUpd1 + "' ");

		String aosDealID = connectSupplyOrderPage.getSupplyOrderID();
		connectMappingsPage.closeSupplyConnect(supplyConnectPage);
		connectMappingsPage.navigateToSales();
		PlannerHeaderPage plannerHeaderPage = new PlannerHeaderPage(getPageBrowser());
		plannerHeaderPage.filterBySingleSelectValue(AutoConfigPlannerHeader.dealIDFilter, aosDealID);
		plannerHeaderPage.verifyDealIdInDealList(aosDealID);
		plannerHeaderPage.verifyPlanNameInDealList(planName);
		plannerHeaderPage.opensearchDeal();
		PlannerDigitalWorkspacePage plannerDigitalWorkspacePage = new PlannerDigitalWorkspacePage(getPageBrowser());
		plannerDigitalWorkspacePage.navigateToWorkSpaceTab();
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(LINameUpd1),"line "+ LINameUpd1+ " is not Displayed in AOS Workspace ");

		Assert.assertEquals(plannerDigitalWorkspacePage.targetSummary(LINameUpd1, MappedLI1tWithOp),true);
		plannerDigitalWorkspacePage.cancelTargetabButton();


	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
