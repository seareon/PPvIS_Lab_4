package model;

import java.util.ArrayList;
import java.util.List;

public class History {		
	private List<MathObject> rpn = new ArrayList<>();
	private List<ItemTree> treeIt = new ArrayList<>();
	private ItemTree node; 
	
	public List<MathObject> getRPN() {
		return rpn;
	}
	
	public void clearRPN() {
		rpn.clear();
	}
	
	public void setTreeItem(MathObject mo) {
		rpn.add(mo);
		node = new ItemTree(mo);
		treeIt.add(node);
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
		}
	}
	
	public ItemTree getItemRoot() {
		return node;
	}
}
