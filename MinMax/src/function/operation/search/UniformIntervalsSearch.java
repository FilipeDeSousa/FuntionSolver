package function.operation.search;

import function.Point;

public class UniformIntervalsSearch extends SearchMethod {
	//Constructor
	public UniformIntervalsSearch(int numberPoints) {
		super(numberPoints);
	}

	@Override
	public SearchPoint[] createSearchPoints(Point[] interval) {
		if(points != null)
			return points;
		double[] steps = new double[Point.getDimensionality()];
		for(int i=0; i<Point.getDimensionality(); i++)
			steps[i]=(interval[1].getValue(i)-interval[0].getValue(i))/(numberPoints+1.0);
		points = new SearchPoint[numberPoints];
		for(int i=0; i<numberPoints; i++) {
			double[] values = new double[Point.getDimensionality()];
			for(int j=0; j<Point.getDimensionality(); j++)
				values[j] = interval[0].getValue(j)+steps[j]*i;
			points[i] = new SearchPoint(values);
		}
		return points;
	}

}
