package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LinguisticAttributes;
import Model.LineralFunctions.Function;

public class ServiceQualityFuzzySet extends InputFuzzySet{

	public ServiceQualityFuzzySet(Function function, ServiceQualityLinguisticValues linguisticValue) {
		super(function, LinguisticAttributes.FoodQuality, linguisticValue);
	}


}
