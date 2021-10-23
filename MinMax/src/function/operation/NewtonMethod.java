package function.operation;

import function.FunctionSolver;
import function.Point;
import function.SolvingFunction;
import function.operation.search.SearchMethod;
import function.operation.search.SearchPoint;

public class NewtonMethod implements Operation, Optimizer {
	Point argMin;
	
	@Override
	public Double solve(SolvingFunction function, Point point) {
		for(int i=0; i<FunctionSolver.getIterations(); i++) {
			double image = function.image(point);
			double[] gradient = new Gradient().solve(function, point);
			double[] newValues = new double[Point.getDimensionality()];
			double[] steps = new double[Point.getDimensionality()];
			if(FunctionSolver.printsIterations())
				System.out.print("Iteration " + (i+1) + ":");
			for(int j=0; j<Point.getDimensionality(); j++) {
				if(FunctionSolver.printsIterations()) {
					String dimension = Point.getDimension(j);
					System.out.print(" "+dimension+"="+point.getValue(j)+" f("+dimension+")="+image+" f'("+dimension+")="+gradient[j]);
				}
				steps[j] = image / gradient[j] ;
				newValues[j] = point.getValue(j) - steps[j];
			}
			if(FunctionSolver.printsIterations())
				System.out.println();
			Point newPoint = new Point(newValues);
			double newImage = function.image(newPoint);
			if(FunctionSolver.getEnableDegrade())
				point = newPoint;
			else if(newImage<=image)
				point = newPoint;
			//Altered nuance to avoid degradation
			else {
				newPoint = avoidDegradation(function, point, image, newImage, steps);
			}
		}
		argMin = point;
		return function.image(point);
	}

	private Point avoidDegradation(SolvingFunction function, Point point, double image, double newImage, double[] steps) {
		Point newPoint;
		do{
			double[] newValues = new double[Point.getDimensionality()];
			for(int i=0; i<Point.getDimensionality(); i++) {
				steps[i] *= .5;
				newValues[i] = point.getValue(i)-steps[i];
			}
			newPoint = new Point(newValues);
			newImage = function.image(newPoint);
		}while(newImage>image);
		if(FunctionSolver.printsIterations()) {
			System.out.print("Avoiding degradation:");
			for(int i=0; i<Point.getDimensionality(); i++) {
				String dimension = Point.getDimension(i);
				System.out.print(" step("+dimension+")="+steps[i]+" "+dimension+"="+newPoint.getValue(i));
			}
			System.out.println("f="+newImage);
		}
		return newPoint;
	}

	@Override
	public Point argMin(SolvingFunction function, Point point) {
		for(int i=0; i<FunctionSolver.getIterations(); i++) {
			double image = function.image(point);
			double[] gradient = new Gradient().solve(function, point);
			double[] newValues = new double[Point.getDimensionality()];
			double[] steps = new double[Point.getDimensionality()];
			if(FunctionSolver.printsIterations())
				System.out.print("Iteration " + (i+1) + ":");
			for(int j=0; j<Point.getDimensionality(); j++) {
				if(FunctionSolver.printsIterations()) {
					String dimension = Point.getDimension(j);
					System.out.print(" "+dimension+"="+point.getValue(j)+" f("+dimension+")="+image+" f'("+dimension+")="+gradient[j]);
				}
				steps[j] = image / gradient[j] ;
				newValues[j] = point.getValue(j) - steps[j];
			}
			Point newPoint = new Point(newValues);
			double newImage = function.image(newPoint);
			if(FunctionSolver.getEnableDegrade())
				point = newPoint;
			else if(newImage<=image)
				point = newPoint;
			//Altered nuance to avoid degradation
			else {
				avoidDegradation(function, point, image, newImage, steps);
			}
		}
		argMin = point;
		return point;
	}

	@Override
	public double optimize(SolvingFunction function, Point[] interval, SearchMethod searchMethod) {
		SearchPoint[] searchPoints = searchMethod.createSearchPoints(interval);
		for(int i=0; i<FunctionSolver.getIterations(); i++) {
			boolean optimizablePoints = false;
			if(FunctionSolver.printsIterations())
				System.out.println("Iteration "+(i+1)+":");
			//Initialize images
			double[] images = new double[searchPoints.length];
			for(int j=0; j<images.length; j++)
				images[j] = function.image(searchPoints[j]);
			for(int j=0; j<searchPoints.length; j++) {
				if(!searchPoints[j].isOptimizable())
					continue;
				
				double[] gradient = new Gradient().solve(function, searchPoints[j]);
				double[] newValues = new double[Point.getDimensionality()], steps = new double[Point.getDimensionality()];
				if(FunctionSolver.printsIterations())
					System.out.print("Point "+j+":");
				for(int k=0; k<Point.getDimensionality(); k++) {
					if(FunctionSolver.printsIterations()) {
						String dimension = Point.getDimension(k);
						System.out.print(" "+dimension+"="+searchPoints[j].getValue(k)+" f'("+dimension+")="+gradient[k]);
					}
					//No further optimization possible
					if(gradient[k] == 0 || images[j] == 0) {
						searchPoints[j].setOptimizable(false);
						if(FunctionSolver.printsIterations())
							System.out.println(" -> No furter optimization possible!");
						continue;
					}
					steps[k] = images[j] / gradient[k];
					newValues[k] = searchPoints[j].getValue(k) - steps[k];
				}
				if(FunctionSolver.printsIterations())
					System.out.println(" f="+images[j]);
				Point newPoint = new Point(newValues);
				double newImage = function.image(newPoint);
				
				if(FunctionSolver.getEnableDegrade()) {
					searchPoints[j].setPoint(newPoint);
					optimizablePoints = true;
				}else if(newImage<=images[j]) {
					searchPoints[j].setPoint(newPoint);
					optimizablePoints = true;
				//Altered nuance to avoid degradation
				}else {
					avoidDegradation(function, newPoint, newImage, newImage, steps);
				}
			}
			if(!optimizablePoints)
				break;
		}
		//Select best to return
		double minimum = Double.MAX_VALUE;
		for(SearchPoint point: searchPoints) {
			double image = function.image(point);
			if(image<minimum) {
				argMin  = point;
				minimum = image;
			}
		}
		
		return minimum;
	}

	//Getter
	@Override
	public Point getArgMin() {
		return argMin;
	}

}
