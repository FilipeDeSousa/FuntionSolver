package function;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import function.operation.Operation;
import function.operation.OperationFactory;

public class FunctionSolver {
	static double epsilon=1E-3;
	static int numberIterations = 100;
	static boolean printsIterations = false;
	
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
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OperationFactory opFactory = new OperationFactory();
		Operation op = opFactory.create(args[0]);
		SolvingFunction f = new SolvingFunction(args[1]);
		String[] strArray = args[2].split(",");
		Object result = null;
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
		System.out.println(result);
	}
}
