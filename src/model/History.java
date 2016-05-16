package model;

import java.util.ArrayList;
import java.util.List;

public class History {		
	private List<MathObject> rpn = new ArrayList<>();
	private List<ItemTree> treeIt = new ArrayList<>();
	private ItemTree node; 
//	private int countOperator = 0;
	
	public List<MathObject> getRPN() {
		List<MathObject> temp = new ArrayList<>(rpn);
		return temp;
	}
	
	public void clearRPN() {
		rpn.clear();
	}
	
	public void setTreeItem(MathObject mo) {
		rpn.add(mo);
		node = setItem(mo, treeIt);
/*		ItemTree it = new ItemTree(mo);
		treeIt.add(it);
		node = it;
		int countOperand = 0;
		if(mo instanceof BinaryOperator) {
			countOperand = 2;
		} else if(mo instanceof UnaryOperator) {
			countOperand = 1;
		}
		for(int indexTree = treeIt.size() - 2; indexTree >= 0 && countOperand > 0; indexTree--) {
			if(!treeIt.get(indexTree).isHaveInputArc()) {
				node.setArcOutput(treeIt.get(indexTree), 0); 
				treeIt.get(indexTree).setInput();
				if(countOperand--  == 1) {
					break;
				}
			}
		}*/
	}
	
	public ItemTree setItem(MathObject mo, List<ItemTree> temp/*, ItemTree node*/) {
		ItemTree node = new ItemTree(mo);
		temp.add(node);
		int countOperand = 0;
		if(mo instanceof BinaryOperator) {
			countOperand = 2;
		} else if(mo instanceof UnaryOperator) {
			countOperand = 1;
		}
		for(int indexTree = temp.size() - 2; indexTree >= 0 && countOperand > 0; indexTree--) {
			if(!temp.get(indexTree).isHaveInputArc()) {
				node.setArcOutput(temp.get(indexTree), 0); 
				temp.get(indexTree).setInput();
				if(countOperand--  == 1) {
					break;
				}
			}
		}
		return node;
	}
	
	public ItemTree getItemRoot() {
/*		List<ItemTree> temp = new ArrayList<>();
		ItemTree node = null;
		for(ItemTree ls : treeIt) {
			setTreeItem(ls.getMathObject(), temp, node);
		}
		return temp.get(temp.size() - 1); */
		return node;
	}
	
/*	public int getNumberOperators() {	//???????????????
		return countOperator;
	} */
	
	public List<ItemTree> getItemTree() {
		return treeIt;
	}
}
