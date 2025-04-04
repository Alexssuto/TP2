package com.example;
import java.util.Comparator;

/**
 * Classe de base pour décrire le mouvement des Item dans la grille.
 * Puisqu'une case contient un tapis roulant qui a possiblement 2 directions,
 * alors cette classe s'occupe des déplacements pour le premier segment d'un tapis roulant
 * dans une case et elle s'occupe des déplacements pour le deuxième segment d'une
 * tapis roulant dans une case.
 *
 * Nous avons seulement besoin d'une instance de chaque sous classe.
 * Ces instances sont préconstruites sous forme de constante dans la classe Direction2D.
 * Pour les méthodes suivantes, le terme 'position' indique la position relative d'un item sur un tapis roulant.
 * La position relative d'un Item est une valeur entre -0.5 et 0.5.
 * Donc, pour un segment qui à un déplacement positif, l'item entre à la position -0.5 d'un tapis roulant,
 * il est dans le premier segment jusqu'à la position 0.0, ensuite il entre dans le deuxième segment à la
 * position 0.0 et quitte le tapis roulant à la position 0.5
 */
public abstract class Direction2D implements Axe, Comparator< Produit > {
    /**
     * Les quatres instances possible pour cet classe :
     */
    public static final Direction2D HAUT = new Haut();
    public static final Direction2D DROITE = new Droite();
    public static final Direction2D BAS = new Bas();
    public static final Direction2D GAUCHE = new Gauche();


    /**
     * variable décrivant le déplacement selon l'axe des 'x'.
     */
    public int deltaX;
    /**
     * variable décrivant le déplacement selon l'axe des 'y'.
     */
    public int deltaY;

    /**
     * variable décrivant la direction et la taille du déplacement.
     * Les valeurs possible pour ce projet sont : -1.0 (gauche et haut), 1.0 (droite et bas).
     */
    protected double direction;

    protected Direction2D( int deltaX, int deltaY, double direction) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.direction = direction;
    }

    /**
     * Vérifie si une position appartient au premier segment.
     *
     * Par exemple, si nous avons un segment qui va vers la droite,
     * alors nous appartenons au premier segment si la position est entre
     * -0.5 et 0.0.
     * @param position une valeur entre -0.5 et 0.5.
     * @return true si la position est dans le premier segment.
     */
    protected boolean appartientSegment1( double position ){
        return (direction * position) < 0.0;
    }

    /**
     * Vérifie si une position appartient au deuxième segment.
     *
     * Par exemple, si nous avons un segment qui va vers la droite,
     * alors nous appartenons au deuxième segment si la position est entre
     * 0.0 et 0.5.
     * @param position une valeur entre -0.5 et 0.5.
     * @return true si la position est dans le deuxième segment.
     */
    protected boolean appartientSegment2( double position ) {
        return 0.0 <= (direction * position );
    }

    /**
     * Calcul la distance restante entre une position et la fin du premier segment.
     * @param position la position de l'item dans le premier segment.
     * @return La distance restante pour atteindre la fin du premier segment.
     */
    protected double distanceRestanteSegment1( double position ) {
        return 0.0 - (direction * position );
    }

    /**
     * Calcul la distance restante entre une position et la fin du deuxième segment.
     * @param position la position de l'item dans le deuxième segment.
     * @return La distance restante pour atteindre la fin du deuxième segment.
     */
    protected double distanceRestanteSegment2( double position ) {
        return 0.5 - ( direction * position );
    }

    /**
     * Calcul la nouvelle position d'un Item à partir de l'ancienne position et la distance à parcourir.
     * @param position L'ancienne position de l'item.
     * @param distance La distance à parcourir.
     * @return La nouvelle position de l'item.
     */
    protected double calculerDeplacement( double position, double distance ){
        return position + direction * distance;
    }

    /**
     * Modifie la position d'un item lorsque nous avançons cet item sur le premier segment d'un tapis roulant.
     * @param produit L'item que nous voulons bouger.  La position de l'item va être modifiée lorsque l'item est
     *             déplacé.
     * @param distance La distance du déplacement.
     * @return Le déplacement réel qu'a subit l'item.  Si l'item arrive au bout du tapis avant d'avoir terminé
     * le déplacement, alors la distance qu'il lui reste à parcourir est retourné.
     */
    public double avancerSegment1( Produit produit, double distance, int posX, int posY ) {
        double positionRelative = getPositionRelative( produit, posX, posY );
        double deplacementPossible = 0.0;

        if ( appartientSegment1( positionRelative ) ) {
            deplacementPossible = Math.min( distanceRestanteSegment1( positionRelative ), distance);
            positionRelative = calculerDeplacement( positionRelative, deplacementPossible );
        }

        setPositionRelative( produit, positionRelative, posX, posY );
        return distance - deplacementPossible;
    }

    /**
     * Modifie la position d'un item lorsque nous avançons cet item sur le deuxième segment d'un tapis roulant.
     * @param produit L'item que nous voulons bouger.  La position de l'item va être modifiée lorsque l'item est
     *             déplacé.
     * @param distance La distance du déplacement.
     * @return Le déplacement réel qu'a subit l'item.  Si l'item arrive au bout du tapis avant d'avoir terminé
     * le déplacement, alors la distance qu'il lui reste à parcourir est retourné.
     */
    public double avancerSegment2( Produit produit, double distance, int posX, int posY ) {
        double positionRelative = getPositionRelative( produit, posX, posY );
        double deplacementPossible = 0.0;

        if ( appartientSegment2( positionRelative ) ) {
            deplacementPossible = Math.min( distanceRestanteSegment2( positionRelative ), distance);
            positionRelative = calculerDeplacement( positionRelative, deplacementPossible );
        }

        setPositionRelative( produit, positionRelative, posX, posY );
        return distance - deplacementPossible;
    }
}
