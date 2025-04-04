package com.example;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.function.BiPredicate;

import static org.junit.jupiter.api.Assertions.*;
import com.example.Logistique; 

class LogistiqueTest {
    public static final double DELTA = 0.000_01;
    private static BiPredicate< List< Produit >,List< Produit > > ensemblesEgaux
            = ( g, d ) -> g.containsAll( d ) && d.containsAll( g );

    private static boolean approxEgal( double g, double d ) {
        return Math.abs( g - d ) < DELTA;
    }

    @Test
    void setTapis1() {
        Logistique u = new Logistique(2, 2);

        assertEquals(TapisRoulant.VIDE, u.getTapis(1, 1));

        u.setTapis(1, 1, TapisRoulant.BAS_HAUT);

        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 0));
        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 1));
        assertEquals(TapisRoulant.VIDE, u.getTapis(1, 0));
        assertEquals(TapisRoulant.BAS_HAUT, u.getTapis(1, 1));
    }

    @Test
    void setTapis2() {
        Logistique u = new Logistique(2, 2);

        assertThrows(IndexOutOfBoundsException.class, () -> u.setTapis(2, 1, TapisRoulant.BAS_HAUT));
        assertThrows(IndexOutOfBoundsException.class, () -> u.setTapis(1, 2, TapisRoulant.BAS_HAUT));
        assertThrows(IndexOutOfBoundsException.class, () -> u.setTapis(-1, 0, TapisRoulant.BAS_HAUT));
        assertThrows(IndexOutOfBoundsException.class, () -> u.setTapis(0, -1, TapisRoulant.BAS_HAUT));
    }

    @Test
    void setTapis3() {
        Logistique u = new Logistique(2, 2);

        u.setTapis(1, 1, TapisRoulant.BAS_HAUT);
        u.setTapis(1, 1, TapisRoulant.HAUT_BAS);

        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 0));
        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 1));
        assertEquals(TapisRoulant.VIDE, u.getTapis(1, 0));
        assertEquals(TapisRoulant.HAUT_BAS, u.getTapis(1, 1));
    }

    @Test
    void setTapis4() {
        Logistique u = new Logistique(2, 2);

        u.setTapis(1, 1, TapisRoulant.BAS_HAUT);
        u.setTapis(1, 0, TapisRoulant.HAUT_BAS);

        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 0));
        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 1));
        assertEquals(TapisRoulant.HAUT_BAS, u.getTapis(1, 0));
        assertEquals(TapisRoulant.BAS_HAUT, u.getTapis(1, 1));
    }

    @Test
    void setTapisHorizontal1() {
        Logistique u = new Logistique(4, 3);

        u.setTapisHorizontal(2, 1, 3);

        for (int x = 0; x < 4; ++x) {
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 0));
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 1));
        }
        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 2));
        assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(1, 2));
        assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(2, 2));
        assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(3, 2));
    }

    @Test
    void setTapisHorizontal2() {
        Logistique u = new Logistique(4, 3);

        u.setTapisHorizontal(1, 2, 1);

        for (int x = 0; x < 4; ++x) {
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 0));
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 2));
        }
        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 1));
        assertEquals(TapisRoulant.DROITE_GAUCHE, u.getTapis(1, 1));
        assertEquals(TapisRoulant.DROITE_GAUCHE, u.getTapis(2, 1));
        assertEquals(TapisRoulant.VIDE, u.getTapis(3, 1));
    }

    @Test
    void setTapisHorizontal3() {
        Logistique u = new Logistique(4, 3);

        u.setTapis(1, 1, TapisRoulant.OCCUPE);
        assertThrows(PlacementIncorrectException.class, () -> u.setTapisHorizontal(1, 0, 3));

        for (int x = 0; x < 4; ++x) {
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 0));
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 2));
        }
        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 1));
        assertEquals(TapisRoulant.OCCUPE, u.getTapis(1, 1));
        assertEquals(TapisRoulant.VIDE, u.getTapis(2, 1));
        assertEquals(TapisRoulant.VIDE, u.getTapis(3, 1));
    }

    @Test
    void setTapisVertical1() {
        Logistique u = new Logistique(4, 5);

        u.setTapisVertical(2, 1, 4);

        for (int y = 0; y < 5; ++ y) {
            assertEquals(TapisRoulant.VIDE, u.getTapis(0, y));
            assertEquals(TapisRoulant.VIDE, u.getTapis(1, y));
            assertEquals(TapisRoulant.VIDE, u.getTapis(3, y));
        }
        assertEquals(TapisRoulant.VIDE, u.getTapis(2, 0));
        assertEquals(TapisRoulant.HAUT_BAS, u.getTapis(2, 1));
        assertEquals(TapisRoulant.HAUT_BAS, u.getTapis(2, 2));
        assertEquals(TapisRoulant.HAUT_BAS, u.getTapis(2, 3));
        assertEquals(TapisRoulant.HAUT_BAS, u.getTapis(2, 4));
    }

    @Test
    void setTapisVertical2() {
        Logistique u = new Logistique(4, 5);

        u.setTapisVertical(2, 3, 1);

        for (int y = 0; y < 5; ++ y) {
            assertEquals(TapisRoulant.VIDE, u.getTapis(0, y));
            assertEquals(TapisRoulant.VIDE, u.getTapis(1, y));
            assertEquals(TapisRoulant.VIDE, u.getTapis(3, y));
        }
        assertEquals(TapisRoulant.VIDE, u.getTapis(2, 0));
        assertEquals(TapisRoulant.BAS_HAUT, u.getTapis(2, 1));
        assertEquals(TapisRoulant.BAS_HAUT, u.getTapis(2, 2));
        assertEquals(TapisRoulant.BAS_HAUT, u.getTapis(2, 3));
        assertEquals(TapisRoulant.VIDE, u.getTapis(2, 4));
    }

    @Test
    void setTapisVertical3() {
        Logistique u = new Logistique(4, 5);

        u.setTapis( 2, 3, TapisRoulant.OCCUPE );
        assertThrows( PlacementIncorrectException.class, () -> u.setTapisVertical(2, 4, 0) );

        for (int y = 0; y < 5; ++ y) {
            assertEquals(TapisRoulant.VIDE, u.getTapis(0, y));
            assertEquals(TapisRoulant.VIDE, u.getTapis(1, y));
            assertEquals(TapisRoulant.VIDE, u.getTapis(3, y));
        }
        assertEquals(TapisRoulant.VIDE, u.getTapis(2, 0));
        assertEquals(TapisRoulant.VIDE, u.getTapis(2, 1));
        assertEquals(TapisRoulant.VIDE, u.getTapis(2, 2));
        assertEquals(TapisRoulant.OCCUPE, u.getTapis(2, 3));
        assertEquals(TapisRoulant.VIDE, u.getTapis(2, 4));
    }

    @Test
    void setTapisH1plusV2() {
        Logistique u = new Logistique(4, 5);

        u.setTapisHorizontal(4, 0, 3);
        u.setTapisVertical(3, 4, 0);

        for (int x = 0; x < 3; ++ x) {
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 0));
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 1));
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 2));
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 3));
            assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(x, 4));
        }
        for( int y = 0; y < 4; ++ y ) {
            assertEquals(TapisRoulant.BAS_HAUT, u.getTapis(3, y));
        }
        assertEquals(TapisRoulant.GAUCHE_HAUT, u.getTapis(3, 4));
    }

    @Test
    void setTapisH1plusV2erreur() {
        Logistique u = new Logistique(4, 5);

        u.setTapisHorizontal(3, 0, 3);
        assertThrows( PlacementIncorrectException.class, () -> u.setTapisVertical(2, 4, 0) );

        for (int x = 0; x < 4; ++ x) {
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 0));
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 1));
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 2));
            assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(x, 3));
            assertEquals(TapisRoulant.VIDE, u.getTapis(x, 4));
        }
    }

    @Test
    void setTapis1Hplus2V() {
        Logistique u = new Logistique(3, 2);

        u.setTapisVertical(0, 0, 1);
        u.setTapisVertical(2, 1, 0);
        u.setTapisHorizontal(1, 0, 2);

        assertEquals(TapisRoulant.HAUT_BAS, u.getTapis(0, 0));
        assertEquals(TapisRoulant.VIDE, u.getTapis(1, 0));
        assertEquals(TapisRoulant.BAS_HAUT, u.getTapis(2, 0));
        assertEquals(TapisRoulant.HAUT_DROITE, u.getTapis(0, 1));
        assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(1, 1));
        assertEquals(TapisRoulant.GAUCHE_HAUT, u.getTapis(2, 1));
    }

    @Test
    void setTapis2Hplus1V() {
        Logistique u = new Logistique(3, 2);

        u.setTapisVertical(0, 1, 0);
        u.setTapisHorizontal(0, 0, 2);
        u.setTapisHorizontal(1, 2, 0);

        assertEquals(TapisRoulant.BAS_DROITE, u.getTapis(0, 0));
        assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(1, 0));
        assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(2, 0));
        assertEquals(TapisRoulant.DROITE_HAUT, u.getTapis(0, 1));
        assertEquals(TapisRoulant.DROITE_GAUCHE, u.getTapis(1, 1));
        assertEquals(TapisRoulant.DROITE_GAUCHE, u.getTapis(2, 1));
    }

    @Test
    void setTapis2H() {
        Logistique u = new Logistique(3, 2);

        u.setTapisHorizontal(1, 1, 2);
        u.setTapisHorizontal(1, 0, 1);

        assertEquals(TapisRoulant.VIDE, u.getTapis(0, 0));
        assertEquals(TapisRoulant.VIDE, u.getTapis(1, 0));
        assertEquals(TapisRoulant.VIDE, u.getTapis(2, 0));
        assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(0, 1));
        assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(1, 1));
        assertEquals(TapisRoulant.GAUCHE_DROITE, u.getTapis(2, 1));
    }

    @Test
    void setTapis2V() {
        Logistique u = new Logistique(3, 4);

        u.setTapisVertical(2, 0, 2);
        u.setTapisVertical(2, 2, 3);

        for( int y = 0; y < 4; ++ y ) {
            assertEquals(TapisRoulant.VIDE, u.getTapis(0, y));
            assertEquals(TapisRoulant.VIDE, u.getTapis(1, y));
            assertEquals(TapisRoulant.HAUT_BAS, u.getTapis(2, y));
        }
    }

    @Test
    void placerItem1() {
        Logistique u = new Logistique( 4, 5 );

        assertThrows( PlacementIncorrectException.class, () -> u.placerItem( 1, 1, new Produit( null ) ) );
    }

    @Test
    void placerItem2() {
        Logistique u = new Logistique( 4, 5 );
        Produit i = new Produit( null );

        u.setTapis( 1, 2, TapisRoulant.BAS_HAUT );
        u.placerItem( 1, 2, i );
        assertTrue( u.contiensItem( 1, 2 ) );
    }

    @Test
    void placerItem3() {
        Logistique u = new Logistique( 4, 5 );
        Produit i = new Produit( null );

        u.setTapis( 1, 2, TapisRoulant.BAS_HAUT );
        u.placerItem( 1, 2, i );
        assertThrows( PlacementIncorrectException.class, () -> u.placerItem( 1, 2, i ) );
    }

    @Test
    void consulterItems1() {
        Logistique u = new Logistique( 3, 2 );
        List< Produit > resAttendu = new ArrayList<>();

        assertTrue( ensemblesEgaux.test( resAttendu, u.consulterItems() ) );
    }

    @Test
    void consulterItems2() {
        Logistique u = new Logistique( 3, 2 );
        Produit i1 = new Produit( null );
        List< Produit > resAttendu = new ArrayList<>( Arrays.asList( i1 ) );

        u.setTapis( 0, 0, TapisRoulant.GAUCHE_DROITE );
        u.placerItem( 0, 0, i1 );
        assertTrue( ensemblesEgaux.test( resAttendu, u.consulterItems() ) );
    }

    @Test
    void consulterItems3() {
        Logistique u = new Logistique( 3, 2 );
        Produit i1 = new Produit( null );
        Produit i2 = new Produit( null );
        List< Produit > resAttendu = new ArrayList<>( Arrays.asList( i1, i2 ) );

        u.setTapis( 0, 0, TapisRoulant.GAUCHE_DROITE );
        u.setTapis( 0, 1, TapisRoulant.GAUCHE_DROITE );
        u.placerItem( 0, 0, i1 );
        u.placerItem( 0, 1, i2 );
        assertTrue( ensemblesEgaux.test( resAttendu, u.consulterItems() ) );
    }

    @Test
    void consulterItems4() {
        Logistique u = new Logistique( 3, 2 );
        Produit i1 = new Produit( null );
        Produit i2 = new Produit( null );
        Produit i3 = new Produit( null );
        List< Produit > resAttendu = new ArrayList<>( Arrays.asList( i1, i2, i3 ) );

        u.setTapis( 0, 0, TapisRoulant.GAUCHE_DROITE );
        u.setTapis( 0, 1, TapisRoulant.GAUCHE_DROITE );
        u.setTapis( 1, 1, TapisRoulant.GAUCHE_DROITE );
        u.placerItem( 0, 0, i1 );
        u.placerItem( 0, 1, i2 );
        u.placerItem( 1, 1, i3 );
        assertTrue( ensemblesEgaux.test( resAttendu, u.consulterItems() ) );
    }

    @Test
    void extraireItem1() {
        Logistique u = new Logistique( 2, 2 );
        Produit i1 = new Produit( null );
        List< Produit > resAttendu = new ArrayList<>();

        u.setTapis( 0, 1, TapisRoulant.GAUCHE_DROITE );
        u.placerItem( 0, 1, i1 );
        assertEquals( i1, u.extraireItem( 0, 1 ) );
        assertTrue( ensemblesEgaux.test( resAttendu, u.consulterItems() ) );
    }

    @Test
    void extraireItem2() {
        Logistique u = new Logistique( 2, 2 );
        Produit i1 = new Produit( null );
        Produit i2 = new Produit( null );
        List< Produit > resAttendu = new ArrayList<>( Arrays.asList( i1 ) );

        u.setTapis( 0, 1, TapisRoulant.GAUCHE_DROITE );
        u.setTapis( 1, 1, TapisRoulant.GAUCHE_DROITE );
        u.placerItem( 0, 1, i1 );
        u.placerItem( 1, 1, i2 );
        assertEquals( i2, u.extraireItem( 1, 1 ) );
        assertTrue( ensemblesEgaux.test( resAttendu, u.consulterItems() ) );
    }

    @Test
    void extraireItem3() {
        Logistique u = new Logistique( 2, 2 );
        Produit i1 = new Produit( null );
        Produit i2 = new Produit( null );
        Produit i3 = new Produit( null );
        List< Produit > resAttendu = new ArrayList<>( Arrays.asList( i2 ) );

        u.setTapis( 0, 1, TapisRoulant.GAUCHE_DROITE );
        u.setTapis( 1, 1, TapisRoulant.GAUCHE_DROITE );
        u.setTapis( 1, 0, TapisRoulant.GAUCHE_DROITE );
        u.placerItem( 0, 1, i1 );
        u.placerItem( 1, 1, i2 );
        u.placerItem( 1, 0, i3 );
        assertEquals( i1, u.extraireItem( 0, 1 ) );
        assertEquals( i3, u.extraireItem( 1, 0 ) );
        assertTrue( ensemblesEgaux.test( resAttendu, u.consulterItems() ) );
    }

    @Test
    void tic1() {
        Logistique u = new Logistique( 2, 2 );
        Produit i1 = new Produit( null );

        u.setTapis( 0, 1, TapisRoulant.GAUCHE_DROITE );
        u.placerItem( 0, 1, i1 );

        u.tic();

        List< Produit > produits = u.consulterItems();
        assertEquals( 0.05, produits.get( 0 ).getX(), DELTA );
        assertEquals( 1.0, produits.get( 0 ).getY(), DELTA );
    }

    @Test
    void tic2() {
        Logistique u = new Logistique( 2, 2 );
        Produit i1 = new Produit( null );

        u.setTapis( 0, 1, TapisRoulant.DROITE_GAUCHE );
        u.placerItem( 0, 1, i1 );

        u.tic();

        List< Produit > produits = u.consulterItems();
        assertEquals( -0.05, produits.get( 0 ).getX(), DELTA );
        assertEquals( 1.0, produits.get( 0 ).getY(), DELTA );
    }

    @Test
    void tic3() {
        Logistique u = new Logistique( 2, 2 );
        Produit i1 = new Produit( null );

        u.setTapis( 1, 1, TapisRoulant.DROITE_HAUT );
        u.placerItem( 1, 1, i1 );

        u.tic();

        List< Produit > produits = u.consulterItems();
        assertEquals( 1.0, produits.get( 0 ).getX(), DELTA );
        assertEquals( 0.95, produits.get( 0 ).getY(), DELTA );
    }

    @Test
    void tic4() {
        Logistique u = new Logistique( 3, 3 );
        Produit i1 = new Produit( null );

        u.setTapis( 1, 1, TapisRoulant.HAUT_BAS );
        u.placerItem( 1, 1, i1 );

        for( int i = 0; i < 11; ++ i ) {
            u.tic();
        }

        List< Produit > produits = u.consulterItems();
        assertEquals( 0, produits.size() );
    }

    @Test
    void tic5() {
        Logistique u = new Logistique( 3, 3 );
        Produit i1 = new Produit( null );

        u.setTapis( 1, 1, TapisRoulant.HAUT_BAS );
        u.setTapis( 1, 2, TapisRoulant.HAUT_BAS );
        u.placerItem( 1, 1, i1 );

        for( int i = 0; i < 11; ++ i ) {
            u.tic();
        }

        List< Produit > produits = u.consulterItems();
        assertEquals( 1.0, produits.get( 0 ).getX(), DELTA );
        assertEquals( 1.55, produits.get( 0 ).getY(), DELTA );
    }

    @Test
    void tic6() {
        Logistique u = new Logistique( 3, 3 );
        Produit i1 = new Produit( null );
        Produit i2 = new Produit( null );

        u.setTapis( 1, 1, TapisRoulant.HAUT_BAS );
        u.setTapis( 1, 2, TapisRoulant.DROITE_GAUCHE );
        u.placerItem( 1, 1, i1 );
        u.placerItem( 1, 2, i2 );

        u.tic();

        List< Produit > produits = u.consulterItems();
        Produit p0 = produits.get( 0 );
        Produit p1 = produits.get( 1 );

        boolean cas1 = approxEgal( p0.getX(), 1.0 ) && approxEgal( p0.getY(), 1.05 )
                && approxEgal( p1.getX(), 0.95 ) && approxEgal( p1.getY(), 2.0 );
        boolean cas2 = approxEgal( p0.getX(), 0.95 ) && approxEgal( p0.getY(), 2.0 )
                && approxEgal( p1.getX(), 1.0 ) && approxEgal( p1.getY(), 1.05 );
        assertTrue( cas1 || cas2 );
    }

    @Test
    void tic7() {
        Logistique u = new Logistique( 3, 3 );
        Produit i1 = new Produit( null );
        Produit i2 = new Produit( null );

        u.setTapis( 1, 1, TapisRoulant.HAUT_BAS );
        u.setTapis( 1, 2, TapisRoulant.HAUT_BAS );
        u.placerItem( 1, 1, i1 );

        for( int i = 0; i < 11; ++ i ) {
            u.tic();
        }

        u.placerItem( 1, 1, i2 );

        List< Produit > produits = u.consulterItems();
        Produit p0 = produits.get( 0 );
        Produit p1 = produits.get( 1 );

        boolean cas1 = approxEgal( p0.getX(), 1.0 ) && approxEgal( p0.getY(), 1.0 )
                && approxEgal( p1.getX(), 1.0 ) && approxEgal( p1.getY(), 1.55 );
        boolean cas2 = approxEgal( p0.getX(), 1.0 ) && approxEgal( p0.getY(), 1.55 )
                && approxEgal( p1.getX(), 1.0 ) && approxEgal( p1.getY(), 1.0 );
        assertTrue( cas1 || cas2 );
    }

    @Test
    void tic8() {
        Logistique u = new Logistique( 3, 3 );
        Produit i1 = new Produit( null );
        Produit i2 = new Produit( null );

        u.setTapis( 1, 1, TapisRoulant.HAUT_BAS );
        u.placerItem( 1, 1, i1 );

        for( int i = 0; i < 9; ++ i ) {
            u.tic();
        }

        assertThrows( PlacementIncorrectException.class, () -> u.placerItem( 1, 1, i2 ) );
    }

}