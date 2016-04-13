package it.polito.tdp.lab3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Studente;

public class StudenteDAO {
	
	//Possibili metodi che possono servire (CRUD)
	
	public void create(Studente s){
		Connection conn = DBConnect.getConnection();
		
		//String sql = "INSERT INTO `iscritticorsi`.`studente` (`matricola`, `cognome`, `nome`, `CDS`) VALUES ("+s.getMatricola()+", '"+s.getCognome()+"', '"+s.getNome()+"', '"+s.getCds()+"');";
		String sql = "INSERT INTO `iscritticorsi`.`studente` (`matricola`, `cognome`, `nome`, `CDS`) VALUES (?, '?', '?', '?');";
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, s.getMatricola());
			st.setString(2, s.getCognome());
			st.setString(3, s.getNome());
			st.setString(4, s.getCds());
			 
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public boolean updateIscritti(String cod, int matricola){
		Connection conn = DBConnect.getConnection();
		String sql = "INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES (?, ?);";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			
			st.setInt(1, matricola);
			st.setString(2, cod);
			
			int res =st.executeUpdate();
			if(res!=0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	
	}
	
	public Studente read(int matricola){
		Studente s=new Studente(matricola, null,null,null);
		if(read(s))
			return s;
		else
			return null;
	}
	
	public Studente readMIO(int matricola){
		Connection conn = DBConnect.getConnection();
		Studente s=null;
		//String sql = "INSERT INTO `iscritticorsi`.`studente` (`matricola`, `cognome`, `nome`, `CDS`) VALUES ("+s.getMatricola()+", '"+s.getCognome()+"', '"+s.getNome()+"', '"+s.getCds()+"');";
		String sql = "select * from studente where matricola=?;";
		 
		try {
			//Statement st = conn.createStatement();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			if(res.next()){
				s= new Studente(res.getInt("matricola"),
						                  res.getString("cognome"),
						                  res.getString("nome"),
						                  res.getString("cds"));
//				int id = res.getInt("matricola");
//				String cognome = res.getString("cognome");
//				String nome = res.getString("nome");
//				String cds = res.getString("CDS");
//				s=new Studente(id,cognome,nome,cds);
			}
			return s;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}
	
	//OPPURE
	/**
	 * Legge da DB lo Studente che possiede la matricola data da s.matricola, 
	 * e riempie di conseguenza tutti i rimanenti campi di s.
	 * 
	 * Se la matricola viene trovata, ritorna True,
	 * altrimenti ritorna false e non modifica nessun campo di s
	 * 
	 * @param s Lo studente da ricercare attraverso la matricola
	 *          e i cui campi andranno riempiti se la matricola esiste
	 * @return true se esiste, false se non è stato trovato         
	 */
	
	public boolean read(Studente s){
		
		Connection conn = DBConnect.getConnection();
		String sql = "SELECT * FROM studente WHERE matricola=?;";
		
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			
			st.setInt(1, s.getMatricola());
			ResultSet res = st.executeQuery();
			
			if(res.next()){
				s.setCognome(res.getString("cognome"));
				s.setNome(res.getString("nome"));
				s.setCds(res.getString("CDS"));
				
				return true;
			}else{
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//Nel caso dell'update per la natura del dato,
	//l'update può interessare solo il campo cds (corsi che segue)
	// NB. Anche in questo caso posso passare solo la matricola ma
	//     è meglio passare sempre l'intero oggetto
	public boolean updateCds(Studente s){
		String sql = "UPDATE studente SET CDS=? WHERE matricola=?";
		
		Connection conn = DBConnect.getConnection();
		PreparedStatement st;
		
		try {
			st= conn.prepareStatement(sql);
			st.setString(1, s.getCds());
			st.setInt(2, s.getMatricola());
			
			int n = st.executeUpdate();//restituisce il numero di righe modificate
			if(n==1) //Sto modificando solo un elemento
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateCds(int matricola, String cds){
		
		Studente s = new Studente(matricola, null, null, cds);
		return updateCds(s);
	}
	
	//il delete è meglio non farlo MAI!!!

	//Possono interessarmi magari anche delle ricerche non seocndo la matricola ma altri parametri
	public List<Studente> searchByCognome(String cognome){
		Connection conn = DBConnect.getConnection();
		
		String sql = "SELECT * from studente where cognome = ? ORDER BY matricola ASC;";
		List<Studente> result = new ArrayList<>();
		PreparedStatement st;
		
		try {
			st = conn.prepareStatement(sql);
			
			st.setString(1,  cognome);
			ResultSet res = st.executeQuery();
			while(res.next()){
				
				Studente s= new Studente(res.getInt("matricola"),
						                  res.getString("cognome"),
						                  res.getString("nome"),
						                  res.getString("cds"));
				result.add(s);
			}
			
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		}
	//public List<Studente> searchByCognome(Studente s){}   meno preferibile alla prima 
	
	public void fillFrequenta2 (Studente s, List<Corso> corsi){
		
		
	}
	
	public List<String> fillFrequenta (Studente s){
		Connection conn = DBConnect.getConnection();
		String sql = "Select * from corso where codins in (select codins from iscrizione where matricola=?) ;";
		List<String> lista = new ArrayList<>();
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			
			st.setInt(1, s.getMatricola());
			ResultSet res = st.executeQuery();
			
			while(res.next()){
				lista.add(res.getString("codins"));		
			}
			return lista;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
	

}
