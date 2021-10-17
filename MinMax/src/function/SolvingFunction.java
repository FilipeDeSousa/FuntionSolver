package function;

public class SolvingFunction {
	String f;
	Double intervalMin, intervalMax;
	
	//Constructor
	SolvingFunction(String function, String interval){
		f=function;
		String intervalStrArray[] = interval.split(",");
		intervalMin = Double.parseDouble(intervalStrArray[0]); intervalMax = Double.parseDouble(intervalStrArray[1]);
	}
}