package com.example;
public abstract class Station {
    protected int positionX;
    protected int positionY;

    public Station( int positionX, int positionY ) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Simule un tour de simulation pour cette station.
     * @param parent Un lien vers l'usine afin que la station puisse interagir avec
     *               les variables d'instance de l'usine tel que {\code logistique}.
     */
    public abstract void tic( Usine parent );

    public abstract void placer( int x, int y, Usine parent )
            throws PlacementIncorrectException;
}
