package tp2;

/**
 * La direction vers la gauche sur la grille (direction negative sur l'Axe des x).
 */
public class Gauche extends Negative implements Horizontale {
    public Gauche() {
        super(-1, 0);
    }

    public int compare( Produit g, Produit d ) {
        return Double.compare( d.getX(), g.getX() );
    }
}
