package Model.FuzzySets;

import Model.CrispValues.CrispValuesDatabase;
import Model.LineralFunctions.Function;

public class TipFuzzySet extends FuzzySet {

	private TipLinguisticValues linguisticValue;

	public TipFuzzySet(TipLinguisticValues linguisticValue, Function function) {
		super(function);
		this.linguisticValue = linguisticValue;
	}

	public TipLinguisticValues getType() {
		return linguisticValue;
	}

}
