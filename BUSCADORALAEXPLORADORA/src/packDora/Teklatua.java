package packmysql;

import java.util.Scanner;

public class Teklatua{
	//atributua
	private Scanner sc;
	private static Teklatua nireTeklatua;
	
	//eraikitzailea
	private Teklatua (){
		this.sc=new Scanner (System.in);
	}
	
	//metodoak
	public static Teklatua getNireTeklatua(){
		if (nireTeklatua==null){
			nireTeklatua=new Teklatua();
		}
		return nireTeklatua;
	}
	
	public int irakurriZenb() throws NumberFormatException {
		String sar=this.sc.nextLine();
		int zenb= Integer.parseInt(sar);
		return zenb;
	}
	
	public String irakurriHitz() throws NumberFormatException {
		int sar=this.sc.nextInt();
		String hitz= Integer.toString(sar);
		return hitz;
	}
	public void irakurriEnter() {
		this.sc.nextLine();
	}
}
