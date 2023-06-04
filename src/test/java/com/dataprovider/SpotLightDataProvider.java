/**
 * 
 */
package com.dataprovider;

import java.io.File;

import org.testng.annotations.DataProvider;

import com.operative.base.utils.ExcelUtilities;

/**
 * @author tharun.kg
 *
 */
public class SpotLightDataProvider {
	@DataProvider(name = "applyDateSeperator")
	public static Object[][] applyingDateSeperator() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_SpotLight.xls", "SpotLightDev", "applyDateSeperator");
	}
	@DataProvider(name = "checkingDefaultColumns")
	public static Object[][] checkingDefaultColumns() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_SpotLight.xls", "SpotLightDev", "checkingDefaultColumns");
	}
	@DataProvider(name = "defaultFilters")
	public static Object[][] defaultFilters() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_SpotLight.xls", "SpotLightDev", "defaultFilters");
	}
	@DataProvider(name = "checkUncheckFilters")
	public static Object[][] checkUncheckFilters() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_SpotLight.xls", "SpotLightDev", "checkUncheckFilters");
	}
	
	@DataProvider(name = "allColumnsInGrid")
	public static Object[][] allColumnsInGrid() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_SpotLight.xls", "SpotLightDev", "allColumnsInGrid");
	}
}