package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Model.LineralFunctions.Domain;
import Model.LineralFunctions.Function;
import Model.LineralFunctions.LinePart;

public class CenterOfMaximumFinder implements ISpecialPointFinder{
	@Override
	public float findSpecialPoint(Function func) throws Exception {
		List<Domain> maxDomains = new ArrayList<>();
		float currentMax = Float.MIN_VALUE;
		float currentPoint = -1;
		List<LinePart> lineParts = func.getLineParts();
		for( LinePart part : lineParts){
			if(  part.getMax() >= currentMax ){
				if( part.getMax() > currentMax){
					maxDomains.clear();
				}
				currentMax = part.getMax();
				if( part.isLine()){
					maxDomains.add(part.getDomain());
				} else if( part.getSlope() > 0 ){
					maxDomains.add(Domain.fromPoint(part.getDomain().getMax()));
				} else {
					maxDomains.add(Domain.fromPoint(part.getDomain().getMin()));
				}
			}
		}
		
		if( maxDomains.size() == 0 ){
			return 5; // TODO ugly hack
		}
		float first = maxDomains.get(0).getMin(); 
		float last = maxDomains.get(maxDomains.size() - 1).getMax();
//		if( firstLine.isPresent() ){
//			return (firstLine.get().getMax() - firstLine.get().getMin() ) / 2;
//		}
		return  ((last + first ) / 2);
		//return (float) (maxDomains.stream().mapToDouble( p -> p.getMin()).sum() / maxDomains.size()); 
	}

	@Override
	public String getSpecialPointName() {
		return "Środek maksimum";
	}

}
