package function.operation.search;

import function.Point;

public class SearchPoint extends Point{
	//Attribute
	boolean optimizable = true;
	
	//Constructor
	public SearchPoint(double[] point) {
		super(point);
	}

	//Getters
	public boolean isOptimizable() {
		return optimizable;
	}

	//Setters
	public void setOptimizable(boolean optimizable) {
		this.optimizable = optimizable;
	}

	public void setPoint(Point point) {
		values = point.getValues();
	}
}
