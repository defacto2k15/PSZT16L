package Model.Rules;

import java.util.Optional;

import com.sun.javafx.scene.control.behavior.OptionalBoolean;

import Model.CrispValuesDatabase;
import Model.FuzzySets.FuzzySet;
import Model.FuzzySets.InputFuzzySet;
import Model.FuzzySets.FuzzySet;
import Model.FuzzySets.TipFuzzySet;
import Model.FuzzySets.TipLinguisticValues;
import Model.LineralFunctions.Diagram;
import Model.LineralFunctions.FunctionInterferencer;
import Model.LineralFunctions.LineralFunction;

public class Rule {
	private InputFuzzySet leftSet;
	private Optional<Operation> operation;
	private Optional<InputFuzzySet> rightSet;
	private TipFuzzySet conclusion;
	
	private FunctionInterferencer interferenceEngine;
	private CrispValuesDatabase crispValuesDatabase;
	
	public Rule( InputFuzzySet leftSet, TipFuzzySet conclusion, FunctionInterferencer interferenceEngine ){
		this.leftSet = leftSet;
		this.conclusion = conclusion;
		this.interferenceEngine = interferenceEngine;
		
		operation = java.util.Optional.empty();
		rightSet = java.util.Optional.empty();
		
	}
	
	public Rule( InputFuzzySet leftSet, Operation op, InputFuzzySet rightSet, TipFuzzySet conclusion, FunctionInterferencer interferenceEngine ){
		this.leftSet = leftSet;
		this.interferenceEngine = interferenceEngine;
		this.operation = Optional.of(op);
		this.rightSet = Optional.of(rightSet);
		this.conclusion = conclusion;
	}

	RuleType getRuleType(){
		if( operation.isPresent() ){
			return RuleType.Binary;
		} else {
			return RuleType.Unary;
		}
	}

	public InputFuzzySet getLeftSet() {
		return leftSet;
	}

	public Operation getOperation() {
		return operation.get();
	}

	public InputFuzzySet getRightSet() {
		return rightSet.get();
	}

	public TipFuzzySet getConclusion() {
		return conclusion;
	}

	public TipFuzzySet getOutputSet(){
		float cutoffValue = 0;
		if( operation.isPresent() == false ){ 
			cutoffValue = leftSet.getDegree(crispValuesDatabase);
		} else {
			if( operation.get() == Operation.AND){
				cutoffValue = Math.max(leftSet.getDegree(crispValuesDatabase), rightSet.get().getDegree(crispValuesDatabase));
			} else {
				cutoffValue = Math.min(leftSet.getDegree(crispValuesDatabase), rightSet.get().getDegree(crispValuesDatabase));				
			}
		}
		return new TipFuzzySet(conclusion.getType(), interferenceEngine.generateMinInterference(conclusion.getFunction(), cutoffValue));
	}
	
	public Diagram getDiagram(){
		// TODO
		return null;
	}
}
