package function.operation.search;

public class SearchPoint {
	//Attribute
	double point;
	boolean optimizable = true;
	
	//Constructor
	public SearchPoint(double point) {
		this.point = point;
	}

	//Getters
	public double getPoint() {
		return point;
	}
	public boolean isOptimizable() {
		return optimizable;
	}

	//Setters
	public void setPoint(double newPoint) {
		point = newPoint;
	}
	public void setOptimizable(boolean optimizable) {
		this.optimizable = optimizable;
	}
}
