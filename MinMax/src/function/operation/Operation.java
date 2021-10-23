package function.operation;

import function.Point;
import function.SolvingFunction;

public interface Operation {
	public Object solve(SolvingFunction f, Point point);
}
