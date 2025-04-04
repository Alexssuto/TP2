package com.example;
public class Moulin extends Machine {
    //   ......
    //   .eMOs.
    //   ......
    // M( x,   y ) : Première case occupé par le Moulin
    // O( x+1, y ) : deuxième case occupé par le Moulin
    // e( x-1, y ) : Case où le Moulin prend ses entrées.
    // s( x+2, y ) : Case où le Moulin place les sorties.

    /**
     * Construit un Moulin
     * @param positionX Position en X de la première case du Moulin (M).
     * @param positionY Position en Y de la première case du Moulin (M).
     */
    public Moulin( int positionX, int positionY ) {
        super( positionX, positionY );
    }

    @Override
    public void tic( Usine parent ) {

    }

    @Override
    public void placer( int x, int y, Usine parent ) throws PlacementIncorrectException {

    }
}
