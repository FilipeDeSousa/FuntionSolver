package function.operation.search;

public abstract class SearchMethod {
	//Enums
	public enum SearchMethodOption {
			Random, UniformIntervals
	}
	
	//Attributes
	int numberPoints;
	double[] points;
	
	//Abstract methods
	public abstract double[] createSearchPoints(double[] interval);

	//Constructor
	public SearchMethod(int numberPoints) {
		this.numberPoints = numberPoints;
	}
}
