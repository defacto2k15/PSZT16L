package Model.FuzzySets;

import Model.LinguisticAttributes;
import Model.CrispValues.CrispValuesDatabase;
import Model.LineralFunctions.Function;

public class FoodQualityFuzzySet extends InputFuzzySet{ // TODO remove
	public FoodQualityFuzzySet(Function function, FoodQualityLinguisticValues linguisticValue) {
		super(function, LinguisticAttributes.FoodQuality, linguisticValue);
	}
}
