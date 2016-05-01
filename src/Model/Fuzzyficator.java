package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.FuzzySets.ILinguisticValue;
import Model.FuzzySets.InputFuzzySet;
import Model.FuzzySets.MembershipFunctionsDatabase;
import Model.FuzzySets.TipFuzzySet;
import Model.FuzzySets.TipLinguisticValues;
import Model.LineralFunctions.Diagram;
import Model.LineralFunctions.Function;

public class Fuzzyficator {
	MembershipFunctionsDatabase membershipFunctions ;
	
	public Fuzzyficator(MembershipFunctionsDatabase membershipFunctions) {
		this.membershipFunctions = membershipFunctions;
	}

	public Diagram getDiagramForAttribute(LinguisticAttributes attribute) {
		List<Function> outFunctions = new ArrayList<>();
		for( ILinguisticValue value : attribute.getLinguisticValues() ){
			outFunctions.add(getFuzzySetFor(attribute, value).getFunction());
		}
		
		return new Diagram( outFunctions);
	}

	public List<InputFuzzySet> getInputFuzzySets() {
		List<InputFuzzySet> outList = new ArrayList<>();
		for( LinguisticAttributes attribute : LinguisticAttributes.values()){
			for( ILinguisticValue value : attribute.getLinguisticValues() ){
				outList.add(getFuzzySetFor(attribute, value));
			}
		}
		return outList;
	}
	
	public InputFuzzySet getFuzzySetFor( LinguisticAttributes attribute, ILinguisticValue value ){
		return new InputFuzzySet(membershipFunctions.getFunctionFor(attribute, value), attribute, value);
	}

	public TipFuzzySet getTipFuzzySetFor(TipLinguisticValues tipLinguisticValue) {
		return new TipFuzzySet(tipLinguisticValue, 
				membershipFunctions.getFunctionFor(LinguisticAttributes.Tip, tipLinguisticValue));
	}

}
