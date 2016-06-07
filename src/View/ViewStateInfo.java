package View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.LinguisticAttributes;
import Model.CrispValues.CrispValuesDatabase;
import Model.FuzzySets.InputFuzzySet;

public class ViewStateInfo {
	
	private List<DistributionSettingProperties> distributionSettingProperties;
	int activeDistributionIndex = 0;
	Map< LinguisticAttributes, Integer> linguisticAttributesValues = new HashMap<>();
	private FuzzySetsValuesTableModel fuzzySetsValuesTableModel;

	public ViewStateInfo(CrispValuesDatabase crispValuesDatabase){
		distributionSettingProperties = Arrays.asList(
				DistributionSettingProperties.getGaussDistributionSettings(),
				DistributionSettingProperties.getUniformDistributionSettings()
				);
		fuzzySetsValuesTableModel = new FuzzySetsValuesTableModel(crispValuesDatabase);
	}
	
	public List<DistributionSettingProperties> getDistributionSettingProperties(){
		return distributionSettingProperties;
	}
	
	public DistributionSettingProperties getActiveDistribution( ){
		return distributionSettingProperties.get(activeDistributionIndex);
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
		fuzzySetsValuesTableModel.setFuzzySets(sets);
	}
}
