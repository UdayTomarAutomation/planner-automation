/**
 * 
 */
package com.dataprovider;

import java.io.File;

import org.testng.annotations.DataProvider;

import com.operative.base.utils.ExcelUtilities;

/**
 * TODO:Akshay
 *
 * @author Akshay
 * @date 12/1/2023
 *
 */
public class ConnectDataprovider {

	@DataProvider(name = "PlanCreation")
	public static Object[][] PlanCreation() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_Connect.xls", "Connect_UI", "PlanCreation");
	}
	@DataProvider(name = "DealCreation")
	public static Object[][] DealCreation() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_Connect.xls", "Connect_UI", "DealCreation");
	}
	@DataProvider(name = "AddLineswithADU_MG_DifferentCostMethodAndHeaderwithNoagency")
	public static Object[][] verifyDealCreationWithDifferentCostMethodMGADU() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_Connect.xls", "Connect_UI", "AddLineswithADU_MG_DifferentCostMethodAndHeaderwithNoagency");
	}
	
	@DataProvider(name = "verifyAdvertiserAgencyinAOS")
	public static Object[][] verifyAdvertiserAgencyinAOS() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_Connect.xls", "Connect_UI", "verifyAdvertiserAgencyinAOS");
	}
	
	@DataProvider(name = "AOS_VerifySupplyExposeProducttoProductGrid")
	public static Object[][] AOS_VerifySupplyExposeProducttoProductGrid() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_Connect.xls", "Connect_UI", "AOS_VerifySupplyExposeProducttoProductGrid");
	}
	
	@DataProvider(name = "DealCreationWithTarget")
	public static Object[][] DealCreationWithTarget() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_Connect.xls", "Connect_UI", "DealCreationWithTarget");
	}
	
	@DataProvider(name = "AOS_verifyExposedTargetwithoutProd")
	public static Object[][] AOS_verifyExposedTargetwithoutProd() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_Connect.xls", "Connect_UI", "AOS_verifyExposedTargetwithoutProd");
	}
	


}
