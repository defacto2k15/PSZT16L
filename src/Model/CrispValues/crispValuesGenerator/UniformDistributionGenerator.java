package Model.CrispValues.crispValuesGenerator;

import java.util.Random;

import Model.LinguisticAttributes;
import Model.CrispValues.CrispValue;
import Model.CrispValues.CrispValuesDatabase;
import Model.CrispValues.ICrispValuesProvider;

public class UniformDistributionGenerator implements ICrispValuesProvider {
	Random random = new Random();
	private CrispValue min;
	private CrispValue max;

	public UniformDistributionGenerator(CrispValue min, CrispValue max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public CrispValue getValueForAttribute(LinguisticAttributes attribute) {
		try {
			return new CrispValue( (float) ((random.nextDouble() * ( max.getValue() - min.getValue())) + max.getValue()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



}
