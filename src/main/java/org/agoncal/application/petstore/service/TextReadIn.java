package org.agoncal.application.petstore.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TextReadIn {

	public TextReadIn(){

	}
	
	private String getFilePath(String fileName){
		String fileSeparator= java.io.File.separator;
		return "src" + fileSeparator + "main" + fileSeparator + 
				"resources" + fileSeparator + "ItemDescriptions" + 
				fileSeparator + fileName + ".txt";
	}
	
	public String getContents(String fileName){
		try{
			BufferedReader br = new BufferedReader(new FileReader(getFilePath(fileName)));
			String result = "";
			String line = br.readLine();

			while (line != null) {
				result = result + line + "\n";
				line = br.readLine();
			}
			
			br.close();
			return result;	
		}
		catch (FileNotFoundException e) {
			return "";

		}
		catch (IOException e) {
			return "";
		}
	}

	public String getContents(String fileName, String category){
		boolean done = false;
		boolean found = false;
		try{
			BufferedReader br = new BufferedReader(new FileReader(getFilePath(fileName)));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while(line != null && !found){
				if(line.equals(category)){
					found = true;
					line = br.readLine();
				}
				else{
					line = br.readLine();
				}
			}
			while(line != null && !done){
				if(!line.equals("---")){
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				else{
					done = true;
				}
			}

			br.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
