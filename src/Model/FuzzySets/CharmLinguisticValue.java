package Model.FuzzySets;

public enum CharmLinguisticValue implements ILinguisticValue{
	paskudny("Paskudny"), rozczarowujacy("Rozczarowujący"), nijaki("Nijaki"),
	zadowalajacy("Zadowalający"), rewelacyjny("Rewelacyjny");
	
	private String name;

	private CharmLinguisticValue(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
}

