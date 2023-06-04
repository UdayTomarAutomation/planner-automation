package com.dataprovider;

import java.io.File;

import org.testng.annotations.DataProvider;

import com.operative.base.utils.ExcelUtilities;
import com.operative.aos.configs.AutoConfigWorkflow;

public class WorkFlowDataProvider {
	@DataProvider(name ="verifyAllWorkflowStatus")
	public static Object[][] PlanCreation() {
		return ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator + "TestData"
				+ File.separator + "O1_Auto_TestData_Nightly_Workflow.xls", "StatusAttributeDetails", "verifyAllWorkflowStatus");
	}	
}
