package tp2;

import java.util.ArrayList;
import java.util.List;

public class Touraille extends Machine {
	private List<Produit>receptionDeStation=new ArrayList<>();
	private Produit produitEnFabrication=null;
	private int compteurTourDeFabrication = 0;
	private int compteurToursRequisProduit=0;
    //   ......
    //   .eMO..
    //   ..OOs.
    //   ......
    // M( x,   y )   : Premiere case occupe par la Touraille
    // O( x+1, y )   : deuxieme case occupe par la Touraille
    // O( x,   y+1 ) : troisieme case occupe par la Touraille
    // O( x+1, y+1 ) : quatrieme case occupe par la Touraille
    // e( x-1, y )   : Case ou la Touraille prend ses entrees.
    // s( x+2, y+1 ) : Case ou la Touraille place les sorties.

    public Touraille( int positionX, int positionY ) {
        super( positionX, positionY );
    }

    private boolean EstBonneProduitPourStation(Produit produit) {
		if (produit == null || produit.identite == null) {
			return false;
			}
		
		switch(produit.identite){
		case CHARBON:
			return true;
			default:
				return false;
			
		}
	}
private Produit recette(Produit produitEntree) {
		
		  
		
		
		switch (produitEntree.identite) {
		case CHARBON:
			return new Produit(IdentiteProduit.COKE);
		default:
			return null;

		}
	}
private int nombreDeTourParProduit(Produit produit) {
	 if (produit == null || produit.identite == null) return 0;
	
	switch (produit.identite) {
	
	case CHARBON:
		return 20;

	default:
		return 0;

	}

}

	@Override
	public void tic(Usine parent) {
		int xEntree=positionX-1;
		int yEntree=positionY;
		int xSortie=positionX+2;
		int ySortie=positionY+1;
		
		
		
		Produit produitTrouver=parent.logistique.trouverItem(xEntree, yEntree);



		
		if(this.receptionDeStation.isEmpty()&&produitTrouver!=null && EstBonneProduitPourStation(produitTrouver)) {
		this.receptionDeStation.add(produitTrouver);
		parent.logistique.extraireItem(xEntree, yEntree);
		}
		

		
		
	

			
			if(produitEnFabrication!=null) {

				
				if(compteurTourDeFabrication>=compteurToursRequisProduit) {
					Produit produitSortie=recette(produitEnFabrication);
					 boolean tapisOk = parent.logistique.getTapis(xSortie, ySortie) != TapisRoulant.VIDE;
				        boolean caseLibre = !parent.logistique.contiensItem(xSortie, ySortie);
				        if(tapisOk && caseLibre) {

					
					parent.logistique.placerItem(xSortie, ySortie, produitSortie);
					produitEnFabrication=null;
					compteurTourDeFabrication=0;
				        }
					
				}else {
					compteurTourDeFabrication++;
					}
				
				}
			Produit produitEnAttend=null;
				
				if(!(this.receptionDeStation.isEmpty())) {
					 produitEnAttend=receptionDeStation.get(0);
				}
					if(EstBonneProduitPourStation(produitEnAttend)) {
						produitEnFabrication=produitEnAttend;
						compteurToursRequisProduit=nombreDeTourParProduit(produitEnAttend);
						
						compteurTourDeFabrication=0;
						receptionDeStation.clear();
					}
					
	
				}
			

    @Override
    public void placer( int x, int y, Usine parent ) throws PlacementIncorrectException {
    	boolean caseLibre=parent.stations[y][x] == null && 
				parent.stations[y][x + 1] == null && 
				parent.stations[y + 1][x] == null && 
				parent.stations[y+1][x + 1] == null;	
		boolean tapisLibre=parent.logistique.getTapis(x, y) == TapisRoulant.VIDE &&
				parent.logistique.getTapis(x+1, y) == TapisRoulant.VIDE &&
				parent.logistique.getTapis(x, y+1) == TapisRoulant.VIDE &&
				parent.logistique.getTapis(x+1, y+1) == TapisRoulant.VIDE;
		
		if(caseLibre&&tapisLibre) {
			parent.stations[y][x]=this;
			parent.stations[y][x+1]=this;
			parent.stations[y+1][x]=this;
			parent.stations[y+1][x+1]=this;
			parent.logistique.setTapis(x, y, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x + 1, y, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x, y + 1, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x + 1, y+1, TapisRoulant.OCCUPE);
			this.positionX=x;
			this.positionY=y;
		}else {
			throw new PlacementIncorrectException();
			
		}

    }

    }

