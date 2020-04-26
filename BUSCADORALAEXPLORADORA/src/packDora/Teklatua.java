package packDora;

import java.util.Scanner;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
		int zenb = 0;
		boolean denaOndo=false;
		do{
			try{
				String sar=this.sc.nextLine();
				zenb= Integer.parseInt(sar);
				denaOndo=true;
			}catch(NumberFormatException e){
				System.out.println("Sartutako nan-a zenbakizkoa izan behar da.");
			}
		}while(!denaOndo);
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
		String data="";
		int eguna=0;
		int hilabetea=0;
		int urtea=0;
		boolean egokia=false;
		while (!egokia) {
			egokia=true;
			System.out.print("Eguna: ");
			eguna=this.irakurriZenb();
			System.out.print("Hilabetea: ");
			hilabetea=this.irakurriZenb();
			System.out.print("Urtea: ");
			urtea=this.irakurriZenb();
			data=urtea+"-"+hilabetea+"-"+eguna;
			try {
	            SimpleDateFormat dataFormatua = new SimpleDateFormat("yyyy-MM-dd");
	            dataFormatua.setLenient(false);
	            dataFormatua.parse(data);
	            Date gaur=new Date();
				int gaurkoEguna=gaur.getDate();
				int gaurkoHilabetea=gaur.getMonth()+1;
				int gaurkoUrtea=gaur.getYear()+1900;
	            String gaurkoData=gaurkoUrtea+"-"+gaurkoHilabetea+"-"+gaurkoEguna;
	            if (gaurkoData.compareTo(data)<0) {
	            	throw new Exception();
	            }
	        }
			catch (ParseException e) {
	        	System.out.println();
	        	System.out.println("Sartutako data ez da egokia. Berriro saiatu:");
	        	System.out.println();
				egokia=false;
	        }
			catch (Exception e) {
	        	System.out.println();
	        	System.out.println("Sartutako data etorkizunekoa da. Berriro saiatu:");
	        	System.out.println();
				egokia=false;
			}
		}
		return data;
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
