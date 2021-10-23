package function.operation;

public class OperationFactory {

	public Operation create(String opType) {
		Operation op = null;
		switch(opType) {
		case "Slope":
			op = new Gradient(); break;
		case "NewtonMethod":
			op = new NewtonMethod();break;
		}
		
		return op;
	}
	
}
