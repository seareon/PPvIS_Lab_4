package controller;

import java.util.List;
import java.util.ListIterator;
import model.BinaryOperator;
import model.History;
import model.MathObject;
import model.Operand;
import model.UnaryOperator;

public class Decision {
	private double result;
	private History h;
	
	public Decision(History h) throws Exception {
		this.h = h;
		setRPN();
	}
	
	public void setRPN() throws Exception {
		decision();
	}
	
	private void decision() throws Exception {
		List<MathObject> lmo = h.getRPN();
		decisionOperator(lmo, lmo.size());
		if(lmo.size() > 1) {
			throw new Exception("Не верно построена формула!!!");
		}
		result = ((Operand) lmo.get(0)).getOperand();
	}
	
	public static double decisionOperator(List<MathObject> lmo, int operator) {
		ListIterator<MathObject> lister = lmo.listIterator();
		int op = operator;
		while(lister.hasNext() && op > 0) {
			MathObject mo = lister.next();
			if(mo instanceof BinaryOperator) {
				BinaryOperator bo = (BinaryOperator) mo;
				lister.remove();
				Operand o2 = (Operand) lister.previous();
				lister.remove();
				Operand o1 = (Operand) lister.previous();
				lister.remove();
				lister.add(new Operand(bo.getResult(o1.getOperand(), o2.getOperand())));
				op--;
			} else if(mo instanceof UnaryOperator) {
				UnaryOperator uo = (UnaryOperator) mo;
				lister.remove();
				Operand o = (Operand) lister.previous();
				lister.remove();
				lister.add(new Operand(uo.getResult(o.getOperand())));  
				op--;
			}
		}
		return ((Operand)lister.previous()).getOperand();
	} 
	
	public double getResult() {
		return result;
	}
}
