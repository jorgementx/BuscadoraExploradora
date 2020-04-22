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
	public Date irakurriData() throws ParseException{
		String entrada=this.sc.nextLine();
		DateFormat format = new SimpleDateFormat("DD/MM/YYYY");
		Date data = format.parse(entrada);
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
