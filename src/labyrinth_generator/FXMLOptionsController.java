/*
MIT License

Copyright (c) 2016 Tondeur Herve

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
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
