package Model.LineralFunctions;

import java.util.ArrayList;
import java.util.List;

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
	
	
}
