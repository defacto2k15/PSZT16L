package Model;
import Model.crispValuesGenerator.GaussianDistributionGenerator;
import Model.crispValuesGenerator.UniformDistributionGenerator;

public class Model implements IModel{
	private FuzzyRuleBase fuzzyRuleBase;
	private InterferenceEngine interferenceEngine;
	private Fuzzyficator fuzzyficator;
	private Defuzzyficator defuzzyficator;
	
	private GaussianDistributionGenerator gaussianGenerator;
	private UniformDistributionGenerator uniformGenerator;
	
	private CrispValuesDatabase crispValuesDatabase;
	private TipPercentage output;
	private FuzzyInput fuzzyInput;
	private FuzzyOutput fuzzyOutput;
	
	@Override
	public void setCrispInputs(CrispValue crispCharm, CrispValue crispFoodQuality, CrispValue crispServiceQuality) {
		crispValuesDatabase.setValues( crispCharm, crispFoodQuality, crispServiceQuality );
	}


	@Override
	public CrispValue getCrispValue(LiteralAttributes attribute) {
		return crispValuesDatabase.getValueForAttribute( attribute );
	}

	@Override
	public void generateCrispValuesUsingGaussianDistribution(CrispValue expectedValue, float standardDeviation) {
		crispValuesDatabase = gaussianGenerator.generateRandomValues(expectedValue, standardDeviation);
	}

	@Override
	public void generateCrispValuesUsingUniformDistribution(CrispValue min, CrispValue max) {
		crispValuesDatabase = uniformGenerator.generateRandomBetween( min, max);
		
	}

	@Override
	public void setFuzzyficationFunction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefuzzyficationMethod(DefuzzyficationMethod method) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculate() {
		fuzzyInput = fuzzyficator.fuzyficate( crispValuesDatabase );
		fuzzyOutput = interferenceEngine.generateOutput( fuzzyInput, fuzzyRuleBase );
		output = defuzzyficator.defuzzyficate( fuzzyOutput );
	}

	@Override
	public TipPercentage getTipPercent() {
		return output;
	}



}
