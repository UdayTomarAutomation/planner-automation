package com.operative.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * @author upratap
 *
 */
public class FileUtils {
	
	public static String getContent(String path) throws IOException {
		
		
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(path.toString()))))) {
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		

	}
	
	public void readFile(String fileName) throws IOException
	 {
	  FileInputStream inputStream=null;
	  
	  try {
	   // Getting ClassLoader obj
	   ClassLoader classLoader = this.getClass().getClassLoader();
	   // Getting resource(File) from class loader
	   File configFile=new File(classLoader.getResource(fileName).getFile());
	  
	   inputStream = new FileInputStream(configFile);
	   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	   String line;
	   while ((line = reader.readLine()) != null) {
	   System.out.println(line);
	   }
	 
	   reader.close();
	 
	 
	  } catch (FileNotFoundException e) {
	 
	   e.printStackTrace();
	  }catch (IOException e) {
	 
	   e.printStackTrace();
	  }
	  finally {
	   inputStream.close();
	  }
	 
	  
	 }

}
