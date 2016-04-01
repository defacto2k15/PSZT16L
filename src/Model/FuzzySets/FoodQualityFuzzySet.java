package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LinguisticAttributes;
import Model.LineralFunctions.Function;

public class FoodQualityFuzzySet extends InputFuzzySet{
	public FoodQualityFuzzySet(Function function, FoodQualityLinguisticValues linguisticValue) {
		super(function, LinguisticAttributes.FoodQuality, linguisticValue);
	}
}
