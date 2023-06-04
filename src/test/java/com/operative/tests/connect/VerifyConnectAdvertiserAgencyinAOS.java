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

/**
 * @author akrathi
 *
 */
public class VerifyConnectAdvertiserAgencyinAOS extends BaseTest {
	@BeforeTest
	public void beforeTest() throws InterruptedException {
		launchO1OperativeApplication();
		new LoginPage(getPageBrowser()).loginApplicationO1Operative();

	}

	@Test(dataProviderClass = ConnectDataprovider.class, dataProvider = "verifyAdvertiserAgencyinAOS")
	public void verifyConnectAdvAgcinAOS(String orderName, String orderAdvertizer, String primarySalesPerson,
			String orderAgency, String salesStage, String orderOwner, String orderBelongs, String currency,
			String rateCard, String orderType, String salesContract, String productName) throws ParseException {
		final String planName = orderName + getBasePage().getRandomNumber();
		SalesOrderAdOpsPage salesorderAdops = new SalesOrderAdOpsPage(getPageBrowser());
		// creating Sales Header1
		new SalesOrderAdOpsPage(getPageBrowser()).createSalesOrderAndGetSalesOrderId(planName, orderAdvertizer,
				primarySalesPerson, orderAgency, salesStage, orderOwner, orderBelongs, currency, rateCard, orderType,
				salesContract);
		Assert.assertTrue(salesorderAdops.isDealDisplayed(planName));
		String soID = salesorderAdops.getOrderId();
		String O1AdvertiserDetails = salesorderAdops.getAdvDetailsOfProposal();
		String O1AgencyDetails = salesorderAdops.getAgcDetailsOfProposal();	
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
		Logger.log(costMethod + costMethod1 + StartDate + StartDate1 + endDate + endDate1 + qunty + qunty1 + nuc + nuc1
				+ lineID + lineID1 +O1AdvertiserDetails +O1AgencyDetails);
		Page connectNavPage = salesorderAdops.navigateToConnect();
		ConnectDemandOrderPage connectDemandOrderPage = new ConnectDemandOrderPage(connectNavPage);
		connectDemandOrderPage.navigateToDemandTab(connectNavPage);
		Assert.assertTrue((connectDemandOrderPage.createProposal(soID, connectNavPage)), "Proposal is not created");
		connectDemandOrderPage.clickSubmit(connectNavPage);
		Assert.assertEquals(costMethod, connectDemandOrderPage.getCostMethod(prdName[0]),
				"cost method is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(costMethod1, connectDemandOrderPage.getCostMethod(prdName[1]),
				"cost method is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(StartDate, connectDemandOrderPage.getStartDate(prdName[0]),
				"start date is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(StartDate1, connectDemandOrderPage.getStartDate(prdName[1]),
				"start date is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(endDate, connectDemandOrderPage.getEndDate(prdName[0]),
				"End date is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(endDate1, connectDemandOrderPage.getEndDate(prdName[1]),
				"End date is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(qunty, connectDemandOrderPage.getQuantity(prdName[0]),
				"Quantity is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(qunty1, connectDemandOrderPage.getQuantity(prdName[1]),
				"Quantity is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(nuc, connectDemandOrderPage.getNetUnitCost(prdName[0]),
				"NUC is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(nuc1, connectDemandOrderPage.getNetUnitCost(prdName[1]),
				"NUC is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(lineID, connectDemandOrderPage.getLineId(prdName[0]),
				"Line ID is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(lineID1, connectDemandOrderPage.getLineId(prdName[1]),
				"Line ID is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals( connectDemandOrderPage.getAccountDetails("advertiser"),O1AdvertiserDetails,
				"Adverstiser is matching with O1 and demand Connect");
		Assert.assertEquals(O1AgencyDetails, connectDemandOrderPage.getAccountDetails("agency"),
				"Agency is matching with O1 and demand Connect");
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
		Assert.assertEquals(costMethod, connectDemandOrderPage1.getCostMethod(prdName[0]),
				"cost method is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(costMethod1, connectDemandOrderPage1.getCostMethod(prdName[1]),
				"cost method is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(StartDate, connectDemandOrderPage1.getStartDate(prdName[0]),
				"start date is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(StartDate1, connectDemandOrderPage1.getStartDate(prdName[1]),
				"start date is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(endDate, connectDemandOrderPage1.getEndDate(prdName[0]),
				"End date is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(endDate1, connectDemandOrderPage1.getEndDate(prdName[1]),
				"End date is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(qunty, connectDemandOrderPage1.getQuantity(prdName[0]),
				"Quantity is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(qunty1, connectDemandOrderPage1.getQuantity(prdName[1]),
				"Quantity is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(nuc, connectDemandOrderPage1.getNetUnitCost(prdName[0]),
				"NUC is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(nuc1, connectDemandOrderPage1.getNetUnitCost(prdName[1]),
				"NUC is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(prdName[0], lineID),
				"Line ID is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(prdName[1], lineID1),
				"Line ID is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(2, connectSupplyOrderPage.getNumberofLine(),
				"Number of lines are correct in supply connect");
		Assert.assertEquals(O1AdvertiserDetails, connectSupplyOrderPage.getAccountDetails("advertiser"),
				"Adverstiser is matching with O1 and supply Connect");
		Assert.assertEquals(O1AgencyDetails, connectSupplyOrderPage.getAccountDetails("agency"),
				"Agency is matching with O1 and supply Connect");
		Assert.assertTrue((connectSupplyOrderPage.getConnectStatus()), "status is not order submitted");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[0]),
				"Push is not pushed in supply connect before saveSync '" + prdName[0] + "' ");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[1]),
				"Push is not pushed in supply connect before saveSync '" + prdName[0] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getorderProcessionStatus(AutoConfigConnect.pendingOrderProcessingStatus),
				"Order Processing Status is not pending in supply connect before saveSync");	
		connectSupplyOrderPage.clickSaveBtn();
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(prdName[0]),
				"Push is not inprogress in supply connect after saveSync '" + prdName[0] + "' ");
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(prdName[1]),
				"Push is not inprogress in supply connect after saveSync '" + prdName[0] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getorderProcessionStatus(AutoConfigConnect.inprogressProcessingStatus),
				"Order Processing Status is not inprogress in supply connect after saveSync");
		Assert.assertEquals(connectSupplyOrderPage.waitForOrdertoSyncinAOS(),AutoConfigConnect.completeOrderProcessingStatus,
				"Order Processing Status is not complete in supply connect after AOS saveSync'");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[0]),
				"Push is not inprogress in supply connect after AOS saveSync '" + prdName[0] + "' ");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[1]),
				"Push is not inprogress in supply connect after AOS saveSync '" + prdName[0] + "' ");
		String aosDealID = connectSupplyOrderPage.getSupplyOrderID();
		connectMappingsPage.closeSupplyConnect(supplyConnectPage);
		connectMappingsPage.navigateToSales();
		PlannerHeaderPage plannerHeaderPage = new PlannerHeaderPage(getPageBrowser());
		plannerHeaderPage.clickApplyOrClearIfenabled(AutoConfigPlannerHeader.clear);
		plannerHeaderPage.filterBySingleSelectValue(AutoConfigPlannerHeader.dealIDFilter, aosDealID);
		plannerHeaderPage.verifyDealIdInDealList(aosDealID);
		plannerHeaderPage.verifyPlanNameInDealList(planName);
		plannerHeaderPage.opensearchDeal();
		plannerHeaderPage.clickAdvertiserdotsPopUp();
		//verify selected value for adv from pop up
		Assert.assertTrue(plannerHeaderPage.verifyAdvertiserValue(O1AdvertiserDetails),"Advertiser details in Connect and AOS is not same");
		plannerHeaderPage.clickCancelButton(); 
		plannerHeaderPage.clickAgecnyDots();
		Assert.assertTrue(plannerHeaderPage.verifyBuyingAgencyValue(O1AgencyDetails),"Agecny details in Connect and AOS is not same");


	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
