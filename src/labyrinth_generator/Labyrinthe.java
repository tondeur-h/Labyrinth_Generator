package labyrinth_generator;

import java.util.LinkedList;

/**
 * *******************
 *
 * @author tondeurh
 ********************
 */
public class Labyrinthe {

    private final int maxXCel;
    private final int maxYCel;

    private final int LIBRE = 0;
    private final int COURANTE = 1;
    private final int SUIVANTE = 2;
    private final int VISITE = 3;

    //liste des variables
    private int fxc, fyc, fxcs, fycs;
    private int choixCel;
    private boolean isCaseValide;
    private boolean LabyrintheFini;
    private boolean noBactrack;

    private final LinkedList<Cellule> matriceCellules;
    private final LinkedList<Cellule> backTracking;

    private boolean DEBUG;

    /**
     * ***************************
     * Choisir la premiere cellule
     ****************************
     */
    private void choisir_premiere_cellule()
    {
        //choisir une cellule au hasard
        fxc = (int) (Math.random() * maxXCel);
        fyc = (int) (Math.random() * maxYCel);
        this.getCellule(fxc, fyc).setValeur(COURANTE);

        if (DEBUG) System.out.println("Premiere cellule :"+fxc+","+fyc);

        fxcs = 0;
        fycs = 0;
        choixCel = 0;
        isCaseValide = false;
        LabyrintheFini = false;
    }

    /**
     * *******************************
     * Choisir une cellule autour de la
     * cellule courante la premiere au hasard
     * ensuite on teste chaque possibilité
     *
     * @return
     ********************************
     */
    private boolean choisir_cellule_autour()
    {
        int nbtest = 0;
        isCaseValide = false;
        int MAXALEATOIRE=4;
        //autoriser 2 essai au hasard sinon
        //le hasard ne fait pas bien les choses
        //et on va chercher selon les position Nord/est/sud/ouest
        while ((!isCaseValide) && (nbtest < MAXALEATOIRE))
        {
            //choisir au hasard une cellule autour de la cellule courante
            choixCel = (int) (Math.random() * 4);
            if (DEBUG) System.out.println("Direction au hasard : "+choixCel);
            //verifier notre choix
            if (choixCel == 0)
            {
                //au nord
                if ((fyc - 1) >= 0)
                {
                    //test contenu cellule
                    if (this.getCellule(fxc, fyc - 1).getValeur() != VISITE)
                    {
                        isCaseValide = true;
                        fxcs = fxc;
                        fycs = fyc - 1;
                    }
                }
            }
            if (choixCel == 1)
            {
                //a l'est
                if ((fxc + 1) < maxXCel)
                {
                    //test contenu cellule
                    if (this.getCellule(fxc + 1, fyc).getValeur() != VISITE)
                    {
                        isCaseValide = true;
                        fxcs = fxc + 1;
                        fycs = fyc;
                    }
                }
            }
            if (choixCel == 2)
            {
                //au sud
                if ((fyc + 1) < maxYCel)
                {
                    //test contenu cellule
                    if (this.getCellule(fxc, fyc + 1).getValeur() != VISITE)
                    {
                        isCaseValide = true;
                        fxcs = fxc;
                        fycs = fyc + 1;
                    }
                }
            }
            if (choixCel == 3)
            {
                //a l'ouest
                if ((fxc - 1) >= 0)
                {
                    //test contenu cellule
                    if (this.getCellule(fxc - 1, fyc).getValeur() != VISITE)
                    {
                        isCaseValide = true;
                        fxcs = fxc - 1;
                        fycs = fyc;
                    }
                }
            }
            nbtest++;
        }
        //nombre de test au hasard est supérieur à 2
        //alors on va tester les différents cas O 1 2 3 si possible
        //si aucun n'est possibl c'est que l'on est dans un impasse et
        //il faut faire un bactracking
        if ((!isCaseValide) && (nbtest >= MAXALEATOIRE))
        {
            //alors tester tous les cas possibles.
            choixCel = -1;
            while ((!isCaseValide) && (choixCel < 4))
            {
                //choisir au hasard une cellule autour de la cellule courante
                //verifier notre choix
                choixCel++;
                if (DEBUG) System.out.println("Cherche direction : "+choixCel);
                if (choixCel == 0)
                {
                    //au nord
                    if ((fyc - 1) >= 0)
                    {
                        //test contenu cellule
                        if (this.getCellule(fxc, fyc - 1).getValeur() != VISITE)
                        {
                            isCaseValide = true;
                            fxcs = fxc;
                            fycs = fyc - 1;
                        }
                    }
                }
                if (choixCel == 1)
                {
                    //a l'est
                    if ((fxc + 1) < maxXCel)
                    {
                        //test contenu cellule
                        if (this.getCellule(fxc + 1, fyc).getValeur() != VISITE)
                        {
                            isCaseValide = true;
                            fxcs = fxc + 1;
                            fycs = fyc;
                        }
                    }
                }
                if (choixCel == 2)
                {
                    //au sud
                    if ((fyc + 1) < maxYCel)
                    {
                        //test contenu cellule
                        if (this.getCellule(fxc, fyc + 1).getValeur() != VISITE)
                        {
                            isCaseValide = true;
                            fxcs = fxc;
                            fycs = fyc + 1;
                        }
                    }
                }
                if (choixCel == 3)
                {
                    //a l'ouest
                    if ((fxc - 1) >= 0)
                    {
                        //test contenu cellule
                        if (this.getCellule(fxc - 1, fyc).getValeur() != VISITE)
                        {
                            isCaseValide = true;
                            fxcs = fxc - 1;
                            fycs = fyc;
                        }
                    }
                }
            }
        }
        //retourner la possibilite d'une case
        return isCaseValide;
    }

    /**
     * ********************************
     * ouvrir les murs entre 2 cellules
     *********************************
     */
    private void ouvrir_les_murs()
    {

        if (DEBUG)
        {
            System.out.println("fxc="+fxc);
            System.out.println("fyc="+fyc);
            System.out.println("fxcs="+fxcs);
            System.out.println("fycs="+fycs);
            System.out.println("Choix Cel="+choixCel);
        }
        //nord cour + sud suiv
        if (choixCel == 0)
        {
            this.getCellule(fxc, fyc).setNord(false);
            this.getCellule(fxcs, fycs).setSud(false);
        }
        //est cour + ouest suiv
        if (choixCel == 1)
        {
            this.getCellule(fxc, fyc).setEst(false);
            this.getCellule(fxcs, fycs).setOuest(false);
        }
        // sud cour + nord suiv
        if (choixCel == 2)
        {
            this.getCellule(fxc, fyc).setSud(false);
            this.getCellule(fxcs, fycs).setNord(false);
        }
        // ouest cour + est suiv
        if (choixCel == 3)
        {
            this.getCellule(fxc, fyc).setOuest(false);
            this.getCellule(fxcs, fycs).setEst(false);
        }
    }

    /**
     * *****************************
     * Reculer d'une cellule
     ******************************
     */
    private void backtrack_cellule()
    {
        //mettre la cellule precedente en courante
        //et lancer les recherches a partir de cette cellule
        //marquer la cellule courante comme visite
        this.getCellule(fxcs, fycs).setValeur(VISITE);
        //retirer la derniere cellule dans le backtracking qui est la cellule courante
        backTracking.removeLast();
        if (DEBUG) System.out.println("removelast");
        //placer la cellule du bactracking comme cellule courante
        Cellule l=backTracking.get(backTracking.size()-1);
        if (DEBUG) System.out.println("Backtracking : "+l.toString());
        fxc=l.getX(); fyc=l.getY();
        if (DEBUG) System.out.println("fxc:"+fxc);
        if (DEBUG) System.out.println("fyc:"+fyc);

    }


    /**
     * *************************
     * Calculer le labyrinthe
     **************************
     */
    public void calculer_labyrinthe()
    {
        //choisir la premiere cellule au hasard
        choisir_premiere_cellule();

        int cpt = 0;
        //repeter tant qu'il y a des cellules libres
        while (!LabyrintheFini)
        {
            //choisir une cellule autour au hasard
            noBactrack = false;
            noBactrack = choisir_cellule_autour();
            while (!noBactrack)
            {
                backtrack_cellule();
                noBactrack = choisir_cellule_autour();
            }

            //rendre la cellule choisie comme suivante
            this.getCellule(fxcs, fycs).setValeur(SUIVANTE);

            //ouvrir les murs de chaque cellule
            ouvrir_les_murs();

            //Rendre la cellule courante->visite
            this.getCellule(fxc, fyc).setValeur(VISITE);
            this.getCellule(fxc, fyc).setX(fxc);
            this.getCellule(fxc, fyc).setY(fyc);
            //ajouter la cellule visite dans la liste de bactracking...
            backTracking.add(this.getCellule(fxc, fyc));

            //Rendre la cellule suivante->courante
            this.getCellule(fxcs, fycs).setValeur(COURANTE);

            //idem pour les variables de positions
            fxc = fxcs;
            fyc = fycs;
            if (DEBUG) System.out.println("Cellule courante :"+fxc+","+fyc);

            //est ce que le labyrinthe est terminé?
            isLabyrintheFini();
        }
    }

    /**
     * **********************************
     * Tester s'il n'y a plus de cellule libres dans la matrice
     ***********************************
     */
    private void isLabyrintheFini()
    {
        boolean hit = false;
        for (Cellule c : matriceCellules) {
            if (c.getValeur() == LIBRE) {
                hit = true;
            }
        }
        if (!hit) {
            LabyrintheFini = true;
        }

    }

    /**
     * *************************
     * Récuperer la taille de la matrice en maxXCel
     *
     * @return
    **************************
     */
    public int getSizeX()
    {
        return maxXCel;
    }

    /**
     * ********************
     * Récuperer la taille de la matrice en maxYCel
     *
     * @return
    ********************
     */
    public int getSizeY()
    {
        return maxYCel;
    }

    /**
     * ****************************
     * Création d'une matrice de cellule non traités
     *
     * @param sizeX
     * @param sizeY
     * @param debug
     ******************************
     */
    public Labyrinthe(int sizeX, int sizeY, boolean debug)
    {
        maxXCel = sizeX;
        maxYCel = sizeY;
        DEBUG=debug;
        //creer une matrice de cellules
        matriceCellules = new LinkedList<>();
        backTracking = new LinkedList<>();
        int nbCel = sizeX * sizeY;
        for (int nc = 0; nc < nbCel; nc++)
        {
            matriceCellules.add(new Cellule());
        }
    }

    /**
     * *******************
     * afficher la matrice
     *
     * @return
     ********************
     */
    @Override
    public String toString()
    {
        String retour ="laby100\nsize|"+maxXCel+"|"+maxYCel+"\n";
        int numCel = 0;
        for (Cellule c : matriceCellules) {
            retour = retour + "cell|"+numCel+"|"+ c.toString() + "\n";
            numCel++;
        }
        return retour;
    }

    /*******************************
     * Retour arriere dans la liste
     * de backtracking
     * @return
     *******************************/
    public String backTrackToString()
    {
       String retour = "";
        int numCel = 0;
        for (Cellule c : backTracking) {
            retour = retour + numCel+":"+c.toString() + "\n";
            numCel++;
        }
        retour=retour+numCel+":"+this.getCellule(fxcs, fycs).toString()+"\n";
        return retour;
    }

    /**
     * ************************************
     * Récuperer une cellule spécifique de la matrice
     *
     * @param x
     * @param y
     * @return
     ************************************
     */
    public Cellule getCellule(int x, int y)
    {
        //base 0 pour x & y
        return (Cellule) matriceCellules.get((y * maxXCel) + x);
    }

    /**
     * *******************
     * Obtenir une valeur aléatoire selon un range passéen paramétre
     *
     * @param min
     * @param max
     * @return
    *********************
     */
    public long aleatoire(long min, long max)
    {
        return (long) ((Math.random() * (max - min)) + min);
    }

}
