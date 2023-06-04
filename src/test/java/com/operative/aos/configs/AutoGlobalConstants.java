package com.operative.aos.configs;


/**
 * @author upratap
 *
 */

public class AutoGlobalConstants {
	
	public static String browserType = AutoConfiguration.initAutomatioProperties().getProperty("BrowserType");
	public static String recordTestScripts = AutoConfiguration.initAutomatioProperties().getProperty("RecordingTestScripts");
	public static String traceViewer = AutoConfiguration.initAutomatioProperties().getProperty("TraceViewer");
	public static String devTools = AutoConfiguration.initAutomatioProperties().getProperty("DevTools");
	public static String environmentUrl = AutoConfiguration.initEnvironmentProperties().getProperty("EnvironmentUrl");
	public static String userName = AutoConfiguration.initEnvironmentProperties().getProperty("username");
	public static String passWord = AutoConfiguration.initEnvironmentProperties().getProperty("password");
	public static String dbhost = AutoConfiguration.initEnvironmentProperties().getProperty("DBhost");
	public static String dbuserId = AutoConfiguration.initEnvironmentProperties().getProperty("DBUserID");
	public static String dbpwd = AutoConfiguration.initEnvironmentProperties().getProperty("DBPassword");
	public static String dbname = AutoConfiguration.initEnvironmentProperties().getProperty("DBName");
	public static String setDefaultTimeout = AutoConfiguration.initEnvironmentProperties().getProperty("setDefaultTimeout");
	public static String environmentUrlO1Operative = AutoConfiguration.initEnvironmentProperties().getProperty("EnvironmentUrlO1");
	public static String userNameO1Operative = AutoConfiguration.initEnvironmentProperties().getProperty("usernameO1");
	public static String passWordO1Operative = AutoConfiguration.initEnvironmentProperties().getProperty("passwordO1");
	public static String tenantName = AutoConfiguration.initEnvironmentProperties().getProperty("TenantName");
	
	public static String firefox = "FIREFOX";
	public static String webkit = "webkit";
	public static String chrome = "Chrome";
	public static String chromium="chromium";
	
	public static String recordTestScriptsYes="Yes";
	public static String recordTestScriptsNo="No";
	
}
