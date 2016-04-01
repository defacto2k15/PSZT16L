package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LinguisticAttributes;
import Model.LineralFunctions.LineralFunction;

public class FoodQualityFuzzySet extends InputFuzzySet{
	public FoodQualityFuzzySet(LineralFunction function, FoodQualityLinguisticValues linguisticValue) {
		super(function, LinguisticAttributes.FoodQuality, linguisticValue);
	}
}
