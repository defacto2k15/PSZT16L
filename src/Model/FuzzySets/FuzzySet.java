package Model.FuzzySets;

import Model.CrispValues.CrispValuesDatabase;
import Model.LineralFunctions.Function;

public abstract class FuzzySet {
	private Function function;
	
	protected FuzzySet( Function function){
		this.function = function;
	}

	public Function getFunction(){
		return function;
	}


}
