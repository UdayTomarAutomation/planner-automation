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
public class ConnectSalesOrderCreationwithADU_MG_DifferentCostMethod_NoAgency extends BaseTest {
	@BeforeTest
	public void beforeTest() throws InterruptedException {
		launchO1OperativeApplication();
		new LoginPage(getPageBrowser()).loginApplicationO1Operative();

	}

	@Test(dataProviderClass = ConnectDataprovider.class, dataProvider = "AddLineswithADU_MG_DifferentCostMethodAndHeaderwithNoagency")
	public void verifyDealCreationWithDifferentCostMethodMGADU(String orderName, String orderAdvertizer, String primarySalesPerson,
			String orderAgency, String salesStage, String orderOwner, String orderBelongs, String currency,
			String rateCard, String orderType, String salesContract, String productName,String CostMethod1 ,String	StartDate1 ,String	EndDate1 ,String	Quantity1 ,String	NetUnitCost1 ,String AddedValue1 ,String MakeGood1 ,String	CostMethod2 ,String	StartDate2 ,String	EndDate2 ,String	Quantity2 ,String	NetUnitCost2 ,String	AddedValue2 ,String	MakeGood2 ,String	CostMethod3 ,String	StartDate3 ,String	EndDate3,String Quantity3 ,String	NetUnitCost3,String	AddedValue3 ,String	MakeGood3 ,String CostMethod4,String StartDate4	
			,String EndDate4 ,String	Quantity4 ,String	NetUnitCost4 ,String	AddedValue4	,String MakeGood4,String CostMethod5 ,String StartDate5 ,String	EndDate5 ,String	Quantity5 ,String	NetUnitCost5 ,String	AddedValue5 ,String	MakeGood5	,String CostMethod6 ,String	StartDate6 ,String	EndDate6 ,String	Quantity6	,String NetUnitCost6 ,String AddedValue6 ,String	MakeGood6 ,String	CostMethod7	,String StartDate7	,String EndDate7 ,String	Quantity7,String	NetUnitCost7 ,String	AddedValue7 ,String	MakeGood7) throws ParseException {
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
		String lineID = mediaPlanPage.getLineId(prdName[0]);
		String lineID1 = mediaPlanPage.getLineId(prdName[1]);
		String lineID2 = mediaPlanPage.getLineId(prdName[2]);
		String lineID3 = mediaPlanPage.getLineId(prdName[3]);
		String lineID4 = mediaPlanPage.getLineId(prdName[4]);
		String lineID5 = mediaPlanPage.getLineId(prdName[5]);
		String lineID6 =  mediaPlanPage.getLineId(prdName[6]);
		
		mediaPlanPage.addMultipleFieldOnLineItem(prdName[0], lineID, prdName[0], CostMethod1,Quantity1, NetUnitCost1,
				StartDate1, EndDate1, AddedValue1, MakeGood1,"");
		mediaPlanPage.addMultipleFieldOnLineItem(prdName[1], lineID1, prdName[1], CostMethod2,Quantity2, NetUnitCost2,
				StartDate2, EndDate2, AddedValue2, MakeGood2,"");
		mediaPlanPage.addMultipleFieldOnLineItem(prdName[2], lineID2, prdName[2], CostMethod3,Quantity3, NetUnitCost3,
				StartDate3, EndDate3, AddedValue3, MakeGood3,"");
		mediaPlanPage.addMultipleFieldOnLineItem(prdName[3], lineID3, prdName[3], CostMethod4,Quantity4, NetUnitCost4,
				StartDate4, EndDate4, AddedValue4, MakeGood4,"");
		mediaPlanPage.addMultipleFieldOnLineItem(prdName[4], lineID4, prdName[4], CostMethod5,Quantity5, NetUnitCost5,
				StartDate5, EndDate5, AddedValue5, MakeGood5,"");
		mediaPlanPage.addMultipleFieldOnLineItem(prdName[5], lineID5, prdName[5], CostMethod6,Quantity6, NetUnitCost6,
				StartDate6, EndDate6, AddedValue6, MakeGood6,"");
		mediaPlanPage.addMultipleFieldOnLineItem(prdName[6], lineID6, prdName[6], CostMethod7,Quantity7, NetUnitCost7,
				StartDate7, EndDate7, AddedValue7, MakeGood7,"");
		
		mediaPlanPage.saveTheDetails();
		
		
		String costMethod = mediaPlanPage.getCostMethod(prdName[0]);
		String costMethod1 = mediaPlanPage.getCostMethod(prdName[1]);
		String costMethod2 = mediaPlanPage.getCostMethod(prdName[2]);
		String costMethod3 = mediaPlanPage.getCostMethod(prdName[3]);
		String costMethod4 = mediaPlanPage.getCostMethod(prdName[4]);
		String costMethod5 = mediaPlanPage.getCostMethod(prdName[5]);
		String costMethod6 = mediaPlanPage.getCostMethod(prdName[6]);
		String startDate = mediaPlanPage.getStartDate(prdName[0]);
		String startDate1 = mediaPlanPage.getStartDate(prdName[1]);
		String startDate2 = mediaPlanPage.getStartDate(prdName[2]);
		String startDate3 = mediaPlanPage.getStartDate(prdName[3]);
		String startDate4 = mediaPlanPage.getStartDate(prdName[4]);
		String startDate5 = mediaPlanPage.getStartDate(prdName[5]);
		String startDate6 = mediaPlanPage.getStartDate(prdName[6]);
		String endDate = mediaPlanPage.getEndDate(prdName[0]);
		String endDate1 = mediaPlanPage.getEndDate(prdName[1]);
		String endDate2 = mediaPlanPage.getEndDate(prdName[2]);
		String endDate3 = mediaPlanPage.getEndDate(prdName[3]);
		String endDate4 = mediaPlanPage.getEndDate(prdName[4]);
		String endDate5 = mediaPlanPage.getEndDate(prdName[5]);
		String endDate6 = mediaPlanPage.getEndDate(prdName[6]);
		String qunty = mediaPlanPage.getQuantity(prdName[0]);
		String qunty1 = mediaPlanPage.getQuantity(prdName[1]);
		String qunty2 = mediaPlanPage.getQuantity(prdName[2]);
		String qunty3 = mediaPlanPage.getQuantity(prdName[3]);
		String qunty4 = mediaPlanPage.getQuantity(prdName[4]);
		String qunty5 = mediaPlanPage.getQuantity(prdName[5]);
		String qunty6 = mediaPlanPage.getQuantity(prdName[6]);
		String nuc = mediaPlanPage.getNetUnitCost(prdName[0]);
		String nuc1 = mediaPlanPage.getNetUnitCost(prdName[1]);
		String nuc2 = mediaPlanPage.getNetUnitCost(prdName[2]);
		String nuc3 = mediaPlanPage.getNetUnitCost(prdName[3]);
		String nuc4 = mediaPlanPage.getNetUnitCost(prdName[4]);
		String nuc5 = mediaPlanPage.getNetUnitCost(prdName[5]);
		String nuc6 = mediaPlanPage.getNetUnitCost(prdName[6]);
		String netCost = mediaPlanPage.getNetCost(prdName[0]);
		String netCost1 = mediaPlanPage.getNetCost(prdName[1]);
		String netCost2 = mediaPlanPage.getNetCost(prdName[2]);
		String netCost3 = mediaPlanPage.getNetCost(prdName[3]);
		String netCost4 = mediaPlanPage.getNetCost(prdName[4]);
		String netCost5 = mediaPlanPage.getNetCost(prdName[5]);
		String netCost6 = mediaPlanPage.getNetCost(prdName[6]);
		String totalNetCost = mediaPlanPage.getTotalNetCostOfProposal();
		//Demand Connect
		Page connectNavPage = salesorderAdops.navigateToConnect();
		ConnectDemandOrderPage connectDemandOrderPage = new ConnectDemandOrderPage(connectNavPage);
		connectDemandOrderPage.navigateToDemandTab(connectNavPage);
		Assert.assertTrue((connectDemandOrderPage.createProposal(soID, connectNavPage)), "Proposal is not created");
		connectDemandOrderPage.clickSubmit(connectNavPage);
		Assert.assertEquals(costMethod, connectDemandOrderPage.getCostMethod(prdName[0]),
				"cost method is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(costMethod1, connectDemandOrderPage.getCostMethod(prdName[1]),
				"cost method is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(costMethod2, connectDemandOrderPage.getCostMethod(prdName[2]),
				"cost method is not same in demand connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(costMethod3, connectDemandOrderPage.getCostMethod(prdName[3]),
				"cost method is not same in demand connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(costMethod4, connectDemandOrderPage.getCostMethod(prdName[4]),
				"cost method is not same in demand connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(costMethod5, connectDemandOrderPage.getCostMethod(prdName[5]),
				"cost method is not same in demand connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(costMethod6, connectDemandOrderPage.getCostMethod(prdName[6]),
				"cost method is not same in demand connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals(startDate, connectDemandOrderPage.getStartDate(prdName[0]),
				"start date is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(startDate1, connectDemandOrderPage.getStartDate(prdName[1]),
				"start date is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(startDate2, connectDemandOrderPage.getStartDate(prdName[2]),
				"start date is not same in demand connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(startDate3, connectDemandOrderPage.getStartDate(prdName[3]),
				"start date is not same in demand connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(startDate4, connectDemandOrderPage.getStartDate(prdName[4]),
				"start date is not same in demand connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(startDate5, connectDemandOrderPage.getStartDate(prdName[5]),
				"start date is not same in demand connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(startDate6, connectDemandOrderPage.getStartDate(prdName[6]),
				"start date is not same in demand connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals(endDate, connectDemandOrderPage.getEndDate(prdName[0]),
				"End date is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(endDate1, connectDemandOrderPage.getEndDate(prdName[1]),
				"End date is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(endDate2, connectDemandOrderPage.getEndDate(prdName[2]),
				"End date is not same in demand connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(endDate3, connectDemandOrderPage.getEndDate(prdName[3]),
				"End date is not same in demand connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(endDate4, connectDemandOrderPage.getEndDate(prdName[4]),
				"End date is not same in demand connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(endDate5, connectDemandOrderPage.getEndDate(prdName[5]),
				"End date is not same in demand connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(endDate6, connectDemandOrderPage.getEndDate(prdName[6]),
				"End date is not same in demand connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals(qunty, connectDemandOrderPage.getQuantity(prdName[0]),
				"Quantity is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(qunty1, connectDemandOrderPage.getQuantity(prdName[1]),
				"Quantity is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(qunty2, connectDemandOrderPage.getQuantity(prdName[2]),
				"Quantity is not same in demand connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(qunty3, connectDemandOrderPage.getQuantity(prdName[3]),
				"Quantity is not same in demand connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(qunty4, connectDemandOrderPage.getQuantity(prdName[4]),
				"Quantity is not same in demand connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(qunty5, connectDemandOrderPage.getQuantity(prdName[5]),
				"Quantity is not same in demand connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(qunty6, connectDemandOrderPage.getQuantity(prdName[6]),
				"Quantity is not same in demand connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals(nuc, connectDemandOrderPage.getNetUnitCost(prdName[0]),
				"NUC is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(nuc1, connectDemandOrderPage.getNetUnitCost(prdName[1]),
				"NUC is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(nuc2, connectDemandOrderPage.getNetUnitCost(prdName[2]),
				"NUC is not same in demand connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(nuc3, connectDemandOrderPage.getNetUnitCost(prdName[3]),
				"NUC is not same in demand connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(nuc4, connectDemandOrderPage.getNetUnitCost(prdName[4]),
				"NUC is not same in demand connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(nuc5, connectDemandOrderPage.getNetUnitCost(prdName[5]),
				"NUC is not same in demand connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(nuc6, connectDemandOrderPage.getNetUnitCost(prdName[6]),
				"NUC is not same in demand connect and O1 '" + prdName[6] + "' ");	
		Assert.assertEquals("$"+totalNetCost, connectDemandOrderPage.getTotalNetCostOfProposal(),
				"Total Net cost proposal is not same in demand connect and O1");	
		Assert.assertTrue(connectDemandOrderPage.isCheckboxChecked(AutoConfigConnect.makegoodColId, prdName[0]), "Makegood is not checked for>>>" +prdName[0]  );
		Assert.assertTrue(connectDemandOrderPage.isCheckboxChecked(AutoConfigConnect.addedvalueColId, prdName[4]), "Makegood is not checked for>>>" +prdName[4]  );
		Assert.assertEquals(netCost, connectDemandOrderPage.getNetCost(prdName[0]),
				"Net Cost is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(netCost1, connectDemandOrderPage.getNetCost(prdName[1]),
				"Net Cost is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(netCost2, connectDemandOrderPage.getNetCost(prdName[2]),
				"Net Cost is not same in demand connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(netCost3, connectDemandOrderPage.getNetCost(prdName[3]),
				"Net Cost is not same in demand connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(netCost4, connectDemandOrderPage.getNetCost(prdName[4]),
				"Net Cost is not same in demand connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(netCost5, connectDemandOrderPage.getNetCost(prdName[5]),
				"Net Cost is not same in demand connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(netCost6, connectDemandOrderPage.getNetCost(prdName[6]),
				"Net Cost is not same in demand connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals(lineID, connectDemandOrderPage.getLineId(prdName[0]),
				"Line ID is not same in demand connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(lineID1, connectDemandOrderPage.getLineId(prdName[1]),
				"Line ID is not same in demand connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(lineID2, connectDemandOrderPage.getLineId(prdName[2]),
				"Line ID is not same in demand connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(lineID3, connectDemandOrderPage.getLineId(prdName[3]),
				"Line ID is not same in demand connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(lineID4, connectDemandOrderPage.getLineId(prdName[4]),
				"Line ID is not same in demand connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(lineID5, connectDemandOrderPage.getLineId(prdName[5]),
				"Line ID is not same in demand connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(lineID6, connectDemandOrderPage.getLineId(prdName[6]),
				"Line ID is not same in demand connect and O1 '" + prdName[6] + "' ");	
		Assert.assertEquals(7, connectDemandOrderPage.getNumberofLine(),
				"Number of lines are correct in demand connect");
		Assert.assertTrue((connectDemandOrderPage.getConnectStatus(connectNavPage)), "status is not order submitted");
		getbrowsercontext().close();
		getPageBrowser().close();
		//Supply AOS
		launchFoxApplication();
		new LoginPage(getPageBrowser()).loginApplication();
		ConnectMappingsPage connectMappingsPage = new ConnectMappingsPage(getPageBrowser());
		//Supply Connect 
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
		Assert.assertEquals(costMethod2, connectDemandOrderPage1.getCostMethod(prdName[2]),
				"cost method is not same in supply connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(costMethod3, connectDemandOrderPage1.getCostMethod(prdName[3]),
				"cost method is not same in supply connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(costMethod4, connectDemandOrderPage1.getCostMethod(prdName[4]),
				"cost method is not same in supply connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(costMethod5, connectDemandOrderPage1.getCostMethod(prdName[5]),
				"cost method is not same in supply connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(costMethod6, connectDemandOrderPage1.getCostMethod(prdName[6]),
				"cost method is not same in supply connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals(startDate, connectDemandOrderPage1.getStartDate(prdName[0]),
				"start date is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(startDate1, connectDemandOrderPage1.getStartDate(prdName[1]),
				"start date is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(startDate2, connectDemandOrderPage1.getStartDate(prdName[2]),
				"start date is not same in supply connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(startDate3, connectDemandOrderPage1.getStartDate(prdName[3]),
				"start date is not same in supply connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(startDate4, connectDemandOrderPage1.getStartDate(prdName[4]),
				"start date is not same in supply connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(startDate5, connectDemandOrderPage1.getStartDate(prdName[5]),
				"start date is not same in supply connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(startDate6, connectDemandOrderPage1.getStartDate(prdName[6]),
				"start date is not same in supply connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals(endDate, connectDemandOrderPage1.getEndDate(prdName[0]),
				"End date is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(endDate1, connectDemandOrderPage1.getEndDate(prdName[1]),
				"End date is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(endDate2, connectDemandOrderPage1.getEndDate(prdName[2]),
				"End date is not same in supply connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(endDate3, connectDemandOrderPage1.getEndDate(prdName[3]),
				"End date is not same in supply connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(endDate4, connectDemandOrderPage1.getEndDate(prdName[4]),
				"End date is not same in supply connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(endDate5, connectDemandOrderPage1.getEndDate(prdName[5]),
				"End date is not same in supply connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(endDate6, connectDemandOrderPage1.getEndDate(prdName[6]),
				"End date is not same in supply connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals(qunty, connectDemandOrderPage1.getQuantity(prdName[0]),
				"Quantity is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(qunty1, connectDemandOrderPage1.getQuantity(prdName[1]),
				"Quantity is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(qunty2, connectDemandOrderPage1.getQuantity(prdName[2]),
				"Quantity is not same in supply connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(qunty3, connectDemandOrderPage1.getQuantity(prdName[3]),
				"Quantity is not same in supply connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(qunty4, connectDemandOrderPage1.getQuantity(prdName[4]),
				"Quantity is not same in supply connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(qunty5, connectDemandOrderPage1.getQuantity(prdName[5]),
				"Quantity is not same in supply connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(qunty6, connectDemandOrderPage1.getQuantity(prdName[6]),
				"Quantity is not same in supply connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals(nuc, connectDemandOrderPage1.getNetUnitCost(prdName[0]),
				"NUC is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(nuc1, connectDemandOrderPage1.getNetUnitCost(prdName[1]),
				"NUC is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(nuc2, connectDemandOrderPage1.getNetUnitCost(prdName[2]),
				"NUC is not same in supply connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(nuc3, connectDemandOrderPage1.getNetUnitCost(prdName[3]),
				"NUC is not same in supply connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(nuc4, connectDemandOrderPage1.getNetUnitCost(prdName[4]),
				"NUC is not same in supply connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(nuc5, connectDemandOrderPage1.getNetUnitCost(prdName[5]),
				"NUC is not same in supply connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(nuc6, connectDemandOrderPage1.getNetUnitCost(prdName[6]),
				"NUC is not same in supply connect and O1 '" + prdName[6] + "' ");
		
		Assert.assertEquals(netCost, connectDemandOrderPage1.getNetCost(prdName[0]),
				"netCost is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertEquals(netCost1, connectDemandOrderPage1.getNetCost(prdName[1]),
				"netCost is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertEquals(netCost2, connectDemandOrderPage1.getNetCost(prdName[2]),
				"netCost is not same in supply connect and O1 '" + prdName[2] + "' ");
		Assert.assertEquals(netCost3, connectDemandOrderPage1.getNetCost(prdName[3]),
				"netCost is not same in supply connect and O1 '" + prdName[3] + "' ");
		Assert.assertEquals(netCost4, connectDemandOrderPage1.getNetCost(prdName[4]),
				"netCost is not same in supply connect and O1 '" + prdName[4] + "' ");
		Assert.assertEquals(netCost5, connectDemandOrderPage1.getNetCost(prdName[5]),
				"netCost is not same in supply connect and O1 '" + prdName[5] + "' ");
		Assert.assertEquals(netCost6, connectDemandOrderPage1.getNetCost(prdName[6]),
				"netCost is not same in supply connect and O1 '" + prdName[6] + "' ");
		Assert.assertEquals("$"+totalNetCost, connectDemandOrderPage1.getTotalNetCostOfProposal(),
				"Total Net Cost of proposal is not same in supply connect and O1 ");
		Assert.assertTrue(connectDemandOrderPage1.isCheckboxChecked(AutoConfigConnect.makegoodColId, prdName[0]), "Makegood is not checked for>>>" +prdName[0]+ "in supply side" );
		Assert.assertTrue(connectDemandOrderPage1.isCheckboxChecked(AutoConfigConnect.addedvalueColId, prdName[4]), "AddedValue is not checked for>>>" +prdName[4]+ "in supply side"  );
		Assert.assertTrue(connectSupplyOrderPage.getLineId(prdName[0], lineID),
				"Line ID is not same in supply connect and O1 '" + prdName[0] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(prdName[1], lineID1),
				"Line ID is not same in supply connect and O1 '" + prdName[1] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(prdName[2], lineID2),
				"Line ID is not same in supply connect and O1 '" + prdName[2] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(prdName[3], lineID3),
				"Line ID is not same in supply connect and O1 '" + prdName[3] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(prdName[4], lineID4),
				"Line ID is not same in supply connect and O1 '" + prdName[4] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(prdName[5], lineID5),
				"Line ID is not same in supply connect and O1 '" + prdName[5] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getLineId(prdName[6], lineID6),
				"Line ID is not same in supply connect and O1 '" + prdName[6] + "' ");		
		Assert.assertEquals(7, connectSupplyOrderPage.getNumberofLine(),
				"Number of lines are correct in demand connect");
		Assert.assertTrue((connectSupplyOrderPage.getConnectStatus()), "status is not order submitted");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[0]),
				"Push is not pushed in supply connect before saveSync '" + prdName[0] + "' ");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[1]),
				"Push is not pushed in supply connect before saveSync '" + prdName[1] + "' ");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[2]),
				"Push is not pushed in supply connect before saveSync '" + prdName[2] + "' ");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[3]),
				"Push is not pushed in supply connect before saveSync '" + prdName[3] + "' ");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[4]),
				"Push is not pushed in supply connect before saveSync '" + prdName[4] + "' ");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[5]),
				"Push is not pushed in supply connect before saveSync '" + prdName[5] + "' ");
		Assert.assertEquals(AutoConfigConnect.notPushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[6]),
				"Push is not pushed in supply connect before saveSync '" + prdName[6] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getorderProcessionStatus(AutoConfigConnect.pendingOrderProcessingStatus),
				"Order Processing Status is not pending in supply connect before saveSync");	
		connectSupplyOrderPage.clickSaveBtn();
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(prdName[0]),
				"Push is not inprogress in supply connect after saveSync '" + prdName[0] + "' ");
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(prdName[1]),
				"Push is not inprogress in supply connect after saveSync '" + prdName[1] + "' ");
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(prdName[2]),
				"Push is not inprogress in supply connect after saveSync '" + prdName[2] + "' ");
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(prdName[3]),
				"Push is not inprogress in supply connect after saveSync '" + prdName[3] + "' ");
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(prdName[4]),
				"Push is not inprogress in supply connect after saveSync '" + prdName[4] + "' ");
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(prdName[5]),
				"Push is not inprogress in supply connect after saveSync '" + prdName[5] + "' ");
		Assert.assertEquals(AutoConfigConnect.inprogressLineStatus, connectSupplyOrderPage.getPushStatus(prdName[6]),
				"Push is not inprogress in supply connect after saveSync '" + prdName[6] + "' ");
		Assert.assertTrue(connectSupplyOrderPage.getorderProcessionStatus(AutoConfigConnect.inprogressProcessingStatus),
				"Order Processing Status is not inprogress in supply connect after saveSync");
		Assert.assertEquals(connectSupplyOrderPage.waitForOrdertoSyncinAOS(),AutoConfigConnect.completeOrderProcessingStatus,
				"Order Processing Status is not complete in supply connect after AOS saveSync'");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[0]),
				"Push is not inprogress in supply connect after AOS saveSync '" + prdName[0] + "' ");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[1]),
				"Push is not inprogress in supply connect after AOS saveSync '" + prdName[1] + "' ");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[2]),
				"Push is not inprogress in supply connect after AOS saveSync '" + prdName[2] + "' ");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[3]),
				"Push is not inprogress in supply connect after AOS saveSync '" + prdName[3] + "' ");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[4]),
				"Push is not inprogress in supply connect after AOS saveSync '" + prdName[4] + "' ");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[5]),
				"Push is not inprogress in supply connect after AOS saveSync '" + prdName[5] + "' ");
		Assert.assertEquals(AutoConfigConnect.completePushedLineStatus, connectSupplyOrderPage.getPushStatus(prdName[5]),
				"Push is not inprogress in supply connect after AOS saveSync '" + prdName[5] + "' ");		
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
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(prdName[0]),"line "+ prdName[0]+ " is not Displayed in AOS Workspace ");
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(prdName[1]),"line "+ prdName[1]+ " is not Displayed in AOS Workspace ");
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(prdName[2]),"line "+ prdName[2]+ " is not Displayed in AOS Workspace ");
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(prdName[3]),"line "+ prdName[3]+ " is not Displayed in AOS Workspace ");
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(prdName[4]),"line "+ prdName[4]+ " is not Displayed in AOS Workspace ");
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(prdName[5]),"line "+ prdName[5]+ " is not Displayed in AOS Workspace ");
		Assert.assertTrue(plannerDigitalWorkspacePage.isLinePresent(prdName[6]),"line "+ prdName[6]+ " is not Displayed in AOS Workspace ");
		Assert.assertEquals(plannerDigitalWorkspacePage.getTotalNOofLine(),7,"number of lines in workspace and connect is not same");
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[0], AutoConfigDigitalPlannerWorkSpace.quantityColId),qunty);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[1], AutoConfigDigitalPlannerWorkSpace.quantityColId),qunty1);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[2], AutoConfigDigitalPlannerWorkSpace.quantityColId),qunty2);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[3], AutoConfigDigitalPlannerWorkSpace.quantityColId),qunty3);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[4], AutoConfigDigitalPlannerWorkSpace.quantityColId),qunty4);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[5], AutoConfigDigitalPlannerWorkSpace.quantityColId),qunty5);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[6], AutoConfigDigitalPlannerWorkSpace.quantityColId),qunty6);
		Assert.assertTrue(("$"+nuc).contains(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[0], AutoConfigDigitalPlannerWorkSpace.netUnitCostColId)));
		Assert.assertTrue(("$"+nuc1).contains(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[1], AutoConfigDigitalPlannerWorkSpace.netUnitCostColId)));
		Assert.assertTrue(("$"+nuc2).contains(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[2], AutoConfigDigitalPlannerWorkSpace.netUnitCostColId)));
		Assert.assertTrue(("$"+nuc3).contains(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[3], AutoConfigDigitalPlannerWorkSpace.netUnitCostColId)));
		Assert.assertTrue(("$"+nuc4).contains(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[4], AutoConfigDigitalPlannerWorkSpace.netUnitCostColId)));
		Assert.assertTrue(("$"+nuc5).contains(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[5], AutoConfigDigitalPlannerWorkSpace.netUnitCostColId)));
		Assert.assertTrue(("$"+nuc6).contains(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[6], AutoConfigDigitalPlannerWorkSpace.netUnitCostColId)));
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[0], AutoConfigDigitalPlannerWorkSpace.costMethodIdColId),costMethod);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[1], AutoConfigDigitalPlannerWorkSpace.costMethodIdColId),costMethod1);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[2], AutoConfigDigitalPlannerWorkSpace.costMethodIdColId),costMethod2);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[3], AutoConfigDigitalPlannerWorkSpace.costMethodIdColId),costMethod3);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[4], AutoConfigDigitalPlannerWorkSpace.costMethodIdColId),costMethod4);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[5], AutoConfigDigitalPlannerWorkSpace.costMethodIdColId),costMethod5);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[6], AutoConfigDigitalPlannerWorkSpace.costMethodIdColId),costMethod6);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[0], AutoConfigDigitalPlannerWorkSpace.startDateColId),startDate);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[1], AutoConfigDigitalPlannerWorkSpace.startDateColId),startDate1);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[2], AutoConfigDigitalPlannerWorkSpace.startDateColId),startDate2);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[3], AutoConfigDigitalPlannerWorkSpace.startDateColId),startDate3);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[4], AutoConfigDigitalPlannerWorkSpace.startDateColId), startDate4);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[5], AutoConfigDigitalPlannerWorkSpace.startDateColId), startDate5);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[6], AutoConfigDigitalPlannerWorkSpace.startDateColId), startDate6);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[0], AutoConfigDigitalPlannerWorkSpace.endDateColId), endDate);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[1], AutoConfigDigitalPlannerWorkSpace.endDateColId), endDate1);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[2], AutoConfigDigitalPlannerWorkSpace.endDateColId), endDate2);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[3], AutoConfigDigitalPlannerWorkSpace.endDateColId), endDate3);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[4], AutoConfigDigitalPlannerWorkSpace.endDateColId), endDate4);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[5], AutoConfigDigitalPlannerWorkSpace.endDateColId), endDate5);
		Assert.assertEquals(plannerDigitalWorkspacePage.getValuesOfLineItems(prdName[6], AutoConfigDigitalPlannerWorkSpace.endDateColId), endDate6);
		Assert.assertTrue(("$"+netCost+"000").contains(plannerDigitalWorkspacePage.getReadOnlyValuesOfLineItems(prdName[0], AutoConfigDigitalPlannerWorkSpace.netLineCostColId)));
		Assert.assertTrue(("$"+netCost1+"000").contains(plannerDigitalWorkspacePage.getReadOnlyValuesOfLineItems(prdName[1], AutoConfigDigitalPlannerWorkSpace.netLineCostColId)));
		Assert.assertTrue(("$"+netCost2+"000").contains(plannerDigitalWorkspacePage.getReadOnlyValuesOfLineItems(prdName[2], AutoConfigDigitalPlannerWorkSpace.netLineCostColId)));
		Assert.assertTrue(("$"+netCost3+"000").contains(plannerDigitalWorkspacePage.getReadOnlyValuesOfLineItems(prdName[3], AutoConfigDigitalPlannerWorkSpace.netLineCostColId)));
		Assert.assertTrue(("$"+netCost4+"000").contains(plannerDigitalWorkspacePage.getReadOnlyValuesOfLineItems(prdName[4], AutoConfigDigitalPlannerWorkSpace.netLineCostColId)));
		Assert.assertTrue(("$"+netCost5+"000").contains(plannerDigitalWorkspacePage.getReadOnlyValuesOfLineItems(prdName[5], AutoConfigDigitalPlannerWorkSpace.netLineCostColId)));
		Assert.assertTrue(("$"+netCost6+"000").contains(plannerDigitalWorkspacePage.getReadOnlyValuesOfLineItems(prdName[6], AutoConfigDigitalPlannerWorkSpace.netLineCostColId)));
		Assert.assertEquals("$"+totalNetCost,plannerDigitalWorkspacePage.getTotalNetCostOnDeal());
		plannerDigitalWorkspacePage.clickOnKebabMenu(prdName[0]);
	    Assert.assertTrue(plannerDigitalWorkspacePage.isLineClassCheckboxChecked(AutoConfigDigitalPlannerWorkSpace.makegoodCheckboxField),"makegood is checked in AOS" +prdName[0]);
	    plannerDigitalWorkspacePage.navigateToPricingTab();
	    Assert.assertFalse(plannerDigitalWorkspacePage.isLineClassCheckboxChecked(AutoConfigDigitalPlannerWorkSpace.hasCostCheckboxField));
	    plannerDigitalWorkspacePage.closeSideBar();
	    plannerDigitalWorkspacePage.clickOnKebabMenu(prdName[4]);
	    Assert.assertTrue(plannerDigitalWorkspacePage.isLineClassCheckboxChecked(AutoConfigDigitalPlannerWorkSpace.bonusCheckboxField));
	    plannerDigitalWorkspacePage.navigateToPricingTab();
	    Assert.assertFalse(plannerDigitalWorkspacePage.isLineClassCheckboxChecked(AutoConfigDigitalPlannerWorkSpace.hasCostCheckboxField));
	    plannerDigitalWorkspacePage.closeSideBar();
	    
	}

	@AfterTest
	public void afterTest() {
		getbrowsercontext().close();
		getPageBrowser().close();
	}
}
