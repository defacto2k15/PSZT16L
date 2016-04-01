package Model.LineralFunctions;

import java.util.List;

public class Diagram {
	private List<Function> lineralFunctions;
	private List<Function> markedLineralFunctions;
	private List<HorizontalLines> horizontalLines;
	private List<VerticalLines> verticalLines;
	
	public Diagram(List<Function> lineralFunctions,	List<Function> markedLineralFunctions, 
				List<HorizontalLines> horizontalLines, List<VerticalLines> verticalLines) {
		super();
		this.lineralFunctions = lineralFunctions;
		this.horizontalLines = horizontalLines;
		this.markedLineralFunctions = markedLineralFunctions;
		this.verticalLines = verticalLines;
	}
	
	public List<Function> getLineralFunctions() {
		return lineralFunctions;
	}
	public List<HorizontalLines> getHorizontalLines() {
		return horizontalLines;
	}

	public List<Function> getMarkedLineralFunctions() {
		return markedLineralFunctions;
	}

	public List<VerticalLines> getVerticalLines() {
		return verticalLines;
	}
	
	
	
}
