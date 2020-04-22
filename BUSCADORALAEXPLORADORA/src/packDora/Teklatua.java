package packDora;

import java.util.Scanner;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		String hitz=this.sc.nextLine();
		return hitz;
	}
	public void irakurriEnter() {
		this.sc.nextLine();
	}
	public String irakurriData() throws ParseException{
		int eguna=0;
		int hilabetea=0;
		int urtea=0;
		boolean egEgokia=false;
		boolean hilEgokia=false;
		boolean urteEgokia=false;
		System.out.print("Eguna: ");
		while (!egEgokia) {
			eguna=this.irakurriZenb();
			if (eguna>=1 && eguna<=31) {
				egEgokia=true;
			}
			else {
				System.out.print("1 eta 31 artean dagoen zenbaki bat idatzi mesedez:");
				System.out.println();
			}
		}
		System.out.print("Hilabetea: ");
		while (!hilEgokia) {
			hilabetea=this.irakurriZenb();
			if (hilabetea>=1 && hilabetea<=12) {
				hilEgokia=true;
			}
			else {
				System.out.print("1 eta 12 artean dagoen zenbaki bat idatzi mesedez:");
				System.out.println();
			}
		}
		System.out.print("Urtea: ");
		while (!urteEgokia) {
			urtea=this.irakurriZenb();
			if (Integer.toString(urtea).length()==4) {
				urteEgokia=true;
			}
			else {
				System.out.print("4 digituko zenbaki bat idatzi mesedez:");
				System.out.println();
			}
		}
		return urtea+"/"+hilabetea+"/"+eguna;
	}
	
	public boolean irakurriBaiEz() {
		String karakterea=this.irakurriHitz();
		boolean egokia=false;
		do {
			if (karakterea.length()>1) {
				System.out.println("Bakarrik karaktereak onartzen dira.");
				karakterea=this.irakurriHitz();
			}
			else if ((karakterea.equals("b")) || (karakterea.equals("B")) || (karakterea.equals("e")) || (karakterea.equals("E"))) {
				egokia=true;
			}
			else {
				System.out.println("Bakarrik 'B' (bai) eta 'E' (ez) karaktereak onartzen dira.");
				karakterea=this.irakurriHitz();
			}
		} while (!egokia);
		if ((karakterea.equals("b")) || (karakterea.equals("B"))) {
			return true;
		}
		else return false;
	}
}
