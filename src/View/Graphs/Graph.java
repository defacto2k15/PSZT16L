package View.Graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import Model.LineralFunctions.Diagram;
import Model.LineralFunctions.Domain;
import Model.LineralFunctions.Function;
import Model.LineralFunctions.HorizontalLine;
import Model.LineralFunctions.LinePart;
import Model.LineralFunctions.VerticalLine;

public class Graph extends JInternalFrame  {
	
	public Graph(String NOT_USED_1, String NOT_USED_2, String NOT_USED_3, Diagram Data) {
		super(Data.getDiagramTitle());
		String Title = Data.getDiagramTitle();
		String OY = Data.getyAxisTitle();
		String OX = Data.getxAxisTitle();
		
		Dimension Size = new Dimension();
		Size.height = 400;
		Size.width = 600;
		
	    JFreeChart xylineChart = ChartFactory.createXYLineChart(
	    	Title,
	        OX,
	        OY,
	        createDataset(Data),
	        PlotOrientation.VERTICAL,
	        true, true, false);
	         
	    ChartPanel chartPanel = new ChartPanel(xylineChart);
	    chartPanel.setPreferredSize(Size);
	    final XYPlot plot = xylineChart.getXYPlot();
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	    
	    
	    //Add Horizontal and Vertical lines
	    for (int i = 0; i < Data.getHorizontalLines().size(); i++) {
	    	ValueMarker marker = new ValueMarker(Data.getHorizontalLines().get(i).getyPos());
	    	marker.setPaint(Color.black);
	    	plot.addRangeMarker(marker);
	    }
	    
	    for (int i = 0; i < Data.getVerticalLines().size(); i++) {
	    	ValueMarker marker = new ValueMarker(Data.getVerticalLines().get(i).getxPos());
	    	marker.setPaint(Color.black);
	    	plot.addDomainMarker(marker);
	    }
	    
	    int LinearSize = Data.getLineralFunctions().size();
	    int MarkedSize = Data.getMarkedLineralFunctions().size();
	    for (int i = 0; i < MarkedSize; i++) {
	    	renderer.setSeriesStroke(LinearSize + i, new BasicStroke(7.0f));
	    }
	    
	    plot.setRenderer(renderer); 
	    setContentPane(chartPanel); 
	}
	
	private XYDataset createDataset(Diagram Data) {    
	    XYSeriesCollection dataset = new XYSeriesCollection();
	    
	    List<Function> LF = Data.getLineralFunctions();
	    for (int i = 0; i < LF.size(); i++) {
	    	XYSeries LFSerie = new XYSeries(LF.get(i).getFunctionName());
	    	for (int j = 0; j < LF.get(i).getLineParts().size(); j++) {
	    		LinePart LP = LF.get(i).getLineParts().get(j);
	    		float min = LP.getDomain().getMin();
	    		float max = LP.getDomain().getMax();
	    		LFSerie.add(min, LP.getValueAt(min));
	    		LFSerie.add(max, LP.getValueAt(max));
	    	}
	    	dataset.addSeries(LFSerie);
	    }
	    
	    List<Function> MLF = Data.getMarkedLineralFunctions();
	    for (int i = 0; i < MLF.size(); i++) {
	    	XYSeries LFSerie = new XYSeries(MLF.get(i).getFunctionName());
	    	for (int j = 0; j < MLF.get(i).getLineParts().size(); j++) {
	    		LinePart LP = MLF.get(i).getLineParts().get(j);
	    		float min = LP.getDomain().getMin();
	    		float max = LP.getDomain().getMax();
	    		LFSerie.add(min, LP.getValueAt(min));
	    		LFSerie.add(max, LP.getValueAt(max));
	    	}
	    	dataset.addSeries(LFSerie);
	    }
	    
	    return dataset;
	}

	public static Graph getNiceGraph() throws Exception {
			List<Function> lineralFunctions = new ArrayList<Function>();
			List<Function> markedLineralFunctions = new ArrayList<Function>();
			List<HorizontalLine> horizontalLines = new ArrayList<HorizontalLine>();
			List<VerticalLine> verticalLines = new ArrayList<VerticalLine>();
			
			List<LinePart> LP = new ArrayList<LinePart>();
			LP.add(new LinePart(1.f, 3.f, new Domain(0.f, 5.f)));
			LP.add(new LinePart(-1.f, 13.f, new Domain(5.f, 10.f)));
			
			lineralFunctions.add(new Function(LP,"vddfs"));
			
			List<LinePart> MLP = new ArrayList<LinePart>();
			MLP.add(new LinePart(0.5f, 3.f, new Domain(0.f, 5.f)));
			MLP.add(new LinePart(-0.5f, 13.f, new Domain(5.f, 10.f)));
			
			markedLineralFunctions.add(new Function(MLP,"vddfs"));
			
			List<LinePart> MLP1 = new ArrayList<LinePart>();
			MLP1.add(new LinePart(0.45f, 1.f, new Domain(0.f, 5.f)));
			MLP1.add(new LinePart(-0.63f, 3.f, new Domain(5.f, 10.f)));
			
			markedLineralFunctions.add(new Function(MLP1,"vddfs"));
			
			verticalLines.add(new VerticalLine(5, "fsefre"));
			verticalLines.add(new VerticalLine(2, "fsefre"));
			horizontalLines.add(new HorizontalLine(2, "fsefre"));
			horizontalLines.add(new HorizontalLine(5, "fsefre"));
			
			
			Diagram d = new Diagram(lineralFunctions, markedLineralFunctions, horizontalLines, verticalLines, 0, 100, 0, 1,
					"Przykładowy wykres", "Oś X", "Oś Y");
			Graph chart = new Graph("Graph", "OY", "OX", d);
			chart.pack( );          
			chart.setVisible( true ); 
			return chart;
	}
}
