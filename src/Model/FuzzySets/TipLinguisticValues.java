package Model.FuzzySets;

public enum TipLinguisticValues  implements ILinguisticValue {
	Bardzo_maly("Bardzo mały"), Maly("Mały"), Sredni("Średni"), Duzy("Duży"), BardzoDuzy("Bardzo duży");
	
	private String name;

	TipLinguisticValues( String name){
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
