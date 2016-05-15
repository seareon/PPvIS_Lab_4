package model;

import java.util.ArrayList;
import java.util.List;

public class History {		
	private List<List<String>> stepSolution = new ArrayList<>();
	private List<ItemTree> treeIt = new ArrayList<>();
	private ItemTree node; 
	
	public void setSolution(String ... op) {
	}
	
	public void getStepsSolution() {
		
	}
	
	public void setTreeItem(String str) {
		ItemTree it = new ItemTree(str);
		treeIt.add(it);
		node = it;
		int countOperand = 0;
		if(BinaryOperator.isBinaryOperator(str)) {
			countOperand = 2;
		} else if(UnaryOperator.isUnaryOperator(str)) {
			countOperand = 1;
		}
		for(int indexTree = treeIt.size() - 2; indexTree >= 0 && countOperand > 0; indexTree--) {
			if(!treeIt.get(indexTree).isHaveInputArc()) {
				node.setArcOutput(treeIt.get(indexTree)); 
				treeIt.get(indexTree).setInput();
				if(countOperand--  == 1) {
					break;
				}
			}
		}
	}
	
	public ItemTree getItemTree() {
		return node;
	}
}
