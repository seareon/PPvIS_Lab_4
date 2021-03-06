package model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

public class ItemTree {
	private TreeItem<String> treeIt;
	private boolean input = false;
	private boolean visited = false;		// ���� ���������� ������, �����??????????
	private List<ItemTree> output = new ArrayList<>();
	MathObject mo;
	
	public ItemTree(MathObject mo) {
		treeIt = new TreeItem<>(mo.getString());
		if(mo instanceof BinaryOperator || mo instanceof UnaryOperator) {
			treeIt.setExpanded(true); 
		}
		this.mo = mo;
	}
	
	public MathObject getMathObject() {
		return mo;
	}
	
	public void setMathObgect(MathObject mo) {
		this.mo = mo;
	}
	
	public TreeItem<String> getItem() {
		return treeIt;
	}
	
	public void setInput() {
		input = true;
	}
	
	public boolean isHaveInputArc() {
		return input;
	}
	
	public void setArcOutput(ItemTree it, int n) {
		output.add(n, it);
	}
	
	public void deleteOutput(int n) {
		output.remove(n);
	}
	
	public int countOutput() {
		return output.size();
	}
	
	public ItemTree getOutput(int i) {	
		if(output.size() > i) {
			return output.get(i);
		}
		return null;
	}
	
	public void clear() {
		output.clear();
		input = false;
	}
}
