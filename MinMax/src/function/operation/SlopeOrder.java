package function.operation;

import function.FunctionSolver;
import function.SolvingFunction;

public class SlopeOrder implements Operation {
	//Attributes
	int order;
	
	//Constructors
	public SlopeOrder() {
		order = 2;
	}
	
	public SlopeOrder(int order) {
		this.order = order;
	}
	
	@Override
	public Double solve(SolvingFunction function, double point) {
		double epsilon = Math.pow(FunctionSolver.getEpsilon(), 1.0/((double)order));
		if(order>1) {
			SlopeOrder lowerOrder = new SlopeOrder(--order);
			return (lowerOrder.solve(function, point+epsilon)-lowerOrder.solve(function, point))/ epsilon;
		}
		Slope slope = new Slope();
		return slope.solve(function, point);
	}

	@Override
	public Object solve(SolvingFunction function, double[] interval) {
		// TODO Auto-generated method stub
		return null;
	}

}
