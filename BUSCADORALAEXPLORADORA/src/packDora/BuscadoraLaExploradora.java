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
import java.util.Random;
import javax.xml.crypto.Data;

public class BuscadoraLaExploradora {
	
	private static BuscadoraLaExploradora nireBuscadoraLaExploradora = null;
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
			System.out.println();
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
			System.out.println("11.- Lortu pisu libreak");
			System.out.println("12.- Lortu bezeroaren erreserbak edo/eta pisuen prezioa");
			System.out.println("13.- Lortu jabearen pisuak");
			System.out.println("14.- Lortu hiriko pisuak");
			System.out.println("15.- Lortu mota konkretu bateko pisuak hiri konkretu batean");
			System.out.println("16.- Lortu pisuak aukeratutako prezio tartetan");
			System.out.println("17.- Gau kopurua aldatu");
			System.out.println("18.- Bezeroaren mugikorra aldatu");
			System.out.println("19.- Pisu baten jabea aldatu");
			System.out.println("20.- Pisuz aldatu");
			System.out.println("21.- Pisuaren kostua aldatu");
			System.out.println("22.- Irten");
			Boolean aukeraEgokia=false;
			while (!aukeraEgokia){
				aukera=teklado.irakurriZenb();
				if (aukera>=1 && aukera<=22){
					aukeraEgokia=true;
				}
				else{
					System.out.println("Aukeratu 1-etik 22-rako zenbaki bat");
				}
			}
			if (aukera==1){
				this.eginReserba(); 
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
			else if (aukera==11){
				this.pisuLibreak();
			}
			else if(aukera==12){
				this.ordenatuBezeroak();
			}
			else if(aukera==13){
				
			}
			else if(aukera==14){
				
			}
			else if(aukera==15){
				this.motaHiriBatekoPisuaLortu();
			}
			else if(aukera==16){
				this.prezioakTarteka();
			}
			else if(aukera==17){
				this.gauakAldatu();	
			}
			else if(aukera==18){
				this.mugikorraAldatu();
			}
			else if(aukera==19){
				this.jabeAldatu();
			}
			else if(aukera==20){
				
			}
			else if(aukera==21){
				this.kostuAldatu();
			}
			else if(aukera==22){
				irten=true;
			}
		}
		this.konexioa.close(); //hemen egon behar da agindu hau (konexioa bakarrik itxi programatik irten nahi denean)
	}
	private void prezioakTarteka() throws SQLException {
		//select count(*) from pisua group by round(prezioGau/3.0);
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println();
		System.out.println("Zenbateko prezio tarteak nahi dituzu?");
		int pTarte=Teklatua.getNireTeklatua().irakurriZenb();
		System.out.println();
		agindua="select round(prezioGau/"+pTarte+")*"+pTarte+",count(*),avg(preziogau) 'Batazbesteko prezioa', avg(m2) 'batazbesteko azalera',avg(pertskopmax) 'batazbesteko pertsonak' from pisua group by round(prezioGau/"+pTarte+");";
		ResultSet rs=st.executeQuery(agindua);
		if (!rs.first()) {
			System.out.println();
			System.out.println("Ez dago pisurik sisteman");
		}
		else {
			agindua="select round(prezioGau/"+pTarte+")*"+pTarte+",count(*),avg(preziogau) 'Batazbesteko prezioa', avg(m2) 'batazbesteko azalera',avg(pertskopmax) 'batazbesteko pertsonak' from pisua group by round(prezioGau/"+pTarte+");";
			rs=st.executeQuery(agindua);
			while (rs.next()) {
				int goiMuga;
				int beheMuga;
				int taldea=rs.getInt(1);
				int kop=rs.getInt(2);
				float avgPrez=rs.getFloat(3);
				float avgM2=rs.getFloat(4);
				float avgPerts=rs.getFloat(5);
				if (pTarte%2==0){
					goiMuga=taldea+pTarte/2-1;
					beheMuga=taldea-pTarte/2;
				}
				else{
					goiMuga=taldea+pTarte/2;
					beheMuga=taldea-pTarte/2;
				}
				
				System.out.format("tartea: %s-%s, kopurua: %s, batazbesteko prezioa: %s, batazbesteko azalera: %s, batazbesteko pertsonak: %s\n", beheMuga,goiMuga,kop,avgPrez,avgM2,avgPerts);
			}
			System.out.println();
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
		
	}

	private void pisuLibreak() throws SQLException {
		// select id , mota from pisua where not exists(select * from erreserba where id = pisuId);
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println();
		
		agindua="select * from pisua where not exists(select * from erreserba where id = pisuId);";
		ResultSet rs=st.executeQuery(agindua);
		if (!rs.first()) {
			System.out.println();
			System.out.println("Ez dago pisu librerik momentu honetan, barkatu eragozpenak");
		}
		else {
			agindua="select * from pisua where not exists(select * from erreserba where id = pisuId);";
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

	private void ordenatuBezeroak() throws SQLException{ 
		//ordenatu egingo ditu bezeroak eta haien erreserbak nan-a erakutsiz eta zenbateko kostua daukaten
		
		Statement st=konexioa.createStatement();
		System.out.println("Begiratu nahi dituzu bezeroen erreserbak? Bai edo ez");
		boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
		if(erantzuna){
			String agindua="select nan,abi1,abi2,kostua,pisuId,sarreraD from bezeroa,erreserba where bezeroNan=nan order by nan desc ,abi1,abi2,kostua desc,pisuId,sarreraD desc";
			ResultSet rs=st.executeQuery(agindua);
			if(!rs.first()){
				System.out.println();
				System.out.println("Badirudi datu-basea hutsik dagola,mesedez datuak sartu.");
			}
			else{
				 rs=st.executeQuery(agindua);
				while(rs.next()){
					int NAN=rs.getInt("nan");
					String Abizena1=rs.getString("abi1");
					String Abizena2=rs.getString("abi2");
					int Kostua=rs.getInt("kostua");
					int Pisua=rs.getInt("pisuId");
					Date data=rs.getDate("sarreraD");
					
					System.out.println("Nan: "+NAN+" Abizena1: "+Abizena1+" Abizena2: "+Abizena2+" Kostua: "+Kostua+" Pisua: "+Pisua+" Data: "+data+" ");
				}
			System.out.println();
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
			System.out.println("Orain begiratu nahi dituzu pisuen prezioa gau batez?");
			boolean emaitza=Teklatua.getNireTeklatua().irakurriBaiEz();
				if(emaitza){
					this.ordenatuPisuaPrezioa();
					System.out.println("Enter sakatu jarraitzeko.");
					Teklatua.getNireTeklatua().irakurriEnter();
				}
				else{
					System.out.println("Enter sakatu jarraitzeko.");
					Teklatua.getNireTeklatua().irakurriEnter();
				}
			}	
			
		}
		else{
			this.ordenatuPisuaPrezioa();
			System.out.println();
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
	
	private void ordenatuPisuaPrezioa() throws SQLException{
		Statement st=konexioa.createStatement();
		String agindua="select prezioGau,id from pisua order by prezioGau desc ,id";
		ResultSet rs=st.executeQuery(agindua);
		if(!rs.first()){
			System.out.println();
			System.out.println("Badirudi ez dagoela informazioa pisuei buruz");
		}
		else{
			rs=st.executeQuery(agindua);
			while(rs.next()){
				int PrezioGau=rs.getInt("prezioGau");
				int Id=rs.getInt("id");
				System.out.println("Prezio Gau: "+PrezioGau+" Id-a: "+Id);
			}
		}
	}
	private void eginReserba() throws SQLException, ParseException {
		Teklatua teklado= Teklatua.getNireTeklatua();
		Statement st=konexioa.createStatement();
		String agindua;
		int prezioGau;
		boolean erantzuna=true;
		System.out.println("Sartu zure NAN-a, mesedez");
		int nan=teklado.irakurriZenb();
		agindua="select * from bezeroa where nan="+nan+";";
		ResultSet rs=st.executeQuery(agindua);
		while(!rs.first()) {
			System.out.println("Sartutako NAN-a ez dago bezeroen zerrendan");
			System.out.println("Sartu NAN-a:");
			nan=teklado.irakurriZenb();
			agindua="select * from bezeroa where nan="+nan+";";
			rs=st.executeQuery(agindua);
		}
		System.out.println("Sartu erreserbatu nahi duzun pisuaren ID-a");
		int pisuId=teklado.irakurriZenb();
		agindua="select * from pisua where id="+pisuId+";";
		rs=st.executeQuery(agindua);
		while (!rs.first()) {
			System.out.println();
			System.out.println("Ez da aurkitu pisurik zehaztutako ID-arekin.");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				System.out.println("Sartu erreserbatu nahi duzun pisuaren ID-a");
				pisuId=teklado.irakurriZenb();
				agindua="select * from pisua where id="+pisuId+";";
				rs=st.executeQuery(agindua);
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		prezioGau=rs.getInt(3);
		System.out.println(prezioGau);
		if (erantzuna==true) {
		System.out.println("Sartu sarrera data dd/mm/aaaa");
		String sarreraData= teklado.irakurriData();
		System.out.println("Sartu nahi dituzun gau kopurua");
		int gauKop=teklado.irakurriZenb();
		int prezioa=gauKop*prezioGau;
		agindua= ("INSERT INTO ERRESERBA VALUES('"+nan+"', '"+pisuId+"', '"+sarreraData+"', '"+gauKop+"', '"+prezioa+"');");
		System.out.println(agindua);
		st.execute(agindua);
		System.out.println("Erreserba ondo erregistratu da!");
		}
	}
	
	private void sartuBezeroa() throws SQLException, ParseException {
		Teklatua teklado= Teklatua.getNireTeklatua();
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println("Sartu NAN-a");
		int nan=teklado.irakurriZenb();
		agindua="select * from bezeroa where nan="+nan+";";
		ResultSet rs=st.executeQuery(agindua);
		while (rs.first()) {
			System.out.println("Sartutako NAN-a jadanik badago sisteman");
			System.out.println("Sartu NAN-a");
			nan=teklado.irakurriZenb();
			agindua="select * from bezeroa where nan="+nan+";";
			rs=st.executeQuery(agindua);
		}
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
		agindua= ("INSERT INTO BEZEROA VALUES('"+nan+"', '"+izenaB+"', '"+abizenaB1+"', '"+abizenaB2+"', '"+jaiotzeData+"', '"+telefonoa+"', '"+email+"');");
		System.out.println(agindua);
		st.execute(agindua);
		System.out.println("Bezeroa ondo erregistratu da!");
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
			System.out.println("Bezero gehiago ezabatu nahi al duzu? (B/E)");
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
	}
	private void sartuJabea() throws SQLException, ParseException {
		Teklatua teklado= Teklatua.getNireTeklatua();
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println("Sartu NAN-a");
		int nan=teklado.irakurriZenb();
		agindua="select * from bezeroa where nan="+nan+";";
		ResultSet rs=st.executeQuery(agindua);
		while (rs.first()) {
			System.out.println("Sartutako NAN-a jadanik badago sisteman");
			System.out.println("Sartu NAN-a");
			nan=teklado.irakurriZenb();
			agindua="select * from bezeroa where nan="+nan+";";
			rs=st.executeQuery(agindua);
		}
		System.out.println("Sartu izena");
		String izenaJ=teklado.irakurriHitz();
		System.out.println("Sartu telefonoa");
		int telefonoa=teklado.irakurriZenb();
		System.out.println("sartu email-a");
		String email=teklado.irakurriHitz();
		System.out.println("Sartu banku kontua");
		int bKont=teklado.irakurriZenb();
		agindua= ("INSERT INTO JABEA VALUES('"+nan+"', '"+izenaJ+"', '"+telefonoa+"', '"+email+"', '"+bKont+"');");
		System.out.println(agindua);
		st.execute(agindua);
		System.out.println("Jabea ondo erregistratu da!");
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
			System.out.println("Jabe gehiago ezabatu nahi al duzu? (B/E)");
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
	}
	private void sartuHiria() throws SQLException, ParseException {
		Teklatua teklado= Teklatua.getNireTeklatua();
		Statement st=konexioa.createStatement();
		String agindua;
		System.out.println("Sartu izena");
		String izena=teklado.irakurriHitz();
		System.out.println("Sartu herrialdea");
		String herrialde=teklado.irakurriHitz();
		agindua="select * from hiria where izena='"+izena+"' and herrialdea='"+herrialde+"';";
		ResultSet rs=st.executeQuery(agindua);
		if (rs.first()) {
			System.out.println();
			System.out.println("sartu nahi duzun hiria sisteman dago jadanik");
		}
		else{
		System.out.println("Sartu kontinentea");
		String kontinente=teklado.irakurriHitz();
		System.out.println("Sartu posta kodea");
		int pKode=teklado.irakurriZenb();
		agindua= ("INSERT INTO HIRIA VALUES('"+izena+"', '"+herrialde+"', '"+kontinente+"', '"+pKode+"');");
		System.out.println(agindua);
		st.execute(agindua);}
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
			System.out.println("Hiri gehiago ezabatu nahi al duzu? (B/E)");
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
	}
	private void sartupisua() throws SQLException, ParseException {
		Teklatua teklado= Teklatua.getNireTeklatua();
		Statement st=konexioa.createStatement();
		String agindua;
		boolean erantzuna;
		int jabeNan=0;
		Random aux= new Random (System.nanoTime());
		int id= aux.nextInt(1000000000);
		agindua="select * from pisua where id="+id+";";
		ResultSet rs=st.executeQuery(agindua);
		while (rs.first()) {
			aux= new Random (System.nanoTime());
			id= aux.nextInt(1000000000);
			agindua="select * from pisua where id="+id+";";
			rs=st.executeQuery(agindua);
		}
		System.out.println("Sartu mota: pisua, hotela, apartamentua... ");
		String mota=teklado.irakurriHitz();
		System.out.println("Sartu prezioa gaueko(prezio osoak):");
		int prezioGau=teklado.irakurriZenb();
		System.out.println("Sartu komun kopurua:");
		int komunKop=teklado.irakurriZenb();
<<<<<<< HEAD
		System.out.println("Sartu metro karratuak");
		float m2= teklado.irakurriFloat();
		System.out.println("Sartu pertsona kopuru maximoa");
		int pkopMax=teklado.irakurriZenb();
		System.out.println("Sartu egongela duen: Bai/Ez");
		String egongela=teklado.irakurriHitz();
		System.out.println("Sartu sukaldea duen: Bai/Ez");
		String sukaldea=teklado.irakurriHitz();
		System.out.println("Sartu kalea");
		String kalea=teklado.irakurriHitz();
		System.out.println("Sartu kalearen zenbakia");
		int kaleZenb=teklado.irakurriZenb();
		System.out.println("Sartu solairuaren zenbakia");
		int solairuZenb=teklado.irakurriZenb();
		System.out.println("Sartu letra");
		String pLetra=teklado.irakurriHitz();
		System.out.println("Sartu hiria");
		String hiriIzena=teklado.irakurriHitz();
		System.out.println("Sartu herrialdea");
		String hiriHerrialde=teklado.irakurriHitz();
		agindua="select * from hiria where izena='"+hiriIzena+"' and herrialdea='"+hiriHerrialde+"';";
		rs=st.executeQuery(agindua);
		erantzuna=true;
		while (!rs.first()) {
			System.out.println();
			System.out.println("Ez da aurkitu hiririk zehaztutako izena eta herrialdearekin.");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				System.out.println("Sartu hiria");
				hiriIzena=teklado.irakurriHitz();
				System.out.println("Sartu herrialdea");
				hiriHerrialde=teklado.irakurriHitz();
				agindua="select * from hiria where izena='"+hiriIzena+"' and herrialdea='"+hiriHerrialde+"';";
				rs=st.executeQuery(agindua);
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		if (erantzuna==true) {
		System.out.println("Sartu jabearen NANa");
		jabeNan=teklado.irakurriZenb();
		agindua="select * from jabea where nan="+jabeNan+";";
		rs=st.executeQuery(agindua);
		while (!rs.first()) {
			System.out.println();
			System.out.println("Ez da aurkitu jaberik zehaztutako NAN-arekin.");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				System.out.println("Sartu jabearen NANa");
				jabeNan=teklado.irakurriZenb();
				agindua="select * from jabea where nan="+jabeNan+";";
				rs=st.executeQuery(agindua);
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}}
		if (erantzuna==true) {
		agindua= ("INSERT INTO PISUA VALUES('"+id+"', '"+mota+"', '"+prezioGau+"', '"+komunKop+"', '"+m2+"', '"+pkopMax+"', '"+egongela+"', '"+sukaldea+"', '"+kalea+"', '"+kaleZenb+"', '"+solairuZenb+"', '"+pLetra+"', '"+hiriIzena+"', '"+hiriHerrialde+"', '"+jabeNan+"');");
=======
		System.out.println("Sartu jaiotze data dd/mm/aaaa");
		String jaiotzeData= teklado.irakurriData();
		System.out.println("Sartu telefonoa");
		int telefonoa=teklado.irakurriZenb();
		System.out.println("sartu email-a");
		String email=teklado.irakurriHitz();
		String agindua= ("INSERT INTO PISUA VALUES('"+id+"', '"+mota+"', '"+prezioGau+"', '"+komunKop+"', '"+jaiotzeData+"', '"+telefonoa+"', '"+email+"');");
>>>>>>> branch 'master' of https://github.com/jorgementx/BuscadoraExploradora.git
		System.out.println(agindua);
		st.execute(agindua);
		System.out.println("pisua ondo erregistratu da eta bere ID-a hau da:  "+id);
		}
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
			System.out.println("Pisu gehiago ezabatu nahi al duzu? (B/E)");
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
			System.out.println();
			System.out.println("Erreserba gehiago ezabatu nahi al duzu? (B/E)");
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

	private void gauakAldatu() throws NumberFormatException,ParseException,SQLException{
		Statement st=konexioa.createStatement();
		Teklatua teklado= Teklatua.getNireTeklatua();
		System.out.println("Sartu zure NAN zenbakia");
		int pNAN=Teklatua.getNireTeklatua().irakurriZenb();
		System.out.println();
		System.out.println("Sartu pisuaren id-a");
		int pIdPisu=Teklatua.getNireTeklatua().irakurriZenb();
		System.out.println();
		System.out.println("Sartu erreserbaren sarrera data");
		String pSarreraD=Teklatua.getNireTeklatua().irakurriData();
		System.out.println();
		String agindua;
		agindua="select * from erreserba having bezeroNAN='"+pNAN+"' and pisuID='"+pIdPisu+"' and sarreraD='"+pSarreraD+"';";
		ResultSet rs=st.executeQuery(agindua);
		if(!rs.first()){
			System.out.println();
			System.out.println("Ez da aurkitu erreserbarik hautatutako datuekin");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if(erantzuna==true){
				this.gauakAldatu();
			}
			else{
				System.out.println();
				System.out.println("Ados. ");
				System.out.println("Enter sakatu jarraitzko. ");
				Teklatua.getNireTeklatua().irakurriEnter();	
			}
		}
		else{
			System.out.println("Sartu nahi dituzun gau kopurua");
			int pGauKop=Teklatua.getNireTeklatua().irakurriZenb();
			System.out.println();
			agindua="update * from erreserba gauKop='"+pGauKop+"';";
			st.execute(agindua);
			System.out.println("Enter sakatu jarraitzko. ");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
	private void mugikorraAldatu() throws SQLException, NumberFormatException, ParseException{
		Statement st=konexioa.createStatement();
		Teklatua teklado= Teklatua.getNireTeklatua();
		String agindua;
		System.out.println("Sartu zure NAN zenbakia");	
		int pNAN=Teklatua.getNireTeklatua().irakurriZenb();
		agindua="select * from erreserba having bezeroNAN='"+pNAN+"';";
		ResultSet rs=st.executeQuery(agindua);
		if(!rs.first()){
			System.out.println();
			System.out.println("Ez da aurkitu bezerorik");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if(erantzuna==true){
				this.gauakAldatu();
			}
			else{
				System.out.println();
				System.out.println("Ados. ");
				System.out.println("Enter sakatu jarraitzko. ");
				Teklatua.getNireTeklatua().irakurriEnter();	
			}
		}
		else{
			System.out.println();
			System.out.println("Sartu zure telefono zenbaki berria");
			int ptelf=Teklatua.getNireTeklatua().irakurriZenb();
			System.out.println();
			agindua="update * from bezeroa telef='"+ptelf+"';";
			st.execute(agindua);
			System.out.println("Enter sakatu jarraitzko. ");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
	private void jabeAldatu() throws NumberFormatException,SQLException {
		Statement st=konexioa.createStatement();
		Teklatua teklado= Teklatua.getNireTeklatua();
		String agindua;
		System.out.println("Sartu pisuaren id-a");
		int pID=Teklatua.getNireTeklatua().irakurriZenb();
		agindua="select * from pisua having id='"+pID+"';";
		ResultSet rs=st.executeQuery(agindua);
		if(!rs.first()){
			System.out.println();
			System.out.println("Ez da aurkitu pisurik");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				this.kostuAldatu();
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		else{
			System.out.println();
			System.out.println("Sartu jabearen NAN-a");
			int pNAN=Teklatua.getNireTeklatua().irakurriZenb();
			System.out.println();
			System.out.println("Sartu jabearen izena");
			String pIzen=Teklatua.getNireTeklatua().irakurriHitz();
			System.out.println();
			System.out.println("Sartu jabearen telefono zenbakia");
			int ptelf=Teklatua.getNireTeklatua().irakurriZenb();
			System.out.println();
			System.out.println("Sartu jabearen email-a");
			String pEmail=Teklatua.getNireTeklatua().irakurriHitz();
			System.out.println();
			System.out.println("Sartu jabearen banku kontu zenbakia");
			int pbank=Teklatua.getNireTeklatua().irakurriZenb();
			System.out.println();
			agindua="update * from pisua jabeNan='"+pNAN+"';";
			st.execute(agindua);
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}
	private void pisuzAldatu(){
		
	}
	private void kostuAldatu() throws NumberFormatException,SQLException {
		Statement st=konexioa.createStatement();
		Teklatua teklado= Teklatua.getNireTeklatua();
		String agindua;
		System.out.println("Sartu pisuaren id-a");
		int pID=Teklatua.getNireTeklatua().irakurriZenb();
		agindua="select * from pisua having id='"+pID+"';";
		ResultSet rs=st.executeQuery(agindua);
		if(!rs.first()){
			System.out.println();
			System.out.println("Ez da aurkitu pisurik");
			System.out.println("Berriro saiatu nahi duzu? (B/E)");
			boolean erantzuna=Teklatua.getNireTeklatua().irakurriBaiEz();
			if (erantzuna==true) {
				this.kostuAldatu();
			}
			else {
				System.out.println();
				System.out.println("Ados.");
				System.out.println("Enter sakatu jarraitzeko.");
				Teklatua.getNireTeklatua().irakurriEnter();
			}
		}
		else{
			System.out.println();
			System.out.println("Sartu pisuaren prezioa berria gauero");
			int pPrezio=Teklatua.getNireTeklatua().irakurriZenb();
			System.out.println();
			agindua="update * from pisua prezioGau='"+pPrezio+"';";
			st.execute(agindua);
			System.out.println("Enter sakatu jarraitzeko.");
			Teklatua.getNireTeklatua().irakurriEnter();
		}
	}

}

