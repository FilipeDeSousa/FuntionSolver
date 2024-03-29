package function;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import function.operation.Operation;
import function.operation.OperationFactory;
import function.operation.Optimizer;
import function.operation.search.RandomSearch;
import function.operation.search.SearchMethod;
import function.operation.search.SearchMethod.SearchMethodOption;
import function.operation.search.UniformIntervalsSearch;

public class FunctionSolver {
	//Attributes
	static double epsilon=1E-3;
	static int numberIterations = 100;
	static boolean printsIterations = false;
	static boolean printsTimeElapsed = false;
	//Optimizer attributes
	static boolean optimizerOn = false;
	static int numberSearchPoints = 10;
	static SearchMethod.SearchMethodOption searchMethodOption = SearchMethodOption.Random; static SearchMethod searchMethod;
	static boolean enableDegrade = false;
	
	//Getters
	public static double getEpsilon() {
		return epsilon;
	}
	public static int getIterations() {
		return numberIterations;
	}
	public static boolean printsIterations() {
		return printsIterations;
	}
	public static boolean getEnableDegrade() {
		return enableDegrade;
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
				case "printsTimeElapsed":
					printsTimeElapsed = Boolean.parseBoolean(configs[1].trim());break;
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
				case "enableDegrade":
					enableDegrade = Boolean.parseBoolean(configs[1].trim());break;
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
		String[] dimensions = args[2].split(",");
		Point.setDimensions(dimensions);
		String[] interval = args[3].split(";");
		Object result = null;
		if(optimizerOn) {
			String[] p1Array = interval[0].split(",");
			Point p1 = new Point(p1Array);
			String[] p2Array = interval[1].split(",");
			Point p2 = new Point(p2Array);
			Point[] pointInterval = {p1, p2};
			Optimizer optimizer = (Optimizer)op;
			Instant start = Instant.now();
			result = optimizer.optimize(f, pointInterval, searchMethod);
			if(printsTimeElapsed) {
				Instant end = Instant.now();
				Duration timeElapsed = Duration.between(start, end);
				System.out.println(timeElapsed.toMinutes()+"\""+timeElapsed.toSecondsPart()+"."+timeElapsed.getNano());
			}
			System.out.println("Minimum = "+result+", ArgMin = "+optimizer.getArgMin());
		}else {
			String[] pArray = interval[0].split(",");
			Point point = new Point(pArray);
			result = op.solve(f, point);
		}
	}
}
