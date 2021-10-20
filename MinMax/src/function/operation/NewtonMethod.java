package function.operation;

import function.FunctionSolver;
import function.SolvingFunction;
import function.operation.search.SearchMethod;

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

	@Override
	public Double optimize(SolvingFunction function, double[] interval, SearchMethod searchMethod) {
		double[] searchPoints = searchMethod.createSearchPoints(interval);
		for(int i=0; i<FunctionSolver.getIterations(); i++) {
			if(FunctionSolver.printsIteartions())
				System.out.println("Iteration "+(i+1)+":");
			for(int j=0; j<searchPoints.length; j++) {
				double slope = new Slope().solve(function, searchPoints[j]);
				double secondOrderSlope = new SlopeOrder(2).solve(function, searchPoints[j]);
				searchPoints[j] -= slope / secondOrderSlope;
				if(FunctionSolver.printsIteartions())
					System.out.println(" Point "+(j+1)+": x = "+searchPoints[j]+", f(x) = "+function.image(searchPoints[j])+", f'(x) = "+slope+", f''(x) = "+secondOrderSlope);
			}
		}
		//Select best to return
		double minimum = Double.MAX_VALUE;
		for(double point: searchPoints) {
			double image = function.image(point);
			if(image<minimum)
				minimum = image;
		}
		return minimum;
	}

}
