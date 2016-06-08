package Model;
import java.util.List;

import Model.CrispValues.ConstantCrispValuesProvider;
import Model.CrispValues.CrispValue;
import Model.CrispValues.CrispValuesDatabase;
import Model.CrispValues.crispValuesGenerator.GaussianDistributionGenerator;
import Model.CrispValues.crispValuesGenerator.UniformDistributionGenerator;
import Model.FuzzySets.InputFuzzySet;
import Model.FuzzySets.MembershipFunctionsDatabase;
import Model.LineralFunctions.Diagram;
import Model.LineralFunctions.FunctionInterferencer;
import Model.Rules.FuzzyRuleBase;
import Model.Rules.Rule;

public class Model implements IModel{
	private FunctionInterferencer interferencer = new FunctionInterferencer();
	private MembershipFunctionsDatabase membershipFunctionsDatabase = new MembershipFunctionsDatabase();
	private CrispValuesDatabase crispValuesDatabase = new CrispValuesDatabase();
	private Fuzzyficator fuzzyficator = new Fuzzyficator(membershipFunctionsDatabase, crispValuesDatabase, interferencer);
	private FuzzyRuleBase fuzzyRuleBase = new FuzzyRuleBase(fuzzyficator, crispValuesDatabase, interferencer);
	private Defuzzyficator defuzzyficator = new Defuzzyficator( fuzzyRuleBase, interferencer );
	
	public Model() throws Exception{
	}
	
	@Override
	public void setCrispInputs(CrispValue crispCharm, CrispValue crispFoodQuality, CrispValue crispServiceQuality) {
		crispValuesDatabase.setValuesProvider( new ConstantCrispValuesProvider(crispCharm, crispFoodQuality, crispServiceQuality));
	}


	@Override
	public void generateCrispValuesUsingGaussianDistribution(CrispValue expectedValue, float standardDeviation) {
		crispValuesDatabase.setValuesProvider( new GaussianDistributionGenerator(expectedValue, standardDeviation));
	}

	@Override
	public void generateCrispValuesUsingUniformDistribution(CrispValue min, CrispValue max) {
		crispValuesDatabase.setValuesProvider( new UniformDistributionGenerator(min, max));
	}


	@Override
	public void setDefuzzyficationMethod(DefuzzyficationMethod method) {
		defuzzyficator.setDefuzzyficationMethod(method);
	}

	@Override
	public TipPercentage getTipPercent() throws Exception {
		return defuzzyficator.defuzzyficate();
	}


	@Override
	public Diagram getFuzzyficationDiagramForAttribute(LinguisticAttributes attribute) {
		return fuzzyficator.getDiagramForAttribute( attribute );
	}


	@Override
	public List<InputFuzzySet> getInputFuzzySets() {
		return fuzzyficator.getInputFuzzySets();
	}


	@Override
	public List<Rule> getRules() {
		System.out.println("M43 FRB size "+fuzzyRuleBase.getAsList().size());
		return fuzzyRuleBase.getAsList();
	}

	@Override
	public Diagram getDefuzyficationDiagram() {
		return defuzzyficator.getDefuzzyficationDiagram();
	}

	@Override
	public void regenerate() {
		crispValuesDatabase.regenerateValues();
		fuzzyRuleBase.regenerateRules();
	}

	@Override
	public CrispValue getCharmCrispValue() {
		return crispValuesDatabase.getValueFor(LinguisticAttributes.Charm);
	}


	@Override
	public CrispValue getFoodQualityCrispValue() {
		return crispValuesDatabase.getValueFor(LinguisticAttributes.FoodQuality);
	}


	@Override
	public CrispValue getServiceQualityCrispValue() {
		return crispValuesDatabase.getValueFor(LinguisticAttributes.ServiceQuality);
	}

	@Override
	public CrispValuesDatabase getCrispValuesDatabase() {
		return crispValuesDatabase;
	}

}
