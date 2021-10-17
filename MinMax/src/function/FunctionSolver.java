package function;

import function.operation.Operation;
import function.operation.OperationFactory;

public class FunctionSolver {
	
	public static void main(String[] args) {
		OperationFactory opFactory = new OperationFactory();
		Operation op = opFactory.create(args[0]);
		SolvingFunction f = new SolvingFunction(args[1], args[2]);
	}

}
