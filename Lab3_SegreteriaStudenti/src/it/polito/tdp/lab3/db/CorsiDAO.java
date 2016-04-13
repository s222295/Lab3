package it.polito.tdp.lab3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Studente;



public class CorsiDAO {
	
	public List<Corso> loadCorsi(){
		
		List<Corso> lista = new LinkedList<Corso>();
		
		try {
			Connection conn = DBConnect.getConnection();
			
			Statement st = conn.createStatement();
			
			String sql = "select * from corso";
			ResultSet res = st.executeQuery(sql);
			
			while(res.next()){
				String cod = res.getString("codins");
				int cfu = res.getInt("crediti");
				String nome = res.getString("nome");
				int pd = res.getInt("pd");
				lista.add(new Corso(cod, cfu, nome, pd));
			}
			
			res.close();
			conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
		
	}
	
	public  List<Studente> loadIscritti(String cod){
		Connection conn = DBConnect.getConnection();
		
		String sql = "Select matricola,cognome,nome,CDS from studente where matricola in (select matricola from iscrizione where codins=?) ;";
		List<Studente> lista = new LinkedList<Studente>();
		PreparedStatement st;
		
		try {
			

//			Statement st=conn.createStatement();
			st = conn.prepareStatement(sql);
			st.setString(1, cod);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()){
				lista.add(new Studente(res.getInt("matricola"), res.getString("cognome"), res.getString("nome"),res.getString("CDS")));
			}
			
			res.close();
			conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
		
	}

}
