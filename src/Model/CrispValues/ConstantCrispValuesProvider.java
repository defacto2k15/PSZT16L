package Model.CrispValues;

import java.util.HashMap;

import Model.LinguisticAttributes;

public class ConstantCrispValuesProvider implements ICrispValuesProvider {
	private HashMap<LinguisticAttributes, CrispValue > map = new HashMap<>();


	public ConstantCrispValuesProvider(CrispValue crispCharm, CrispValue crispFoodQuality,
			CrispValue crispServiceQuality) {
		map.put(LinguisticAttributes.Charm, crispCharm);
		map.put(LinguisticAttributes.FoodQuality, crispFoodQuality);
		map.put(LinguisticAttributes.ServiceQuality, crispServiceQuality);
	}


	@Override
	public CrispValue getValueForAttribute(LinguisticAttributes attribute) {
		return map.get(attribute);
	}

}
