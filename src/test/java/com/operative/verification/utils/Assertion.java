/**
 * 
 */
package com.operative.verification.utils;

import org.testng.Assert;
import org.testng.Reporter;

/**
 * @author upratap
 *
 */
public class Assertion {
	
	
	public static void assertEquals(String actual, String expected, String message) {
		if (actual.equalsIgnoreCase(expected)) {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#009933' size='3'> <b> :==> [Actual : "
							+ actual + "] [Expected : " + expected + "] ==> PASS </b></font>",
							true);
		} else {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#ff0000' size='3'> <b>:==> [Actual : "
							+ actual + "] [Expected : " + expected + "] ==> FAIL </b></font>",
							true);
		}
		Assert.assertEquals(actual, expected, message);
	}

	public static void assertEquals(int actual, int expected, String message) {
		if (actual == expected) {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#009933' size='3'> <b> :==> [Actual : "
							+ actual + "] [Expected : " + expected + "] ==> PASS </b></font>",
							true);
		} else {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#ff0000' size='3'> <b>:==> [Actual : "
							+ actual + "] [Expected : " + expected + "] ==> FAIL </b></font>",
							true);
		}
		Assert.assertEquals(actual, expected, message);
	}

	public static void assertNotEquals(String actual, String expected, String message) {
		if (actual.equalsIgnoreCase(expected)) {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#ff0000' size='3'> <b>:==> [Actual : "
							+ actual + "] [Expected : " + expected + "] ==> FAIL </b></font>",
							true);
		} else {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#009933' size='3'> <b> :==> [Actual : "
							+ actual + "] [Expected : " + expected + "] ==> PASS </b></font>",
							true);
		}
		Assert.assertNotEquals(actual, expected, message);
	}

	public static void assertNotEquals(int actual, int expected, String message) {
		if (actual == expected) {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#ff0000' size='3'> <b> :==> [Actual : "
							+ actual + "] [Expected : " + expected + "] ==> FAIL </b></font>",
							true);
		} else {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#009933' size='3'> <b> :==> [Actual : "
							+ actual + "] [Expected : " + expected + "] ==> PASS </b></font>",
							true);
		}
		Assert.assertNotEquals(actual, expected, message);
	}

	public static void assertTrue(boolean actual, String message) {
		if (actual) {
			Reporter.log("<size='3'><b>" + message + "</size></b>"
					+ "<font color='#009933' size='3'> <b>:==> [Actual : True] [Expected : True] ==> PASS </b></font>",
					true);
		} else {
			Reporter.log("<size='3'><b>" + message + "</size></b>"
					+ "<font color='#ff0000' size='3'> <b>:==> [Actual : False] [Expected : True] ==> FAIL </b></font>",
					true);
		}
		Assert.assertTrue(actual, message);
	}

	public static void assertFalse(boolean actual, String message) {
		if (actual) {
			Reporter.log("<size='3'><b>" + message + "</size></b>"
					+ "<font color='#ff0000' size='3'> <b>:==> [Actual : True] [Expected : False] ==> FAIL </b></font>",
					true);
		} else {
			Reporter.log("<size='3'><b>" + message + "</size></b>"
					+ "<font color='#009933' size='3'> <b> :==> [Actual : False] [Expected : False] ==> PASS </b></font>",
					true);
		}
		Assert.assertFalse(actual, message);
	}

	public static void assertNull(Object actual, String message) {
		if (actual == null) {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#009933' size='3'> <b>:==> [Actual : "
							+ null + "] [Expected : " + null + "] ==> PASS </b></font>",
							true);
		} else {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#ff0000' size='3'> <b> :==> [Actual : "
							+ actual + "] [Expected : " + null + "] ==> FAIL </b></font>",
							true);
		}
		Assert.assertNull(actual, message);
	}

	public static void assertNotNull(Object actual, String message) {
		if (actual != null) {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#009933' size='3'> <b>:==> [Actual : "
							+ actual + "] [Expected : " + actual + "] ==> PASS </b></font>",
							true);
		} else {
			Reporter.log(
					"<size='3'><b>" + message + "</size></b>" + "<font color='#ff0000' size='3'> <b>:==> [Actual : "
							+ null + "] [Expected : Not null] ==> FAIL </b></font>",
							true);
		}
		Assert.assertNotNull(actual, message);
	}
}
