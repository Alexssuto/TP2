package com.example;

/**
 * Cet interface permet de désigner l'axe qui est utilisé pour un mouvement.
 * Les deux méthodes de cet interface sont complémentaire.
 */
public interface Axe {
    /**
     * Retourne la position linéaire d'un item sur un axe.
     * @param produit L'item duquel la position est extraite.
     * @return la position de l'item projeté sur un axe.
     */
    double getPositionRelative( Produit produit, int posX, int posY );

    /**
     * Assigne la positionRelative linéaire de l'axe d'un Item.
     * @param produit L'item que nous voulons modifier.
     * @param positionRelative La nouvelle positionRelative de l'item.
     */
    void setPositionRelative( Produit produit, double positionRelative, int posX, int posY );
}
