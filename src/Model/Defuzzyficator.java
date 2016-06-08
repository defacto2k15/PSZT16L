package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Model.LineralFunctions.Diagram;
import Model.LineralFunctions.Domain;
import Model.LineralFunctions.Function;
import Model.LineralFunctions.FunctionInterferencer;
import Model.LineralFunctions.HorizontalLine;
import Model.LineralFunctions.LinePart;
import Model.LineralFunctions.VerticalLine;
import Model.Rules.FuzzyRuleBase;
import Model.Rules.Rule;

public class Defuzzyficator {

	private FuzzyRuleBase fuzzyRuleBase;
	private FunctionInterferencer interferencer;
	List<Function> ruleOutputFunctions = null;
	
	DefuzzyficationMethod defuzzyficationMethod = DefuzzyficationMethod.FirstMaximum;
	
	private Function sumFunction = null;
	private float bestPoint;
	private float specialPoint = 0;
	
	public Defuzzyficator(FuzzyRuleBase fuzzyRuleBase, FunctionInterferencer interferencer) {
		this.fuzzyRuleBase = fuzzyRuleBase;
		this.interferencer = interferencer;
	}

	public TipPercentage defuzzyficate() throws Exception {
		ruleOutputFunctions = new ArrayList<>();

		for( Rule rule : fuzzyRuleBase.getAsList() ){
			ruleOutputFunctions.add(rule.getOutputSet().getFunction());
		}
		ruleOutputFunctions = 
				new ArrayList<>(
					new HashSet<>(
						ruleOutputFunctions.stream()
						.filter( f -> !f.isZeroFunction()).distinct()
						.collect(Collectors.toList())));
		
		if( ruleOutputFunctions.size() == 0){
			sumFunction = new Function(new ArrayList<>(), "Zero result");
			return new TipPercentage(5);
		}
		sumFunction = ruleOutputFunctions.get(0);
		for( int i = 1; i < ruleOutputFunctions.size(); i++){
			Function rightElem = ruleOutputFunctions.get(i);
			Function res = interferencer.generateUnion(sumFunction, rightElem);
			sumFunction = res;
		}
		sumFunction = sumFunction.removeZeroLineParts();
		
		List<Domain> maxDomains = findMaxDomains();
		
		if( maxDomains.size() == 0 ){
			int k = 22;
		}
		if( maxDomains.size() == 1 && maxDomains.get(0).isPoint()){
			bestPoint = maxDomains.get(0).getMin();
		} else {
			specialPoint = defuzzyficationMethod.getSpecialPointFinder().findSpecialPoint(sumFunction);
			bestPoint = findMaxPointClosestToCentroid( maxDomains, specialPoint);
		}
		
		return new TipPercentage(bestPoint);
	}

	public Diagram getDefuzzyficationDiagram() {
		if( ruleOutputFunctions == null ){
			throw new IllegalStateException("First you have to call deffuzyficate, than get diagram!");
		}
		
		List<VerticalLine> verticalLinesArray = new ArrayList<>();
		verticalLinesArray.add(new VerticalLine(bestPoint, "rezultat"));
		verticalLinesArray.add(new VerticalLine(specialPoint,
				defuzzyficationMethod.getSpecialPointFinder().getSpecialPointName()));
		
		return new Diagram(
				ruleOutputFunctions,
				Arrays.asList(sumFunction),
				new ArrayList<HorizontalLine>(),
				verticalLinesArray,
				0, 25, 0, 1, "Defuzyfikacja", "Procent napiwka", "Przynależność");
	}
	
	public void setDefuzzyficationMethod( DefuzzyficationMethod method ){
		this.defuzzyficationMethod = method;
	}
	
	private List<Domain> findMaxDomains() throws Exception{
		List<Domain> outList = new ArrayList<>();
		float currentMax = -Float.MAX_VALUE;
		for( LinePart part : sumFunction.getLineParts() ){
			if( part.getMax() >= currentMax ){
				if( part.getMax() > currentMax){
					outList.clear();
					currentMax = part.getMax();
				}
				
				if( part.isLine() ){
					outList.add(part.getDomain());
				} else {
					float bestPointInPart = 0;
					if( part.getSlope() > 0){
						bestPointInPart = part.getDomain().getMax();
					} else {
						bestPointInPart = part.getDomain().getMin();
					}
					outList.add( new Domain( bestPointInPart, bestPointInPart));
				}
			} 
		}
		return outList;
	}
	
	private float findMaxPointClosestToCentroid(List<Domain> maxDomains, float specialPoint ) {
		// maxDomains.sort((d1, d2) -> new Float( d1.getMin()).compareTo(d2.getMin())); Not needed...
		float currentClosest = Float.MAX_VALUE;
		for( Domain domain : maxDomains){
			float candidatePoint = Float.MAX_VALUE; 
			if( domain.isIn(specialPoint ) ){
				return specialPoint;
			} else if( domain.getMin() > specialPoint){
				candidatePoint = domain.getMin();
			} else {
				candidatePoint = domain.getMax();
			}
			if( Math.abs(candidatePoint - specialPoint) < Math.abs(currentClosest - specialPoint) ){
				currentClosest = candidatePoint;
			}
		}
		return currentClosest;
	}
}
