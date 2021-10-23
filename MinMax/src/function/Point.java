package function;

public class Point {
	//Static attributes
	static String[]dimensions;
	static int dimensionality;
	
	//Attributes
	protected double []values;
	
	//Static
	public static void setDimensions(String[] dimensions) {
		Point.dimensions = dimensions.clone();
		dimensionality = dimensions.length;
	}
	public static String getDimension(int i) {
		return dimensions[i];
	}
	
	//Constructor
	public Point(double[] point){
		values = point.clone();
	}
	public Point(String[] point) {
		values = new double[dimensionality];
		for(int i=0; i<point.length; i++)
			values[i] = Double.parseDouble(point[i]);
	}
	
	//Non-static
	public static int length() {
		return dimensions.length;
	}
	
	public Point incrementDimension(String dimension, double epsilon) {
		double[] newValues = values.clone();
		boolean dimensionExists = false;
		for(int i=0; i<dimensions.length; i++)
			if(dimensions[i].equals(dimension)) {
				newValues[i] += epsilon;
			}
		
		//Error - Dimension doesn't exist
		if(!dimensionExists) {
			System.out.println("ERROR: Dimension "+dimension+" doesn't exist!");
			System.exit(0);
		}
		
		return new Point(newValues);
	}
	
	public Point incrementDimension(int i, double epsilon) {
		double[] newValues = values.clone();
		newValues[i] += epsilon;
		return new Point(newValues);
	}
	
	//Getters
	public double getValue(int i) {
		return values[i];
	}
	public double getValue(String dimension) {
		for(int i=0; i<dimensions.length; i++)
			if(dimensions[i].equals(dimension))
				return values[i];
		
		//Error - Dimension doesn't exist
		System.out.println("ERROR: Dimension "+dimension+" doesn't exist!");
		System.exit(0);
		return 0;
	}
	public static int getDimensionality() {
		return dimensionality;
	}
	public double[] getValues() {
		return values;
	}
}
