/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth_generator;

/**
 *
 * @author tondeurh
 */
public class Cellule {

    private boolean nord;
    private boolean sud;
    private boolean est;
    private boolean ouest;
    private int valeur;
    private int X;
    private int Y;


    public Cellule()
    {
        nord=true;
        sud=true;
        ouest=true;
        est=true;
        valeur=0;
        X=0;
        Y=0;
    }

    public Cellule(boolean nord, boolean sud, boolean est, boolean ouest, int valeur, int x, int y)
    {
        this.nord = nord;
        this.sud = sud;
        this.est = est;
        this.ouest = ouest;
        this.valeur = valeur;
        this.X=x;
        this.Y=y;
    }

    public void setCellule(boolean nord, boolean sud, boolean est, boolean ouest, int valeur,int x, int y)
    {
        setNord(nord);
        setSud(sud);
        setOuest(ouest);
        setEst(est);
        setValeur(valeur);
        setX(x);
        setY(y);

    }

    /**********
     * toString pour le Debug
     * @return
     **********/
    @Override
    public String toString()
    {
        return  Boolean.compare(nord,false) + "|" + Boolean.compare(est,false) + "|" + Boolean.compare(sud,false) + "|" + Boolean.compare(ouest, false);
    }

    /*
    Liste des getters et setters
    */
    public int getX()
    {
        return X;
    }

    public void setX(int X)
    {
        this.X = X;
    }

    public int getY()
    {
        return Y;
    }

    public void setY(int Y)
    {
        this.Y = Y;
    }

    public boolean isNord()
    {
        return nord;
    }

    public void setNord(boolean nord)
    {
        this.nord = nord;
    }

    public boolean isSud()
    {
        return sud;
    }

    public void setSud(boolean sud)
    {
        this.sud = sud;
    }

    public boolean isEst()
    {
        return est;
    }

    public void setEst(boolean est)
    {
        this.est = est;
    }

    public boolean isOuest()
    {
        return ouest;
    }

    public void setOuest(boolean ouest)
    {
        this.ouest = ouest;
    }

    public int getValeur()
    {
        return valeur;
    }

    public void setValeur(int valeur)
    {
        this.valeur = valeur;
    }

}
