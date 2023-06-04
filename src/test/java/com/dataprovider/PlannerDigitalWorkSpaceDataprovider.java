/**
 * 
 */
package com.dataprovider;

import java.io.File;

import org.testng.annotations.DataProvider;

import com.operative.base.utils.ExcelUtilities;

/**
 * @author ajay_bhave
 *
 */
public class PlannerDigitalWorkSpaceDataprovider {

	@DataProvider(name = "createDigitalPlan")
	public static Object[][] createDigitalPlan() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
			+ File.separator + "O1_Auto_TestData_Nightly_DigitalWorkSpace.xls", "PlannerDigital_UI", "createDigitalPlan");
	} 
	
	@DataProvider(name = "DigitalWorkspacePackage")
	public static Object[][] DigitalWorkspacePackage() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
			+ File.separator + "O1_Auto_TestData_Nightly_DigitalWorkSpace.xls", "PlannerDigital_UI", "DigitalWorkspacePackage");
	}
}
