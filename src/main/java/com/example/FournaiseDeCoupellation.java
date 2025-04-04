package com.example;
public class FournaiseDeCoupellation extends Machine {
    //   .....
    //   .eO..
    //   ..Ms.
    //   .....
    // M( x,   y )   : Première case occupé par la Founaise
    // O( x,   y-1 ) : deuxième case occupé par la Fournaise
    // e( x-1, y-1 ) : Case où la Fournaise prend ses entrées.
    // s( x+1, y )   : Case où la Fournaise place les sorties.

    public FournaiseDeCoupellation( int positionX, int positionY ) {
        super( positionX, positionY );
    }

    @Override
    public void tic( Usine parent ) {

    }

    @Override
    public void placer( int x, int y, Usine parent ) throws PlacementIncorrectException {

    }
}
