package function.operation;

import function.SolvingFunction;
import function.operation.search.SearchMethod;

public interface Optimizer extends Operation {
	public Double argMin(SolvingFunction function, double point);
	public double optimize(SolvingFunction function, double[] interval, SearchMethod searchMethod);
	public double getArgMin();
}
