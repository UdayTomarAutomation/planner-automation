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
public class PlannerWorkSpaceDataprovider {
	
	@DataProvider(name ="cellpropertiesRateValue")
	public static Object[][] cellpropertiesRateValue() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "cellpropertiesRateValue");
	}
	
	@DataProvider(name ="CellpropertiesImpsValue")
	public static Object[][] CellpropertiesImpsValue() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "CellpropertiesImpsValue");
	}
	
	@DataProvider(name ="netCpmTotalQtr")
	public static Object[][] netCpmTotalQtr() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "netCpmTotalQtr");
	}
	
	@DataProvider(name ="refreshRateCardWorkSpace")
	public static Object[][] refreshRateCardWorkSpace() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "refreshRateCardWorkSpace");
	}
	
	@DataProvider(name ="productCreateSimilarLine")
	public static Object[][] productCreateSimilarLine() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "productCreateSimilarLine");
	}
	
	@DataProvider(name ="ImpsValueTotalQtr")
	public static Object[][] ImpsValueTotalQtr() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "ImpsValueTotalQtr");
	}
	
	@DataProvider(name ="UnitsValueTotalQtr")
	public static Object[][] UnitsValueTotalQtr() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "UnitsValueTotalQtr");
	}
	
	@DataProvider(name ="removeUnitsWorkSpace")
	public static Object[][] removeUnitsWorkSpace() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "removeUnitsWorkSpace");
	}
	
	@DataProvider(name ="lineProrateRate")
	public static Object[][] lineProrateRate() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "lineProrateRate");
	}
	
	@DataProvider(name ="cellpropertiesGrossCPM")
	public static Object[][] cellpropertiesGrossCPM() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "cellpropertiesGrossCPM");
	}
	
	@DataProvider(name ="smokeSuitePlannerData")
	public static Object[][] smokeSuitePlannerData() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "smokeSuitePlannerData");
	}
	
	@DataProvider(name ="smokeSuitePlannerDataWeekly")
	public static Object[][] smokeSuitePlannerDataWeekly() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "smokeSuitePlannerDataWeekly");
	}
	
	@DataProvider(name ="smokeSuiteRemixPlannerData")
	public static Object[][] smokeSuiteRemixPlannerData() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "Planner", "smokeSuiteRemixPlannerData");
	}
	
	@DataProvider(name ="localSmokeSuitePlannerData")
	public static Object[][] localSmokeSuitePlannerData() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "localSmokeSuitePlannerData");
	}
	
	@DataProvider(name ="localSmokeSuitePlannerDataWeekly")
	public static Object[][] localSmokeSuitePlannerDataWeekly() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "localSmokeSuitePlannerDataWeekly");
	}
	
	@DataProvider(name ="localSmokeSuitePlannerDataGrouping")
	public static Object[][] localSmokeSuitePlannerDataGrouping() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "localSmokeSuitePlannerDataGrouping");
	}
	
	@DataProvider(name ="smokeSuiteRemixPlannerData_Local")
	public static Object[][] smokeSuiteRemixPlannerData_Local() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "smokeSuiteRemixPlannerData_Local");
	}
	
	@DataProvider(name ="localSmokeSuitePlannerDataQuickRefresh")
	public static Object[][] localSmokeSuitePlannerDataQuickRefresh() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "localSmokeSuitePlannerDataQuickRefresh");
	}
	
	@DataProvider(name ="localSmokeSuitePlannerUnitsDistribution")
	public static Object[][] localSmokeSuitePlannerUnitsDistribution() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "localSmokeSuitePlannerUnitsDistribution");
	}

	@DataProvider(name ="localSmokeSuitePlannerLines")
	public static Object[][] localSmokeSuitePlannerLines() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "localSmokeSuitePlannerLines");
	}
	@DataProvider(name ="copyPasteUnitsInLineAndWeekly")
	public static Object[][] copyPasteUnitsInLineAndWeekly() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "copyPasteUnitsInLineAndWeekly");
	}
	
	@DataProvider(name ="localSmokeSuitePlannerEditProductAttribues")
	public static Object[][] localSmokeSuitePlannerEditProductAttribues() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "localSmokeSuitePlannerEditProductAttribues");
	}
	
	@DataProvider(name ="localSmokeSuitePlannerDataquadView")
	public static Object[][] localSmokeSuitePlannerDataquadView() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_LinearPlanner.xls", "plannerLocal_Books", "localSmokeSuitePlannerDataquadView");
	}
	
}
