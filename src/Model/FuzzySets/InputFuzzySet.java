package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LinguisticAttributes;
import Model.LineralFunctions.LineralFunction;

public abstract class InputFuzzySet extends FuzzySet{
	private LinguisticAttributes linguisticAttribute;
	private ILinguisticValue linguisticValue;
	
	public InputFuzzySet(LineralFunction function, LinguisticAttributes linguisticAttribute,
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
