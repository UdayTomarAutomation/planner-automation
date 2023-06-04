package com.operative.aos.configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.util.Strings;

/**
 * @author upratap
 *
 */
public class AutoConfiguration {
	@SuppressWarnings("unused")
	  public static Properties initAutomatioProperties() {
	    Properties properties = null;
	    try {
	      final File file = new File(
	          System.getProperty("user.dir") + File.separator + "Env_configs" + File.separator + "Automation.Properties");
	      if (file == null) {
	        throw new FileNotFoundException();
	      }
	      final FileInputStream fileInput = new FileInputStream(file);
	      properties = new Properties();
	      properties.load(fileInput);
	      final String envVal = System.getProperty("Env");
	      if (!Strings.isNullOrEmpty(envVal)) {
	        properties.setProperty("Environment", envVal);
	      }
	      final String recordts = System.getProperty("recordTS");
	      if (!Strings.isNullOrEmpty(recordts)) {
	        properties.setProperty("RecordingTestScripts", recordts);
	      }
	      final String traceViews = System.getProperty("TraceViews");
	      if (!Strings.isNullOrEmpty(traceViews)) {
	        properties.setProperty("TraceViewer", traceViews);
	      }

	      fileInput.close();
	    } catch (final Exception e) {
	      e.printStackTrace();
	    }
	    return properties;
	  }

	  @SuppressWarnings("unused")
	  public static Properties initEnvironmentProperties() {
	    Properties properties = null;
	    try {
	      final File file = new File(System.getProperty("user.dir") + File.separator + "Environments" + File.separator
	          + initAutomatioProperties().getProperty("Environment") + ".Properties");
	      if (file == null) {
	        throw new FileNotFoundException();
	      }
	      final FileInputStream fileInput = new FileInputStream(file);
	      properties = new Properties();
	      properties.load(fileInput);
	      fileInput.close();
	    } catch (final Exception e) {
	      e.printStackTrace();
	    }
	    return properties;
	  }

	
}
