package org.agoncal.application.petstore.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class TextReadIn {

	public TextReadIn(){

	}
	
	private String getFilePath(String filename){
		//String fileSeparator = File.separator;
		return "src" + File.separator + "main" + File.separator + "resources" + File.separator + "ItemDescriptions" + File.separator + filename + ".txt";
	}
	
	public String getContents(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(getFilePath(filename)));
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

	public String getContents(String filename, String category){
		boolean done = false;
		boolean found = false;
		System.out.println("Inside TextReadIn.getContents("+filename+", "+category+")");
		try{
			BufferedReader br = new BufferedReader(new FileReader(getFilePath(filename)));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while(line != null && !found){
				System.out.println("line = " + line);
				if(line.equals(category)){
					System.out.println("Found "+category);
					found = true;
					line = br.readLine();
				}
				else{
					line = br.readLine();
				}
			}
			while(line != null && !done){
				System.out.println("line = " + line);
				if(!line.equals("---")){
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				else{
					System.out.println("Done");
					done = true;
				}
			}

			br.close();
			System.out.println("sb.toString() = " + sb.toString());
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
