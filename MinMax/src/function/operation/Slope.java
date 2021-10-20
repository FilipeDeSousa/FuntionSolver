package function.operation;

import function.FunctionSolver;
import function.SolvingFunction;

public class Slope implements Operation {
	@Override
	public Double solve(SolvingFunction function, double point) {
		double epsilon = FunctionSolver.getEpsilon()*point;
		return (function.image(point + epsilon)-function.image(point)) / epsilon;
	}

	@Override
	public Double solve(SolvingFunction function, double[] interval) {
		return (function.image(interval[1])-function.image(interval[0])) / (interval[1] - interval[0]);
	}

}
