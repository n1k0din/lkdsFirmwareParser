import java.io.*;
import java.util.Scanner;

public class LkdsFirmwareParser{
	
	
	
	public static void main(String[] args) throws Exception{
	FileReader fr = new FileReader("input.txt");
	FileWriter fw = new FileWriter("output.txt");
	
	Scanner scan = new Scanner(fr);
	
	fw.write("<ul>\r\n");
	
	while (scan.hasNextLine()) {
		String s;
        System.out.println(s = scan.nextLine());
		if(s.charAt(0) == '-') s = s.substring(2);
		s = "<li>" + s + "</li>" + "\r\n";
		fw.write(s);		
		}
		
	
	fw.write("</ul>");
	fr.close();
	fw.close();
	
	}
}