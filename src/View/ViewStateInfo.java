package View;

import java.util.Arrays;
import java.util.List;

public class ViewStateInfo {
	
	private List<DistributionSettingProperties> distributionSettingProperties;
	int activeDistributionIndex = 0;

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
}
