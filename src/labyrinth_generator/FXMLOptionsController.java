/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth_generator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tondeur-h
 */
public class FXMLOptionsController implements Initializable {

    @FXML
    private ToggleGroup GroupTaille;
    @FXML
    private Button btnValider;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Spinner<Integer> spTailleX;
    @FXML
    private Spinner<Integer> spTailleY;
    @FXML
    private RadioButton rbAleatoire;
    @FXML
    private RadioButton rbSelectTaille;
    @FXML
    private ColorPicker ccFond;
    @FXML
    private ColorPicker ccMurs;

    private Stage stgLocal;
    private boolean fermeture;
    private boolean aleatoire;
    private SpinnerValueFactory svx,svy;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fermeture=false;
        aleatoire=true;
        svx = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 80);
        svy = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 60);
        spTailleX.setValueFactory(svx);
        spTailleY.setValueFactory(svy);
        ccFond.setValue(Color.BLUE);
    }

    public void setStage(Stage stg,int x,int y, Color fnd, Color frt){
        stgLocal=stg;
        ccFond.setValue(fnd);
        ccMurs.setValue(frt);
        svx.setValue(x);
        svy.setValue(y);
    }

    @FXML
    private void hbtnValider(ActionEvent event) {
        fermeture=true;
        stgLocal.close();
    }

    public int getTx(){
        return spTailleX.getValue();
    }

    public int getTy(){
        return spTailleY.getValue();
    }

    public Color getccFond(){
        return ccFond.getValue();
    }

    public Color getccMurs(){
        return ccMurs.getValue();
    }


    @FXML
    private void hbtnAnnuler(ActionEvent event) {
        stgLocal.close();
    }

    public boolean getFermeture(){
        return fermeture;
    }

    public boolean getAleatoire(){
        return aleatoire;
    }

    @FXML
    private void hrbAleatoire(ActionEvent event) {
        spTailleX.setDisable(true);
        spTailleY.setDisable(true);
        aleatoire=true;
    }

    @FXML
    private void hrbSelectTaille(ActionEvent event) {
        spTailleX.setDisable(false);
        spTailleY.setDisable(false);
        aleatoire=false;
    }

}
