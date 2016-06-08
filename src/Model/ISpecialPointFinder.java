package Model;
import Model.LineralFunctions.Function;

public interface ISpecialPointFinder {
	abstract float findSpecialPoint( Function func) throws Exception;
	abstract String getSpecialPointName();
}
