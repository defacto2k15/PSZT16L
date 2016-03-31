package Model;

public class TipPercentage {
	private float percent;

	public TipPercentage(float percent) throws Exception {
		super();
		if( percent < ConstantValues.MIN_TIP_PERCENT || percent > ConstantValues.MAX_TIP_PERCENT){
			throw new Exception(" Tip percent must be between "+
						ConstantValues.MIN_TIP_PERCENT+" and "+ ConstantValues.MAX_TIP_PERCENT);
		}
		this.percent = percent;
	}

	public float getPercent() {
		return percent;
	}
	
}
