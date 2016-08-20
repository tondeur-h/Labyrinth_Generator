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
