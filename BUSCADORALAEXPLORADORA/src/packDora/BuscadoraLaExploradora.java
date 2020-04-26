package packDora;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

import javax.xml.crypto.Data;

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
		Scanner input = null;
		//Hasierako pantaila inprimatzeko:
		File f = new File("BuscadoraLaExploradora.txt");
		try {
			input = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("Ez da aurkitu fitxategia");
		}

		while (input.hasNextLine())
		{ 
		   System.out.println(input.nextLine());
		}
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

	private void buscadoraMenua() throws NumberFormatException, IOException, SQLException, ParseException {
		Boolean irten=false;
		while (!irten){
			int aukera=10000;
			Teklatua teklado= Teklatua.getNireTeklatua();
			System.out.println("Aukeratu egin nahi duzuna, 1-etik 15-ra");
			System.out.println("1.- Egin erreserba");
			System.out.println("2.- Erregistratu bezeroa");
			System.out.println("3.- Erregistratu jabea");
			System.out.println("4.- Erregistratu hiria");
			System.out.println("5.- Erregistratu pisua");
			System.out.println("6.- Ezabatu bezeroa");
			System.out.println("7.- Ezabatu jabea");
			System.out.println("8.- Ezabatu hiria");
			System.out.println("9.- Ezabatu pisua");
			System.out.println("10.- Ezabatu erreserba");
			System.out.println("11.- Lortu bezeroaren erreserbak");
			System.out.println("12.- Lortu jabearen pisuak");
			System.out.println("13.- Lortu hiriko pisuak");
			System.out.println("14.- Lortu mota konkretu bateko pisuak hiri konkretu batean");
			System.out.println("15.- Irten");
			Boolean aukeraEgokia=false;
			while (!aukeraEgokia){
				aukera=teklado.irakurriZenb();
				if (aukera>=1 && aukera<=15){
					aukeraEgokia=true;
				}
				else{
					System.out.println("Aukeratu 1-etik 15-rako zenbaki bat");
				}
			}
			if (aukera==1){
			
			}
			else if (aukera==2){
				this.sartuBezeroa();
			}
			else if (aukera==3){ 
				this.sartuJabea();
			}
			else if(aukera==4){
				this.sartuHiria();
			}
			else if (aukera==5){
				this.sartupisua();
			}
			else if (aukera==6){ 
				this.ezabatuBezeroa();
			}
			else if(aukera==7){
				this.ezabatuJabea();
			}
			else if(aukera==8){
				this.ezabatuHiria();
			}
			else if(aukera==9){
				this.ezabatuPisua();
			}
			else if(aukera==10){
				this.ezabatuErreserba();
			}
			else if(aukera==11){
				this.ordenatuBezeroak();
			}
			else if(aukera==12){
				
			}
			else if(aukera==13){
				
			}
			else if(aukera==14){
				this.motaHiriBatekoPisuaLortu();
			}
			else if(aukera==15){
				irten=true;
			}
		}
		this.konexioa.close(); //hemen egon behar da agindu hau (konexioa bakarrik itxi programatik irten nahi denean)
	}
	private void ordenatuBezeroak() throws SQLException{ 
		//ordenatu egingo ditu bezeroak eta haien erreserbak nan-a erakutsiz eta zenbateko kostua daukaten.
		
		Statement st=konexioa.createStatement();
		String agindua="select nan,abi1,abi2,kostua,pisuId,sarreraD,hirizena from bezeroa,erreserba,pisua order by nan desc ,abi1,abi2,kostua desc,pisuId,sarreraD desc,hirizena";
		ResultSet rs=st.executeQuery(agindua);
		while(rs.next()){
			int NAN=rs.getInt("nan");
			String Abizena1=rs.getString("abi1");
			String Abizena2=rs.getString("abi2");
			int Kostua=rs.getInt("kostua");
			int Pisua=rs.getInt("pisuId");
			Date data=rs.getDate("sarreraD");
			String Hirizena=rs.getString("hirizena");
			System.out.println(" "+NAN+" "+Abizena1+" "+Abizena2+" "+Kostua+""+""+Pisua+""+data+""+Hirizena);
		}
		System.out.println();
		System.out.println("Enter sakatu jarraitzeko.");
		Teklatua.getNireTeklatua().irakurriEnter();
		/*String agindua="select nan,abi1 from bezeroa order by nan,abi1 ";
		ResultSet rs=st.executeQuery(agindua);
		while(rs.next()){
			int n=rs.getInt("nan");
			System.out.println(n+""+"ab1="+rs.getObject("abi1"));
			
		}
		*/
	}
	private void sartuBezeroa() throws SQLException, ParseException {
		Teklatua teklado= Teklatua.getNireTeklatua();
		Statement st=konexioa.createStatement();
		System.out.println("Sartu NAN-a");
		int nan=teklado.irakurriZenb();
		System.out.println("Sartu izena");
		String izenaB=teklado.irakurriHitz();
		System.out.println("Sartu lehenengo abizena");
		String abizenaB1=teklado.irakurriHitz();
		System.out.println("Sartu bigarren abizena");
		String abizenaB2=teklado.irakurriHitz();
		System.out.println("Sartu jaiotze data dd/mm/aaaa");
		String jaiotzeData= teklado.irakurriData();
		System.out.println("Sartu telefonoa");
		int telefonoa=teklado.irakurriZenb();
		System.out.println("sartu email-a");
		String email=teklado.irakurriHitz();
		String agindua= ("INSERT INTO BEZEROA VALUES('"+nan+"', '"+izenaB+"', '"+abizenaB1+"', '"+abizenaB2+"', '"+jaiotzeData+"', '"+telefonoa+"', '"+email+"');");
		System.out.println(agindua);
		st.execute(agindua);
	}
	private void ezabatuBezeroa() throws SQLException {
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println();
		System.out.println("Sartu ezabatu nahi duzun bezeroaren NAN-a:");
		int pNan=Teklatua.getNireTeklatua().irakurriZenb();
		agindua="select * from bezeroa where nan="+pNan+";";
		ResultSet rs=st.executeQuery(agindua);
		if (!rs.first()) {
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
			agindua="select * from erreserba where bezeroNan="+pNan+";";
			rs=st.executeQuery(agindua);
			if (rs.first()) {
				System.out.println();
				System.out.println("Ezin izan da bezeroa ezabatu gutxienez erreserba batean erabiltzen delako.");
			}
			else {
				agindua="delete from bezeroa where nan="+pNan+";";
				st.execute(agindua);
				System.out.println();
				System.out.println("Aukeratutako bezeroa modu egokian ezabatu egin da.");
			}
			System.out.println();
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
	private void sartuJabea() throws SQLException, ParseException {
		Teklatua teklado= Teklatua.getNireTeklatua();
		Statement st=konexioa.createStatement();
		System.out.println("Sartu NAN-a");
		int nan=teklado.irakurriZenb();
		System.out.println("Sartu izena");
		String izenaJ=teklado.irakurriHitz();
		System.out.println("Sartu telefonoa");
		int telefonoa=teklado.irakurriZenb();
		System.out.println("sartu email-a");
		String email=teklado.irakurriHitz();
		System.out.println("Sartu banku kontua");
		int bKont=teklado.irakurriZenb();
		String agindua= ("INSERT INTO BEZEROA VALUES('"+nan+"', '"+izenaJ+"', '"+telefonoa+"', '"+email+"', '"+bKont+"');");
		System.out.println(agindua);
		st.execute(agindua);
	}
	private void ezabatuJabea() throws SQLException {
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println();
		System.out.println("Sartu ezabatu nahi duzun jabearen NAN-a:");
		int pNan=Teklatua.getNireTeklatua().irakurriZenb();
		agindua="select * from jabea where nan="+pNan+";";
		ResultSet rs=st.executeQuery(agindua);
		if (!rs.first()) {
			System.out.println();
			System.out.println("Ez da aurkitu jaberik zehaztutako NAN-arekin.");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				this.ezabatuJabea();
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		else {
			agindua="select * from pisua where jabeNan="+pNan+";";
			rs=st.executeQuery(agindua);
			if (rs.first()) {
				System.out.println();
				System.out.println("Ezin izan da jabea ezabatu gutxienez pisu batean erabiltzen delako.");
			}
			else {
				agindua="delete from jabea where nan="+pNan+";";
				st.execute(agindua);
				System.out.println();
				System.out.println("Aukeratutako jabea modu egokian ezabatu egin da.");
			}
			System.out.println();
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
	private void sartuHiria() throws SQLException, ParseException {
		Teklatua teklado= Teklatua.getNireTeklatua();
		Statement st=konexioa.createStatement();
		System.out.println("Sartu izena");
		String izena=teklado.irakurriHitz();
		System.out.println("Sartu herrialdea");
		String herrialde=teklado.irakurriHitz();
		System.out.println("Sartu kontinentea");
		String kontinente=teklado.irakurriHitz();
		System.out.println("Sartu posta kodea");
		int pKode=teklado.irakurriZenb();
		String agindua= ("INSERT INTO HIRIA VALUES('"+izena+"', '"+herrialde+"', '"+kontinente+"', '"+pKode+"');");
		System.out.println(agindua);
		st.execute(agindua);
	}
	private void ezabatuHiria() throws SQLException {
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println();
		System.out.println("Sartu ezabatu nahi duzun hiriaren izena:");
		String pIzen=Teklatua.getNireTeklatua().irakurriHitz();
		System.out.println();
		System.out.println("Sartu ezabatu nahi duzun hiriaren herrialdearen izena:");
		String pHerri=Teklatua.getNireTeklatua().irakurriHitz();
		agindua="select * from hiria where izena='"+pIzen+"' and herrialdea='"+pHerri+"';";
		ResultSet rs=st.executeQuery(agindua);
		if (!rs.first()) {
			System.out.println();
			System.out.println("Ez da aurkitu hiririk zehaztutako izena eta herrialdearekin.");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				this.ezabatuHiria();
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		else {
			agindua="select * from pisua where hirizena='"+pIzen+"' and hiriHerrialde='"+pHerri+"';";
			rs=st.executeQuery(agindua);
			if (rs.first()) {
				System.out.println();
				System.out.println("Ezin izan da hiria ezabatu gutxienez pisu batean erabiltzen delako.");
			}
			else {
				agindua="delete from hiria where izena='"+pIzen+"' and herrialdea='"+pHerri+"';";
				st.execute(agindua);
				System.out.println();
				System.out.println("Aukeratutako hiria modu egokian ezabatu egin da.");
			}
			System.out.println();
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
	private void sartupisua() throws SQLException, ParseException {
		Teklatua teklado= Teklatua.getNireTeklatua();
		Statement st=konexioa.createStatement();
		System.out.println("Sartu ID-a");
		int nan=teklado.irakurriZenb();
		System.out.println("Sartu izena");
		String izenaB=teklado.irakurriHitz();
		System.out.println("Sartu lehenengo abizena");
		String abizenaB1=teklado.irakurriHitz();
		System.out.println("Sartu bigarren abizena");
		String abizenaB2=teklado.irakurriHitz();
		System.out.println("Sartu jaiotze data dd/mm/aaaa");
		String jaiotzeData= teklado.irakurriData();
		System.out.println("Sartu telefonoa");
		int telefonoa=teklado.irakurriZenb();
		System.out.println("sartu email-a");
		String email=teklado.irakurriHitz();
		String agindua= ("INSERT INTO BEZEROA VALUES('"+nan+"', '"+izenaB+"', '"+abizenaB1+"', '"+abizenaB2+"', '"+jaiotzeData+"', '"+telefonoa+"', '"+email+"');");
		System.out.println(agindua);
		st.execute(agindua);
	}
	private void ezabatuPisua() throws SQLException {
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println();
		System.out.println("Sartu ezabatu nahi duzun pisuaren ID-a:");
		int pId=Teklatua.getNireTeklatua().irakurriZenb();
		agindua="select * from pisua where id="+pId+";";
		ResultSet rs=st.executeQuery(agindua);
		if (!rs.first()) {
			System.out.println();
			System.out.println("Ez da aurkitu pisurik zehaztutako ID-arekin.");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				this.ezabatuPisua();
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		else {
			agindua="select * from erreserba where pisuID="+pId+";";
			rs=st.executeQuery(agindua);
			if (rs.first()) {
				System.out.println();
				System.out.println("Ezin izan da pisua ezabatu gutxienez erreserba batean erabiltzen delako.");
			}
			else {
				agindua="delete from pisua where id="+pId+";";
				st.execute(agindua);
				System.out.println();
				System.out.println("Aukeratutako pisua modu egokian ezabatu egin da.");
			}
			System.out.println();
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
	
	private void ezabatuErreserba() throws SQLException, ParseException {
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println();
		System.out.println("Sartu ezabatu nahi duzun erreserbaren bezeroaren NAN-a:");
		int pNan=Teklatua.getNireTeklatua().irakurriZenb();
		System.out.println();
		System.out.println("Sartu ezabatu nahi duzun erreserbaren pisuaren ID-a:");
		int pID=Teklatua.getNireTeklatua().irakurriZenb();
		System.out.println();
		System.out.println("Sartu ezabatu nahi duzun erreserbaren sarrera-dataren informazioa:");
		System.out.println();
		String pSarreraD=Teklatua.getNireTeklatua().irakurriData();
		agindua="select * from erreserba where bezeroNan='"+pNan+"' and pisuID='"+pID+"' and sarreraD='"+pSarreraD+"';";
		ResultSet rs=st.executeQuery(agindua);
		if (!rs.first()) {
			System.out.println();
			System.out.println("Ez da aurkitu erreserbarik zehaztutako bezeroaren NAN-a, pisuaren ID-a eta sarrera-datarekin.");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				this.ezabatuErreserba();
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		else {
			agindua="delete from erreserba where bezeroNan="+pNan+" and pisuID="+pID+" and sarreraD='"+pSarreraD+"';";
			st.execute(agindua);
			System.out.println();
			System.out.println("Aukeratutako erreserba modu egokian ezabatu egin da.");
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}

	private void motaHiriBatekoPisuaLortu() throws SQLException {
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println();
		System.out.println("Sartu pisuaren mota:");
		String pMota=Teklatua.getNireTeklatua().irakurriHitz();
		System.out.println();
		System.out.println("Sartu pisuaren hiriaren izena:");
		String pIzen=Teklatua.getNireTeklatua().irakurriHitz();
		System.out.println();
		System.out.println("Sartu pisuaren hiriaren herrialdearen izena:");
		String pHerri=Teklatua.getNireTeklatua().irakurriHitz();
		System.out.println();
		agindua="select * from pisua having mota='"+pMota+"' and hirizena='"+pIzen+"' and hiriHerrialde='"+pHerri+"';";
		ResultSet rs=st.executeQuery(agindua);
		if (!rs.first()) {
			System.out.println();
			System.out.println("Ez da aurkitu pisurik zehaztutako mota, hiriaren izena eta hiriaren herrialdearen izenarekin.");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				this.motaHiriBatekoPisuaLortu();
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		else {
			agindua="select * from pisua having mota='"+pMota+"' and hirizena='"+pIzen+"' and hiriHerrialde='"+pHerri+"';";
			rs=st.executeQuery(agindua);
			while (rs.next()) {
				int id=rs.getInt("id");
				String mota=rs.getString("mota");
				int prezioGau=rs.getInt("prezioGau");
				int komunKop=rs.getInt("komunKop");
				float m2=rs.getFloat("m2");
				int pertsKopMax=rs.getInt("pertsKopMax");
				String egongela=rs.getString("egongela");
				String sukaldea=rs.getString("sukaldea");
				String kalea=rs.getString("kalea");
				int zenbakia=rs.getInt("zenbakia");
				int solairua=rs.getInt("solairua");
				char letra=rs.getString("letra").charAt(0);
				String hirizena=rs.getString("hirizena");
				String hiriHerrialde=rs.getString("hiriHerrialde");
				int jabeNan=rs.getInt("jabeNan");
				System.out.format("id: %s, mota: %s, prezioGau: %s, komunKop: %s, m2: %s, pertsKopMax: %s, egongela: %s, sukaldea: %s, kalea: %s, zenbakia: %s, solairua: %s, letra: %s, hirizena: %s, hiriHerrialde: %s, jabeNan: %s\n", id, mota, prezioGau, komunKop, m2, pertsKopMax, egongela, sukaldea, kalea, zenbakia, solairua, letra, hirizena, hiriHerrialde, jabeNan);
			}
			System.out.println();
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
}
