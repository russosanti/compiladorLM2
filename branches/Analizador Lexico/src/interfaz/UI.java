package interfaz;

public class UI {
	
	public static void informar(String s){
		System.out.println(s);
	}
	
	public static void informar(String title, String s){
		System.out.println(title + ": " + s);
	}
	
	public static void error(String s){
		System.out.println(s);
		System.out.println();
	}
	
	public static void error(String title, String s){
		System.out.println(title + ": " + s);
		System.out.println();
	}
}
