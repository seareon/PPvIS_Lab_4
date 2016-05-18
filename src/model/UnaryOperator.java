package model;

public class UnaryOperator extends MathObject {
	private String operator;
	private double result = 0.0;
	
	public UnaryOperator(String str) {
		operator = str;
	}
	
	public String getString() {
		return operator;
	}
	
	public double getResult() {
		return result;
	}
	
	public double getResult(double op) {
		switch(operator) {
			case Constants.FACTORIAL:
				result = 1;
				for(int currentNumber = 2; currentNumber <= op; ++currentNumber) {
					result *= currentNumber;
				}
				break;
			case Constants.LN:
				result = Math.log10(op); 
				break;
			case Constants.LOG:
				result = Math.log(op); 
				break;
			case Constants.SQRT:
				result = Math.sqrt(op);
				break;
			case "+/-":
				result = -op;
		}
		return result; 
	}
	
	public static boolean isUnaryOperator(String str) {
		switch(str) {
			case Constants.FACTORIAL:	
				return true;
			case Constants.LN:
				return true;
			case Constants.LOG:
				return true;
			case Constants.SQRT:
				return true;
		}
		return false;
	}
}
