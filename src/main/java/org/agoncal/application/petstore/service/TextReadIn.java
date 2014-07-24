package org.agoncal.application.petstore.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TextReadIn {

	public TextReadIn(){

	}
	public String getContents(String fileName){
		
		String fileSeparator= java.io.File.separator;
		String filePath = "src" + fileSeparator + "main" + fileSeparator + 
				"resources" + fileSeparator + "ItemDescriptions" + 
				fileSeparator + fileName + ".txt";
		
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String result = "";
			String line = br.readLine();

			while (line != null) {
				result = result + line + "\n";
				line = br.readLine();
				}
			return result;	
		}
		catch (FileNotFoundException e) {
			return "";
		
		}
		catch (IOException e) {
			return "";
		}
	}
}
