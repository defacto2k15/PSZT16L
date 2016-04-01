package Model.FuzzySets;

import Model.CrispValuesDatabase;
import Model.LineralFunctions.LineralFunction;

public abstract class FuzzySet {
	private LineralFunction function;
	
	protected FuzzySet( LineralFunction function){
		this.function = function;
	}

	public LineralFunction getFunction(){
		return function;
	}


}
