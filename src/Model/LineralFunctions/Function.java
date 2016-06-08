package Model.LineralFunctions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Function {
	private List<LinePart> lineParts;
	private String functionName;
	
	public Function(List<LinePart> lineParts, String functionName) {
		super();
		this.lineParts = lineParts;
		this.functionName = functionName;
	}
	
	public List<LinePart> getLineParts() {
		return lineParts;
	}
	public String getFunctionName() {
		return functionName;
	}

	public float getValueAt(float x) {
		for( LinePart part : lineParts){
			if( part.getDomain().isIn(x)){
				return part.getValueAt(x);
			}
		}
		return 0;
	}

	public static Function LeftHalfTrapezoid(float currentFirstPoint, float oneOffset, String functionName) throws Exception {
		List<LinePart> lineParts = new ArrayList<>();
		lineParts.add(new LinePart(0, 1, new Domain(currentFirstPoint, currentFirstPoint + oneOffset)));
		lineParts.add(new LinePart( 1/(-oneOffset), (currentFirstPoint + 2*oneOffset)/(oneOffset), 
				new Domain(currentFirstPoint+oneOffset, currentFirstPoint + 2*oneOffset)));
		return new Function(lineParts, functionName);
	}

	public static Function Triangle(float currentFirstPoint, float oneOffset, String functionName) throws Exception {
		List<LinePart> lineParts = new ArrayList<>();
		float c1 = currentFirstPoint;
		float c2 = c1 + oneOffset;
		float c3 = c2 + oneOffset;
		
		lineParts.add(new LinePart(1/(c2-c1), -c1/(c2-c1), new Domain(c1, c2)));
		lineParts.add(new LinePart(1/(c2-c3), -c3/(c2-c3), new Domain(c2, c3)));
		return new Function(lineParts, functionName);
	}

	public static Function RightHalfTrapezoid(float currentFirstPoint, float oneOffset, String functionName) throws Exception {
		List<LinePart> lineParts = new ArrayList<>();
		float c1 = currentFirstPoint;
		float c2 = c1 + oneOffset;
		float c3 = c2 + oneOffset;
		lineParts.add(new LinePart(1/(c2-c1), -c1/(c2-c1), new Domain(c1, c2)));
		lineParts.add(new LinePart(0, 1, new Domain(c2, c3)));
		return new Function(lineParts, functionName);
	}

	public static Function Line(float extent, Domain domain, String name) {
		List<LinePart> lineParts = new ArrayList<>();
		lineParts.add(new LinePart(0.0f, extent, domain));
		return new Function( lineParts,  name );
	}

	public boolean isZeroFunction() {
		return lineParts.stream().allMatch( p -> p.isZeroLinePart());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((functionName == null) ? 0 : functionName.hashCode());
		result = prime * result + ((lineParts == null) ? 0 : lineParts.hashCode());
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
		Function other = (Function) obj;
		if (functionName == null) {
			if (other.functionName != null)
				return false;
		} else if (!functionName.equals(other.functionName))
			return false;
		if (lineParts == null) {
			if (other.lineParts != null)
				return false;
		} else if (!lineParts.equals(other.lineParts))
			return false;
		return true;
	}

	public Function getScaledFunction(float scale) {
		List<LinePart> scaledParts = lineParts.stream().map( p -> {
			try {
				return p.getScaled(scale);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}).collect(Collectors.toList());
		return new Function(scaledParts, functionName);
	}
	
	public Function getoffsetFunction(float offset) {
		List<LinePart> offsetParts = lineParts.stream().map( p -> {
			try {
				return p.getOffsetPart(offset);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}).collect(Collectors.toList());
		
		return new Function(offsetParts, functionName);
	}
}
