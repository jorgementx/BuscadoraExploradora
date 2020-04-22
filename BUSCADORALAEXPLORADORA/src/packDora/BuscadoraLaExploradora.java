package packDora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class BuscadoraLaExploradora {
	
	private static BuscadoraLaExploradora nireBuscadoraLaExploradora = null;
	private BufferedReader br;
	private Connection konexioa;
	
	private BuscadoraLaExploradora() {}
	
	public static BuscadoraLaExploradora getNireBuscadoraLaExploradora() {
		if (nireBuscadoraLaExploradora == null) {
			nireBuscadoraLaExploradora = new BuscadoraLaExploradora();
		}
		return nireBuscadoraLaExploradora;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException, SQLException, ParseException {
		BuscadoraLaExploradora nireBLE = new BuscadoraLaExploradora();
		nireBLE.buscadoraHasieratu();
		
	}
	
	private void buscadoraHasieratu() throws NumberFormatException, IOException, SQLException, ParseException {
		this.konektatu();
		br = new BufferedReader(new InputStreamReader(System.in));
		this.buscadoraMenua();
	}
	
	private void konektatu() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String zerbitzaria = "jdbc:mysql://localhost:3306/alokairuak";
			String erabiltzailea = "root";
			String pasahitza = "";
			konexioa = DriverManager.getConnection(zerbitzaria, erabiltzailea, pasahitza);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
<<<<<<< HEAD
	private void buscadoraMenua() throws NumberFormatException, IOException, SQLException {
		int aukera;
		Teklatua teklado= Teklatua.getNireTeklatua();
		System.out.println("Aukeratu egin nahi duzuna, 1-etik 7-ra");
		System.out.println("1.- Egin erreserba");
		System.out.println("2.- Erregistratu bezeroa");
		System.out.println("3.- Erregistratu jabea");
		System.out.println("4.- Erregistratu hiria");
		System.out.println("5.- Lortu bezeroaren erreserbak");
		System.out.println("6.- Lortu jabearen pisuak");
		System.out.println("7.- Lortu hiriko pisuak");
		Boolean aukeraEgokia=false;
		while (!aukeraEgokia){
			aukera=teklado.irakurriZenb();
			if (aukera>=1 && aukera<=7){
				aukeraEgokia=true;
=======
	private void buscadoraMenua() throws NumberFormatException, IOException, SQLException, ParseException {
		Boolean irten=false;
		while (!irten){
			int aukera=10000;
			Teklatua teklado= Teklatua.getNireTeklatua();
			System.out.println("Aukeratu egin nahi duzuna, 1-etik 10-ra");
			System.out.println("1.- Egin erreserba");
			System.out.println("2.- Erregistratu bezeroa");
			System.out.println("3.- Erregistratu jabea");
			System.out.println("4.- Erregistratu pisua");
			System.out.println("5.- Erregistratu hiria");
			System.out.println("6.- Ezabatu bezeroa");
			System.out.println("7.- Lortu bezeroaren erreserbak");
			System.out.println("8.- Lortu jabearen pisuak");
			System.out.println("9.- Lortu hiriko pisuak");//hola soy muy tonto
			System.out.println("10.- Irten");
			Boolean aukeraEgokia=false;
			while (!aukeraEgokia){
				aukera=teklado.irakurriZenb();
				if (aukera>=1 && aukera<=10){
					aukeraEgokia=true;
				}
				else{
					System.out.println("Aukeratu 1-etik 10-erako zenbaki bat");
				}
>>>>>>> branch 'master' of https://github.com/jorgementx/BuscadoraExploradora.git
			}
			if (aukera==1){
			
			}	
			
			else if (aukera==2){
				System.out.println("Sartu NAN-a");
				int nan=teklado.irakurriZenb();
				System.out.println("Sartu izena");
				String izenaB=teklado.irakurriHitz();
				System.out.println("Sartu lehenengo abizena");
				String abizenaB1=teklado.irakurriHitz();
				System.out.println("Sartu bigarren abizena");
				String abizenaB2=teklado.irakurriHitz();
				System.out.println("Sartu jaiotze data dd/mm/aaaa");
				Date jaiotzeData= teklado.irakurriData();
				System.out.println(jaiotzeData);
				System.out.println("Sartu telefonoa");
				int telefonoa=teklado.irakurriZenb();
				System.out.println("sartu email-a");
				String email=teklado.irakurriHitz();
			}
		
			else if (aukera==3){ 
				
			}
		
			else if(aukera==4){
				
			}
			else if (aukera==5){
				
			}
		
			else if (aukera==6){ 
				this.ezabatuBezeroa();
			}
		
			else if(aukera==7){
				
			}
			else if(aukera==8){
				
			}
			else if(aukera==9){
				
			}
			else if(aukera==10){
				irten=true;
				this.konexioa.close(); //hemen egon behar da agindu hau (konexioa bakarrik itxi programatik irten nahi denean)
			}
		}
	}
	
	private void ezabatuBezeroa() throws SQLException {
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println();
		System.out.println("Sartu ezabatu nahi duzun bezeroaren NAN-a:");
		int pNan=Teklatua.getNireTeklatua().irakurriZenb();
		agindua="select * from bezeroa where nan="+pNan+";";
		ResultSet rs=st.executeQuery(agindua);
		if (rs.first()==false) {
			System.out.println();
			System.out.println("Ez da aurkitu bezerorik zehaztutako NAN-arekin.");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				this.ezabatuBezeroa();
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		else {
			agindua="delete from bezeroa where nan="+pNan+";";
			st.execute(agindua);
			System.out.println();
			System.out.println("Aukeratutako bezeroa modu egokian ezabatu egin da.");
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
}
