package Model.FuzzySets;

import Model.LinguisticAttributes;
import Model.CrispValues.CrispValuesDatabase;
import Model.LineralFunctions.Function;

public class CharmFuzzySet extends InputFuzzySet { // TODO remove

	public CharmFuzzySet(Function function,	CharmLinguisticValue linguisticValue) {
		super(function, LinguisticAttributes.Charm, linguisticValue);
	}


}
