package function.operation.search;

import java.util.Random;

import function.Point;

public class RandomSearch extends SearchMethod {
	//Constructor
	public RandomSearch(int numberPoints) {
		super(numberPoints);
	}

	@Override
	public SearchPoint[] createSearchPoints(Point[] interval) {
		if(points != null)
			return points;
		
		points = new SearchPoint[numberPoints];
		Random random = new Random();
		for(int i=0; i<numberPoints; i++) {
			double[] values = new double[Point.getDimensionality()];
			for(int j=0; j<Point.getDimensionality(); j++) {
				values[j] = random.nextDouble(interval[0].getValue(j), interval[1].getValue(j));
			}
			points[i] = new SearchPoint(values);
		}
		return points;
	}
	
}
