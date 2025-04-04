package tp2;

public class Fournaise extends Machine {
	// ......
	// .eO...
	// ..MOs.
	// .fO...
	// ......
	// M( x, y ) : Premiere case occupe par la Founaise
	// O( x, y-1 ) : deuxieme case occupe par la Fournaise
	// O( x+1, y ) : troisieme case occupe par la Fournaise
	// O( x, y+1 ) : quatrieme case occupe par la Fournaise
	// e( x-1, y-1 ) : Case ou la Fournaise prend ses entrees pour la boite 1.
	// f( x-1, y+1 ) : Case ou la Fournaise prend ses entrees pour la boite 2.
	// s( x+2, y ) : Case ou la Fournaise place les sorties.
	private Produit currentEntree1 = null;
	private Produit currentEntree2 = null;
	private int currentTick = 0;
	private IdentiteProduit currentProduction = null;

    public Fournaise(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	public void tic(Usine parent)
	{
		int x = this.positionX;
		int y = this.positionY;
		if (currentEntree1 == null)
		{
			currentEntree1 = parent.logistique.trouverItem(x - 1, y - 1);
		}
		if (currentEntree2 == null)
		{
			currentEntree2 = parent.logistique.trouverItem(x - 1, y + 1);
		}
		if (currentTick > 0)
		{
			currentTick++;
			if (currentTick >= getRequiredTicks(currentProduction)) {
				Produit sortie = parent.logistique.trouverItem(x + 2, y);
				if (sortie == null && currentProduction != null) {
					parent.logistique.placerItem(x + 2, y, new Produit(currentProduction));
				}
				resetProduction();
			}
		} else if (canStartProduction()) {
			parent.logistique.extraireItem((int)currentEntree1.getX(), (int)currentEntree1.getY());
			if (currentEntree2 != null) {
				parent.logistique.extraireItem((int)currentEntree2.getX(), (int)currentEntree2.getY());
			}
			currentProduction = determineOutputProduct();
			currentTick = 1;
		}
	}

	private boolean canStartProduction() {
		if (currentEntree1 != null && currentEntree2 != null &&
				currentEntree1.identite == IdentiteProduit.ACANTHITE &&
				currentEntree2.identite == IdentiteProduit.CHARBON) {
			return true;
		}

		if (currentEntree1 != null &&
				currentEntree1.identite == IdentiteProduit.POUDRE_ACANTHITE) {
			return true;
		}

		if (currentEntree1 != null && currentEntree2 != null &&
				currentEntree1.identite == IdentiteProduit.CHALCOCITE &&
				currentEntree2.identite == IdentiteProduit.CHARBON) {
			return true;
		}
		if (currentEntree1 != null && currentEntree2 != null &&
				((currentEntree1.identite == IdentiteProduit.LINGOT_CUIVRE &&
						currentEntree2.identite == IdentiteProduit.LINGOT_ETAIN) ||
						(currentEntree1.identite == IdentiteProduit.LINGOT_ETAIN &&
								currentEntree2.identite == IdentiteProduit.LINGOT_CUIVRE))) {
			return true;
		}

		return false;
	}

	private IdentiteProduit determineOutputProduct() {
		if (currentEntree1 == null)
		{
			return null;
		}
		if (currentEntree1.identite == IdentiteProduit.ACANTHITE ||
				currentEntree1.identite == IdentiteProduit.POUDRE_ACANTHITE) {
			return IdentiteProduit.LITHARGE;
		}
		if (currentEntree1.identite == IdentiteProduit.CHALCOCITE) {
			return IdentiteProduit.LINGOT_CUIVRE;
		}
		if (currentEntree1.identite == IdentiteProduit.LINGOT_CUIVRE ||
				currentEntree1.identite == IdentiteProduit.LINGOT_ETAIN) {
			return IdentiteProduit.LINGOT_BRONZE;
		}

		return null;
	}

	private int getRequiredTicks(IdentiteProduit product) {
		if (product == null) return 0;

		switch(product) {
			case LITHARGE: return 4;
			case LINGOT_CUIVRE: return 4;
			case LINGOT_BRONZE: return 4;
			default: return 0;
		}
	}

	private void resetProduction() {
		currentEntree1 = null;
		currentEntree2 = null;
		currentTick = 0;
		currentProduction = null;
	}

	@Override
	public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {

		boolean caseLibre = parent.stations[y][x] == null && parent.stations[y - 1][x] == null
				&& parent.stations[y + 1][x] == null && parent.stations[y][x + 1] == null;

		boolean tapisLibre = parent.logistique.getTapis(x, y) == TapisRoulant.VIDE
				&& parent.logistique.getTapis(x, y - 1) == TapisRoulant.VIDE
				&& parent.logistique.getTapis(x, y + 1) == TapisRoulant.VIDE
				&& parent.logistique.getTapis(x + 1, y) == TapisRoulant.VIDE;

		if (caseLibre && tapisLibre) {
			parent.stations[y][x] = this;
			parent.stations[y - 1][x] = this;
			parent.stations[y+1][x] = this;
			parent.stations[y][x+1] = this;
			parent.logistique.setTapis(x, y, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x, y - 1, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x, y+1, TapisRoulant.OCCUPE);
			parent.logistique.setTapis(x+1, y, TapisRoulant.OCCUPE);
			this.positionX = x;
			this.positionY = y;
		} else {
			throw new PlacementIncorrectException();

		}

	}
}
