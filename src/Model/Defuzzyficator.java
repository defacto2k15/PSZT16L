package Model;

import java.util.ArrayList;
import java.util.Arrays;
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
	private Function sumFunction = null;
	private float bestPoint;
	private Optional<Float> centroid = Optional.empty();
	
	public Defuzzyficator(FuzzyRuleBase fuzzyRuleBase, FunctionInterferencer interferencer) {
		this.fuzzyRuleBase = fuzzyRuleBase;
		this.interferencer = interferencer;
	}

	public TipPercentage defuzzyficate() throws Exception {
		List<Function> ruleOutputFunctions = new ArrayList<>();

		for( Rule rule : fuzzyRuleBase.getAsList() ){
			ruleOutputFunctions.add(rule.getOutputSet().getFunction());
		}
		
		sumFunction = ruleOutputFunctions.get(0);
		for( int i = 1; i < ruleOutputFunctions.size(); i++){
			Function rightElem = ruleOutputFunctions.get(i);
			Function res = interferencer.generateUnion(sumFunction, rightElem);
			sumFunction = res;
		}
		
		List<Domain> maxDomains = findMaxDomains();
		if( maxDomains.size() == 0 ){
			int k = 22;
		}
		if( maxDomains.size() == 1 && maxDomains.get(0).isPoint()){
			bestPoint = maxDomains.get(0).getMin();
		} else {
			centroid = findCentroid();
			bestPoint = findMaxPointClosestToCentroid( maxDomains);
		}
		
		float percent = ConstantValues.MIN_TIP_PERCENT +
						( ( ConstantValues.MAX_TIP_PERCENT - ConstantValues.MIN_TIP_PERCENT ) 
								* ( bestPoint / LinguisticAttributes.Tip.getMaxCrispValue()));
		int z=14;
		return new TipPercentage(percent);
	}

	public Diagram getDefuzzyficationDiagram() {
		if( ruleOutputFunctions == null ){
			throw new IllegalStateException("First you have to call deffuzyficate, than get diagram!");
		}
		
		return new Diagram(ruleOutputFunctions,
				Arrays.asList(sumFunction),
				new ArrayList<HorizontalLine>(),
				Arrays.asList(new VerticalLine(bestPoint, "rezultat"), new VerticalLine(centroid.get(), "środek ciężkości")));
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
	

	private Optional<Float> findCentroid() {
		return Optional.of(2.0f); // TODO niezaimplementowane LOL
	}

	private float findMaxPointClosestToCentroid(List<Domain> maxDomains ) {
		// maxDomains.sort((d1, d2) -> new Float( d1.getMin()).compareTo(d2.getMin())); Not needed...
		float currentClosest = Float.MAX_VALUE;
		for( Domain domain : maxDomains){
			float candidatePoint = Float.MAX_VALUE; 
			if( domain.isIn(centroid.get() ) ){
				return centroid.get();
			} else if( domain.getMin() > centroid.get()){
				candidatePoint = domain.getMin();
			} else {
				candidatePoint = domain.getMax();
			}
			if( Math.abs(candidatePoint - centroid.get()) < Math.abs(currentClosest - centroid.get()) ){
				currentClosest = candidatePoint;
			}
		}
		return currentClosest;
	}
}
