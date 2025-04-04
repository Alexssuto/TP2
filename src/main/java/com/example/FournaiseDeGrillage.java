package com.example;
public class FournaiseDeGrillage extends Machine {
    //   .......
    //   ...f...
    //   ...O...
    //   .eOMOs.
    //   .......
    // M( x,   y )   : Première case occupé par la Founaise
    // O( x-1, y )   : deuxième case occupé par la Fournaise
    // O( x,   y-1 ) : troisième case occupé par la Fournaise
    // O( x+1, y )   : quatrième case occupé par la Fournaise
    // e( x-2, y )   : Case où la Fournaise prend ses entrées pour la boite 1.
    // f( x,   y-2 ) : Case où la Fournaise prend ses entrées pour la boite 2.
    // s( x+2, y )   : Case où la Fournaise place les sorties.

    public FournaiseDeGrillage( int positionX, int positionY ) {
        super( positionX, positionY );
    }

    @Override
    public void tic( Usine parent ) {

    }

    @Override
    public void placer( int x, int y, Usine parent ) throws PlacementIncorrectException {

    }
}
