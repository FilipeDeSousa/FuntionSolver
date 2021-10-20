package function.operation.search;

public class UniformIntervalsSearch extends SearchMethod {
	//Constructor
	public UniformIntervalsSearch(int numberPoints) {
		super(numberPoints);
	}

	@Override
	public double[] createSearchPoints(double[] interval) {
		if(points != null)
			return points;
		
		double step=(interval[1]-interval[0])/(numberPoints+1.0);
		points = new double[numberPoints];
		for(int i=0; i<numberPoints; i++) {
			points[i] = interval[0]+step;
		}
		return points;
	}

}
