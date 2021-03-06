package Model.Rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.sun.javafx.scene.control.behavior.OptionalBoolean;

import Model.CrispValues.CrispValuesDatabase;
import Model.FuzzySets.FuzzySet;
import Model.FuzzySets.InputFuzzySet;
import Model.FuzzySets.FuzzySet;
import Model.FuzzySets.TipFuzzySet;
import Model.FuzzySets.TipLinguisticValues;
import Model.LineralFunctions.Diagram;
import Model.LineralFunctions.FunctionInterferencer;
import Model.LineralFunctions.HorizontalLine;
import Model.LineralFunctions.VerticalLine;
import Model.LineralFunctions.Function;

public class Rule {
	private InputFuzzySet leftSet;
	private Optional<Operation> operation;
	private Optional<InputFuzzySet> rightSet;
	private TipFuzzySet conclusion;
	private Float cutoffValue;
	private String cutoffDescription;
	private Function outputSetFunction;
	
	private FunctionInterferencer interferenceEngine;
	private CrispValuesDatabase crispValuesDatabase;
	
	public Rule( InputFuzzySet leftSet, TipFuzzySet conclusion, FunctionInterferencer interferenceEngine, 
			CrispValuesDatabase crispValuesDatabase ){
		this.leftSet = leftSet;
		this.conclusion = conclusion;
		this.interferenceEngine = interferenceEngine;
		this.crispValuesDatabase = crispValuesDatabase;
		
		operation = java.util.Optional.empty();
		rightSet = java.util.Optional.empty();
		
	}
	
	public Rule( InputFuzzySet leftSet, Operation op, InputFuzzySet rightSet, TipFuzzySet conclusion,
			FunctionInterferencer interferenceEngine, CrispValuesDatabase crispValuesDatabase ){
		this.leftSet = leftSet;
		this.interferenceEngine = interferenceEngine;
		this.operation = Optional.of(op);
		this.rightSet = Optional.of(rightSet);
		this.conclusion = conclusion;
		this.crispValuesDatabase = crispValuesDatabase;
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

	public TipFuzzySet getOutputSet() throws Exception{
		if( operation.isPresent() == false ){ 
			cutoffValue = leftSet.getDegree(crispValuesDatabase);
			cutoffDescription = cutoffValue.toString();
		} else {
			String op = null;
			if( operation.get() == Operation.AND){
				cutoffValue = Math.min(leftSet.getDegree(crispValuesDatabase), rightSet.get().getDegree(crispValuesDatabase));
				op = "Min";
			} else {
				cutoffValue = Math.max(leftSet.getDegree(crispValuesDatabase), rightSet.get().getDegree(crispValuesDatabase));				
				op = "Max";
			}
			cutoffDescription = cutoffValue.toString() +" = "+op+"( " + leftSet.getDegree(crispValuesDatabase) + " , " +
					rightSet.get().getDegree(crispValuesDatabase) + " )";
		}
		outputSetFunction = interferenceEngine.generateMinInterference(conclusion.getFunction(), cutoffValue);
		return new TipFuzzySet(conclusion.getType(), outputSetFunction);
	}
	
	public Diagram getDiagram(){
			try {
				getOutputSet();
			} catch (Exception e) {
				System.err.println("E89");
				e.printStackTrace();
			}
		List<HorizontalLine> horizontalLines  = new ArrayList<>();
		float val1 = leftSet.getDegree(crispValuesDatabase);
		horizontalLines.add( new HorizontalLine(val1, leftSet.toString()+" ( "+val1+" )"));
		float val2 = rightSet.get().getDegree(crispValuesDatabase);
		horizontalLines.add( new HorizontalLine(val2, rightSet.toString()+" ( "+val2+" )"));
		horizontalLines.add(new HorizontalLine(cutoffValue, cutoffDescription));
		return new Diagram(
				Arrays.asList(conclusion.getFunction()),
				Arrays.asList(outputSetFunction),
				horizontalLines,
				new ArrayList<>(),
				0, 25, 0 ,1, "Diagram reguły", "Procent napiwku", "Przynależność");
	}
}
