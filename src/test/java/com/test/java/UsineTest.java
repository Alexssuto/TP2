package com.example;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsineTest {
    @Test
    void testMinePlacer() {
        Usine usine = new Usine( 3, 4 );
        Mine mine = new Mine( IdentiteProduit.ACANTHITE, 1, 1 );

        assertDoesNotThrow( () -> mine.placer( 1, 1, usine ) );
        assertEquals( mine, usine.stations[ 1 ][ 1 ] );
    }

    @Test
    void testMineAcanthiteTic() {
        Usine usine = new Usine( 3, 4 );
        Mine mine = new Mine( IdentiteProduit.ACANTHITE, 1, 1 );
        mine.placer( 1, 1, usine );
        usine.setTapisHorizontal( 1, 2, 2 );

        mine.tic( usine );
        assertNull( usine.trouverItem( 2, 1 ) );
        mine.tic( usine );
        assertEquals( IdentiteProduit.ACANTHITE, usine.trouverItem( 2, 1 ).identite );
    }

    @Test
    void testMineCassiteriteTic() {
        Usine usine = new Usine( 3, 4 );
        Mine mine = new Mine( IdentiteProduit.CASSITERITE, 1, 2 );
        mine.placer( 1, 2, usine );
        usine.setTapisHorizontal( 2, 2, 2 );

        for( int i = 0; i < 6; ++ i ) {
            mine.tic( usine );
            assertNull( usine.trouverItem( 2, 2 ) );
        }
        mine.tic( usine );
        assertEquals( IdentiteProduit.CASSITERITE, usine.trouverItem( 2, 2 ).identite );
    }

    @Test
    void testMineChalcociteTic() {
        Usine usine = new Usine( 3, 4 );
        Mine mine = new Mine( IdentiteProduit.CHALCOCITE, 1, 2 );
        mine.placer( 1, 2, usine );
        usine.setTapisHorizontal( 2, 2, 2 );

        for( int i = 0; i < 2; ++ i ) {
            mine.tic( usine );
            assertNull( usine.trouverItem( 2, 2 ) );
        }
        mine.tic( usine );
        assertEquals( IdentiteProduit.CHALCOCITE, usine.trouverItem( 2, 2 ).identite );
    }

    @Test
    void testMineCharbonTic() {
        Usine usine = new Usine( 3, 4 );
        Mine mine = new Mine( IdentiteProduit.CHARBON, 1, 2 );
        mine.placer( 1, 2, usine );
        usine.setTapisHorizontal( 2, 2, 2 );

        mine.tic( usine );
        assertEquals( IdentiteProduit.CHARBON, usine.trouverItem( 2, 2 ).identite );
    }
}