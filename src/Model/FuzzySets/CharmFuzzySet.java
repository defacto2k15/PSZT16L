package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LinguisticAttributes;
import Model.LineralFunctions.LineralFunction;

public class CharmFuzzySet extends InputFuzzySet {

	public CharmFuzzySet(LineralFunction function,	CharmLinguisticValue linguisticValue) {
		super(function, LinguisticAttributes.Charm, linguisticValue);
	}


}
