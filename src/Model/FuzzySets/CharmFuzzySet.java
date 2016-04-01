package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LinguisticAttributes;
import Model.LineralFunctions.Function;

public class CharmFuzzySet extends InputFuzzySet {

	public CharmFuzzySet(Function function,	CharmLinguisticValue linguisticValue) {
		super(function, LinguisticAttributes.Charm, linguisticValue);
	}


}
