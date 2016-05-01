package Model.LineralFunctions;

public class Domain {
	private float max;
	private float min;
	
	public Domain(float min, float max) throws Exception {
		super();
		if( max< min){
			throw new Exception("max must be bigger than min in domain");
		}
		this.max = max;
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public float getMin() {
		return min;
	}
	
	public boolean isIn( float x ){
		return x >= getMin() && x <= getMax();
	}
	
	public boolean isPoint(){
		return getMin() == getMax();
	}
	
	
}
