package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.LineralFunctions.Domain;
import Model.LineralFunctions.Function;
import Model.LineralFunctions.LinePart;

public class FirstMaximumFinder implements ISpecialPointFinder{

	@Override
	public float findSpecialPoint(Function func) throws Exception {
		float currentMax = Float.MIN_VALUE;
		float currentPoint = -1;
		List<LinePart> lineParts = func.getLineParts();
		for( LinePart part : lineParts){
			if( part.getMax() > currentMax ){
				currentMax = part.getMax();
				if( part.isLine()  || part.getSlope() < 0){
					currentPoint = part.getDomain().getMin();
				} else {
					currentPoint = part.getDomain().getMax();
				}
			}
		}
		return currentPoint;
	}

	@Override
	public String getSpecialPointName() {
		return "Pierwsze maksimum";
	}

}
