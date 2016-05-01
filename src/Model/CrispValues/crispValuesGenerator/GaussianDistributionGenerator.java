package Model.CrispValues.crispValuesGenerator;

import java.util.Random;

import Model.LinguisticAttributes;
import Model.CrispValues.CrispValue;
import Model.CrispValues.CrispValuesDatabase;
import Model.CrispValues.ICrispValuesProvider;

public class GaussianDistributionGenerator implements ICrispValuesProvider {
	private Random random = new Random();
	private CrispValue expectedValue;
	private float standardDeviation;

	public GaussianDistributionGenerator(CrispValue expectedValue, float standardDeviation) {
		this.expectedValue = expectedValue;
		this.standardDeviation = standardDeviation;
	}

	@Override
	public CrispValue getValueForAttribute(LinguisticAttributes attribute) {
		try {
			return new CrispValue ( 
					(float) Math.max(0.0f, 
							Math.min(100.0f,
									(random.nextGaussian() * standardDeviation + expectedValue.getValue()))));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



}
