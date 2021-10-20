package function.operation.search;

public abstract class SearchMethod {
	//Enums
	public enum SearchMethodOption {
			Random, UniformIntervals
	}
	
	//Attributes
	int numberPoints;
	SearchPoint[] points;
	
	//Abstract methods
	public abstract SearchPoint[] createSearchPoints(double[] interval);

	//Constructor
	public SearchMethod(int numberPoints) {
		this.numberPoints = numberPoints;
	}
}
