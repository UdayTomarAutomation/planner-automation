package com.operative.helper;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
//import com.operative.base.utils.workFlow.DataBase.pojo.DbWorkflowStatusDetails;
//import com.operative.cablenet.configs.AutoConfigScheduleLogger.log;
import com.operative.aos.configs.AutoConfigWorkflow;
import com.operative.base.utils.BasePage;
import com.operative.base.utils.BaseTest;
import com.operative.base.utils.Logger;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class End2EndWorkflowHelper extends BasePage {
	public End2EndWorkflowHelper(Page getpageBrowser) {
		super(getpageBrowser);
		// TODO Auto-generated constructor stub
	}


	/**
	 * TODO:Shruthi S S this class contains workflow module specific helper class
	 * details
	 *
	 * @author shruthiss
	 * @date 16/3/2020
	 *
	 */
	BaseTest baseTest = new BaseTest();
	// this method is used to get transition id from response

	public String getTransitionIdFromResponse(Response response, String aosIdFromDb) throws IOException {
		String id = "";
		final ObjectMapper objectMapper = new ObjectMapper();
		final JsonNode rootNode = objectMapper.readTree(response.asString());
		final JsonNode workflowTransitionIds = rootNode.findValue("workflowTransitions");
		final List<JsonNode> objectTypeIds = workflowTransitionIds.findValues("id");
		Logger.log("api data for camunda ids" + objectTypeIds.get(1));
		for (int i = 0; i < objectTypeIds.size(); i = i + 2) {
			Logger.log("api" + objectTypeIds.get(i).toString());
			Logger.log("db" + aosIdFromDb);
			if (objectTypeIds.get(i).toString().equalsIgnoreCase(aosIdFromDb)) {
				Logger.log("Workflow transition id returned by api is : " + objectTypeIds.get(i));

				id = objectTypeIds.get(i).toString();
				break;
			}

		}
		return id;
	}
}
