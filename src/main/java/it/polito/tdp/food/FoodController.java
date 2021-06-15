/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    Food scelto;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer porzioni=0;
    	try {
    		porzioni=Integer.parseInt(txtPorzioni.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("Inserisci un numero intero di porzioni");
    		return;
    	}catch(NullPointerException npe) {
    		txtResult.setText("Inserisci un numero intero di porzioni");
    		return;
    	}
    	
    	model.creaGrafo(porzioni);
    	txtResult.appendText("Caratteristiche del grafo:\n#VERTICI = "+model.getNVertici()+"\n#ARCHI = "+model.getNArchi());
    	
    	boxFood.getItems().addAll(model.getVertici());
    	
    }
    
    
    @FXML
    void doCalorie(ActionEvent event) {
    	if(model.getGraph()==null)
    		txtResult.setText("Devi prima creare il grafo");
    	scelto=null;
    	try {
    		scelto=boxFood.getValue();
    	}catch(NullPointerException npe) {
    		txtResult.setText("Scegli un cibo");
    		return;
    	}
    	
    	txtResult.appendText("\nI 5 cibi con le massime calorie congiunte sono: \n");
    	List <Vicino> topFive= model.getTopFive(scelto);
    	if(topFive.size()==0) {
    		txtResult.appendText("Il vertice selezionato non è connesso a nulla");
    		return;
    	}
    	for(Vicino v: topFive)
    		txtResult.appendText(v.toString()+"\n");
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	if(model.getGraph()==null)
    		txtResult.setText("Devi prima creare il grafo");
    	Integer K; //stazioni di lavoro
    	try {
    		K=Integer.parseInt(txtK.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("Inserisci un numero intero di stazioni tra 1 e 10");
    		return;
    	}catch(NullPointerException npe) {
    		txtResult.setText("Inserisci un numero intero di stazioni tra 1 e 10");
    		return;
    	}
    	if(K<1||K>10) {
    		txtResult.setText("Inserisci un numero intero di stazioni tra 1 e 10");
    		return;
    	}
    	
    	model.simula(K,scelto);
    	
    	txtResult.appendText("\nIl numero di cibi preparati è: "+model.getNCucinati()+" in "+model.getTotTIME()+" minuti.");
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
