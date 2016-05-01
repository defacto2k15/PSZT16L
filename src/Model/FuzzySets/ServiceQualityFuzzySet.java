package Model.FuzzySets;

import Model.LinguisticAttributes;
import Model.CrispValues.CrispValuesDatabase;
import Model.LineralFunctions.Function;

public class ServiceQualityFuzzySet extends InputFuzzySet{ // TODO remove

	public ServiceQualityFuzzySet(Function function, ServiceQualityLinguisticValues linguisticValue) {
		super(function, LinguisticAttributes.FoodQuality, linguisticValue);
	}


}
