package com.example;
/**
 * La direction vers la droite sur la grille (direction positive sur l'Axe des x).
 */
public class Droite extends Positive implements Horizontale {
    public Droite() {
        super(1, 0);
    }

    public int compare( Produit g, Produit d ) {
        return Double.compare( g.getX(), d.getX() );
    }
}
