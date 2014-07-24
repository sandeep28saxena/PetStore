package org.agoncal.application.petstore.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Serializable;

public class TextReadIn implements Serializable{

	public TextReadIn(){

	}
	
	private String getFilePath(String filename){
		//String fileSeparator = File.separator;
		return /* "applications" + File.separator + "applicationPetstore" + */File.separator + "resources" + File.separator + "files" + File.separator + filename + ".txt";
	}
	
	public String getContents(String filename){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename+".txt")));
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
			BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename+".txt")));
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
			return " ";
		} catch (IOException e) {
			e.printStackTrace();
			return " ";
		} catch (NullPointerException e){
			e.printStackTrace();
			return " ";
		}
	}
}
