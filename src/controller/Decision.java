package controller;

import java.util.List;
import java.util.ListIterator;
import model.BinaryOperator;
import model.History;
import model.MathObject;
import model.Operand;
import model.UnaryOperator;

public class Decision {
	private List<MathObject> rpn;
	private double result;
	private History h;
	
	public Decision(List<MathObject> rpn, History h) throws Exception {
		this.h = h;
		setRPN(rpn);
	}
	
	public void setRPN(List<MathObject> rpn) throws Exception {
		this.rpn = rpn;
		decision();
	}
	
	private void decision() throws Exception {
		ListIterator<MathObject> lister = rpn.listIterator();
		while(lister.hasNext()) {
			MathObject mo = lister.next();
			if(mo instanceof BinaryOperator) {
				BinaryOperator bo = (BinaryOperator) mo;
				lister.remove();
				Operand o2 = (Operand) lister.previous();
				lister.remove();
				Operand o1 = (Operand) lister.previous();
				lister.remove();
				lister.add(new Operand(bo.getResult(o1.getOperand(), o2.getOperand())));
			} else if(mo instanceof UnaryOperator) {
				UnaryOperator uo = (UnaryOperator) mo;
				lister.remove();
				Operand o = (Operand) lister.previous();
				lister.remove();
				lister.add(new Operand(uo.getResult(o.getOperand())));  
			}
			h.setTreeItem(mo.getString());
		}
		if(rpn.size() > 1) {
			throw new Exception("Не верно построена формула!!!");
		}
		result = ((Operand) rpn.get(0)).getOperand();
	}
	
	public double getResult() {
		return result;
	}
}
