package Model;

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
	
}
