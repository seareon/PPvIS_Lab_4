package model;

public class BinaryOperator extends MathObject {
	private String operator;
	
	public BinaryOperator(String str) {
		operator = str;
	}
	
	public String getString() {
		return operator;
	}
	
	public double getResult(double op1, double op2) {
		double result = 0.0;
		switch(operator) {
			case Constants.PLUS:
				result = op1 + op2;
				break;
			case Constants.MINUS:
				result = op1 - op2;
				break;
			case Constants.MULTIPLICATION:
				result = op1 * op2;
				break;
			case Constants.DIVISION:
				result = op1 / op2;
				break;
			case Constants.POWER:
				result = Math.pow(op1, op2);
				break;
			case Constants.PERCENT:
				result = op1 % op2;
		}
		return result; 
	}
	
	static public boolean isBinaryOperator(String str) {
		switch(str) {
			case Constants.PLUS:
				return true;
			case Constants.MINUS:
				return true;
			case Constants.MULTIPLICATION:
				return true;
			case Constants.DIVISION:
				return true;
			case Constants.POWER:
				return true;
			case Constants.PERCENT:
				return true;
		}
		return false;
	}
}
