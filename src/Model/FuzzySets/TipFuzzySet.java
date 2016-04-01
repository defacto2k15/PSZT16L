package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LineralFunctions.LineralFunction;

public class TipFuzzySet extends FuzzySet {

	private TipLinguisticValues linguisticValue;

	public TipFuzzySet(TipLinguisticValues linguisticValue, LineralFunction function) {
		super(function);
		this.linguisticValue = linguisticValue;
	}

	public TipLinguisticValues getType() {
		return linguisticValue;
	}

}
