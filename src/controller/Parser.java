package controller;

import java.util.Stack;
import model.BinaryOperator;
import model.Constants;
import model.History;
import model.Operand;
import model.UnaryOperator;

public class Parser {

	private History h;
	private Stack<String> operators; 
	
	public Parser(String str, History h) throws Exception {
		this.h = h;
		operators = new Stack<>();
		operators.push(Constants.START_END_FORMULA);
		parse(str);
	}
	
	private void parse(String str) throws Exception {
		str += Constants.START_END_FORMULA;
		String elem = "";
		for(int indexStr = 0; indexStr < str.length(); indexStr++) {
			if(Character.isDigit(str.charAt(indexStr)) || str.charAt(indexStr) == Constants.POINT) {
				elem += str.charAt(indexStr);
			}
			else { 
				if(!elem.equals("")) {
					h.setTreeItem(new Operand(elem));
					elem = "";
				}
				indexStr = readOperand(str, indexStr);
			}
		}
	}
	
	public void setStrForParse(String str) throws Exception { 
		h.clearRPN();
		operators.clear();
		operators.push(Constants.START_END_FORMULA);
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
				h.setTreeItem(new UnaryOperator(Constants.FACTORIAL));
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
			if(operators.peek().equals(Constants.START_END_FORMULA)) {
				throw new Exception("Ошибка - открытая скобка в начале формулы!");
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
			if(operators.peek().equals(Constants.START_END_FORMULA)) {
				break;
			} else if(operators.peek().equals(Constants.BRACKETLEFT)) {
				throw new Exception("Ошибка - открытая скобка после конца формулы!");
			}
			operatorToRPN();
		}while(true);
	}
	
	private void checkPlusMinusOperators(String str) {
		do{
			if(operators.peek().equals(Constants.START_END_FORMULA) || operators.peek().equals(Constants.BRACKETLEFT)) {
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
					operators.peek().equals(Constants.POWER)) {
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
				h.setTreeItem(new BinaryOperator(operators.pop()));
			} else {
				h.setTreeItem(new UnaryOperator(operators.pop()));
			}
		}
	}
	
	private int readOperand(String str, int indexStr) throws Exception {
		int newIndex = -1;
		switch(str.charAt(indexStr)) {
			case Constants.CEKSPANENTA:
				h.setTreeItem(new Operand(Constants.NEKSPANENTA));
				newIndex = indexStr;
				break;
			case Constants.L:
				if(str.charAt(indexStr + 1) == Constants.O) {
					addOperator(Constants.LOG);
					newIndex = indexStr + 2;
				} else {
					addOperator(Constants.LN);
					newIndex = indexStr + 1;
				}
				break;
			case '-':
				if(indexStr == 0 || str.charAt(indexStr - 1) == Constants.BRACKETLEFT.charAt(0)) {
					operators.add(Constants.NEGATIVE);
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
}
