import java.io.*;
import java.util.Scanner;

public class LkdsFirmwareParser{
	
	class Firmware{
	
	}
	
	/*
		Ждём файл вида:
		- блабла1
		- блабла2
		...
		- блаблаN
		
		и оборачиваем каждую строчку в <li></li>, а весь текст файла в <ul></ul>
	*/
	
	public static void main(String[] args) throws Exception{
	FileReader fr = new FileReader("input.txt");
	FileWriter fw = new FileWriter("output.htm");
	
	Scanner scan = new Scanner(fr);
	
	fw.write("<ul>\r\n");
	
	while (scan.hasNextLine()) {
		String s;
        System.out.println(s = scan.nextLine());//
		String subS = s.substring(0,2);
		if(subS.equals("- ")) {
		s = s.substring(2);
		s = "<li>" + s + "</li>" + "\r\n";
		fw.write(s);			
		}
		//обычно строчка начинается с "- "(дефис, пробел) 
		else if (subS.equals("  ")) {//но некоторые могут начинаться с кучки пробелов
		fw.write("<ul>\r\n");		
		
		while (s.substring(0,2).equals("  ") && scan.hasNextLine()) {//пока начинается с пробелов и есть чо почитать	
		char[] chr = s.toCharArray();//найдем количество этих пробелов
		int i;
		for(i = 0; i < chr.length && chr[i] == ' '; i++);		
		s = s.substring(i);		
		s = "<li>" + s + "</li>" + "\r\n";
		fw.write(s);
		System.out.println(s = scan.nextLine());//
		}		
		fw.write("</ul>\r\n");
		subS = s.substring(0,2);
		if (subS.equals("- ")) {
		s = s.substring(2);
		s = "<li>" + s + "</li>" + "\r\n";
		fw.write(s);
		}
		
		
		
		}
		
				
			
		
		
		}
		
	
	fw.write("</ul>");
	fr.close();
	fw.close();
	
	}
}