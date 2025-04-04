package tp2;

public class FournaiseDeGrillage extends Machine {
	// .......
	// ...f...
	// ...O...
	// .eOMOs.
	// .......
	// M( x, y ) : Premiere case occupe par la Founaise
	// O( x-1, y ) : deuxieme case occupe par la Fournaise
	// O( x, y-1 ) : troisieme case occupe par la Fournaise
	// O( x+1, y ) : quatrieme case occupe par la Fournaise
	// e( x-2, y ) : Case ou la Fournaise prend ses entrees pour la boite 1.
	// f( x, y-2 ) : Case ou la Fournaise prend ses entrees pour la boite 2.
	// s( x+2, y ) : Case ou la Fournaise place les sorties.

	public FournaiseDeGrillage(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	public void tic(Usine parent) {
		int x = this.positionX;
		int y = this.positionY;

		Produit entree1 = parent.logistique.trouverItem(x - 2, y);
		Produit entree2 = parent.logistique.trouverItem(x, y - 2);
		Produit sortie = parent.logistique.trouverItem(x + 2, y);

		if (entree1 != null && entree2 != null && sortie == null &&
				entree1.identite == IdentiteProduit.CHALCOCITE &&
				entree2.identite == IdentiteProduit.CHALCOCITE) {
			parent.logistique.getProduits().remove(entree1);
			parent.logistique.getProduits().remove(entree2);

			parent.logistique.placerItem(x + 2, y, new Produit(IdentiteProduit.OXYDE_CUIVRE));
		}
	}

	@Override
	public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
		boolean caseLibre=parent.stations[y][x] == null && 
				parent.stations[y][x - 1] == null && 
				parent.stations[y - 1][x] == null && 
				parent.stations[y][x + 1] == null;	
		boolean tapisLibre=parent.logistique.getTapis(x, y) == TapisRoulant.VIDE &&
				parent.logistique.getTapis(x-1, y) == TapisRoulant.VIDE &&
				parent.logistique.getTapis(x, y-1) == TapisRoulant.VIDE &&
				parent.logistique.getTapis(x+1, y) == TapisRoulant.VIDE;
		
		if(caseLibre&&tapisLibre) {
			parent.stations[y][x]=this;
			parent.stations[y][x-1]=this;
			parent.stations[y-1][x]=this;
			parent.stations[y][x+1]=this;
			parent.logistique.setTapis(x, y, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x - 1, y, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x, y - 1, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x + 1, y, TapisRoulant.OCCUPE);
			this.positionX=x;
			this.positionY=y;
		}else {
			throw new PlacementIncorrectException();
			
		}
			
		}
			
			
			
			
//		if (parent.stations[y][x] == null && parent.stations[y][x - 1] == null && parent.stations[y - 1][x] == null
//				&& parent.stations[y][x + 1] == null) {
//			parent.stations[y][x] = this;
//			parent.stations[y][x - 1] = this;
//			parent.stations[y - 1][x] = this;
//			parent.stations[y][x + 1] = this;
//			this.positionX=x;
//			this.positionY=y;
//	
//			
//		} else {
//			throw new PlacementIncorrectException();
//			}
//			if(parent.logistique.getTapis(x, y) == TapisRoulant.VIDE||
//					parent.logistique.getTapis(x-1, y) == TapisRoulant.VIDE||
//					parent.logistique.getTapis(x, y-1) == TapisRoulant.VIDE||
//					parent.logistique.getTapis(x+1, y) == TapisRoulant.VIDE) {
//				
//				parent.logistique.setTapis(x, y, TapisRoulant.OCCUPE);
//				parent.logistique.setTapis(x - 1, y, TapisRoulant.OCCUPE);
//				parent.logistique.setTapis(x, y - 1, TapisRoulant.OCCUPE);
//				parent.logistique.setTapis(x + 1, y, TapisRoulant.OCCUPE);
//				
//			}else {
//				throw new PlacementIncorrectException();
//			}
//			}

		
//
//		if (parent.stations[y][x] == null && parent.logistique.getTapis(x, y) == TapisRoulant.VIDE) {
//			this.positionX = x;
//			this.positionY = y;
//			parent.logistique.setTapis(x, y, TapisRoulant.OCCUPE);
//			parent.logistique.setTapis(x - 1, y, TapisRoulant.OCCUPE);
//			parent.logistique.setTapis(x, y - 1, TapisRoulant.OCCUPE);
//			parent.logistique.setTapis(x + 1, y, TapisRoulant.OCCUPE);
//		} else {
//
//			throw new PlacementIncorrectException();
//		}
		// parent.
	}


