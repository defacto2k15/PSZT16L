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

	public static Domain fromPoint(float max) throws Exception {
		return new Domain(max, max);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(max);
		result = prime * result + Float.floatToIntBits(min);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Domain other = (Domain) obj;
		if (Float.floatToIntBits(max) != Float.floatToIntBits(other.max))
			return false;
		if (Float.floatToIntBits(min) != Float.floatToIntBits(other.min))
			return false;
		return true;
	}

	public Domain getScaled(float scale) throws Exception {
		return new Domain(min*scale, max*scale);
	}

	public Domain getOffset(float offset) throws Exception {
		return new Domain( min+offset, max+offset);
	}
	
//	@Override
//	public boolean equals(Object other){
//	    if (other == null) return false;
//	    if (other == this) return true;
//	    if (!(other instanceof LinePart))return false;
//	    Domain otherDomain = (Domain)other;
//	    return max == otherDomain.getMax() &&
//	    		min == otherDomain.getMin();
//	}
	
	
	
}
