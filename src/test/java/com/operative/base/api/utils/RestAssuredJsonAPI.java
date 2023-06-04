/**
 * 
 */
package com.operative.base.api.utils;



import com.amazonaws.util.Base64;
import com.operative.base.utils.Logger;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


/**
 * @author upratap
 *
 */
public class RestAssuredJsonAPI {

	

	// for post Response with String request json
	@Step("post Response with String request json")
	public Response jsonClientPost(String url, String json, String authVal) {
		Response response = null;
		final RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(json);
		httpRequest.header("Authorization", authVal);
		Logger.log("Request Url : " + url);
		try {
			response = httpRequest.post(url);
			if (response.statusCode() != 200 && response.statusCode() != 201) {
				Logger.log("Failed : RestAssured error code : " + response.statusCode());
			} else {
				Logger.log("Failed : HTTP error code : " + response.statusCode());
			}
			
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
		}
		return response;
	}

	// for put Response with String request json
	@Step("put Response with String request json")
	public Response jsonClientPut(String url, String json, String authVal) {
		Response response = null;
		final RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(json);
		httpRequest.header("Authorization", authVal);
		Logger.log("Request Url : " + url);
		try {
			response = httpRequest.put(url);
			if (response.statusCode() != 200 && response.statusCode() != 422) {
				Logger.log("Failed : RestAssured error code : " + response.statusCode());
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
		}
		return response;
	}

	// for get Response with String request json
	@Step("get Response with String request json")
	public Response jsonClientGet(String url, String authVal) {
		Response response = null;
		final RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", authVal);
		Logger.log("Request Url : " + url);
		try {
			response = httpRequest.get(url);
			if (response.statusCode() != 200) {
				Logger.log("Failed : RestAssured error code : " + response.statusCode());
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
		}
		return response;
	}
	
	// To fetch JWt token
	public static Response jsonClientGetJWTTokenJSON(String url, String userId, String tenantId) {
		final String encodedAuth = new String(
				Base64.encode(new StringBuilder("admin").append(":").append("sintecmedia").toString().getBytes()));
		final String basicAuth = new StringBuilder("Basic ").append(encodedAuth).toString();
		Response response = null;
		final RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", basicAuth);
		httpRequest.header("userId", userId).header("tenantId", tenantId);

		try {
			response = httpRequest.get(url);
			if (response.statusCode() != 200) {
				Logger.log("Failed : RestAssured error code : " + response.statusCode());
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
		}
		return response;
	}

}
