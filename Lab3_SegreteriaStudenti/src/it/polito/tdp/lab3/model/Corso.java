package it.polito.tdp.lab3.model;

import java.util.ArrayList;
import java.util.List;

public class Corso {
	
	private String codIns;
	private int crediti;
	private String nome;
	private int pd;  // periodo didattico
	
	private List<Studente> iscritti; //relazione n:n
	
	public Corso(String codIns, int crediti, String nome, int pd) {
		this.codIns = codIns;
		this.crediti = crediti;
		this.nome = nome;
		this.pd = pd;
		
		this.iscritti = new ArrayList<>();
	}

	public void addIscritto(Studente s){
		iscritti.add(s);
	}
	
	public List<Studente> getIscritti(){
		return this.iscritti;
	}
	
	public String getCodIns() {
		return codIns;
	}
	public void setCodIns(String codIns) {
		this.codIns = codIns;
	}
	public int getCrediti() {
		return crediti;
	}
	public void setCrediti(int crediti) {
		this.crediti = crediti;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getPd() {
		return pd;
	}
	public void setPd(int pd) {
		this.pd = pd;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codIns == null) ? 0 : codIns.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Corso other = (Corso) obj;
		if (codIns == null) {
			if (other.codIns != null)
				return false;
		} else if (!codIns.equals(other.codIns))
			return false;
		return true;
	}

	
}
