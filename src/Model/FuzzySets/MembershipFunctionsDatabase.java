package Model.FuzzySets;

import java.util.HashMap;
import java.util.Map.Entry;

import Model.LineralFunctions.Function;

import Model.LinguisticAttributes;

public class MembershipFunctionsDatabase {
	HashMap<LinguisticAttributes, HashMap< ILinguisticValue, Function>> membershipFunctions = new HashMap<>();	
	
	public MembershipFunctionsDatabase() throws Exception{
		membershipFunctions.put(LinguisticAttributes.Charm, new HashMap<ILinguisticValue, Function>());
		membershipFunctions.put(LinguisticAttributes.FoodQuality, new HashMap<ILinguisticValue, Function>());
		membershipFunctions.put(LinguisticAttributes.ServiceQuality, new HashMap<ILinguisticValue, Function>());
		membershipFunctions.put(LinguisticAttributes.Tip, new HashMap<ILinguisticValue, Function>());
	
		//create charm membershipFunctions
		addEqualTriangualMembershipFunctions(LinguisticAttributes.Charm, CharmLinguisticValue.values());
		addEqualTriangualMembershipFunctions(LinguisticAttributes.FoodQuality, FoodQualityLinguisticValues.values());
		addEqualTriangualMembershipFunctions(LinguisticAttributes.ServiceQuality, ServiceQualityLinguisticValues.values());
		addEqualTriangualMembershipFunctions(LinguisticAttributes.Tip, TipLinguisticValues.values());
	}

	public Function getFunctionFor( LinguisticAttributes attribute, ILinguisticValue value){
		return membershipFunctions.get(attribute).get(value);
	}
	
	private void addEqualTriangualMembershipFunctions(LinguisticAttributes attribute, ILinguisticValue[] values) throws Exception {
		float oneOffset = attribute.getMaxCrispValue() / (values.length + 1);
		float currentFirstPoint = attribute.getCrispMinCrispValue();
		
		membershipFunctions.get(attribute)
			.put(values[0], Function.LeftHalfTrapezoid(currentFirstPoint, oneOffset, attribute.toString()+ values[0].getName()));
		currentFirstPoint += oneOffset;
		for(int i = 1; i < values.length - 1; i++){
			membershipFunctions.get(attribute)
				.put(values[i], Function.Triangle(currentFirstPoint, oneOffset, attribute.toString() + values[i].getName()));
			currentFirstPoint += oneOffset;
		}
		membershipFunctions.get(attribute)
			.put(values[values.length-1], Function.RightHalfTrapezoid(currentFirstPoint, oneOffset, attribute.toString() + values[values.length-1].getName()));
	}
	

	private void scaleFunctions(LinguisticAttributes tip, float scale) {
		HashMap< ILinguisticValue, Function> newMap = new HashMap<>();	
		
		for( Entry<ILinguisticValue, Function> pair : membershipFunctions.get(tip).entrySet()){
			newMap.put(pair.getKey(), pair.getValue().getScaledFunction(scale));
		}
		membershipFunctions.put(tip, newMap);
	}
	
	private void addOffsetToFunctions(LinguisticAttributes tip, int offset) {
		HashMap< ILinguisticValue, Function> newMap = new HashMap<>();	
		
		for( Entry<ILinguisticValue, Function> pair : membershipFunctions.get(tip).entrySet()){
			newMap.put(pair.getKey(), pair.getValue().getoffsetFunction(offset));
		}
		membershipFunctions.put(tip, newMap);
	}
}
