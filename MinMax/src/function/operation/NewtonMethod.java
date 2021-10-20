package function.operation;

import function.FunctionSolver;
import function.SolvingFunction;

public class NewtonMethod implements Operation, Optimizer {

	@Override
	public Double solve(SolvingFunction function, double point) {
		if(FunctionSolver.printsIteartions())
			System.out.println("Initial step: x = "+point+", f(x) = "+function.image(point));
		for(int i=0; i<FunctionSolver.getIterations(); i++) {
			double slope = new Slope().solve(function, point);
			double secondOrderSlope = new SlopeOrder(2).solve(function, point);
			point -= slope / secondOrderSlope;
			if(FunctionSolver.printsIteartions())
				System.out.println("Iteration " + (i+1) + ": x = "+point+", f(x) = "+function.image(point)+", f'(x) = "+slope+", f''(x) = "+secondOrderSlope);
		}
		return function.image(point);
	}

	@Override
	public Object solve(SolvingFunction function, double[] interval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double argMin(SolvingFunction function, double point) {
		if(FunctionSolver.printsIteartions())
			System.out.println("Initial step: x = "+point+", f(x) = "+function.image(point));
		for(int i=0; i<FunctionSolver.getIterations(); i++) {
			double slope = new Slope().solve(function, point);
			double secondOrderSlope = new SlopeOrder(2).solve(function, point);
			point -= slope / secondOrderSlope;
			if(FunctionSolver.printsIteartions())
				System.out.println("Iteration " + (i+1) + ": x = "+point+", f(x) = "+function.image(point)+", f'(x) = "+slope+", f''(x) = "+secondOrderSlope);
		}
		return point;
	}

}
