package Model.CrispValues;

import Model.CrispValue;
import Model.LinguisticAttributes;

public interface ICrispValuesProvider {
	CrispValue getValueForAttribute( LinguisticAttributes attribute);
}
