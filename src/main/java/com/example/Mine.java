package tp2;

public class Mine extends Station {
	protected IdentiteProduit minerai;
	private int compteur = 0;

	// Recettes | temps
	// ------------+----------
	// Acanthite | 2 tours
	// Cassiterite | 7 tours
	// Chalcocite | 3 tours
	// Charbon | 1 tour.

	// ....
	// .Ms.
	// ....
	// M( x, y ) : Case occupe par la Mine
	// s( x+1, y ) : Case ou la Mine place les sorties.

	public Mine(IdentiteProduit minerai, int positionX, int positionY) {
		super(positionX, positionY);

		this.minerai = minerai;
	}

	private int nombreDeTourParProduit() {
		switch (minerai) {
		case ACANTHITE:
			return 2;

		case CASSITERITE:
			return 7;

		case CHALCOCITE:
			return 3;

		case CHARBON:
			return 1;
		default:
			return 0;

		}

	}

	@Override
	public void tic(Usine parent) {
		int x = this.positionX;
		int y = this.positionY;
		int xSortieProduit = x + 1;
		int ySortieProduit = y;
		compteur++;
		if (compteur >= nombreDeTourParProduit()) {
			Produit produire = new Produit(minerai);
			parent.logistique.placerItem(xSortieProduit, ySortieProduit, produire);
			compteur--;

		}

	}

	@Override
	public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
		if (parent.stations[y][x] == null && parent.logistique.getTapis(x, y) == TapisRoulant.VIDE) {
			this.positionX = x;
			this.positionY = y;
			parent.stations[y][x] = this;
			parent.logistique.setTapis(x, y, TapisRoulant.OCCUPE);
		} else {
			throw new PlacementIncorrectException();

		}

	}
}
