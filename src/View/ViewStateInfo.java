package View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.LinguisticAttributes;

public class ViewStateInfo {
	
	private List<DistributionSettingProperties> distributionSettingProperties;
	int activeDistributionIndex = 0;
	Map< LinguisticAttributes, Integer> linguisticAttributesValues = new HashMap<>();

	public ViewStateInfo(){
		distributionSettingProperties = Arrays.asList(
				DistributionSettingProperties.getGaussDistributionSettings(),
				DistributionSettingProperties.getUniformDistributionSettings()
				);
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
}
