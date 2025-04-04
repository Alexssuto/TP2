package tp2;

/**
 * Cet interface permet de designer l'axe qui est utilise pour un mouvement.
 * Les deux methodes de cet interface sont complementaire.
 */
public interface Axe {
    /**
     * Retourne la position lineaire d'un item sur un axe.
     * @param produit L'item duquel la position est extraite.
     * @return la position de l'item projete sur un axe.
     */
    double getPositionRelative( Produit produit, int posX, int posY );

    /**
     * Assigne la positionRelative lineaire de l'axe d'un Item.
     * @param produit L'item que nous voulons modifier.
     * @param positionRelative La nouvelle positionRelative de l'item.
     */
    void setPositionRelative( Produit produit, double positionRelative, int posX, int posY );
}
