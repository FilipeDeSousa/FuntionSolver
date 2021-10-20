package function.operation;

import function.SolvingFunction;

public interface Operation {
	public Object solve(SolvingFunction function, double point);
	public Object solve(SolvingFunction function, double[]interval);
}
