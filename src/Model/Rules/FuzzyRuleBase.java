package Model.Rules;

import java.util.ArrayList;
import java.util.List;

import Model.Fuzzyficator;
import Model.LinguisticAttributes;
import Model.CrispValues.CrispValuesDatabase;
import Model.FuzzySets.CharmLinguisticValue;
import Model.FuzzySets.FoodQualityFuzzySet;
import Model.FuzzySets.FoodQualityLinguisticValues;
import Model.FuzzySets.ILinguisticValue;
import Model.FuzzySets.InputFuzzySet;
import Model.FuzzySets.MembershipFunctionsDatabase;
import Model.FuzzySets.ServiceQualityFuzzySet;
import Model.FuzzySets.ServiceQualityLinguisticValues;
import Model.FuzzySets.TipFuzzySet;
import Model.FuzzySets.TipLinguisticValues;
import Model.LineralFunctions.FunctionInterferencer;

public class FuzzyRuleBase {
	List<Rule> rules = new ArrayList<>();
	Fuzzyficator fuzzyficator;
	CrispValuesDatabase crispValuesDatabase;
	FunctionInterferencer functionInterferencer;
	
	public FuzzyRuleBase(Fuzzyficator fuzzyficator, CrispValuesDatabase crispValuesDatabase,
			FunctionInterferencer functionInterferencer ) throws Exception{
		this.fuzzyficator = fuzzyficator;
		this.crispValuesDatabase = crispValuesDatabase;
		this.functionInterferencer = functionInterferencer;
		regenerateRules();
	}

	public List<Rule> getAsList() {
		if( rules.isEmpty() ){
			regenerateRules();
		}
		return rules;
	}

	public void regenerateRules() {
		addRule( 
				ruleIf(LinguisticAttributes.FoodQuality).Is(FoodQualityLinguisticValues.Bardzo_slaba).
					And(LinguisticAttributes.ServiceQuality).Is(ServiceQualityLinguisticValues.slaba).
						ThanTipIs(TipLinguisticValues.Bardzo_maly));
		
//		addRule( 
//				ruleIf(LinguisticAttributes.FoodQuality).Is(FoodQualityLinguisticValues.Dobra).
//					And(LinguisticAttributes.ServiceQuality).Is(ServiceQualityLinguisticValues.przecietna).
//						ThanTipIs(TipLinguisticValues.Duzy));
//		addRule( 
//				ruleIf(LinguisticAttributes.FoodQuality).Is(FoodQualityLinguisticValues.Bardzo_slaba).
//					And(LinguisticAttributes.Charm).Is(CharmLinguisticValue.paskudny).
//						ThanTipIs(TipLinguisticValues.Bardzo_maly));
//		addRule( 
//				ruleIf(LinguisticAttributes.FoodQuality).Is(FoodQualityLinguisticValues.Dobra).
//					And(LinguisticAttributes.Charm).Is(CharmLinguisticValue.rewelacyjny).
//						ThanTipIs(TipLinguisticValues.BardzoDuzy));
//		addRule( 
//				ruleIf(LinguisticAttributes.Charm).Is(CharmLinguisticValue.zadowalajacy).
//					And(LinguisticAttributes.FoodQuality).Is(FoodQualityLinguisticValues.Srednia).
//						ThanTipIs(TipLinguisticValues.Sredni));
	}
	
	private void addRule(Rule rule) {
		rules.add(rule);
	}
	
	private TempUnaryRuleFirstPart ruleIf( LinguisticAttributes attribute ){
		return new TempUnaryRuleFirstPart( attribute );
	}
	
	class TempUnaryRuleFirstPart{
		public TempUnaryRule tempRule = null;
		public LinguisticAttributes attribute;
		private Operation op = null;
		
		public TempUnaryRuleFirstPart(LinguisticAttributes attribute) {
			this.attribute = attribute;
		}
		public TempUnaryRuleFirstPart(TempUnaryRule tempRule, LinguisticAttributes attribute, Operation op) {
			this.tempRule = tempRule;
			this.attribute = attribute;
			this.op  = op;
		}
		public TempUnaryRule Is(ILinguisticValue linguisticValue) {
			return new TempUnaryRule( this, linguisticValue);
		}
	}
	
	class TempUnaryRule{
		public TempUnaryRuleFirstPart firstPart;
		public ILinguisticValue linguisticValue;
		

		public TempUnaryRule(TempUnaryRuleFirstPart firstPart, ILinguisticValue linguisticValue) {
			this.firstPart = firstPart;
			this.linguisticValue = linguisticValue;
		}

		public TempUnaryRuleFirstPart And(LinguisticAttributes servicequality) {
			return new TempUnaryRuleFirstPart(this, servicequality, Operation.AND);
		}
		
		public Rule ThanTipIs(TipLinguisticValues tipLinguisticValue) {
			InputFuzzySet firstSet = fuzzyficator.getFuzzySetFor(firstPart.attribute, linguisticValue);
			
			TipFuzzySet tipSet = fuzzyficator.getTipFuzzySetFor( tipLinguisticValue );

			if( firstPart.tempRule == null ){
				return new Rule( firstSet, tipSet, functionInterferencer, crispValuesDatabase );
			} else {
				InputFuzzySet secondSet = fuzzyficator.getFuzzySetFor(
								firstPart.tempRule.firstPart.attribute ,
								firstPart.tempRule.linguisticValue);
				Operation op = firstPart.op;
				return new Rule( firstSet, op, secondSet, tipSet, functionInterferencer, crispValuesDatabase);
			}
		}
	}

}
