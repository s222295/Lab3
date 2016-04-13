package it.polito.tdp.lab3.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab3.db.CorsiDAO;
import it.polito.tdp.lab3.db.StudenteDAO;

public class SegreteriaModel {
	

	List<Corso> corsi = new LinkedList<Corso>();
	//List<Studente> studenti = new LinkedList<Studente>();
	
	public SegreteriaModel(){
		CorsiDAO corsodb = new CorsiDAO();
		corsi = corsodb.loadCorsi();
	}
	
	public List<String> elencoNomiCorsi(){
		List<String> l = new LinkedList<String>();
		for(Corso c:corsi){
			l.add(c.getNome());
		}
		return l;
	}
	
	public Studente findStudenteByMatricola(int m){
		StudenteDAO dao = new StudenteDAO();
		Studente s=dao.readMIO(m);
		return s;
	}
	
	public List<Corso> corsiFrequentati(int matricola){
		Studente s = new Studente(matricola, null, null,null);
		StudenteDAO dao = new StudenteDAO();
		for(String t:dao.fillFrequenta(s)){
			Corso c = findCorso(t); 
			s.addFrequenta(c);
		}
		return s.getFrequenta();
	}
	public Corso findCorso(String cod){
		for(Corso c:corsi){
			if(c.getCodIns().compareTo(cod)==0)
				return c;
		}
		return null;
	}
	public Corso trovaCorso(String nome){
		for(Corso c:corsi){
			if(c.getNome().compareTo(nome)==0)
				return c;
		}
		return null;
	}
	public List<Studente> inscrittiCorso(String cod){
		CorsiDAO dao = new CorsiDAO();
		Corso c = trovaCorso(cod);
		List<Studente> l= dao.loadIscritti(c.getCodIns());
		for(Studente s:l){
			c.addIscritto(s);
		}
		return c.getIscritti();
	}
	
	public boolean iscrittoStudenteaCorso(String cod, int matricola){
		List<Studente> l= inscrittiCorso(cod);
		for(Studente s:l){
			if(s.getMatricola()==matricola)
				return true;
		}
		return false;
	}
	public boolean iscrizioneCorso(String cod, int matricola){
		StudenteDAO dao = new StudenteDAO();
		Corso c = trovaCorso(cod);
		return dao.updateIscritti(c.getCodIns(), matricola);
		
	}

}
