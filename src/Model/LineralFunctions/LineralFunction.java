package Model.LineralFunctions;

import java.util.List;

public class LineralFunction {
	private List<LinePart> lineParts;
	private String functionName;
	
	public LineralFunction(List<LinePart> lineParts, String functionName) {
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
		// TODO Auto-generated method stub
		return 0;
	}
}
