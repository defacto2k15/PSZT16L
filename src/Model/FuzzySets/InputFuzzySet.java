package Model.FuzzySets;

import Model.LinguisticAttributes;
import Model.CrispValues.CrispValue;
import Model.CrispValues.CrispValuesDatabase;
import Model.LineralFunctions.Function;

public class InputFuzzySet extends FuzzySet{
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
		Function getFunction = getFunction();
		CrispValue vv = crispValuesDatabase.getValueFor(linguisticAttribute);
		try{
		vv.getValue();
		} catch( Exception e){
			return 44;
		}
		return getFunction().getValueAt( crispValuesDatabase.getValueFor(linguisticAttribute).getValue());
	}
	
}
