package Model.FuzzySets;

public enum FoodQualityLinguisticValues implements ILinguisticValue{
	Bardzo_slaba("Bardzo słaba"), Srednia("Średnia"), Dobra("Dobra");

	private String name;
	
	private FoodQualityLinguisticValues(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
