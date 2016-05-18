package controller;

import javafx.scene.control.TreeItem;
import model.BinaryOperator;
import model.Constants;
import model.ItemTree;
import model.MathObject;
import model.Operand;
import model.UnaryOperator;

public class TreeUtil {	
	static public void checkNoExpandedItem(ItemTree it) {
		for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			if(it.getItem().isExpanded()) {
				if(!Operand.isOperand(it.getMathObject().getString()) && Operand.isOperand(it.getItem().getValue())) {
					it.getItem().setValue(it.getMathObject().getString());
				}
				checkNoExpandedItem(it.getOutput(indexOutputList));
			} else if(!Operand.isOperand(it.getItem().getValue())) {
				if(it.getMathObject() instanceof UnaryOperator) {
					it.getItem().setValue(((UnaryOperator) it.getMathObject()).getResult() + "");
				} else {
					it.getItem().setValue(((BinaryOperator) it.getMathObject()).getResult() + "");
				}
			}
		}
	}
	
	static public String genStrStep(ItemTree it) {	// гавно
		String str = "";
		if(it.countOutput() == 0) {
			str += it.getItem().getValue();
		} else {
			for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
				if(it.getItem().isExpanded()) {
					if(indexOutputList == 0) {
						if(!Constants.FACTORIAL.equals(it.getMathObject().getString()) && 
								UnaryOperator.isUnaryOperator(it.getItem().getValue())) {
							str += it.getItem().getValue() + "(";
						} else {
							str += "(";
						}
					}
					str += genStrStep(it.getOutput(indexOutputList));
					if(indexOutputList == 0) {
						if(it.getMathObject() instanceof UnaryOperator) {
							if(!Constants.FACTORIAL.equals(it.getMathObject().getString())) {
								str += ")";
							} else {
								str += ")!";
							}
							break;
						} else {
							str += it.getItem().getValue();
						}
					} else {
						str += ")";
						break;
					}
				} else {
					str += it.getItem().getValue();
					break;
				}
			}
		}
		return str;
	}
	
	static public TreeItem<String> setTreeView(ItemTree it) {// гавно
		for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			it.getItem().getChildren().add(setTreeView(it.getOutput(indexOutputList))); 
		}
		return it.getItem(); 
	}
	
	static public int doScan(ItemTree it) {// гавно
		int checkDo = 0;
		for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			if(checkDo == 0 ) {
				if(it.getItem().isExpanded()) {
					checkDo = doScan(it.getOutput(indexOutputList));
				} else  {
					it.getItem().setExpanded(true);
					checkDo = -1;
					break;
				}
			} 
		}
		return checkDo;
	}
	
	static public int doConvolution(ItemTree it) {// гавно
		int checkDo = 0;
		for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			if(checkDo == 0 ) {
				if(it.getItem().isExpanded()) {
					checkDo = doConvolution(it.getOutput(indexOutputList));
				} else  {
					checkDo = 1;
					break;
				}
			} 
			if(checkDo == 1 && ((it.getMathObject() instanceof BinaryOperator && indexOutputList == 1) || 
					it.getMathObject() instanceof UnaryOperator)) {
				it.getItem().setExpanded(false);
				checkDo = -1;
				break;
			} else if(checkDo != -1) {
				checkDo = 0;
			}
		}
		if(it.countOutput() == 0) {
			checkDo = 1;
		}
		return checkDo;
	}
}
