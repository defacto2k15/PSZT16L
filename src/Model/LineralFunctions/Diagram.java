package Model.LineralFunctions;

import java.util.ArrayList;
import java.util.List;

public class Diagram {
	private List<Function> lineralFunctions;
	private List<Function> markedLineralFunctions = new ArrayList<Function>();
	private List<HorizontalLine> horizontalLines = new ArrayList<HorizontalLine>();
	private List<VerticalLine> verticalLines = new ArrayList<VerticalLine>();
	
	public Diagram(List<Function> lineralFunctions,	List<Function> markedLineralFunctions, 
				List<HorizontalLine> horizontalLines, List<VerticalLine> verticalLines) {
		this.lineralFunctions = lineralFunctions;
		this.horizontalLines = horizontalLines;
		this.markedLineralFunctions = markedLineralFunctions;
		this.verticalLines = verticalLines;
	}
	
	public Diagram(List<Function> lineralFunctions) {
		this.lineralFunctions = lineralFunctions;
	}

	public List<Function> getLineralFunctions() {
		return lineralFunctions;
	}
	public List<HorizontalLine> getHorizontalLines() {
		return horizontalLines;
	}

	public List<Function> getMarkedLineralFunctions() {
		return markedLineralFunctions;
	}

	public List<VerticalLine> getVerticalLines() {
		return verticalLines;
	}
	
	
	
}
