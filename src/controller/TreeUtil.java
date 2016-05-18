package controller;

import javafx.scene.control.TreeItem;
import model.BinaryOperator;
import model.Constants;
import model.ItemTree;
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
	
	static public String genStrStep(ItemTree it) {
		String str = "";
		if(it.countOutput() == 0) {
			str += it.getItem().getValue();
		} else {
			for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
				if(it.getItem().isExpanded()) {
					if(UnaryOperator.isUnaryOperator(it.getItem().getValue())) {
						if(!Constants.FACTORIAL.equals(it.getMathObject().getString())) {
							str += it.getItem().getValue() + "(";
						} else {
							str += "(";
						}
						str += genStrStep(it.getOutput(indexOutputList));
						if(!Constants.FACTORIAL.equals(it.getMathObject().getString())) {
							str += ")";
						} else {
							str += ")" + Constants.FACTORIAL;
						}
					} else {
						if(indexOutputList == 0) {
							str += "(";
							str += genStrStep(it.getOutput(indexOutputList));
							str += it.getItem().getValue();
						} else {
							str += genStrStep(it.getOutput(indexOutputList));
							str += ")";
						}
					}					
				} else {
					str += it.getItem().getValue();
					break;
				}
			}
		}
		return str;
	}
	
	static public TreeItem<String> setTreeView(ItemTree it) {
		for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			it.getItem().getChildren().add(setTreeView(it.getOutput(indexOutputList))); 
		}
		return it.getItem(); 
	}
	
	static public boolean doScan(ItemTree it) {
		boolean checkDo = false;
		for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			if(!checkDo) {
				if(it.getItem().isExpanded()) {
					checkDo = doScan(it.getOutput(indexOutputList));
				} else  {
					it.getItem().setExpanded(true);
					checkDo = true;
					break;
				}
			} 
		}
		return checkDo;
	}
	
	static public boolean doConvolution(ItemTree it) {
		boolean checkDo = false;
		if(!checkDo) {
			if(it.getMathObject() instanceof UnaryOperator) {
				if(it.getOutput(0).getItem().isExpanded()) {
					checkDo = doConvolution(it.getOutput(0));
				}
				else {
					it.getItem().setExpanded(false); 
					checkDo = true;
				}
			} else if(it.getOutput(1).getItem().isExpanded()) {
				checkDo = doConvolution(it.getOutput(1));
			} else if(it.getOutput(0).getItem().isExpanded()) {
				checkDo = doConvolution(it.getOutput(0));
			} else {
				it.getItem().setExpanded(false); 
				checkDo = true;
			}
		}
		return checkDo;
	}
}
