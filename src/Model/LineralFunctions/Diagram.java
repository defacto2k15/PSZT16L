package Model.LineralFunctions;

import java.util.ArrayList;
import java.util.List;

public class Diagram {
	private List<Function> lineralFunctions;
	private List<Function> markedLineralFunctions = new ArrayList<Function>();
	private List<HorizontalLine> horizontalLines = new ArrayList<HorizontalLine>();
	private List<VerticalLine> verticalLines = new ArrayList<VerticalLine>();
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	String diagramTitle;
	String xAxisTitle;
	String yAxisTitle;
	
	
	public Diagram(List<Function> lineralFunctions,	List<Function> markedLineralFunctions, 
				List<HorizontalLine> horizontalLines, List<VerticalLine> verticalLines, int minX, int maxX, int minY, int maxY,
				String diagramTitle, String xAxisTitle, String yAxisTitle) {
		this.lineralFunctions = lineralFunctions;
		this.horizontalLines = horizontalLines;
		this.markedLineralFunctions = markedLineralFunctions;
		this.verticalLines = verticalLines;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.diagramTitle = diagramTitle;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
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

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public String getDiagramTitle() {
		return diagramTitle;
	}

	public String getxAxisTitle() {
		return xAxisTitle;
	}

	public String getyAxisTitle() {
		return yAxisTitle;
	}
	
	
	
	
}
