package function.operation;

import function.FunctionSolver;
import function.Point;
import function.SolvingFunction;

public class GradientOrder implements Operation {
	//Attributes
	int order;
	
	//Constructors
	public GradientOrder() {
		order = 2;
	}
	
	public GradientOrder(int order) {
		this.order = order;
	}
	
	@Override
	public double[] solve(SolvingFunction function, Point point) {
		double[] gradient = new double[Point.getDimensionality()];
		for(int i=0; i<Point.getDimensionality(); i++) {
			gradient[i] = solve(function, point, i);
		}
		return gradient;
	}

	private double solve(SolvingFunction function, Point point, int i) {
		double epsilon;
		if(point.getValue(i) == 0)
			epsilon = FunctionSolver.getEpsilon();
		else
			epsilon = Math.pow(FunctionSolver.getEpsilon(), 1.0/((double)order));
		if(order>1) {
			GradientOrder lowerOrder = new GradientOrder(--order);
			return (lowerOrder.solve(function, point.incrementDimension(i, epsilon), i)-lowerOrder.solve(function, point, i))/ epsilon;
		}
		Gradient slope = new Gradient();
		return slope.solve(function, point, i);
	}
}
