package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LinguisticAttributes;
import Model.LineralFunctions.LineralFunction;

public class ServiceQualityFuzzySet extends InputFuzzySet{

	public ServiceQualityFuzzySet(LineralFunction function, ServiceQualityLinguisticValues linguisticValue) {
		super(function, LinguisticAttributes.FoodQuality, linguisticValue);
	}


}
