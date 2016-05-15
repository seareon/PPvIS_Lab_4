package model;

public class Operand extends MathObject {
	private String sOperand;
	private double dOperand;
	
	public Operand(String str) throws NumberFormatException {
		sOperand = str;
		dOperand = Double.parseDouble(str);
	}

	public Operand(double result) {
		// TODO Auto-generated constructor stub
		sOperand = result + "";
		dOperand = result;
	}

	public String getString() {
		return sOperand;
	}

	public double getOperand() {
		return dOperand;
	}
}
