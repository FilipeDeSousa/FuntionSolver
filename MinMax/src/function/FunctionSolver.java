package function;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import function.operation.Operation;
import function.operation.OperationFactory;
import function.operation.Optimizer;
import function.operation.search.RandomSearch;
import function.operation.search.SearchMethod;
import function.operation.search.SearchMethod.SearchMethodOption;
import function.operation.search.UniformIntervalsSearch;

public class FunctionSolver {
	static double epsilon=1E-3;
	static int numberIterations = 100;
	static boolean printsIterations = false;
	static boolean optimizerOn = false;
	static int numberSearchPoints = 10;
	static SearchMethod.SearchMethodOption searchMethodOption = SearchMethodOption.Random; static SearchMethod searchMethod;
	
	//Getters
	public static double getEpsilon() {
		return epsilon;
	}
	public static int getIterations() {
		return numberIterations;
	}
	public static boolean printsIteartions() {
		return printsIterations;
	}
	
	public static void main(String[] args) {
		//Config step
		Scanner scanner;
		try {
			scanner = new Scanner(new FileReader("./config.ini"));
			while(scanner.hasNextLine()) {
				String[] configs = scanner.nextLine().split("=");
				switch(configs[0].trim()) {
				case "epsilon":
					epsilon = Double.parseDouble(configs[1]); break;
				case "numberIterations":
					numberIterations = Integer.parseInt(configs[1].trim());break;
				case "printsIterations":
					printsIterations = Boolean.parseBoolean(configs[1].trim());break;
				case "optimizerOn":
					optimizerOn = Boolean.parseBoolean(configs[1].trim());break;
				case "numberSearchPoints":
					numberSearchPoints = Integer.parseInt(configs[1].trim());break;
				case "searchMethod":
					switch(configs[1].trim().toUpperCase()){
					case "random":
						searchMethodOption = SearchMethodOption.Random; break;
					case "uniformIntervals":
						searchMethodOption = SearchMethodOption.UniformIntervals; break;
					}					
					break;
				}
			}
			scanner.close();
			//Setups
			switch(searchMethodOption) {
			case Random:
				searchMethod = new RandomSearch(numberSearchPoints);break;
			case UniformIntervals:
				searchMethod = new UniformIntervalsSearch(numberSearchPoints);break;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OperationFactory opFactory = new OperationFactory();
		Operation op = opFactory.create(args[0]);
		SolvingFunction f = new SolvingFunction(args[1]);
		String[] strArray = args[2].split(",");
		Object result = null;
		if(optimizerOn) {
			double[]interval = {Double.parseDouble(strArray[0]), Double.parseDouble(strArray[1])};
			result = ((Optimizer) op).optimize(f, interval, searchMethod);
		}else {
			switch(strArray.length) {
			case 1:
				double point = Double.parseDouble(strArray[0]);
				result = op.solve(f, point);
				break;
			case 2:
				double[]interval = {Double.parseDouble(strArray[0]), Double.parseDouble(strArray[1])};
				result = op.solve(f, interval);
				break;
			}
		}
		System.out.println(result);
	}
}
