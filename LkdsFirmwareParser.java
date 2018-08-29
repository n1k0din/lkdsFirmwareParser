import java.util.ArrayList;


public class LkdsFirmwareParser{
	
	private String input;
	private String output;
	private ArrayList<String> list;//список строк
	private FileWorker fileWorker;//объект для работы с файлами	
	private ArrayList<Pair> indexList; //список пар индексов второго уровня	
	private boolean[] flagArray; //список флагов первый уровень или второй уровень
	
	public LkdsFirmwareParser(String input, String output) {
	
		this.input = input;
		this.output = output;		
		list = new ArrayList<String>();
		indexList = new ArrayList<Pair>();
	}
	
	public void openAndReadInputFile() throws Exception {
		
		fileWorker = new FileWorker(input, output);
		list = fileWorker.readFiletoArrayList();	
		
	}
	
	public void writeOutputfile() throws Exception {
	
		fileWorker.writeFileFromArrayList(list);		
	}
	
	public void printList() {
		
		for( String s : list) System.out.println(s);
		
	}
	
	public void printIndexList() {
		
		for( Pair p : indexList) {
		
			System.out.println(p.getI() + ", " + p.getJ());
			
		}
		
	}
	
	public void createFlagArray() {
	
		flagArray = new boolean[list.size()];
		
		for(int i = 0; i < list.size(); i++) {
			String subS = list.get(i).substring(0,2);//берём первые два символов
			if(subS.equals("- ")) flagArray[i] = true;//дефис-пробел первый уровень
				else if(subS.equals("  ")) flagArray[i] = false;//два пробела второй уровень
		
		}			
	}
	
	public void createIndexList() {
		
		indexList.add(new Pair(0,list.size()-1));//положили пару (0,n) - типа надо заключить в теги блок с 0 по n строку
		
		for(int i = 0; i < list.size(); i++) {//начинаем-то палюбэ с первого уровня
		
			int j = 0;
			if (flagArray[i] == false) { //ищем первую встречу второго
				for(j = i; j < list.size() && flagArray[j] == false; j++);//;!!! и считаем количество
				indexList.add(new Pair(i,j-1)); //добавляем пару в наш список
				i = j;//продолжаем просмотр списка со следующего элемента первого уровня
			}			
		}		
	
	}
	
	public void clearPrefixes() {
		//щас я вам почистию 
		
		for(int i = 0; i < list.size(); i++) {
		
			String s = list.get(i);
		
			if(flagArray[i]) {				
				s = s.substring(2);//тяп				
			}
			
			else {
			
				char[] chr = s.toCharArray();//найдем количество этих пробелов
				int j;
				for(j = 0; j < chr.length && chr[j] == ' '; j++);//;!!!	
				s = s.substring(j);//тяп		
				
			}
		
			list.set(i,s); // полож где взял
		
		}	
		
	}
	
	public void addHtml() {
	
	addLITags();
	addULTags();		
	
	}
	
	private void addLITags() {
	
		for(int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			s = "<li>"+s+"</li>";
			list.set(i,s);
		}
	
		
	}
	
	private void addULTags() {
		for(int i = 0; i < indexList.size(); i++) {
			
			int startInd = indexList.get(i).getI();//некрасиво(
			int stopInd = indexList.get(i).getJ();
			
			
			String s = list.get(startInd);
			s = "<ul>"+s;
			list.set(startInd, s);
			
			s = list.get(stopInd);
			s = s + "</ul>";
			list.set(stopInd, s);
		}
		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception{
	
		
		LkdsFirmwareParser parser = new LkdsFirmwareParser("input.txt", "output.htm");
		parser.openAndReadInputFile(); //открываем и читаем файл в AL
		parser.createFlagArray(); //создаем массив флагов если строчка первый уровень, то true, если второй, то false
		parser.createIndexList(); //на основе массива флагов делаем список индексов
		parser.printIndexList(); //чо насчитали?
		parser.clearPrefixes();//чистим от мусора в начале строк
		parser.addHtml();
		parser.printList(); //а ну закеш
		parser.writeOutputfile();//теперь в файл
		
		
	}
	
}


	
	
	
	
	
	
