package com.operative.aos.configs;

import java.io.File;

import com.operative.base.api.utils.RestAssuredJsonAPI;

public class AutoConfigWorkflow {
	/**
	 * TODO:Shruthi S S
	 *
	 * @author shruthiss
	 * @date 16/3/2020
	 *
	 */
	public static String moduleName = "System Configuration";
	public static String username = AutoConfiguration.initEnvironmentProperties().getProperty("username");
	public static String password = AutoConfiguration.initEnvironmentProperties().getProperty("password");
	public static String unison = AutoConfiguration.initEnvironmentProperties().getProperty("unison");
	public static String apiUnison = AutoConfiguration.initEnvironmentProperties().getProperty("apiUnison");
	public static String noPermissionUser = AutoConfiguration.initEnvironmentProperties()
			.getProperty("NoPermissionUser");
	public static String authorizationValue_no_permission = AutoConfiguration.initEnvironmentProperties()
			.getProperty("authorizationValue_no_permission");
	public static String user_name_security_user = AutoConfiguration.initEnvironmentProperties()
			.getProperty("usernameForSecurity");
	public static String password_security_user = AutoConfiguration.initEnvironmentProperties()
			.getProperty("passwordForSecurity");
	public static String environmentURL = AutoConfiguration.initEnvironmentProperties().getProperty("EnvironmentUrl");
	public static String tableName = AutoConfiguration.initEnvironmentProperties().getProperty("tableName");
	public static String tenantName = AutoConfiguration.initEnvironmentProperties().getProperty("tenantName");
	public static String userManagementUrl = AutoConfiguration.initEnvironmentProperties()
			.getProperty("userManagementUrl");
	public static String createReadonlyFields = "/statusattributes/entity/planner/create";
	public static String usernameDigital = AutoConfiguration.initEnvironmentProperties().getProperty("usernameDigital");
	public static String userNameWorkFlow = AutoConfiguration.initEnvironmentProperties()
			.getProperty("userNameWorkFlow");
	

}
