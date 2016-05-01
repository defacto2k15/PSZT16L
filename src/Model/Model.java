package Model;
import java.util.List;

import Model.CrispValues.ConstantCrispValuesProvider;
import Model.CrispValues.crispValuesGenerator.GaussianDistributionGenerator;
import Model.CrispValues.crispValuesGenerator.UniformDistributionGenerator;
import Model.FuzzySets.InputFuzzySet;
import Model.LineralFunctions.Diagram;
import Model.Rules.Rule;

public class Model implements IModel{
	private FuzzyRuleBase fuzzyRuleBase = new FuzzyRuleBase();
	private Fuzzyficator fuzzyficator = new Fuzzyficator();
	private Defuzzyficator defuzzyficator = new Defuzzyficator();
	
	private CrispValuesDatabase crispValuesDatabase = new CrispValuesDatabase();

	
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
		// TODO
	}

	@Override
	public TipPercentage getTipPercent() {
		return defuzzyficator.defuzzyficate();
	}


	@Override
	public Diagram getFuzzyficationDiagramForAttribute(LinguisticAttributes attribute) {
		return fuzzyficator.getDiagramForAttribute( attribute );
	}


	@Override
	public List<InputFuzzySet> getInputFuzzySets() {
		return fuzzyficator.getFuzzySets();
	}


	@Override
	public List<Rule> getRules() {
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
		//return crispValuesDatabase.getValueFor(LinguisticAttributes.Charm, linguisticValue)
		return null;
	}


	@Override
	public CrispValue getFoodQualityCrispValue() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public CrispValue getServiceQualityCrispValue() {
		// TODO Auto-generated method stub
		return null;
	}



}
