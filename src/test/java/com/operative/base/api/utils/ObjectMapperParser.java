/**
 * 
 */
package com.operative.base.api.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author upratap
 *
 */
public class ObjectMapperParser {
	
	   public JsonNode objectMapperForJson(String response) {
	        JsonNode rootNode = null;
	        final ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            rootNode = objectMapper.readTree(response);
	        } catch (final IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return rootNode;
	    }


	    public String objectMapperString(JsonNode rootNode, String value) {
	        String mappervalue = rootNode.findValue(value).asText();
	        return mappervalue;
	    }


	    public List<JsonNode> objectMapperList(JsonNode rootNode, String value) {
	        List<JsonNode> mappervalue = rootNode.findValues(value);
	        return mappervalue;
	    }


	    public boolean objectMapperboolean(JsonNode rootNode, String value) {
	        boolean mappervalue = rootNode.findValue(value).asBoolean();
	        return mappervalue;
	    }

	    public ObjectMapper getObjectMapperInstance() {
	        return new ObjectMapper();
	    }
}
