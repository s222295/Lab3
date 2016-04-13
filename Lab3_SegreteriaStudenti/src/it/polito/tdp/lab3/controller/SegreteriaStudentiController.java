package it.polito.tdp.lab3.controller;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.SegreteriaModel;
import it.polito.tdp.lab3.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {
	
	private SegreteriaModel model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cbCorso;

    @FXML
    private TextField txtStudente;
    
    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnSpunta;

    @FXML
    private Button btnCerca;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtStato;

    @FXML
    private Button btnReset;

    @FXML
    void doCerca(ActionEvent event) {
    	btnSpunta.setDisable(true);
    	txtNome.setDisable(true);
    	txtCognome.setDisable(true);
    	String nomeCorso = cbCorso.getValue();
    	String studente = txtStudente.getText();
    	String stato="";
     	
    	if(nomeCorso!=null && studente.compareTo("")!=0){
    		if(!studente.matches("[0-9]+"))
        		stato="Dati non corretti";
    		else if(model.findStudenteByMatricola(Integer.parseInt(studente))==null)
    			stato="Lo studente non è presente nel DB";
    		else{
    			boolean b = model.iscrittoStudenteaCorso(nomeCorso, Integer.parseInt(studente));
    			Studente s = model.findStudenteByMatricola(Integer.parseInt(studente));
    			if(b==true)
    				stato=s.getNome()+" "+s.getCognome()+" ("+s.getMatricola()+") è iscritto al corso di \""+nomeCorso+"\"";
    			else
    				stato=s.getNome()+" "+s.getCognome()+" ("+s.getMatricola()+") non è iscritto al corso di \""+nomeCorso+"\"";
    		}
    	}else if(nomeCorso!=null){
    		List<Studente> iscritti=model.inscrittiCorso(nomeCorso);
    		if(iscritti.isEmpty())
    			stato ="Nessun iscritto al corso";
    		else{
    			for(Studente s:iscritti){
    				stato+=s.getMatricola()+"  "+s.getCognome()+"  "+ s.getNome()+"  "+s.getCds()+"\n";
    			}
    		}
    	}else if(studente!=null){
    		if(!studente.matches("[0-9]+"))
        		stato="Dati non corretti";
    		else if(model.findStudenteByMatricola(Integer.parseInt(studente))== null)
    			stato="Lo studente non è presente nel DB";
    		else{
    			List<Corso> corsifreq = model.corsiFrequentati(Integer.parseInt(studente));
    			if(corsifreq.isEmpty())
    				stato="Lo studente non è iscritto a nessun corso";
    			else{
    			for(Corso c:corsifreq){
    				stato+=c.getCodIns()+"  "+c.getCrediti()+"  "+c.getNome()+"  "+c.getPd()+"\n";
    			}
    			}
    		}
    	}
    	txtStato.setText(stato);

    }

    @FXML
    void doCompletamento(ActionEvent event) {
    	
    	String matricola = txtStudente.getText();
    	Studente s = model.findStudenteByMatricola(Integer.parseInt(matricola));
    	txtNome.setText(s.getNome());
    	txtCognome.setText(s.getCognome());
    	txtNome.setDisable(false);
    	txtCognome.setDisable(false);
    }

    @FXML
    void doIscrivi(ActionEvent event) {

    	String nomeCorso = cbCorso.getValue();
    	String studente = txtStudente.getText();
     	String stato="";
     	if(nomeCorso!=null && studente.compareTo("")!=0){
     		Studente s = model.findStudenteByMatricola(Integer.parseInt(studente));
     		if(model.iscrittoStudenteaCorso(nomeCorso, Integer.parseInt(studente))==true)
     			stato="Lo studente è già iscritto al corso";
     		else{
     			if(model.iscrizioneCorso(nomeCorso, Integer.parseInt(studente))==true)
     				stato=s.getNome()+" "+s.getCognome()+" ("+s.getMatricola()+") è stato iscritto al corso di \""+nomeCorso+"\"";
     			else
     				stato=s.getNome()+" "+s.getCognome()+" ("+s.getMatricola()+") non è possibile iscrivere al corso di \""+nomeCorso+"\"";
     		}
     	}else{
     			stato = "Campi non riempiti in modo corretto";
     		}
     	
     	txtStato.setText(stato);
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtNome.clear();
    	txtCognome.clear();
    	txtStudente.clear();
    	btnSpunta.setDisable(false);
    	cbCorso.setValue(null); 
    	txtStato.clear();

    }

    @FXML
    void initialize() {
        assert cbCorso != null : "fx:id=\"cbCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtStudente != null : "fx:id=\"txtStudente\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnSpunta != null : "fx:id=\"btnSpunta\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtStato != null : "fx:id=\"txtStato\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
//
//        List<String> listaCorsi = model.elencoNomiCorsi();
//        listaCorsi.add(0, "");
//        cbCorso.getItems().addAll(listaCorsi);
        
        txtNome.setDisable(true);
        txtCognome.setDisable(true);
        
    }
    
    public void setModel(SegreteriaModel model){
    	this.model=model;
    	List<String> listaCorsi = model.elencoNomiCorsi();
        listaCorsi.add(0, "");
        cbCorso.getItems().addAll(listaCorsi);
    }
}
