package Model;

public enum DefuzzyficationMethod {
	FirstMaximum(new FirstMaximumFinder()),
	LastMaximum(new LastMaximumFinder()),
	CenterOfMaximum(new CenterOfMaximumFinder()),
	COG( new CenterOfGravityFinder());
	
	private ISpecialPointFinder pointFinder;
	
	private DefuzzyficationMethod(ISpecialPointFinder finder ){
		this.pointFinder = finder;
	}
	
	public ISpecialPointFinder getSpecialPointFinder(){
		return pointFinder;
	}
}
