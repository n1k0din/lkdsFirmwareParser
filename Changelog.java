import java.util.ArrayList;
class Changelog {
	String outerText;//элемент списка внешнего списка
	final ArrayList<String> innerList = new ArrayList<String>(); //его подсписок
	
	public void Changelog() {
	
		outerText = "...";
	}
		
	public void setOuterText(String s) {
	
		outerText = s;
	}
		
	public void addToInnerList(String s) {
		
		innerList.add(s);
	}
	
	public void print() {
	System.out.println("*" + outerText);
	for(String s : innerList) System.out.println("***" + s);
	}
	
	
}