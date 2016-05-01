package Model.LineralFunctions;

// innymi słowy odcinek
public class LinePart {
	private float slope; // a
	private float extent; // b 
	// we wzorze y = ax+b
	private Domain domain; // dziedzina
	
	public LinePart(float slope, float extent, Domain domain) {
		super();
		this.slope = slope;
		this.extent = extent;
		this.domain = domain;
	}
	
	public float getSlope() {
		return slope;
	}
	public float getExtent() {
		return extent;
	}
	public Domain getDomain() {
		return domain;
	}

	public float getValueAt(float x) {
		return getSlope() * x + getExtent();
	}

	public boolean isLine() {
		return getSlope() == 0;
	}
	
	public boolean isPoint(){
		return domain.isPoint();
	}

	public LinePart getSubLine(Float min, Float max) throws Exception {
		return new LinePart( slope, extent, new Domain(min, max));
	}

	public float getMax() {
		if( getSlope() > 0){
			return getValueAt(getDomain().getMax());
		} else {
			return getValueAt(getDomain().getMin());
		}
	}
	
}
