package View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Model.DefuzzyficationMethod;
import Model.LinguisticAttributes;
import Model.Model;
import Model.CrispValues.CrispValuesDatabase;
import Model.FuzzySets.InputFuzzySet;
import Model.LineralFunctions.Diagram;
import Model.Rules.Rule;

public class ViewStateInfo {
	
	private List<DistributionSettingProperties> distributionSettingProperties;
	int activeDistributionIndex = 0;
	Map< LinguisticAttributes, Integer> linguisticAttributesValues = new HashMap<>();
	private FuzzySetsValuesTableModel fuzzySetsValuesTableModel;
	private RulesTableModel rulesTableModel;
	private DefuzzyficationMethod currentDefuzzyficationMethod = DefuzzyficationMethod.FirstMaximum;
	private int currentTip = 0;
	private Diagram currentDiagram;

	public ViewStateInfo( Model model){
		distributionSettingProperties = Arrays.asList(
				DistributionSettingProperties.getGaussDistributionSettings(),
				DistributionSettingProperties.getUniformDistributionSettings()
				);
		fuzzySetsValuesTableModel = new FuzzySetsValuesTableModel(model.getCrispValuesDatabase());
		rulesTableModel = new RulesTableModel(model.getRules(), model.getCrispValuesDatabase());
	}
	
	public List<DistributionSettingProperties> getDistributionSettingProperties(){
		return distributionSettingProperties;
	}
	
	public DistributionSettingProperties getActiveDistribution( ){
		return distributionSettingProperties.get(activeDistributionIndex);
	}
	
	public int getActiveDistributionIndex(){
		return activeDistributionIndex;
	}

	public void setActiveDistribution(int selectedIndex) {
		activeDistributionIndex = selectedIndex;
	}
	
	
	public int getLinguisticAttributeCrispValue( LinguisticAttributes attr){
		if( linguisticAttributesValues.containsKey(attr) == false){
			linguisticAttributesValues.put( attr, new Integer(0));
		}
		return linguisticAttributesValues.get(attr);
	}
	
	public void setLinguisticAttributeCrispValue( LinguisticAttributes attr, int newValue){
		if( linguisticAttributesValues.containsKey(attr) == false){
			linguisticAttributesValues.put( attr, new Integer(0));
		} else {
			linguisticAttributesValues.put( attr, newValue);
		}
	}
	
	public FuzzySetsValuesTableModel getFuzzySetsValuesTableModel(){
		return fuzzySetsValuesTableModel;
	}
	
	public void setInputFuzzySets( List<InputFuzzySet> sets ){
		List<InputFuzzySet> newSets = sets.stream()
				.filter( p -> p.getLinguisticAttribute() != LinguisticAttributes.Tip)
				.collect(Collectors.toList());
		fuzzySetsValuesTableModel.setFuzzySets(newSets);
	}
	
	public RulesTableModel getRulesTableModel(){
		return rulesTableModel;
	}

	public void setCurrentDefuzzyficationMethod(DefuzzyficationMethod method) {
		currentDefuzzyficationMethod = method;
	}
	
	public DefuzzyficationMethod getCurrentDefuzzyficationMethod(){
		return currentDefuzzyficationMethod;
	}
	
	public int getCurrentTip(){
		return currentTip;
	}
	
	public void setCurrentTip( int tip ){
		currentTip = tip;
	}
	
	public void setCurrentDiagram( Diagram currentDiagram ){
		this.currentDiagram = currentDiagram;
	}
	
	public Diagram getCurrentDiagram( ){
		return currentDiagram;
	}
}
