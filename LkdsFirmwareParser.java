import java.util.ArrayList;


public class LkdsFirmwareParser{
	
	private String input;//файл входящий
	private String output;//файл исходящий
	private ArrayList<String> list;//список строк из входящего файла
	private FileWorker fileWorker;//объект для работы с файлами	
		
	private FileWorker fwNameList; //это для списка исполнений, на потом
	private ArrayList<String> nameList; //список исполнений из файла исполнений, на потом
	
	
	final private ArrayList<Firmware> firmwares = new ArrayList<Firmware>(); //список прошивок
	//где прошивка = наименование исполнения + список изменений, где изменений это 
	//
		
	public LkdsFirmwareParser(String input, String output) {
	
		this.input = input;
		this.output = output;		
		list = new ArrayList<String>();
		nameList = new ArrayList<String>();
		
		
	}
	
	public void openAndReadInputFile() throws Exception {
		
		fileWorker = new FileWorker(input, output);
		list = fileWorker.readFiletoArrayList();
		fwNameList = new FileWorker("list.txt", "error.txt");
		nameList = fwNameList.readFiletoArrayList();
		
		
	}
	
		
	
	
	public void writeOutputfile() throws Exception {
	
		fileWorker.writeFileFromArrayList(list);		
	}	
	
	public void stringsToFirmwares() {
	
		//запихаем всё дерьмо сюда
		//исходные данные:
		//есть list со всеми исходными данными-строчкам
		//есть nameList со списком исполнений
		//собсно надо сгенерить ArrayList<Firmware> firmwares;
		//если начинается не с пробела или дефиса, а заканчивается двоеточием - это firmware.name;
		
		for(int i = 0; i < list.size(); i++) {
		
			String s = list.get(i);
			char ch0 = s.charAt(0);
			char chN = s.charAt(s.length() - 1);
			
			if(ch0 != '-' && ch0 != ' ' &&  chN == ':') {//похоже на название
			//	
				//System.out.println("Обнаружено исполнение на строке " + i);
				Firmware fw = new Firmware();//создадим прошивку		
				s = clearPrefixes(s);
				fw.setName(s);//запишем имя исполнения
				//теперь нужно записать список изменений
				//причем уже без массива флагов, а наживую
				//
				int j = 0;
				for(j = i+1; j < list.size() && list.get(j).charAt(0) == '-'; j++) {
					
					s = list.get(j);
					ch0 = s.charAt(0);
					//определять будем по первому символу	
					
					//ага, это внешний список
					
					Changelog changelog = new Changelog(); //создадим 
					s = clearPrefixes(s); //подчистим
					changelog.setOuterText(s);//положим
					//перебираем дальше строки, начинающиеся с пробела
					int k = 0;
					for(k = j + 1; k < list.size() && list.get(k).charAt(0) == ' '; k++) {
							
						//типа пока строка начинается с пробела, пихаем её во внутр. список
						s = list.get(k);
						s = clearPrefixes(s);
						changelog.addToInnerList(s);						
					}
						
					//шутки кончились
					//мы теоретически сожрали сколько-то индексов
					j = k - 1;//проскочили их, теперь сл. строка это или исполнение или внешний список
					//добавим теперь в прошивку ченджлог
					fw.addChangelog(changelog);
						
				}
			i = j - 1;//и тут проскочили, теперь сл. строка это исполнение скорее всего
			
			firmwares.add(fw);
				
			}
		
		}
		
	}
	
	
	
		
	public String clearPrefixes(String s) {
		//щас я вам почистию 
		String res;
		char ch0 = s.charAt(0);
		char chN = s.charAt(s.length() - 1);
		
		if(ch0 != '-' && ch0 != ' ' && chN == ':') res = s.substring(0, s.length() - 1);//подрежем двоеточие
		
		else if(ch0 == '-')  res = s.substring(2);//тяп дефис-пробел
		
		else if (ch0 == ' ') {
			
			char[] chr = s.toCharArray();//найдем количество этих пробелов			
			int j;
			for(j = 0; j < chr.length && chr[j] == ' '; j++);//;!!!	
			res = s.substring(j);//тяп пробелы нахуй
		}
		else res = s;
		
		return res;
	}
	
	
	public void printFirmwareList() {
		
		for(Firmware fw : firmwares) fw.print();			
		
	}

	
	public static void main(String[] args) throws Exception{
	
		
		LkdsFirmwareParser parser = new LkdsFirmwareParser("input.txt", "output.htm");
		parser.openAndReadInputFile(); //открываем и читаем файл в AL		
		parser.stringsToFirmwares();		
		parser.printFirmwareList();
		
		
		
	}
	
}
