package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Model.LineralFunctions.Domain;
import Model.LineralFunctions.Function;
import Model.LineralFunctions.LinePart;

public class CenterOfGravityFinder  implements ISpecialPointFinder{
	@Override
	public float findSpecialPoint(Function func) throws Exception {
		List<LinePart> lineParts = func.getLineParts();
		float gravitySum = 0;
		for( LinePart part : lineParts ){
			gravitySum += calculateAreaOf(part);
		}
		
		float halfSum = gravitySum / 2;
		gravitySum = 0;
		for( LinePart part : lineParts ){
			float areaOfPart =  calculateAreaOf(part);
			if( gravitySum + areaOfPart  == halfSum){
				return part.getDomain().getMax();
			} else if( gravitySum + areaOfPart > halfSum){
				float leftSideArea = halfSum - gravitySum;
				float m = part.getDomain().getMin();
				return (float) ( - m + Math.sqrt(m*m + 2*part.getSlope()*leftSideArea))/part.getSlope();
			}
		}
		return lineParts.get( lineParts.size() - 1).getDomain().getMax();
	}

	private float calculateAreaOf(LinePart part) {
		float x = part.getDomain().getMax() - part.getDomain().getMin();
		return ( part.getMin() * x) + ( x * (part.getMax() - part.getMin()))/2;
	}

	@Override
	public String getSpecialPointName() {
		return "Środek ciężkości";
	}

}