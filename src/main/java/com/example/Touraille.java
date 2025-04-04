package com.example;
public class Touraille extends Machine {
    //   ......
    //   .eMO..
    //   ..OOs.
    //   ......
    // M( x,   y )   : Première case occupé par la Touraille
    // O( x+1, y )   : deuxième case occupé par la Touraille
    // O( x,   y+1 ) : troisième case occupé par la Touraille
    // O( x+1, y+1 ) : quatrième case occupé par la Touraille
    // e( x-1, y )   : Case où la Touraille prend ses entrées.
    // s( x+2, y+1 ) : Case où la Touraille place les sorties.

    public Touraille( int positionX, int positionY ) {
        super( positionX, positionY );
    }

    @Override
    public void tic( Usine parent ) {

    }

    @Override
    public void placer( int x, int y, Usine parent ) throws PlacementIncorrectException {

    }
}
