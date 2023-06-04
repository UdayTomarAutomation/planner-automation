package com.operative.base.utils;

import org.testng.Reporter;

import java.util.ArrayList;
import java.util.Map;
/**
 * @author upratap
 *
 */
public class Logger {
	
	
	public static void log(String message)
    {
        Reporter.log(message, true);
    }

    public static void textSummary(String text) {
        Reporter.log("==>>[ " + text + " ]", true);
    }

    public static void logRequest(String message,String request)
    {
        Reporter.log(" "+message+" :======>> " + request, true);
    }
   
    public static void logResponse(String message,String request)
    {
        Reporter.log(""+message+" :=====>> " + request, true);
    }

    public static void logApiName(String message,String request)
    {
        Reporter.log(""+message+" :=====>> " + request, true);
    }

    public static void logDBRequest(String message,String dbQuery)
    {
        Reporter.log(""+message+" :=====>>" + dbQuery, true);
    }

    public static void logDBResult(String message,String[] dbResult)
    {
        Reporter.log(""+message+" :=====>>" + dbResult, true);
    }

    public static void logDBResult(String message, ArrayList<?> dbResult)
    {
        Reporter.log(" "+message+" :=====>>" + dbResult, true);
    }

    public static void logDBResult(String message, Map<?,?> DbResult)
    {

        Reporter.log(""+message+" :=====>>" + DbResult, true);
    }

}
