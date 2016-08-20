/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth_generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/******************
 *
 * @author tondeurh
 ******************/
public class Labyrinth_Generator extends Application {

    int tailleLabyX=(int)(Math.random()*77)+3;
    int tailleLabyY=(int)(Math.random()*57)+3;
    boolean debug=false;
    Color frontColor=Color.BLUEVIOLET;
    Color backColor=Color.BLACK;
    int fenetreX=tailleLabyX*10;
    int fenetreY=tailleLabyY*10;
    Canvas dessine_labyrinthe;
    Dessiner_labyrintheBMP drawLab;
    MenuBar mnbar;
    Scene scene;
    BorderPane root;
    boolean aleatoire=true;
    Labyrinthe lab;

    /***********************
     *
     * @param primaryStage
     ***********************/
    @Override
    public void start(Stage primaryStage)
    {
        root = new BorderPane();
        scene = new Scene(root);

        menu(primaryStage);
        create_labyrinthe(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    /************************
     * menus de l'application
     ************************/
    private void menu(Stage stage)
    {
        //creer un menu
        mnbar=new MenuBar();
        Menu mnApp=new Menu("Application");
        Menu mnGenerer=new Menu("Generateur");

        MenuItem mnQuitter=new MenuItem("Quitter");
        MenuItem mnSShot=new MenuItem("ScreenShot");
        MenuItem mnExport=new MenuItem("Exporter Laby100");

        MenuItem mngeneration=new MenuItem("Generation");
        MenuItem mnParametres=new MenuItem("Parametres");

        mnApp.getItems().addAll(mnExport,mnSShot,new SeparatorMenuItem(),mnQuitter);
        mnGenerer.getItems().addAll(mngeneration,new SeparatorMenuItem(),mnParametres);

        mnQuitter.setOnAction((ActionEvent event) -> {Platform.exit();});
        mnSShot.setOnAction((ActionEvent event) -> {drawLab.screenShot();});
        mnExport.setOnAction((ActionEvent event) ->{export_laby100();});
        mnParametres.setOnAction((ActionEvent event) ->{setParametres();});

        mngeneration.setOnAction((ActionEvent event) -> {create_labyrinthe(stage);});

        mnbar.getMenus().addAll(mnApp,mnGenerer);

    }


    public void setParametres()
    {
        Stage stg=new Stage();
        FXMLOptionsController controller=null;

        try
        {
            FXMLLoader loader = new FXMLLoader(Labyrinth_Generator.class.getResource("FXMLOptions.fxml"));
            //récuperer le root
            Parent parameterRoot = (Parent) loader.load();
            //recuperer le controleur
            controller = loader.getController();
              //Affecter le stage courant à la nouvelle fenêtre
            controller.setStage(stg,tailleLabyX,tailleLabyY,backColor,frontColor);

            //associer la scene
            Scene parameterScene = new Scene(parameterRoot);
            stg.setScene(parameterScene);

            //placer la fenêtre
            stg.centerOnScreen();
            stg.setTitle("Options du labyrinthe");
            stg.setResizable(false);
            stg.setAlwaysOnTop(true);
            //afficher
        stg.showAndWait();

        if (controller.getFermeture())
        {
            //recuperer les valeurs...
            tailleLabyX=controller.getTx();
            tailleLabyY=controller.getTy();
            frontColor=controller.getccMurs();
            backColor=controller.getccFond();
            aleatoire=controller.getAleatoire();
            //create_labyrinthe(stage);
        }

        } catch (IOException ex)
        {
            //do nothing
        }


    }


    private void export_laby100()
    {
        PrintWriter pw=null;
        try {
            //demander ou ecrire et le nom
            FileChooser fc=new FileChooser();
            fc.setTitle("Exporter au format labyrinthe 100");
            fc.setInitialDirectory(new File(System.getProperty("user.dir")));
            File f=fc.showSaveDialog(null);
            pw = new PrintWriter(setExtension(f.getPath()));

            pw.print(lab.toString());
            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Labyrinth_Generator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }

    }


    /**********************
     * Mettre l'extension laby
     * @param fichier
     * @return
     ***********************/
    public String setExtension(String fichier)
    {
        String retour=fichier;
        //rechercher une extension
        if (fichier.lastIndexOf(".")>0){
         //decouper extension
         retour=fichier.substring(0,fichier.lastIndexOf("."));
        }

        //mettre extension .laby
        retour=retour+".laby";
        return retour;
    }

    /*******************************************
     * Creation et affichage d'un labyrinthe...
     * @param stage
     *******************************************/
    private void create_labyrinthe(Stage stage)
    {
     if (aleatoire)
     {
        tailleLabyX=(int)(Math.random()*77)+3;
        tailleLabyY=(int)(Math.random()*57)+3;
     }
        fenetreX=tailleLabyX*10;
        fenetreY=tailleLabyY*10;

        //creer un dessine_labyrinthe
        long debut=System.currentTimeMillis();
        lab=new Labyrinthe(tailleLabyX, tailleLabyY,debug);
        lab.calculer_labyrinthe();
        //System.out.println(lab.toString());

        drawLab=new Dessiner_labyrintheBMP();
        dessine_labyrinthe=drawLab.dessiner_labyrinthe(lab,tailleLabyX,tailleLabyY,fenetreX,fenetreY,frontColor,backColor);
        System.out.println("fin affichage : "+(System.currentTimeMillis()-debut)+" ms");
        stage.setWidth(fenetreX+20);
        stage.setHeight(fenetreY+62);
        stage.setTitle("Sim.laby("+tailleLabyX+"x"+tailleLabyY+") Windows("+fenetreX+"x"+fenetreY+")");

        //ajouter le dessine_labyrinthe
        root.setCenter(dessine_labyrinthe);
        root.setTop(mnbar);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
