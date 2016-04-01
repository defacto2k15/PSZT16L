package Model.LineralFunctions;

import java.util.List;

public class Diagram {
	private List<LineralFunction> lineralFunctions;
	private List<LineralFunction> markedLineralFunctions;
	private List<HorizontalLines> horizontalLines;
	private List<VerticalLines> verticalLines;
	
	public Diagram(List<LineralFunction> lineralFunctions,	List<LineralFunction> markedLineralFunctions, 
				List<HorizontalLines> horizontalLines, List<VerticalLines> verticalLines) {
		super();
		this.lineralFunctions = lineralFunctions;
		this.horizontalLines = horizontalLines;
		this.markedLineralFunctions = markedLineralFunctions;
		this.verticalLines = verticalLines;
	}
	
	public List<LineralFunction> getLineralFunctions() {
		return lineralFunctions;
	}
	public List<HorizontalLines> getHorizontalLines() {
		return horizontalLines;
	}

	public List<LineralFunction> getMarkedLineralFunctions() {
		return markedLineralFunctions;
	}

	public List<VerticalLines> getVerticalLines() {
		return verticalLines;
	}
	
	
	
}
