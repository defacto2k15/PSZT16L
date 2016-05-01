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
	Charm(100, Arrays.asList(CharmLinguisticValue.values() )),
	FoodQuality(100, Arrays.asList(FoodQualityLinguisticValues.values() )),
	ServiceQuality(100, Arrays.asList(ServiceQualityLinguisticValues.values() )),
	Tip(100, Arrays.asList(TipLinguisticValues.values() ));
	
	private int maxCrispValue;
	private List<ILinguisticValue> linguisticValues;

	private LinguisticAttributes( int maxCrispValue, List<? extends ILinguisticValue> linguisticValues ){
		this.maxCrispValue = maxCrispValue;
		this.linguisticValues =  linguisticValues.stream().map(x -> (ILinguisticValue)x).collect( Collectors.toList());
	}
	
	public int getMaxCrispValue(){
		return maxCrispValue;
	}

	public List<ILinguisticValue> getLinguisticValues() {
		return linguisticValues;
	}
}
