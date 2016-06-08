package Model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import Model.FuzzySets.CharmLinguisticValue;
import Model.FuzzySets.ILinguisticValue;
import Model.FuzzySets.FoodQualityLinguisticValues;
import Model.FuzzySets.ServiceQualityLinguisticValues;
import Model.FuzzySets.TipLinguisticValues;

public enum LinguisticAttributes {
	Charm(100,0,Arrays.asList(CharmLinguisticValue.values() ), "Urok"),
	FoodQuality(100,0, Arrays.asList(FoodQualityLinguisticValues.values() ),"Jakość jedzenia"),
	ServiceQuality(100,0, Arrays.asList(ServiceQualityLinguisticValues.values() ),"Jakość obsługi"),
	Tip(25,5, Arrays.asList(TipLinguisticValues.values() ),"Napiwek");
	
	private int maxCrispValue;
	private int minCrispValue;
	private List<ILinguisticValue> linguisticValues;
	private String niceName;

	private LinguisticAttributes( int maxCrispValue, int minCrispValue, List<? extends ILinguisticValue> linguisticValues, String niceName ){
		this.maxCrispValue = maxCrispValue;
		this.minCrispValue = minCrispValue;
		this.linguisticValues =  linguisticValues.stream().map(x -> (ILinguisticValue)x).collect( Collectors.toList());
		this.niceName = niceName;
	}
	
	public int getMaxCrispValue(){
		return maxCrispValue;
	}

	public List<ILinguisticValue> getLinguisticValues() {
		return linguisticValues;
	}
	
	@Override
	public String toString(){
		return niceName;
	}

	public float getCrispMinCrispValue() {
		return minCrispValue;
	}
}
