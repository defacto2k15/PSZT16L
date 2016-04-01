package Model.FuzzySets;

public enum ServiceQualityLinguisticValues implements ILinguisticValue{
	slaba("Słąba"), przecietna("Przeciętna"), dobra("Dobra"), bardzo_dobra("Bardzo dobra");

	private String name;
	
	private  ServiceQualityLinguisticValues(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
