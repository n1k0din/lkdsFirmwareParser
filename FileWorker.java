
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FileWorker {
	/*
	щас бы джавадок уметь		
	*/
	
	private String inputFileName;
	private String outputFileName;
		
		
	
	public FileWorker(String inputFileName, String outputFileName) {
		
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		
	}
	
	public ArrayList<String> readFiletoArrayList() throws IOException {
		
		ArrayList<String> list = new ArrayList<String>();
		FileReader fr = new FileReader(inputFileName);
		Scanner scan = new Scanner(fr);
		while (scan.hasNextLine()) {
		
			String s;
			s = scan.nextLine();
			list.add(s);		
		}
		
		fr.close();
		
		return list;
		
	}
	
	public void writeFileFromArrayList(ArrayList<String> list) throws IOException {
	
		FileWriter fw = new FileWriter(outputFileName);
		for(String s : list) {
		
			fw.write(s + "\r\n");
			}
		
		fw.close();
		
	}
	
	}