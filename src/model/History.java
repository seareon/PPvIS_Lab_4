package model;

import java.util.ArrayList;
import java.util.List;

public class History {		
	private List<MathObject> rpn = new ArrayList<>();
	private List<ItemTree> treeIt = new ArrayList<>();
	private ItemTree node; 
	
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
	}
	
	public ItemTree setItem(MathObject mo, List<ItemTree> temp) {
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
		return node;
	}
	
	public ItemTree getItemCloneRoot() {		// нужно ли делать копию??
		ItemTree node = null;
		List<ItemTree> temp = new ArrayList<>();
		for(MathObject mo : rpn) {
			node = setItem(mo, temp);
		}
		return node;
	}
	
	public List<ItemTree> getItemTree() {
		return treeIt;
	}
}
