package it.polito.tdp.lab3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	private static final String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root";
	
	public static Connection getConnection(){
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Ripropago la mia eccezione riportandola al chiamante 
			throw new RuntimeException("Errore nella connessione", e);
		}
	}

}
