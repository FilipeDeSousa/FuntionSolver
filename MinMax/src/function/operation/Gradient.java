package function.operation;

import function.FunctionSolver;
import function.Point;
import function.SolvingFunction;

public class Gradient implements Operation {
	@Override
	public double[] solve(SolvingFunction function, Point point) {
		double[] gradient = new double[Point.getDimensionality()];
		for(int i=0; i<Point.getDimensionality(); i++)
			gradient[i] = solve(function, point, i);
		return gradient;
	}
	
	public double solve(SolvingFunction function, Point point, String dimension) {
		double value = point.getValue(dimension);
		double epsilon;
		if(value == 0)
			epsilon = FunctionSolver.getEpsilon();
		else
			epsilon = FunctionSolver.getEpsilon()*value;		
		return (function.image(point.incrementDimension(dimension, epsilon))-function.image(point)) / epsilon;
	}
	
	double solve(SolvingFunction function, Point point, int i) {
		double value = point.getValue(i);
		double epsilon;
		if(value == 0)
			epsilon = FunctionSolver.getEpsilon();
		else
			epsilon = FunctionSolver.getEpsilon()*value;		
		return (function.image(point.incrementDimension(i, epsilon))-function.image(point)) / epsilon;
	}
}
