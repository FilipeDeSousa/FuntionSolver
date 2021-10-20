package function.operation;

import function.FunctionSolver;
import function.SolvingFunction;

public class Slope implements Operation {
	@Override
	public Double solve(SolvingFunction function, double point) {
		double epsilon;
		if(point == 0)
			epsilon = FunctionSolver.getEpsilon();
		else
			epsilon = FunctionSolver.getEpsilon()*point;
		
		/*Test
		System.out.println(function.image(point + epsilon)-function.image(point));
		System.out.println(epsilon);
		System.out.println((function.image(point + epsilon)-function.image(point)) / epsilon);
		System.exit(0);
		//Test*/
		
		return (function.image(point + epsilon)-function.image(point)) / epsilon;
	}

	@Override
	public Double solve(SolvingFunction function, double[] interval) {
		return (function.image(interval[1])-function.image(interval[0])) / (interval[1] - interval[0]);
	}

}
