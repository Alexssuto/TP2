package tp2;

public class Vendeur extends Station {
	// ....
	// .eM.
	// ....
	// M( x, y ) : Case occupe par le Vendeur
	// e( x-1, y ) : Case ou le Vendeur prends ses entrees.

	public Vendeur(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	public void tic(Usine parent)
	{
		int xEntree = positionX - 1;
		int yEntree = positionY;
		if (parent.logistique.contiensItem(xEntree, yEntree)) {
			Produit produit = parent.logistique.trouverItem(xEntree, yEntree);
			if (produit != null) {
				parent.logistique.extraireItem(xEntree, yEntree);
				parent.sales.put(
						produit.identite,
						parent.sales.getOrDefault(produit.identite, 0) + 1
				);
			}
		}
	}

	@Override
	public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
		if (parent.stations[y][x] == null && parent.logistique.getTapis(x, y) == TapisRoulant.VIDE) {
			x = this.positionX;
			y = this.positionY;
			parent.stations[y][x] = this;
			parent.logistique.setTapis(x, y, TapisRoulant.OCCUPE);

		} else {
			throw new PlacementIncorrectException();
		}

	}
}
