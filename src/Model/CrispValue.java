package Model;

public class CrispValue {
	private float value;

	public CrispValue(float value) throws Exception {
		super();
		if( value < ConstantValues.MIN_CRISP_VALUE || value > ConstantValues.MAX_CRISP_VALUE){
			throw new Exception("Crisp value must be between 0 and 100");
		}
		this.value = value;
	}

	public float getValue() {
		return value;
	}
	
	@Override
	public boolean equals ( Object other ){
		if( other.getClass() != this.getClass()){
			return false;
		}
		
		return getValue() == ((CrispValue)other).getValue();
	}
	
}
