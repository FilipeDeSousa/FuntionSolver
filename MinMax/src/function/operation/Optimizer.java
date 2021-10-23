package function.operation;

import function.Point;
import function.SolvingFunction;
import function.operation.search.SearchMethod;

public interface Optimizer extends Operation {
	public Point argMin(SolvingFunction function, Point point);
	public Point getArgMin();
	public double optimize(SolvingFunction f, Point[] interval, SearchMethod searchMethod);
}
