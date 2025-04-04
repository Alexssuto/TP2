package tp2;

import java.util.ArrayList;
import java.util.List;

public class Moulin extends Machine {
	private List<Produit>receptionDeStation=new ArrayList<>();
	private Produit produitEnFabrication=null;
	private int compteurTourDeFabrication = 0;
	private int compteurToursRequisProduit=0;
	
	
	
	
	
	protected IdentiteProduit minerai;

	// ......
	// .eMOs.
	// ......
	// M( x, y ) : Premiere case occupe par le Moulin
	// O( x+1, y ) : deuxieme case occupe par le Moulin
	// e( x-1, y ) : Case ou le Moulin prend ses entrees.
	// s( x+2, y ) : Case ou le Moulin place les sorties.

	/**
	 * Construit un Moulin
	 * 
	 * @param positionX Position en X de la premiere case du Moulin (M).
	 * @param positionY Position en Y de la premiere case du Moulin (M).
	 */
	public Moulin(int positionX, int positionY) {
		super(positionX, positionY);
		
		
	}
	
	private int nombreDeTourParProduit(Produit produit) {
		 if (produit == null || produit.identite == null) return 0;
		
		switch (produit.identite) {
		
		case ACANTHITE:
		case CHALCOCITE:
			return 3;

		case CASSITERITE:
			return 7;
		default:
			return 0;

		}

	}
	private boolean EstBonneProduitPourStation(Produit produit) {
		if (produit == null || produit.identite == null) {
			return false;
			}
		
		switch(produit.identite){
		case ACANTHITE:
			return true;
		case CHALCOCITE:
			return true;
		case CASSITERITE:
			return true;
			default:
				return false;
			
		}
	}

	private Produit recette(Produit produitEntree) {
		if (produitEntree == null || produitEntree.identite == null) {
			return null;
		}
		  
		
		
		switch (produitEntree.identite) {
		case ACANTHITE:
			return new Produit(IdentiteProduit.POUDRE_ACANTHITE);

		case CASSITERITE:
			return new Produit(IdentiteProduit.POUDRE_CASSITERITE);

		case CHALCOCITE:
			return new Produit(IdentiteProduit.POUDRE_CHALCOCITE);
		default:
			return null;

		}
	}
	
	
	private Produit EntreePourTransformation(int nbrTour) {
		
		switch(nbrTour) {
		case 3:
			return 
			new Produit(IdentiteProduit.ACANTHITE);
		
	
			
			
		case 7:
			return new Produit(IdentiteProduit.CASSITERITE);
			default:
				return null;
			
			
		
		}
	}
	
	public void tic(Usine parent) {
		int xEntree = positionX - 1;
		int yEntree = positionY;
		int xSortie = positionX + 2;
		int ySortie = positionY;
		boolean tapisLibre = parent.logistique.getTapis(xSortie, ySortie) != TapisRoulant.VIDE;
		boolean caseLibre = !parent.logistique.contiensItem(xSortie, ySortie);

		// Étape 1 : Si aucun produit en fabrication, tenter d'en prendre un
		if (produitEnFabrication == null) {
			Produit produitTrouve = parent.logistique.trouverItem(xEntree, yEntree);
			if (EstBonneProduitPourStation(produitTrouve)&&tapisLibre&&caseLibre) {
				parent.logistique.extraireItem(xEntree, yEntree);
				produitEnFabrication = produitTrouve;
				compteurToursRequisProduit = nombreDeTourParProduit(produitTrouve);
				compteurTourDeFabrication = 0;
				System.out.println(">>> Produit pris en fabrication : " + produitEnFabrication.identite);
				
			}
		}
//		System.out.println(compteurToursRequisProduit);
//		System.out.println(compteurTourDeFabrication);
		// Étape 2 : Si en fabrication, incrémenter le compteur

		if (produitEnFabrication != null)
		{
			compteurTourDeFabrication++;
			// Étape 3 : Une fois la fabrication terminée
			if (compteurTourDeFabrication -1 >  compteurToursRequisProduit) {
				Produit produitSortie = recette(produitEnFabrication);
		

				if (tapisLibre && caseLibre) {
					parent.logistique.placerItem(xSortie, ySortie, produitSortie);
					produitEnFabrication = null;
					compteurTourDeFabrication = 0;
					compteurToursRequisProduit = 0;
				}
			}
		}

	}

	
/* 
	@Override
	public void tic(Usine parent) {
		int xEntree=positionX-1;
		int yEntree=positionY;
		int xSortie=positionX+2;
		int ySortie=positionY;
		
		
		
		Produit produitTrouver=parent.logistique.trouverItem(xEntree, yEntree);



		
		if(this.receptionDeStation.isEmpty()&&produitTrouver!=null && EstBonneProduitPourStation(produitTrouver)) {
		this.receptionDeStation.add(produitTrouver);
		parent.logistique.extraireItem(xEntree, yEntree);
		}
		
//	}
//		if(this.receptionDeStation.isEmpty()) {
//			if(produitTrouver!=null && EstBonneProduitPourStation(produitTrouver)) {
//				this.receptionDeStation.add(produitTrouver);
//				parent.logistique.extraireItem(xEntree, yEntree);
//				
//			}
//			}
		
		
	

			
			if(produitEnFabrication!=null) {
//				compteurTourDeFabrication++;
				
				if(compteurTourDeFabrication>=compteurToursRequisProduit) {
					//Produit produitEntree=EntreePourTransformation(compteurTourDeFabrication);
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
			
//				else if(!(this.receptionDeStation.isEmpty())) {
//						Produit produitEnAttend=receptionDeStation.get(0);
//						if(EstBonneProduitPourStation(produitEnAttend)) {
//							produitEnFabrication=produitEnAttend;
//							compteurToursRequisProduit=nombreDeTourParProduit(produitEnAttend);
//							
//							compteurTourDeFabrication=0;
//							receptionDeStation.clear();
//						}
//							
//							
//							
//						
//					}
//					
//	}
*/

	@Override
	public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
		boolean caseLibre = parent.stations[y][x] == null && parent.stations[y][x + 1] == null;

		boolean tapisLibre = parent.logistique.getTapis(x, y) == TapisRoulant.VIDE
				&& parent.logistique.getTapis(x + 1, y) == TapisRoulant.VIDE;

		if (caseLibre && tapisLibre) {
			parent.stations[y][x] = this;
			parent.stations[y][x + 1] = this;
			parent.logistique.setTapis(x, y, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x + 1, y, TapisRoulant.OCCUPE);
			this.positionX = x;
			this.positionY = y;
		} else {
			throw new PlacementIncorrectException();

		}

	}
}