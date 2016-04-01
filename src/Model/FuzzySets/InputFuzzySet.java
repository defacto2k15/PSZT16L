package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LinguisticAttributes;
import Model.LineralFunctions.Function;

public abstract class InputFuzzySet extends FuzzySet{
	private LinguisticAttributes linguisticAttribute;
	private ILinguisticValue linguisticValue;
	
	public InputFuzzySet(Function function, LinguisticAttributes linguisticAttribute,
			ILinguisticValue linguisticValue) {
		super(function);
		this.linguisticAttribute = linguisticAttribute;
		this.linguisticValue = linguisticValue;
	}
	
	public LinguisticAttributes getLinguisticAttribute(){
		return linguisticAttribute;
	}
	public ILinguisticValue getLinguisticValue(){
		return linguisticValue;
	}
	
	public float getDegree(CrispValuesDatabase crispValuesDatabase ){
		return getFunction().getValueAt( crispValuesDatabase.getValueFor(linguisticAttribute, linguisticValue));
	}
	
}
