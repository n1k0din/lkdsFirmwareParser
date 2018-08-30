import java.util.ArrayList;

public class Firmware {
	String name = "default";//название исполнения otis, kone, thyssen и т.д.
	final ArrayList<Changelog> list = new ArrayList<Changelog>(); //список изменений
	
	public void print() {
		
		System.out.println(name.toUpperCase()+":");
		for(Changelog c : list) c.print();
	
	}
	
	public void setName(String s) {
		
		name = s;
		
	}
	public void addChangelog(Changelog changelog) {
		
		list.add(changelog);
	}
}