package Model.LineralFunctions;

import java.util.HashSet;

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

	public float getPointOfMax() {
		if( isLine()){
			return getDomain().getMin();
		}
		if( getSlope() > 0){
			return getDomain().getMax();
		} else {
			return getDomain().getMin();
		}		
		
	}

	public float getMin() {
		if( getSlope() > 0){
			return getValueAt(getDomain().getMin());
		} else {
			return getValueAt(getDomain().getMax());
		}
	}

	public boolean isZeroLinePart() {
		return (isPoint() || isLine() ) && (getMax() == 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + Float.floatToIntBits(extent);
		result = prime * result + Float.floatToIntBits(slope);
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
		LinePart other = (LinePart) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (Float.floatToIntBits(extent) != Float.floatToIntBits(other.extent))
			return false;
		if (Float.floatToIntBits(slope) != Float.floatToIntBits(other.slope))
			return false;
		return true;
	}

	public LinePart getScaled(float scale) throws Exception {
		return new LinePart(slope / scale, extent, domain.getScaled(scale));
	}
	
	public LinePart getOffsetPart(float offset )throws Exception{
		return new LinePart(slope, -1*offset*slope + extent, domain.getOffset(offset));
	}
	
}
