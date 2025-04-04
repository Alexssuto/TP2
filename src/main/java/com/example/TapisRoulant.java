package com.example;
/**
 * Ce type énuméré décrit les différentes 'type' de tapis roulant.
 * Un type de tapis roulant décrit la forme et la direction du tapis roulant dans
 * une case de la grille.
 * La forme est décrite en indiquant le côté par lequel le tapis roulant arrive dans la case et
 * le côté par lequel le tapis roulant sort de la case.
 * L'ordre des mots dans le nom des constantes indique la direction du tapis roulant.
 * Par exemple, HAUT_DROITE indique une case où le tapis roulant arrive en haut de la case et tourne vers la
 * droite de la case.
 * Il y a deux constantes spéciales :
 * VIDE : indique une case vide.
 * OCCUPE : indique une case qui contient autre chose qu'un tapis roulant.
 *
 * Ajout :
 * La description des tapis roulants est divisé en deux segments.
 *     segment 1 : la direction de la première partie du tapis roulant.
 *     segment 2 : la direction de la deuxième partie du tapis roulant.
 */
public enum TapisRoulant {

    VIDE("   ", " . ", "   ",
            null, null ),
    OCCUPE( "xxx", "x.x", "xxx",
            null, null ),
    HAUT_DROITE( " | ", " >-", "   ",
            Direction2D.BAS , Direction2D.DROITE ),
    HAUT_BAS( " | ", " \u2228 ", " | ",
            Direction2D.BAS, Direction2D.BAS ),
    HAUT_GAUCHE( " | ", "-< ", "   ",
            Direction2D.BAS, Direction2D.GAUCHE ),
    DROITE_BAS( "   ", " \u2228-", " | ",
            Direction2D.GAUCHE, Direction2D.BAS ),
    DROITE_GAUCHE( "   ", "-<-", "   ",
            Direction2D.GAUCHE, Direction2D.GAUCHE ),
    DROITE_HAUT( " | ", " \u2227-", "   ",
            Direction2D.GAUCHE, Direction2D.HAUT ),
    BAS_GAUCHE( "   ", "-< ", " | ",
            Direction2D.HAUT, Direction2D.GAUCHE ),
    BAS_HAUT( " | ", " \u2227 ", " | ",
            Direction2D.HAUT, Direction2D.HAUT ),
    BAS_DROITE( "   ", " >-", " | ",
            Direction2D.HAUT, Direction2D.DROITE ),
    GAUCHE_HAUT( " | ", "-\u2227 ", "   ",
            Direction2D.DROITE, Direction2D.HAUT ),
    GAUCHE_DROITE( "   ", "->-", "   ",
            Direction2D.DROITE, Direction2D.DROITE ),
    GAUCHE_BAS( "   ", "-\u2228 ", " | ",
            Direction2D.DROITE, Direction2D.BAS ),
    ;
    private String afficheHaut;
    private String afficheMilieu;
    private String afficheBas;
    private Direction2D dirSegment1;
    private Direction2D dirSegment2;

    private static final TapisRoulant[] tapisRoulants = TapisRoulant.class.getEnumConstants();

    TapisRoulant( String afficheHaut, String afficheMilieu, String afficheBas,
                  Direction2D dirSegment1, Direction2D dirSegment2 ) {
        this.afficheHaut = afficheHaut;
        this.afficheMilieu = afficheMilieu;
        this.afficheBas = afficheBas;
        this.dirSegment1 = dirSegment1;
        this.dirSegment2 = dirSegment2;
    }

    public String afficheHaut() {
        return afficheHaut;
    }

    public String afficheMilieu() {
        return afficheMilieu;
    }

    public String afficheBas() {
        return afficheBas;
    }

    /**
     * Indique si un Item est avant l'autre lorqu'ils sont sur le même tapis roulant.
     * @param g le premier item.
     * @param d le deuxieme item.
     * @return retourne vrai si le premier item est avant le deuxieme item.
     */
    public boolean vientAvant( Produit g, Produit d ) {
        return dirSegment2.thenComparing( dirSegment1 ).compare( g, d ) < 0;
    }

    /**
     * Avance un Item qui est sur un tapis roulant.
     * @param i L'item que nous voulons avancer.
     * @param distance La distance de réplacement pour l'item
     * @return La distance non parcourue par l'item lorsqu'il n'y avait pas assez de place pour le déplacement
     * complet.
     */
    public double avancer( Produit i, double distance, int posX, int posY ) {
        double reste = dirSegment1.avancerSegment1( i, distance, posX, posY );
        return dirSegment2.avancerSegment2( i, reste, posX, posY );
    }

    public Direction2D dirSegment2() {
        return dirSegment2;
    }

    public Direction2D dirSegment1() {
        return dirSegment1;
    }

    /*
     * Place un nouveau tapis roulant en tenant compte de l'ancien.
     *
     * Le nouveau et l'ancien tapis roulant sont remplacé par un tapis roulant qui commence à la même place
     * que l'ancien tapis et qui termine à la même place que le nouveau.  S'il n'y a pas d'ancien tapis,
     * alors le nouveau tapis est placé.  Si le placement est impossible, alors une exception est lancée.
     * @param depart Le type du nouveau tapis.
     * @exception PlacementIncorrectException S'il est impossible de placer le tapis, soit parceque le nouveau
     * tapis est VIDE ou OCCUPE, soit l'ancien tapis est OCCUPE, soit l'ancien commence où le nouveau termine.
     */
    public TapisRoulant combine( TapisRoulant depart ) {
        return
                switch( this ) {
                    case VIDE -> depart;
                    case HAUT_BAS, HAUT_DROITE, HAUT_GAUCHE ->
                            switch( depart ) {
                                case HAUT_BAS, GAUCHE_BAS, DROITE_BAS -> TapisRoulant.HAUT_BAS;
                                case DROITE_GAUCHE, HAUT_GAUCHE, BAS_GAUCHE -> TapisRoulant.HAUT_GAUCHE;
                                case GAUCHE_DROITE, HAUT_DROITE, BAS_DROITE -> TapisRoulant.HAUT_DROITE;
                                default -> throw new PlacementIncorrectException();
                            };
                    case DROITE_GAUCHE, DROITE_HAUT, DROITE_BAS ->
                            switch( depart ) {
                                case HAUT_BAS, GAUCHE_BAS, DROITE_BAS -> TapisRoulant.DROITE_BAS;
                                case DROITE_GAUCHE, HAUT_GAUCHE, BAS_GAUCHE -> TapisRoulant.DROITE_GAUCHE;
                                case BAS_HAUT, DROITE_HAUT, GAUCHE_HAUT -> TapisRoulant.DROITE_HAUT;
                                default -> throw new PlacementIncorrectException();
                            };
                    case BAS_HAUT, BAS_DROITE, BAS_GAUCHE ->
                            switch( depart ) {
                                case DROITE_GAUCHE, HAUT_GAUCHE, BAS_GAUCHE -> TapisRoulant.BAS_GAUCHE;
                                case BAS_HAUT, DROITE_HAUT, GAUCHE_HAUT -> TapisRoulant.BAS_HAUT;
                                case GAUCHE_DROITE, HAUT_DROITE, BAS_DROITE -> TapisRoulant.BAS_DROITE;
                                default -> throw new PlacementIncorrectException();
                            };
                    case GAUCHE_DROITE ->
                            switch( depart ) {
                                case HAUT_BAS, GAUCHE_BAS, DROITE_BAS -> TapisRoulant.GAUCHE_BAS;
                                case BAS_HAUT, DROITE_HAUT, GAUCHE_HAUT -> TapisRoulant.GAUCHE_HAUT;
                                case GAUCHE_DROITE, HAUT_DROITE, BAS_DROITE -> TapisRoulant.GAUCHE_DROITE;
                                default -> throw new PlacementIncorrectException();
                            };
                    default -> throw new PlacementIncorrectException();
                };
    }

    /**
     * Retourne l'élément de l'énumération qui contient les directions indiquées.
     * @param segment1 La direction que le résultat doit avoir dans le premier segment.
     * @param segment2 La direction que le résultat doit avoir dans le deuxième segment.
     * @return Le type de Tapis roulant qui a les directions demandées.
     */
    public static TapisRoulant trouver( Direction2D segment1, Direction2D segment2 ) {
        TapisRoulant resultat = null;
        int position = 2;

        while( position < tapisRoulants.length &&
                ( tapisRoulants[ position ].dirSegment1 != segment1 ||
                        tapisRoulants[ position ].dirSegment2 != segment2 ) ) {
            ++ position;
        }

        if( position < tapisRoulants.length ) {
            resultat = tapisRoulants[ position ];
        }

        return resultat;
    }
}
