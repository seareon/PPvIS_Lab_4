package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.BinaryOperator;
import model.Constants;
import model.MathObject;
import model.Operand;
import model.UnaryOperator;

public class Parser {
	private List<MathObject> rpn;
	private Stack<String> operators; 
//	boolean negative = false;
	
	public Parser(String str) throws Exception {
		rpn = new ArrayList<>();
		operators = new Stack<>();
		operators.push("|");
		parse(str);
	}
	
	private void parse(String str) throws Exception {
		str += "|";
		String elem = "";
		for(int indexStr = 0; indexStr < str.length(); indexStr++) {
			if(Character.isDigit(str.charAt(indexStr)) || str.charAt(indexStr) == '.') {
				elem += str.charAt(indexStr);
			}
			else { 
				if(!elem.equals("")) {
					rpn.add(new Operand(elem));
//					negative = false;
					elem = "";
				}
				indexStr = readOperand(str, indexStr);
			}
		}
	}
	
	public void setStrForParse(String str) throws Exception { 
		rpn.clear();
		operators.clear();
		operators.push("|");
		parse(str);
	}
	
	private void addOperator(String str) throws Exception {
		switch(str) {
			case Constants.BRACKETLEFT:
				operators.add(Constants.BRACKETLEFT);
				break;
			case Constants.BRACKETRIGHT:
				checkBracket();
				break;
			case Constants.DIVISION:
				checkDivMultModPowOperators(Constants.DIVISION);
				break;
			case Constants.FACTORIAL:
				rpn.add(new UnaryOperator(Constants.FACTORIAL));
				break;
			case Constants.LN:
				operators.add(Constants.LN);
				break;
			case Constants.LOG:
				operators.add(Constants.LOG);
				break;
			case Constants.MINUS:
				checkPlusMinusOperators(Constants.MINUS);
				break;
			case Constants.MULTIPLICATION:
				checkDivMultModPowOperators(Constants.MULTIPLICATION);
				break;
			case Constants.PERCENT:
				checkDivMultModPowOperators(Constants.PERCENT);
				break;
			case Constants.PLUS:
				checkPlusMinusOperators(Constants.PLUS);
				break;
			case Constants.POWER:
				checkDivMultModPowOperators(Constants.POWER);
				break;
			case Constants.SQRT:
				operators.add(Constants.SQRT);
				break;
			case "|":
				checkEndFormula();
		}
	}
	
	private void checkBracket() throws Exception {
		do {
			if(operators.peek().equals("|")) {
				throw new Exception("������ - �������� ������ � ������ �������!");
			} else if(operators.peek().equals(Constants.BRACKETLEFT)) {
				operators.pop();
				if(operators.peek().equals(Constants.LOG) || operators.peek().equals(Constants.LN) ||
						operators.peek().equals("+/-")) {
					operatorToRPN();
				}
				break;
			}
			operatorToRPN();
		}while(true);
	}
	
	private void checkEndFormula() throws Exception {
		do {
			if(operators.peek().equals("|")) {
				break;
			} else if(operators.peek().equals(Constants.BRACKETLEFT)) {
				throw new Exception("������ - �������� ������ ����� ����� �������!");
			}
			operatorToRPN();
		}while(true);
	}
	
	private void checkPlusMinusOperators(String str) {
		do{
			if(operators.peek().equals("|") || operators.peek().equals(Constants.BRACKETLEFT)) {
				operators.add(str);
				break;
			} else {
				operatorToRPN();
			}
		}while(true);
	}
	
	private void checkDivMultModPowOperators(String str) {
		do{ 
			if(operators.peek().equals(Constants.MULTIPLICATION) || 
					operators.peek().equals(Constants.DIVISION) || 
					operators.peek().equals(Constants.SQRT) || 
					operators.peek().equals(Constants.PERCENT) ||
					operators.peek().equals(Constants.POWER)/* || 
					operators.peek().equals(Constants.LN) ||
					operators.peek().equals(Constants.LOG)*/) {
				operatorToRPN();
			} else {
				operators.add(str);
				break;
			}
		}while(true);
	}
	
	private void operatorToRPN() {
		if(!operators.isEmpty()) {
			if(BinaryOperator.isBinaryOperator(operators.peek())) {
				rpn.add(new BinaryOperator(operators.pop()));
			} else {
				rpn.add(new UnaryOperator(operators.pop()));
			}
		}
	}
	
	private int readOperand(String str, int indexStr) throws Exception {
		int newIndex = -1;
		switch(str.charAt(indexStr)) {
			case 'e':
				rpn.add(new Operand("2.7182"/*, false*/));
				newIndex = indexStr;
				break;
			case 'l':
				if(str.charAt(indexStr + 1) == 'o') {
					addOperator(Constants.LOG);
					newIndex = indexStr + 2;
				} else {
					addOperator(Constants.LN);
					newIndex = indexStr + 1;
				}
				break;
			case '-':
				if(indexStr == 0 || str.charAt(indexStr - 1) == Constants.BRACKETLEFT.charAt(0)) {
					operators.add("+/-");
				} else {
					addOperator(str.charAt(indexStr) + "");
				}
				newIndex = indexStr;
				break;
			default :
				addOperator(str.charAt(indexStr) + "");
				newIndex = indexStr;
		}
		return newIndex;
	}
	
	public List<MathObject> getRPN() {
		return rpn;
	}
}
