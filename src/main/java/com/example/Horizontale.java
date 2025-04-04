package tp2;

/**
 * Decrit l'axe des 'x'.
 */
public interface Horizontale extends Axe {
    @Override
    default double getPositionRelative( Produit produit, int posX, int posY ) {
        return produit.getX() - posX;
    }

    @Override
    default void setPositionRelative( Produit produit, double positionRelative, int posX, int posY ) {
        produit.setX( positionRelative + posX );
    }
}
