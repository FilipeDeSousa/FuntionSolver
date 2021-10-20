package function.operation;

import function.SolvingFunction;

public interface Optimizer extends Operation {
	public Double argMin(SolvingFunction function, double point);
}
