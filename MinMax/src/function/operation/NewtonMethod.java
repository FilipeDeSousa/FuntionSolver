package function.operation;

import function.FunctionSolver;
import function.SolvingFunction;
import function.operation.search.SearchMethod;
import function.operation.search.SearchPoint;

public class NewtonMethod implements Operation, Optimizer {
	double argMin;
	
	@Override
	public Double solve(SolvingFunction function, double point) {
		for(int i=0; i<FunctionSolver.getIterations(); i++) {
			double image = function.image(point);
			double slope = new Slope().solve(function, point);
			if(FunctionSolver.printsIterations())
				System.out.println("Iteration " + (i+1) + ": x = "+point+", f(x) = "+image+", f'(x) = "+slope);
			double step = image / slope ;
			double newPoint = point - step;
			double newImage = function.image(newPoint);
			if(FunctionSolver.getEnableDegrade())
				point = newPoint;
			else if(newImage<=image)
				point = newPoint;
			//Altered nuance to avoid degradation
			else {
				while(newImage>image) {
					step*=.5;
					newPoint = point - step;
					newImage = function.image(newPoint);
				}
				System.out.println("Avoiding degradation: step = "+step+" x = "+newPoint+", f(x) "+newImage);
			}
		}
		argMin = point;
		return function.image(point);
	}

	@Override
	public Object solve(SolvingFunction function, double[] interval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double argMin(SolvingFunction function, double point) {
		if(FunctionSolver.printsIterations())
			System.out.println("Initial step: x = "+point+", f(x) = "+function.image(point));
		for(int i=0; i<FunctionSolver.getIterations(); i++) {
			double image = function.image(point);
			double slope = new Slope().solve(function, point);
			point -= image / slope;
			if(FunctionSolver.printsIterations())
				System.out.println("Iteration " + (i+1) + ": x = "+point+", f(x) = "+function.image(point)+", f'(x) = "+slope);
		}
		return point;
	}

	@Override
	public double optimize(SolvingFunction function, double[] interval, SearchMethod searchMethod) {
		SearchPoint[] searchPoints = searchMethod.createSearchPoints(interval);
		for(int i=0; i<FunctionSolver.getIterations(); i++) {
			boolean optimizablePoints = false;
			if(FunctionSolver.printsIterations())
				System.out.println("Iteration "+(i+1)+":");
			for(int j=0; j<searchPoints.length; j++) {
				if(!searchPoints[j].isOptimizable())
					continue;
				double image = function.image(searchPoints[j].getPoint());
				double slope = new Slope().solve(function, searchPoints[j].getPoint());
				if(FunctionSolver.printsIterations())
					System.out.print(" Point " + (j+1) + ": x = "+searchPoints[j].getPoint()+", f(x) = "+image+", f'(x) = "+slope);
				//No further optimization possible
				if(slope == 0 || image == 0) {
					searchPoints[j].setOptimizable(false);
					if(FunctionSolver.printsIterations())
						System.out.println(" -> No furter optimization possible!");
					continue;
				}
				double step = image / slope;
				double newPoint = searchPoints[j].getPoint() - step;
				double newImage = function.image(newPoint);
				if(FunctionSolver.printsIterations())
					System.out.println(", x_{i+1} = "+newPoint+", f(x_{i+1}) = " + newImage);
				
				if(FunctionSolver.getEnableDegrade()) {
					searchPoints[j].setPoint(newPoint);
					optimizablePoints = true;
				}else if(newImage<=image) {
					searchPoints[j].setPoint(newPoint);
					optimizablePoints = true;
				//Altered nuance to avoid degradation
				}else {
					while(newImage>image) {
						step*=.5;
						
						/*Test
						System.out.print("Step = " + step);
						if(step == Double.NEGATIVE_INFINITY || step == Double.POSITIVE_INFINITY) {
							System.out.print("STEP NAN!!!");
							System.exit(0);
						}
						//Test*/
						
						if(step == 0) {
							searchPoints[j].setOptimizable(false);
							if(FunctionSolver.printsIterations())
								System.out.println(" -> No furter optimization possible!");
							continue;
						}
						newPoint = searchPoints[j].getPoint() - step;
						newImage = function.image(newPoint);
					}
					searchPoints[j].setPoint(newPoint);
					optimizablePoints = true;
					if(FunctionSolver.printsIterations())
						System.out.println("Avoiding degradation: step = "+step+" x = "+newPoint+", f(x) "+newImage);
				}
			}
			if(!optimizablePoints)
				break;
		}
		//Select best to return
		double minimum = Double.MAX_VALUE;
		for(SearchPoint point: searchPoints) {
			double image = function.image(point.getPoint());
			if(image<minimum) {
				argMin  = point.getPoint();
				minimum = image;
			}
		}
		
		return minimum;
	}

	//Getter
	@Override
	public double getArgMin() {
		return argMin;
	}

}
