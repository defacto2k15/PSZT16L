package Model.LineralFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.html.Option;

public class FunctionInterferencer {

	public Function generateMinInterference(Function function, float cutoff) throws Exception {
		List<LinePart> outParts = new ArrayList<>();
		
		for( LinePart onePart : function.getLineParts() ){
			if( onePart.getSlope() == 0){
				outParts.add( new LinePart(0, Math.min(cutoff, onePart.getExtent()), onePart.getDomain()));
				continue;
			} 
			
			float meetingPlace = (cutoff - onePart.getExtent())/onePart.getSlope();
			if( onePart.getDomain().isIn(meetingPlace) == false ){
				if( onePart.getValueAt(onePart.getDomain().getMin()) > cutoff ){
					outParts.add(new LinePart(0, cutoff, onePart.getDomain()));
					continue;
				}
				outParts.add(onePart);
				continue;
			} 
			
			if( onePart.getSlope() > 0 ){
				outParts.add( new LinePart( onePart.getSlope(), onePart.getExtent(), 
						new Domain(onePart.getDomain().getMin(), meetingPlace)));
				outParts.add(new LinePart( 0, cutoff, 
						new Domain(meetingPlace, onePart.getDomain().getMax() )));
				continue;
			}
			

			outParts.add(new LinePart( 0, cutoff, 
					new Domain(onePart.getDomain().getMin(), meetingPlace)));
			outParts.add( new LinePart( onePart.getSlope(), onePart.getExtent(), 	
					new Domain(meetingPlace, onePart.getDomain().getMax() )));			
		}
		return new Function(removePointParts(outParts), " Min( "+cutoff+", "+function.getFunctionName()+" )");
	}
	
	public Function generateUnion( Function func1, Function func2 ) throws Exception{
		List<LinePart> parts1 = func1.getLineParts();
		List<LinePart> parts2 = func2.getLineParts();
		List<LinePart> outParts = new ArrayList<>();
		
		parts1.sort( (p1, p2) -> new Float(p1.getDomain().getMin()).compareTo( p2.getDomain().getMin()));
		parts2.sort( (p1, p2) -> new Float(p1.getDomain().getMin()).compareTo( p2.getDomain().getMin()));
		
		int i = 0;
		int j = 0;
		LinePart part1 = parts1.get(i);
		LinePart part2 = parts2.get(j);
		
		Optional<Float> oldIntrestingPoint 
			= Optional.of(Math.min(part1.getDomain().getMin(), part2.getDomain().getMin()));
		

		Situation situationInOldPoint  = getSituationInPoint(part1, part2, oldIntrestingPoint.get());
		Optional<Float> intrestingPoint = getIntrestingPoint(part1, part2, oldIntrestingPoint.get() );
		do{
			Situation situationInNewPoint = getSituationInPoint(part1, part2, intrestingPoint.get());
			LinePart betterPart = null;
			if( situationInNewPoint == Situation.Equal ){
				if( situationInOldPoint == Situation.FirstBigger ){
					betterPart = part1;
				} else if ( situationInOldPoint == Situation.SecondBigger ){
					betterPart = part2;
				} else { // equal
					betterPart = part1;
				}
			} else if( situationInNewPoint == Situation.FirstBigger ){
				betterPart = part1;
			} else if (situationInNewPoint == Situation.SecondBigger){
				betterPart = part2;
			}
			
			outParts.add( betterPart.getSubLine( oldIntrestingPoint.get(), intrestingPoint.get() ));
			
			if(  i < (parts1.size()-1)) {
				if( part1.getDomain().getMax() == intrestingPoint.get()   ){
					part1 = parts1.get(++i);
				}else {
					part1 = part1.getSubLine(intrestingPoint.get() , part1.getDomain().getMax() );
				}
			}
			
			if( j < (parts2.size()-1) ){
				if( part2.getDomain().getMax() == intrestingPoint.get()  ){
					part2 = parts2.get(++j);
				}else {
					part2 = part2.getSubLine(intrestingPoint.get() , part2.getDomain().getMax() );
				}
			}
			
			oldIntrestingPoint = intrestingPoint;
			intrestingPoint = getIntrestingPoint(part1, part2, oldIntrestingPoint.get() );
		} while( intrestingPoint.isPresent() );
			
		return new Function(removePointParts(outParts), func1.getFunctionName() + " U " + func2.getFunctionName());
		
	}
	
	private Optional<Float> getIntrestingPoint ( LinePart part1, LinePart part2, Float lastIntrestingPoint ){
		List<Float> possiblePointsOfIntrest = new ArrayList<Float>();
		possiblePointsOfIntrest.addAll( Arrays.asList( part1.getDomain().getMin(), part1.getDomain().getMax(),
				part2.getDomain().getMin(), part2.getDomain().getMax() ));
		if( !part1.isLine() && !part2.isLine() ){
			float meetingPlace = ( part2.getExtent() - part1.getExtent()) / ( part1.getSlope() - part2.getSlope());
			if( part1.getDomain().isIn(meetingPlace) && part1.getDomain().isIn(meetingPlace)){
				possiblePointsOfIntrest.add(meetingPlace);
			}
		}
		return possiblePointsOfIntrest.stream().filter(f -> f > lastIntrestingPoint).sorted().findFirst();
	}
	
	enum Situation{
		FirstBigger, SecondBigger, Equal
	}
	
	private Situation getSituationInPoint( LinePart part1, LinePart part2, Float point ){
		float part1Value =  part1.getValueAt(point);
		float part2Value = part2.getValueAt(point);
		if(part1Value > part2Value ){
			return Situation.FirstBigger;
		} else if (part1Value <  part2Value  ){
			return Situation.SecondBigger;
		} else {
			return Situation.Equal;
		}
	}
	
	private List<LinePart> removePointParts( List<LinePart> inList ){
		return inList.stream().filter( p -> !p.isPoint() ).collect( Collectors.toList());
	}
}
