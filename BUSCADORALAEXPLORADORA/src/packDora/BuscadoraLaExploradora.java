package packDora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public static void main(String[] args) throws NumberFormatException, IOException, SQLException {
		BuscadoraLaExploradora nireBLE = new BuscadoraLaExploradora();
		nireBLE.buscadoraHasieratu();
		
	}
	
	private void buscadoraHasieratu() throws NumberFormatException, IOException, SQLException {
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
	
	private void buscadoraMenua() throws NumberFormatException, IOException, SQLException {
		int aukera;
		System.out.println("Aukeratu egin nahi duzuna, 1-etik 7-ra");
		System.out.println("1.- Egin erreserba");
		System.out.println("2.- Erregistratu bezeroa");
		System.out.println("3.- Erregistratu jabea");
		System.out.println("4.- Erregistratu hiria");
		System.out.println("5.- Lortu bezeroaren erreserbak");
		System.out.println("6.- Lortu jabearen pisuak");
		System.out.println("7.- Lortu hiriko pisuak");
		this.konexioa.close();
	}

}
