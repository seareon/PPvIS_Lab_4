package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import model.BinaryOperator;
import model.Constants;
import model.ItemTree;
import model.MathObject;
import model.UnaryOperator;

public class TreeUtil {
	static public void checkItem(ItemTree it, MathObject mo,  int n) {		// гавно
		if(mo != null) {
			if(n > -1) {
				it.deleteOutput(n);
				it.setArcOutput(new ItemTree(mo), n);
			} else {
				it.clear();
				it.setMathObgect(mo);
			} 
		}
	}
	
	static public MathObject checkNoExpandedItem(ItemTree it, ItemTree itCopy) {// гавно
		MathObject mo;
		for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			if(it.getItem().isExpanded()) {
				mo = checkNoExpandedItem(it.getOutput(indexOutputList), 
						itCopy.getOutput(indexOutputList));
				checkItem(itCopy, mo, indexOutputList);
			} else {
				List<MathObject> rpn = new ArrayList<>();
				getRPNForOperator(it, rpn);
				Decision.decisionOperator(rpn);
				return rpn.get(0);
			}
		}
		return null;
	}
	
	static public void getRPNForOperator(ItemTree it, List<MathObject> rpn) {// гавно
		for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			getRPNForOperator(it.getOutput(indexOutputList), rpn);
		}
		rpn.add(it.getMathObject());
	}
	
	static public String genStrStep(ItemTree it) {	// гавно
		String str = ""; 
		int indexOutputList;
		for(indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			if(it.getMathObject() instanceof UnaryOperator && indexOutputList == 0 &&
					!it.getMathObject().getString().equals(Constants.FACTORIAL)) {
				str += it.getMathObject().getString() + "(";
			} else if(indexOutputList == 0) {
				str += "(";
			}
			str += genStrStep(it.getOutput(indexOutputList));
			if(it.getMathObject() instanceof BinaryOperator && indexOutputList == 0 ) {
				str += it.getMathObject().getString();
			} else if(it.getMathObject().getString().equals(Constants.FACTORIAL)) {
				str += ")!";
			} else {
				str += ")"; 
			}
		}
		if((!(it.getMathObject() instanceof BinaryOperator) && 
							!(it.getMathObject() instanceof UnaryOperator))) {
			str += it.getMathObject().getString();
			if(indexOutputList == 2) {
				str += ")";
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
