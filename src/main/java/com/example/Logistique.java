package com.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Logistique {
    private static final double PRECISION = 0.000_01;
    private static final double DISTANCE_BASE = 0.05;
    private static Predicate< Double > presqueEntier = ( x ) -> Math.abs( x - Math.round(x) ) <= PRECISION;

    /*
     * Caractère utilisé pour faire le contour des cadres pour le toString de la grille.
     */
    private static final String SEP_CHAR = "#";

    /*
     * Va contenir la chaine de caractères qui sera placée entre chaque ligne de la grille pour le toString.
     * Elle sera construite une seule fois par instance, afin d'optimiser le code un peu.
     */
    private String fix;

    /**
     * Matrice contenant l'information sur les tapis roulant et l'usine.
     * La première dimension est utilisé pour les 'y', la deuxième dimension est utilisé pour les 'x'.
     *
     * Exemple : grille[y][x]
     */
    protected TapisRoulant [][] grille;
    protected List< Produit > produits;

    /**
     * Indique le nombre de case que contiens une ligne de la matrice.
     * Donc, le nombre de colonnes.
     */
    protected int tailleX;
    /**
     * Indique le nombre de lignes de la matrice.
     */
    protected int tailleY;

    /**
     * Constructeur.
     *
     * Construit une nouvelle grille vide.
     *
     * Vous devez conserver la signature de ce constructeur.
     * @param tailleX Contiens le nombre de colonne de la matrice.  Doit être plus grand que 0.
     * @param tailleY Contiens le nombre de lignes de la matrice.  Doit être plus grand que 0.
     */
    public Logistique( int tailleX, int tailleY ) {
        assert 0 < tailleX;
        assert 0 < tailleY;

        this.tailleX = tailleX;
        this.tailleY = tailleY;
        this.grille = new TapisRoulant[tailleY][tailleX];

        for( int y = 0; y < tailleY; ++ y ) {
            for( int x = 0; x < tailleX; ++ x ) {
                grille[y][x] = TapisRoulant.VIDE;
            }
        }

        fix = Stream.generate( () -> SEP_CHAR )
                .limit( 4 * tailleX + 1 )
                .collect( Collectors.joining( "", "", "\n" ) );

        produits = new ArrayList<>();
    }

    /**
     * Place un tapis roulant dans une case de la grille.
     *
     * @param x La position en 'x' de la case à modifier.  Cette valeur doit être entre 0 et TailleX - 1.
     * @param y La position en 'y' de la case à modifier.  Cette valeur doit être entre 0 et TailleY - 1.
     * @param type Le type de tapis roulant à placer dans la case.
     * @exception IndexOutOfBoundsException Lancé si 'x' ou 'y' ne sont pas à l'intérieur de la grille.
     */
    public void setTapis( int x, int y, TapisRoulant type ) {
        grille[y][x] = type;
    }

    private void setPremierTapis( int x, int y, TapisRoulant type ) {
        TapisRoulant courant = grille[y][x];

        if( courant != TapisRoulant.VIDE ) {
            type = courant.combine( type );
        }

        setTapis( x, y, type );
    }

    private void setDernierTapis( int x, int y, TapisRoulant type ) {
        TapisRoulant courant = grille[y][x];

        if( TapisRoulant.VIDE != courant ) {
            type = type.combine( courant );
        }

        setTapis( x, y, type );
    }



    /**
     * Retourne le type de tapis roulant dans la grille.
     *
     * @param x La position en 'x' de la case à modifier.  Cette valeur doit être entre 0 et TailleX - 1.
     * @param y La position en 'y' de la case à modifier.  Cette valeur doit être entre 0 et TailleY - 1.
     * @exception IndexOutOfBoundsException Lancé si 'x' ou 'y' ne sont pas à l'intérieur de la grille.
     * @return Le Carre dans la grille.
     */
    public TapisRoulant getTapis( int x, int y ) {
        return grille[y][x];
    }

    /**
     * Vérifie si la case de la grille contient la fin d'un tapis roulant.
     *
     * C'est la fin d'un tapis roulant si la case à la suite du tapis roulant ne contient pas un tapis roulant
     * qui à la même direction de départ que la direction d'arrivé de la case courante.
     * @param x la coordonnée x de la case à tester.
     * @param y la coordonnée y de la case à tester.
     * @return true si c'est la fin d'un tapis roulant, false sinon.
     */
    public boolean caseValideFin(int x, int y ) {
        boolean resultat = false;
        Direction2D avant = grille[ y ][ x ].dirSegment1();
        try {
                resultat = ( TapisRoulant.OCCUPE != grille[ y ][ x ] ) && (
                                    ( TapisRoulant.VIDE == grille[ y ][ x ] )
                                            ||
                                            grille[ y - avant.deltaY ][ x - avant.deltaX ].dirSegment2() != avant );
        } catch( IndexOutOfBoundsException e ) {
            resultat = true;
        }

        return resultat;
    }

    /**
     * Vérifie si la case de la grille contient le début d'un tapis roulant.
     *
     * C'est le début d'un tapis roulant si la case avant le tapis roulant ne contient pas un tapis roulant
     * qui à la même direction de fin que la direction de départ de la case courante.
     * @param x la coordonnée x de la case à tester.
     * @param y la coordonnée y de la case à tester.
     * @return true si c'est le début d'un tapis roulant, false sinon.
     */
    public boolean caseValideDebut(int x, int y ) {
        boolean resultat = false;
        Direction2D apres = grille[y][x].dirSegment2();

        try {
                resultat = ( TapisRoulant.OCCUPE != grille[ y ][ x ] ) && (
                        ( TapisRoulant.VIDE == grille[ y ][ x ] )
                                ||
                                grille[ y + apres.deltaY ][ x + apres.deltaX ]
                                        .dirSegment1() != apres );
        } catch ( IndexOutOfBoundsException e ) {
            resultat = true;
        }
        return resultat;
    }

    /**
     * Construit un tapis roulant horizontal (sur une ligne) dans la grille.
     *
     * Place des tapis roulant de la case (x1,y) à la case (x2,y) dans la grille.
     * Si x1 < x2, alors ce sont des TapisRoulant.GAUCHE_DROITE.
     * Si x2 < x1, alors ce sont des TapisRoulant.DROITE_GAUCHE.
     * Si l'extrémité de départ du tapis roulant arrive sur l'extrémité final d'un autre tapis roulant, alors
     * le bon tapis roulant est placé pour connecté les deux.
     * Si l'extrémité final du tapis roulant arrive sur l'extrémité de départ d'un autre tapis roulant, alors
     * le bon tapis roulant est placé pour connecté les deux.
     * Sinon, toutes les cases du nouveau tapis roulant doivent être vide.
     *
     * @param y Le numéro de ligne où construire le tapis roulant.  Doit être entre 0 et TailleY - 1.
     * @param x1 Le numéro de colonne où commence le tapis roulant.  Doit être entre 0 et TailleX - 1.
     * @param x2 Le numéro de colonne où se termine le tapis roulant.  Doit être entre 0 et TailleX - 1.
     * @exception PlacementIncorrectException Lancé s'il est impossible de placer le tapis roulant.
     */
    public void setTapisHorizontal( int y, int x1, int x2 ) {
        int delta;
        TapisRoulant type;

        if( x1 <= x2 ) {
            delta = +1;
            type = TapisRoulant.GAUCHE_DROITE;
        } else {
            delta = -1;
            type = TapisRoulant.DROITE_GAUCHE;
        }

        if( ! ( caseValideDebut( x1, y ) && caseValideFin( x2, y ) ) ) {
            throw new PlacementIncorrectException();
        }

        for( int x = x1 + delta; x != x2; x = x + delta ) {
            if( getTapis( x, y ) != TapisRoulant.VIDE ) {
                throw new PlacementIncorrectException();
            }
        }

        setPremierTapis( x1, y, type );
        x1 = x1 + delta;
        while( x1 != x2 ) {
            setTapis( x1, y, type );
            x1 = x1 + delta;
        }
        setDernierTapis( x2, y, type );
    }

    /**
     * Construit un tapis roulant vertical (sur une colonne) dans la grille.
     *
     * Place des tapis roulant de la case (x,y1) à la case (x,y2) dans la grille.
     * Si y1 < y2, alors ce sont des TapisRoulant.HAUT_BAS.
     * Si y2 < y1, alors ce sont des TapisRoulant.BAS_HAUT.
     * Si l'extrémité de départ du tapis roulant arrive sur l'extrémité final d'un autre tapis roulant, alors
     * le bon tapis roulant est placé pour connecté les deux.
     * Si l'extrémité final du tapis roulant arrive sur l'extrémité de départ d'un autre tapis roulant, alors
     * le bon tapis roulant est placé pour connecté les deux.
     * Sinon, toutes les cases du nouveau tapis roulant doivent être vide.
     *
     * @param x Le numéro de colonne où construire le tapis roulant.  Doit être entre 0 et TailleX - 1.
     * @param y1 Le numéro de ligne où commence le tapis roulant.  Doit être entre 0 et TailleY - 1.
     * @param y2 Le numéro de ligne où se termine le tapis roulant.  Doit être entre 0 et TailleY - 1.
     * @exception PlacementIncorrectException Lancé s'il est impossible de placer le tapis roulant.
     */
    public void setTapisVertical( int x, int y1, int y2 ) {
        int delta;
        TapisRoulant type;

        if( y1 <= y2 ) {
            delta = +1;
            type = TapisRoulant.HAUT_BAS;
        } else {
            delta = -1;
            type = TapisRoulant.BAS_HAUT;
        }

        if( ! ( caseValideDebut( x, y1 ) && caseValideFin( x, y2 ) ) ) {
            throw new PlacementIncorrectException();
        }

        for( int y = y1 + delta; y != y2; y = y + delta ) {
            if( getTapis( x, y ) != TapisRoulant.VIDE ) {
                throw new PlacementIncorrectException();
            }
        }

        setPremierTapis( x, y1, type );
        y1 = y1 + delta;
        while ( y1 != y2 ) {
            setTapis( x, y1, type );
            y1 = y1 + delta;
        }
        setDernierTapis( x, y2, type );
    }

    private static boolean estALaCoordonnee( Produit produit, int x, int y ) {
        return Math.round( produit.getX() ) == x && Math.round( produit.getY() ) == y;
    }

    /**
     * Place un item sur le tapis roulant.
     *
     *  L'item est placé sur le tapis roulant présent à la case (x,y).
     * @param x Le numéro de colonne où placer l'item.  Doit être entre 0 et TailleX - 1.
     * @param y Le numéro de ligne où placer l'item.  Doit être entre 0 et TailleY - 1.
     * @param produit L'item à placer.  Ne doit pas être null.
     * @exception PlacementIncorrectException Lancé s'il n'y a pas de tapis roulant à la position indiqué.
     */
    public void placerItem( int x, int y, Produit produit ) {
        if( grille[y][x] == TapisRoulant.VIDE || grille[y][x] == TapisRoulant.OCCUPE ) {
            throw new PlacementIncorrectException();
        }

        for( Produit i : produits ) {
            if( estALaCoordonnee( i, x, y )
                &&
                    ( presqueEntier.test( i.getX() ) || presqueEntier.test( i.getY() ) )
            ) {
                throw new PlacementIncorrectException();
            }
        }

        produit.setX( x );
        produit.setY( y );

        produits.add( produit );
    }

    /**
     * Indique s'il y a au moins un item dans la case.
     * @param x Le numéro de colonne à vérifier.  Doit être entre 0 et TailleX - 1.
     * @param y Le numéro de ligne à vérifier.  Doit être entre 0 et TailleY - 1.
     * @return true si le tapis roulant contiens au moins un item, false sinon.
     */
    public boolean contiensItem( int x, int y ) {
        int position = 0;

        while( position < produits.size() && ! estALaCoordonnee( produits.get( position ), x, y ) ) {
            ++ position;
        }

        return position < produits.size();
    }

    /**
     * Retourne l'item le plus proche de la fin du TapisRoulant à la case indiquée.
     *
     * @param x Le numéro de colonne où consulter un item.  Doit être entre 0 et TailleX - 1.
     * @param y Le numéro de ligne où consulter un item.  Doit être entre 0 et TailleY - 1.
     * @return L'item consulté.  L'Item n'est pas enlevé.
     * S'il n'y a pas d'item dans cette case, alors null est retourné.
     */
    public Produit trouverItem( int x, int y ) {
        Produit resultat = null;

        List< Produit > temp = new ArrayList<>();
        for( Produit i : produits ) {
            if( estALaCoordonnee( i, x, y ) ) {
                temp.add( i );
            }
        }

        if( 1 == temp.size() ) {
            resultat = temp.get( 0 );
        }
        if( 2 == temp.size() ) {
            if( getTapis( x, y ).vientAvant( temp.get( 0 ), temp.get( 1 ) ) ) {
                resultat = temp.get( 1 );
            } else {
                resultat = temp.get( 0 );
            }
        }

        return resultat;
    }

    /**
     * Enlève un item du tapis roulant à la case indiqué.
     *
     * L'item enlevé est retourné.  S'il y a plus d'un item dans la case, alors l'item le plus proche de la
     * fin du tapis roulant est retiré.
     * @param x Le numéro de colonne où enlevé un item.  Doit être entre 0 et TailleX - 1.
     * @param y Le numéro de ligne où enlevé un item.  Doit être entre 0 et TailleY - 1.
     * @return L'item enlevé.  S'il n'y a pas d'item dans cette case, alors null est retourné.
     */
    public Produit extraireItem( int x, int y ) {
        Produit resultat = trouverItem( x, y );

        if( null != resultat ) {
            produits.remove( resultat );
        }

        return resultat;
    }

    /**
     * Retourne la liste des Items présent dans la grille.
     * Les items restent dans la grille, c'est seulement pour pouvoir les consulter.
     * @return La liste des items présent dans la grille.  S'il n'y a pas d'item, alors la liste est vide.
     */
    public List< Produit > consulterItems() {
        return produits;
    }

    /**
     * Fait avancer un Item pour un mouvement de un tic.
     *
     * @param i L'item que nous voulons bouger.
     */
    private void ticItem( Produit i ) {
        int positionX = (int)Math.round( i.getX() );
        int positionY = (int)Math.round( i.getY() );
        TapisRoulant courant = grille[ positionY ][ positionX ];

        double reste = courant.avancer( i, DISTANCE_BASE, positionX, positionY );
        if( 0.0 < reste ) {
            Direction2D dirCourant = courant.dirSegment2();
            try {
                TapisRoulant suivant = grille[ positionY + dirCourant.deltaY ][ positionX + dirCourant.deltaX ];
                Direction2D dirSuivant = suivant.dirSegment1();

                if( dirCourant == dirSuivant ) {
                    suivant.avancer( i, reste, positionX + dirCourant.deltaX, positionY + dirCourant.deltaY );
                } else {
                    produits.remove( i );
                }
            } catch ( IndexOutOfBoundsException e ) {
                produits.remove( i );
            }
        }
    }

    /**
     * Fait avancer les Items sur les Tapis Roulants.  Si un item dépasse un tapis roulant, il disparait.
     */
    public void tic() {
        for( int i = produits.size() - 1; i >= 0; -- i ) {
            ticItem( produits.get(i ) );
        }
    }

    /**
     * Construit une chaine de caractères pour afficher une ligne de la matrice en utilisant une ligne à l'écran.
     * @param ligne La ligne à afficher
     * @param m La fonction d'extraction pour trouver les caractères qui affiche le tapis roulant.
     * @return La chaine de caractères représentant la ligne de la matrice.
     */
    private String toStringTier( TapisRoulant[] ligne, Function< TapisRoulant, String> m ) {
        return Arrays.stream( ligne )
                .map( m )
                .collect( Collectors.joining( SEP_CHAR, SEP_CHAR, SEP_CHAR + "\n" ) );
    }

    /**
     * Construit une chaine de caractères pour afficher une ligne de la matrice en utilisant 3 lignes à l'écran.
     * @param ligne La ligne à afficher
     * @return La chaine de caractères représentant la ligne de la matrice.
     */
    private String toStringLigne( TapisRoulant[] ligne ) {
        return
                toStringTier( ligne, TapisRoulant::afficheHaut ) +
                toStringTier( ligne, TapisRoulant::afficheMilieu ) +
                toStringTier( ligne, TapisRoulant::afficheBas );
    }

    /**
     * Transforme la matrice de la grille en une chaine de caractères.
     * @return La chaine de caractères représentant les tapis roulant de la matrice.
     */
    @Override
    public String toString() {
       return
                Arrays.stream( grille )
                        .map( this::toStringLigne )
                        .collect( Collectors.joining( fix, fix, fix ) );
    }
}
