package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import Model.CrispValues.CrispValuesDatabase;
import Model.FuzzySets.CharmLinguisticValue;
import Model.FuzzySets.ILinguisticValue;
import Model.FuzzySets.InputFuzzySet;
import Model.FuzzySets.MembershipFunctionsDatabase;
import Model.FuzzySets.TipFuzzySet;
import Model.FuzzySets.TipLinguisticValues;
import Model.LineralFunctions.Diagram;
import Model.LineralFunctions.Function;
import Model.LineralFunctions.FunctionInterferencer;
import Model.LineralFunctions.HorizontalLine;
import Model.LineralFunctions.VerticalLine;

public class Fuzzyficator {
	MembershipFunctionsDatabase membershipFunctions ;
	private CrispValuesDatabase crispValuesDatabase;
	private FunctionInterferencer functionInterferencer;
	
	public Fuzzyficator(MembershipFunctionsDatabase membershipFunctions, CrispValuesDatabase crispValuesDatabase, 
			FunctionInterferencer functionInterferencer) {
		this.membershipFunctions = membershipFunctions;
		this.crispValuesDatabase = crispValuesDatabase;
		this.functionInterferencer = functionInterferencer;
	}

	public Diagram getDiagramForAttribute(LinguisticAttributes attribute) {
		
		List<InputFuzzySet> fuzzySets = new ArrayList<>();
		for( ILinguisticValue value : attribute.getLinguisticValues() ){
			fuzzySets.add(getFuzzySetFor(attribute, value));
		}
		
		List<Function> allOuterFunctions = fuzzySets.stream().map( s -> s.getFunction()).collect(Collectors.toList());
		List<Function> allInnerFunctions = fuzzySets.stream()
				.map( s -> {
					try {
						return functionInterferencer.generateMinInterference(s.getFunction(), s.getDegree(crispValuesDatabase));
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("E456");
						return null;
					}
				}).collect(Collectors.toList());
		
		List<VerticalLine> verticalLines = Arrays.asList( new VerticalLine(crispValuesDatabase.getValueFor(attribute).getValue(), "Wartość "+attribute.toString() ));
		List<HorizontalLine> horizontalLines = new ArrayList<>();
		
		return new Diagram( allOuterFunctions, allInnerFunctions, horizontalLines, verticalLines,
				0, 100, 0, 1, "Fuzyfikacja "+attribute, "jakość w procentach", "przynależność");
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
