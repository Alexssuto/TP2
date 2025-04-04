package com.example;
public class Produit {
    protected IdentiteProduit identite;
    /**
     * Position en 'x' de l'item dans l'univers de l'usine.
     */
    protected double x;
    /**
     * Position en 'y' de l'item dans l'univers de l'usine.
     */
    protected double y;

    public Produit( IdentiteProduit identite ) {
        this.identite = identite;
    }

    public double getX() {
        return x;
    }

    public void setX( double x ) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY( double y ) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Item{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
