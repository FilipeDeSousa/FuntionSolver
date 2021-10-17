package function.operation;

public class OperationFactory {

	public Operation create(String opType) {
		Operation op = null;
		switch(opType) {
		case "Minimizer":
		case "Min":
		case "min":
			op = new Minimizer(); break;
		}
		
		return op;
	}
	
}
