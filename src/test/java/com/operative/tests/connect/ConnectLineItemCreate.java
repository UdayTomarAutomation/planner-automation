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
public class ConnectLineItemCreate extends BaseTest {
	@BeforeTest
	public void beforeTest() throws InterruptedException {
		launchO1OperativeApplication();
		new LoginPage(getPageBrowser()).loginApplicationO1Operative();

	}

	@Test(dataProviderClass = ConnectDataprovider.class, dataProvider = "DealCreation")
	public void verifyLineItemCreate(String orderName, String orderAdvertizer, String primarySalesPerson,
			String orderAgency, String salesStage, String orderOwner, String orderBelongs, String currency,
			String rateCard, String orderType, String salesContract, String productName, String CMUpd1,
			String startDt1, String endDt1,String quantityUdp1,String nucUpd1, String addedValueUpd1,
			String makegdUpd1, String CMUpd2, String startDt2, String endDt2,String quantityUdp2,
			String nucUpd2, String addedValueUpd2, String makegdUpd2) throws ParseException {
		final String planName = orderName + getBasePage().getRandomNumber();
		SalesOrderAdOpsPage salesorderAdops = new SalesOrderAdOpsPage(getPageBrowser());
		// creating Sales Header1
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
		String prdName[] = productName.split(";");
		String costMethod = mediaPlanPage.getCostMethod(prdName[0]);
		String costMethod1 = mediaPlanPage.getCostMethod(prdName[1]);
		String StartDate = mediaPlanPage.getStartDate(prdName[0]);
		String StartDate1 = mediaPlanPage.getStartDate(prdName[1]);
		String endDate = mediaPlanPage.getEndDate(prdName[0]);
		String endDate1 = mediaPlanPage.getEndDate(prdName[1]);
		String qunty = mediaPlanPage.getQuantity(prdName[0]);
		String qunty1 = mediaPlanPage.getQuantity(prdName[1]);
		String nuc = mediaPlanPage.getNetUnitCost(prdName[0]);
		String nuc1 = mediaPlanPage.getNetUnitCost(prdName[1]);
		String lineID = mediaPlanPage.getLineId(prdName[0]);
		String lineID1 = mediaPlanPage.getLineId(prdName[1]);
		Logger.log(costMethod +" , "+ costMethod1+" , " + StartDate+" , " + StartDate1+" , " + endDate
				+" , " + endDate1+" , "+ qunty+" , " + qunty1+" , " + nuc+" , " + nuc1+" , "+ lineID+" , " + lineID1);
		String LINameUpd1 = prdName[0]+" Update";
		String LINameUpd2 = prdName[1]+" Update";
		mediaPlanPage.addMultipleFieldOnLineItem(prdName[0], lineID, LINameUpd1, CMUpd1,quantityUdp1, nucUpd1,
				startDt1, endDt1, addedValueUpd1, makegdUpd1,"");
		mediaPlanPage.addMultipleFieldOnLineItem(prdName[1], lineID1, LINameUpd2, CMUpd2,quantityUdp2, nucUpd2,
				startDt2, endDt2, addedValueUpd2, makegdUpd2,"");
		mediaPlanPage.saveTheDetails();
		
		Page connectNavPage = salesorderAdops.navigateToConnect();
		ConnectDemandOrderPage connectDemandOrderPage = new ConnectDemandOrderPage(connectNavPage);
		connectDemandOrderPage.navigateToDemandTab(connectNavPage);
		Assert.assertTrue((connectDemandOrderPage.createProposal(soID, connectNavPage)), "Proposal is not created");
		connectDemandOrderPage.clickSubmit(connectNavPage);
		Assert.assertEquals(costMethod, connectDemandOrderPage.getCostMethod(LINameUpd1),
				"cost method is not same in demand connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(CMUpd2, connectDemandOrderPage.getCostMethod(LINameUpd2),
				"cost method is not same in demand connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(startDt1, connectDemandOrderPage.getStartDate(LINameUpd1),
				"start date is not same in demand connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(startDt2, connectDemandOrderPage.getStartDate(LINameUpd2),
				"start date is not same in demand connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(endDt1, connectDemandOrderPage.getEndDate(LINameUpd1),
				"End date is not same in demand connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(endDt2, connectDemandOrderPage.getEndDate(LINameUpd2),
				"End date is not same in demand connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(quantityUdp1, connectDemandOrderPage.getQuantity(LINameUpd1),
				"Quantity is not same in demand connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(quantityUdp2, connectDemandOrderPage.getQuantity(LINameUpd2),
				"Quantity is not same in demand connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(nucUpd1, connectDemandOrderPage.getNetUnitCost(LINameUpd1),
				"NUC is not same in demand connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(nucUpd2, connectDemandOrderPage.getNetUnitCost(LINameUpd2),
				"NUC is not same in demand connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(lineID, connectDemandOrderPage.getLineId(LINameUpd1),
				"Line ID is not same in demand connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(lineID1, connectDemandOrderPage.getLineId(LINameUpd2),
				"Line ID is not same in demand connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(2, connectDemandOrderPage.getNumberofLine(),
				"Number of lines are correct in demand connect");
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
		Assert.assertEquals(CMUpd2, connectDemandOrderPage1.getCostMethod(LINameUpd2),
				"cost method is not same in supply connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(startDt1, connectDemandOrderPage1.getStartDate(LINameUpd1),
				"start date is not same in supply connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(startDt2, connectDemandOrderPage1.getStartDate(LINameUpd2),
				"start date is not same in supply connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(endDt1, connectDemandOrderPage1.getEndDate(LINameUpd1),
				"End date is not same in supply connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(endDt2, connectDemandOrderPage1.getEndDate(LINameUpd2),
				"End date is not same in supply connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(quantityUdp1, connectDemandOrderPage1.getQuantity(LINameUpd1),
				"Quantity is not same in supply connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(quantityUdp2, connectDemandOrderPage1.getQuantity(LINameUpd2),
				"Quantity is not same in supply connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(nucUpd1, connectDemandOrderPage1.getNetUnitCost(LINameUpd1),
				"NUC is not same in supply connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertEquals(nucUpd2, connectDemandOrderPage1.getNetUnitCost(LINameUpd2),
				"NUC is not same in supply connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(LINameUpd1, lineID),
				"Line ID is not same in supply connect and O1 '" + LINameUpd1 + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(LINameUpd2, lineID1),
				"Line ID is not same in supply connect and O1 '" + LINameUpd2 + "' ");
		Assert.assertEquals(2, connectSupplyOrderPage.getNumberofLine(),
				"Number of lines are correct in demand connect");
		Assert.assertTrue((connectSupplyOrderPage.getConnectStatus()), "status is not order submitted");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(LINameUpd1),
				"Push is not pushed in supply connect before saveSync '" + LINameUpd1 + "' ");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(LINameUpd2),
				"Push is not pushed in supply connect before saveSync '" + LINameUpd1 + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getorderProcessionStatus(AutoConfigConnect.pendingOrderProcessingStatus),
				"Order Processing Status is not pending in supply connect before saveSync");	
		connectSupplyOrderPage.clickSaveBtn();
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(LINameUpd1),
				"Push is not inprogress in supply connect after saveSync '" + LINameUpd1 + "' ");
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(LINameUpd2),
				"Push is not inprogress in supply connect after saveSync '" + LINameUpd1 + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getorderProcessionStatus(AutoConfigConnect.inprogressProcessingStatus),
				"Order Processing Status is not inprogress in supply connect after saveSync");
		Assert.assertEquals(connectSupplyOrderPage.waitForOrdertoSyncinAOS(),AutoConfigConnect.completeOrderProcessingStatus,
				"Order Processing Status is not complete in supply connect after AOS saveSync'");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(LINameUpd1),
				"Push is not inprogress in supply connect after AOS saveSync '" + LINameUpd1 + "' ");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(LINameUpd2),
				"Push is not inprogress in supply connect after AOS saveSync '" + LINameUpd1 + "' ");
		String aosDealID = connectSupplyOrderPage.getSupplyOrderID();
		connectMappingsPage.closeSupplyConnect(supplyConnectPage);
		connectMappingsPage.navigateToSales();
		PlannerHeaderPage plannerHeaderPage = new PlannerHeaderPage(getPageBrowser());
		plannerHeaderPage.clickApplyOrClearIfenabled(AutoConfigPlannerHeader.clear);
		plannerHeaderPage.filterBySingleSelectValue(AutoConfigPlannerHeader.dealIDFilter, aosDealID);
		plannerHeaderPage.verifyDealIdInDealList(aosDealID);
		plannerHeaderPage.verifyPlanNameInDealList(planName);
		plannerHeaderPage.opensearchDeal();
		PlannerDigitalWorkspacePage plannerDigitalWorkspacePage = new PlannerDigitalWorkspacePage(getPageBrowser());
		plannerDigitalWorkspacePage.navigateToWorkSpaceTab();
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(LINameUpd1),"line "+ LINameUpd1+ " is not Displayed in AOS Workspace ");
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(LINameUpd2),"line "+ LINameUpd2+ " is not Displayed in AOS Workspace ");
		Assert.assertEquals(plannerDigitalWorkspacePage.getTotalNOofLine(),2,"number of lines in workspace and connect is not same");
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd1, AutoConfigDigitalPlannerWorkSpace.quantityColId),quantityUdp1);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd2, AutoConfigDigitalPlannerWorkSpace.quantityColId),quantityUdp2);
		Assert.assertTrue(("$"+nucUpd1).contains(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd1, AutoConfigDigitalPlannerWorkSpace.netUnitCostColId)));
		Assert.assertTrue(("$"+nucUpd2).contains(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd2, AutoConfigDigitalPlannerWorkSpace.netUnitCostColId)));
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd1, AutoConfigDigitalPlannerWorkSpace.costMethodIdColId),costMethod);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd2, AutoConfigDigitalPlannerWorkSpace.costMethodIdColId),CMUpd2);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd1, AutoConfigDigitalPlannerWorkSpace.startDateColId),(startDt1));
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd2, AutoConfigDigitalPlannerWorkSpace.startDateColId),(startDt2));
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd1, AutoConfigDigitalPlannerWorkSpace.endDateColId),(endDt1));
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(LINameUpd2, AutoConfigDigitalPlannerWorkSpace.endDateColId),(endDt2));
		


	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
