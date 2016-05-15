package model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

public class ItemTree {
	private TreeItem<String> treeIt;
//	private TreeItem<String> input = null;		// boolean??????????????????????????
	private boolean input = false;
	private List<ItemTree> output = new ArrayList<>();
	
	public ItemTree(String str) {
		treeIt = new TreeItem<>(str);
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
	
	public void setArcOutput(ItemTree it) {
		output.add(0, it);
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
}
