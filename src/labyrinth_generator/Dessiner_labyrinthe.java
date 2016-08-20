/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth_generator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author tondeurh
 */
public class Dessiner_labyrinthe {
  
        /************************
     * Dessine le labyrinthe
     * @param m
     * @param tx
     * @param ty
     * @param front
     * @param back
     * @return
     ************************/
    public Canvas dessiner_labyrinthe(Labyrinthe m,int tx,int ty,int fenetreX, int fenetreY,Color front, Color back)
    {
        //definir un canvas
        Canvas aCanvas=new Canvas(fenetreX, fenetreY);
        GraphicsContext gc = aCanvas.getGraphicsContext2D();
        gc.save();
        gc.setFill(back);
        gc.fillRect(0, 0, fenetreX, fenetreY);
        int nx=0,ny=0;
        int tailleX=fenetreX/tx;
        int tailleY=fenetreY/ty;

        long debut=System.currentTimeMillis();
        while (ny<ty)
        {
            while (nx<tx)
                        {
                            Cellule c=m.getCellule(nx, ny);
                            dessine_Cellule(nx*tailleX, ny*tailleY,tailleX,tailleY,c.isNord(),c.isEst(),c.isSud(),c.isOuest(),front ,gc);
                            nx++;
                        }
                            ny++;
                            nx=0;
        }
        System.out.println("Fin dessin cellules "+(System.currentTimeMillis()-debut)+" ms");
        //dessiner départ et arrivée
        dessiner_depart_arrive(tx,ty,tailleX,tailleY,gc);

        gc.restore();
        //retourner le canvas vers
         return aCanvas;
    }


    /******************
     *
     * @param tx
     * @param ty
     * @param taillex
     * @param tailley
     * @param gc
     ******************/
    private void dessiner_depart_arrive(int tx,int ty,int taillex,int tailley,GraphicsContext gc)
    {
        //a la position 0,0
        gc.setFill(Color.RED);
        gc.fillRoundRect(0, 0, taillex, tailley,5,5);

        //sortie à la position
        gc.setFill(Color.GREEN);
        gc.fillRoundRect((tx-1)*taillex, (ty-1)*tailley, taillex, tailley,5,5);
    }


    /**********************
     * Dessine une cellule
     * @param x
     * @param y
     * @param taillex
     * @param tailley
     * @param nord
     * @param est
     * @param sud
     * @param ouest
     * @param c
     * @param gc
     **********************/
    private void dessine_Cellule(int x,int y,int taillex,int tailley, boolean nord,boolean est, boolean sud, boolean ouest,Color c,GraphicsContext gc)
    {
       gc.beginPath();
        gc.moveTo(x, y);
        gc.setStroke(c);
        if (nord){gc.lineTo(x+taillex, y);} else {gc.moveTo(x+taillex, y);}
        if (est){gc.lineTo(x+taillex, y+tailley);} else {gc.moveTo(x+taillex, y+tailley);}
        if (sud){gc.lineTo(x, y+tailley);} else {gc.moveTo(x, y+tailley);}
        if (ouest){gc.lineTo(x, y);} else {gc.moveTo(x, y);}
        gc.stroke();
    }

    
}
