import java.util.ArrayList;


public class LkdsFirmwareParser{
	
	private String input;//���� ��������
	private String output;//���� ���������
	private ArrayList<String> list;//������ ����� �� ��������� �����
	private FileWorker fileWorker;//������ ��� ������ � �������	
		
	private FileWorker fwNameList; //��� ��� ������ ����������, �� �����
	private ArrayList<String> nameList; //������ ���������� �� ����� ����������, �� �����
	
	
	final private ArrayList<Firmware> firmwares = new ArrayList<Firmware>(); //������ ��������
	//��� �������� = ������������ ���������� + ������ ���������, ��� ��������� ��� 
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
	
		//�������� �� ������ ����
		//�������� ������:
		//���� list �� ����� ��������� �������-��������
		//���� nameList �� ������� ����������
		//������ ���� ��������� ArrayList<Firmware> firmwares;
		//���� ���������� �� � ������� ��� ������, � ������������� ���������� - ��� firmware.name;
		
		for(int i = 0; i < list.size(); i++) {
		
			String s = list.get(i);
			char ch0 = s.charAt(0);
			char chN = s.charAt(s.length() - 1);
			
			if(ch0 != '-' && ch0 != ' ' &&  chN == ':') {//������ �� ��������
			//	
				//System.out.println("���������� ���������� �� ������ " + i);
				Firmware fw = new Firmware();//�������� ��������		
				s = clearPrefixes(s);
				fw.setName(s);//������� ��� ����������
				//������ ����� �������� ������ ���������
				//������ ��� ��� ������� ������, � �������
				//
				int j = 0;
				for(j = i+1; j < list.size() && list.get(j).charAt(0) == '-'; j++) {
					
					s = list.get(j);
					ch0 = s.charAt(0);
					//���������� ����� �� ������� �������	
					
					//���, ��� ������� ������
					
					Changelog changelog = new Changelog(); //�������� 
					s = clearPrefixes(s); //���������
					changelog.setOuterText(s);//�������
					//���������� ������ ������, ������������ � �������
					int k = 0;
					for(k = j + 1; k < list.size() && list.get(k).charAt(0) == ' '; k++) {
							
						//���� ���� ������ ���������� � �������, ������ � �� �����. ������
						s = list.get(k);
						s = clearPrefixes(s);
						changelog.addToInnerList(s);						
					}
						
					//����� ���������
					//�� ������������ ������� �������-�� ��������
					j = k - 1;//���������� ��, ������ ��. ������ ��� ��� ���������� ��� ������� ������
					//������� ������ � �������� ��������
					fw.addChangelog(changelog);
						
				}
			i = j - 1;//� ��� ����������, ������ ��. ������ ��� ���������� ������ �����
			
			firmwares.add(fw);
				
			}
		
		}
		
	}
	
	
	
		
	public String clearPrefixes(String s) {
		//��� � ��� �������� 
		String res;
		char ch0 = s.charAt(0);
		char chN = s.charAt(s.length() - 1);
		
		if(ch0 != '-' && ch0 != ' ' && chN == ':') res = s.substring(0, s.length() - 1);//�������� ���������
		
		else if(ch0 == '-')  res = s.substring(2);//��� �����-������
		
		else if (ch0 == ' ') {
			
			char[] chr = s.toCharArray();//������ ���������� ���� ��������			
			int j;
			for(j = 0; j < chr.length && chr[j] == ' '; j++);//;!!!	
			res = s.substring(j);//��� ������� �����
		}
		else res = s;
		
		return res;
	}
	
	
	public void printFirmwareList() {
		
		for(Firmware fw : firmwares) fw.print();			
		
	}

	
	public static void main(String[] args) throws Exception{
	
		
		LkdsFirmwareParser parser = new LkdsFirmwareParser("input.txt", "output.htm");
		parser.openAndReadInputFile(); //��������� � ������ ���� � AL		
		parser.stringsToFirmwares();		
		parser.printFirmwareList();
		
		
		
	}
	
}
