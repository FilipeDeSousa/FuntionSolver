package function;

import java.util.Arrays;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class SolvingFunction {
	//Attributes
	String f;
	ScriptEngineManager engineManager = new ScriptEngineManager();
	ScriptEngine engine = engineManager.getEngineByName("nashorn");
	
	enum Priority {
		BracketsAndParentheses, Exponents, Division, Multiplication, Addition, Subtraction
	}//BEDMAS
	
	//Constructor
	SolvingFunction(String function){
		f=function;
		
		//Substitute operations
		replacePow();
		f = f.replaceAll("cos", "Math.cos").replaceAll("exp", "Math.exp").replaceAll("sqrt", "Math.sqrt").replaceAll("pi", "Math.PI");
	}

	private void replacePow() {
		String[] hands = f.split("\\^");
		if(hands.length == 1)
			return;
		int[] rightIndexs = new int[hands.length], leftIndexs = new int[hands.length];
		Arrays.fill(leftIndexs, -1);
		Arrays.fill(rightIndexs, -1);
		for(int i=0; i<hands.length-1; i++)
			leftIndexs[i]=getLeftScope(hands[i], Priority.Exponents);
		for(int i=1; i<hands.length; i++) {
			rightIndexs[i]=getRightScope(hands[i], Priority.Exponents);
		}
		
		for(int i=0; i<hands.length; i++) {
			if(leftIndexs[i]!=-1) {
				/*Test
				System.out.println(hands[i]+" -> "+leftIndexs[i]);
				//Test*/
				
				hands[i] = hands[i].substring(0, leftIndexs[i]) + "Math.pow(" + hands[i].substring(leftIndexs[i]);
			}if(rightIndexs[i]!=-1) {
				String rightHand = "";
				if(rightIndexs[i]>0)
					rightHand = hands[i].substring(rightIndexs[i]);
				
				hands[i] = "," + hands[i].substring(0, rightIndexs[i]) + ")" + rightHand;
			}
		}
		
		f="";
		for(String hand: hands)
			f+=hand;
		
		/*Test
		System.out.println(f);
		System.exit(0);
		//Test*/
	}

	private int getLeftScope(String expression, Priority priority) {
		for(int i = expression.length()-1; i>=0; i--)
			switch(priority) {
			case Exponents:
				if(expression.charAt(i) == '/')
					return i+1;
			case Division:
				if(expression.charAt(i) == '*')
					return i+1;
			case Multiplication:
				if(expression.charAt(i) == '+')
					return i+1;
			case Addition:
				if(expression.charAt(i) == '-')
					return i+1;
			case Subtraction:
				if(expression.charAt(i) == '(')
					return i+1;
			default:
				break;
			}
		return 0;
	}
	
	private int getRightScope(String expression, Priority priority) {
		for(int i = 0; i<expression.length(); i++)
			switch(priority) {
			case Exponents:
				if(expression.charAt(i) == '/')
					return i;
			case Division:
				if(expression.charAt(i) == '*')
					return i;
			case Multiplication:
				if(expression.charAt(i) == '+')
					return i;
			case Addition:
				if(expression.charAt(i) == '-')
					return i;
			default:
				break;
			}
		return expression.length();
	}

	public double image(Double x) {
		double y = 0;
		try {
			/*Test
			System.out.println(f);
			//System.exit(0);
			//Test*/
			
			engine.eval("x = "+x);
			y = (double) engine.eval(f);
			
			/*Test
			System.out.println(y);
			System.exit(0);
			//Test*/
			
		} catch (ScriptException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return y;
	}
}