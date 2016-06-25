package it.polito.esame;

import java.net.URL;
import java.util.Queue;
import java.util.ResourceBundle;

import it.polito.esame.model.Model;
import it.polito.esame.model.Parola;
import it.polito.esame.model.ParoleStats;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class ControllerT1 {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnCarica;

	@FXML
	private Button btnCerca;

	@FXML
	private BorderPane theRoot;

	@FXML
	private TextField txtLunghezza;

	@FXML
	private TextField txtParolaBFV;

	@FXML
	private TextField txtParolaDFV;

	@FXML
	private TextArea txtResult;

	
	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	void initialize() {
		assert btnCarica != null : "fx:id=\"btnCarica\" was not injected: check your FXML file 'parole.fxml'.";
		assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'parole.fxml'.";
		assert theRoot != null : "fx:id=\"theRoot\" was not injected: check your FXML file 'parole.fxml'.";
		assert txtLunghezza != null : "fx:id=\"txtLunghezza\" was not injected: check your FXML file 'parole.fxml'.";
		assert txtParolaBFV != null : "fx:id=\"txtParolaFinale\" was not injected: check your FXML file 'parole.fxml'.";
		assert txtParolaDFV != null : "fx:id=\"txtParolaIniziale\" was not injected: check your FXML file 'parole.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'parole.fxml'.";

		btnCerca.setDisable(true);

	}

	@FXML
	void doCaricaParole(ActionEvent event) {
		if(txtLunghezza.getText().isEmpty()){
			txtResult.setText("Errore: inserire una lunghezza \n");
			return;
		}
		try{
			int l = Integer.parseInt(txtLunghezza.getText());
			if(l<=0){
				txtResult.setText("Errore: inserire una lunghezza positiva \n");
				return;
			}
			ParoleStats result = model.caricaParole(l);
			txtResult.clear();
			txtResult.appendText("Numero di parole di lunghezza "
					+ txtLunghezza.getText()+" caricate: "+result.getTotParole()+"\n");
			
			txtResult.appendText("Numero di collegamenti: "+result.getTotColl()+"\n");
			txtResult.appendText("Parola con più collegamenti: "+result.getParola().getNome()+" ( "+result.getDegP()+" collegamenti )\n");
			
			/*
			 * ATT: avevo messo btnCerca.setVisible(true) ma non me lo dava...
			 * Ciò perché setVisible() vuol dire se lo vuoi far vedere o no, mentre
			 * setDisable() se vuoi consentirne il poterlo pigiare oppure no...Sono
			 * quindi due cose diverse!!!
			 */
			btnCerca.setDisable(false);
		}catch(NumberFormatException e){
			txtResult.setText("Errore: inserisci un numero come lunghezza \n");
		}

		
	}

	/**
	 * Action handler for the "Cerca" button: 
	 * @param event
	 */
	@FXML
	void doCercaParole(ActionEvent event) {
		if(txtParolaDFV.getText().isEmpty() || txtParolaBFV.getText().isEmpty() ){
			txtResult.setText("Errore: inserire le 2 parole \n");
			return;
		}
		if(txtParolaDFV.getText().length()!=Integer.parseInt(txtLunghezza.getText()) || txtParolaBFV.getText().length()!=Integer.parseInt(txtLunghezza.getText()) ){
			txtResult.setText("Errore: inserire 2 parole della lunghezza corretta \n");
			return;
		}
		String s1= txtParolaDFV.getText();
		String s2= txtParolaBFV.getText();
		
		//faccio una visita in ampiezza
		Queue<Parola> listBFV = model.visitaBFV(s1);
		
		//faccio una visita in profondità
		Queue<Parola> listDFV = model.visitaDFV(s2);
		
		txtResult.clear();
		Parola p;
		if(listBFV==null){
			txtResult.appendText("Non esiste la parola inserita per la visita BFV\n");
			
		}
		else{
			txtResult.appendText(" Visita BFV da "+s1+": \n");
			while((p= listBFV.poll())!=null)
				txtResult.appendText(p.getNome()+"  ");
		}
		if(listDFV==null){
			txtResult.appendText("\n Non esiste la parola inserita per la visita DFV\n");
		}
		else{
			txtResult.appendText("\n\n\n Visita DFV da "+s2+": \n");
			while((p= listDFV.poll())!=null)
				txtResult.appendText(p.getNome()+"  ");
		}
		
		
		
		
	}

}
