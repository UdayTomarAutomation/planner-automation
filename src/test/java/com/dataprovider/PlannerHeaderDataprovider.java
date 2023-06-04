/**
 * 
 */
package com.dataprovider;

import java.io.File;

import org.testng.annotations.DataProvider;

import com.operative.base.utils.ExcelUtilities;

/**
 * @author upratap
 *
 */
public class PlannerHeaderDataprovider {

	@DataProvider(name = "PlanCreation")
	public static Object[][] PlanCreation() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "PlanCreation");
	}

	@DataProvider(name = "PlanCreationAllEnv")
	public static Object[][] PlanCreationAllEnv() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "PlanCreationAllEnv");
	}

}
