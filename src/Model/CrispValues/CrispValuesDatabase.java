package Model.CrispValues;

import java.util.HashMap;
import java.util.Map;

import Model.LinguisticAttributes;
import Model.CrispValues.crispValuesGenerator.GaussianDistributionGenerator;
import Model.FuzzySets.ILinguisticValue;

public class CrispValuesDatabase {
		ICrispValuesProvider provider;
		Map<LinguisticAttributes, CrispValue> valuesMap = new HashMap<>();

	public void setValuesProvider(ICrispValuesProvider crispValuesProvider) {
		provider = crispValuesProvider;
	}

	public CrispValue getValueFor(LinguisticAttributes linguisticAttribute) {
		if( valuesMap.isEmpty() ){
			regenerateValues();
		}
		return valuesMap.get(linguisticAttribute);//provider.getValueForAttribute(linguisticAttribute);
	}

	public void regenerateValues() {
		valuesMap.clear();
		for( LinguisticAttributes attribute : LinguisticAttributes.values()){
			valuesMap.put(attribute, provider.getValueForAttribute(attribute));
		}
	}

}
