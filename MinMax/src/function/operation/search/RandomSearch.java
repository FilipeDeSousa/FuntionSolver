package function.operation.search;

import java.util.Random;

public class RandomSearch extends SearchMethod {
	//Constructor
	public RandomSearch(int numberPoints) {
		super(numberPoints);
	}

	@Override
	public double[] createSearchPoints(double[] interval) {
		if(points != null)
			return points;
		
		points = new double[numberPoints];
		Random random = new Random();
		for(int i=0; i<numberPoints; i++) {
			points[i] = random.nextDouble(interval[0], interval[1]);
		}
		return points;
	}
	
}
