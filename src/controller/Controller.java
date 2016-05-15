package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import model.Constants;
import model.History;
import model.ItemTree;

public class Controller {
	@FXML
    private TextField inputFieldFormula;
	
	@FXML
    private Label outputFieldResult;
	
	@FXML
    private TreeView<String> treeView;  
	
	@FXML
    private Button factor;
	
	@FXML
    private Button exp;
	
	@FXML
    private Button log;
	
	@FXML
    private Button ln;
	
	@FXML
    private Button pow;
	
	private Parser p = null;
	
	private Decision d = null;
	
	private History h = new History();
	
	@FXML
    private void initialize() { // ����� ��?!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
	}
	
	public void setStepResult(String str) {
		inputFieldFormula.setText(str); 
	}
	
/*	public void setResult(String str) {
		if(str != null) {
			outputFieldResult.setText(str);
		}
		else {
			outputFieldResult.setText("");
		}
	} */
	
	public TreeItem<String> setTreeView(ItemTree it) {
		for(int indexOutputList = 0; indexOutputList < it.countOutput(); indexOutputList++) {
			it.getItem().getChildren().add(setTreeView(it.getOutput(indexOutputList))); 
		}
		return it.getItem(); 
	}
	
	@FXML 
	private void clearPress() {
		inputFieldFormula.setText("");
		outputFieldResult.setText("");
//		treeView.
	}

	@FXML 
	private void delete() {
		String str = inputFieldFormula.getText();
		if(!str.equals("")) {
			String substr = str.substring(0, str.length() - 1);
			inputFieldFormula.setText(substr);  
			inputFieldFormula.positionCaret(inputFieldFormula.getText().length());
		}
	} 
	
	@FXML
	private void logPress() {
		addText("log(");
	}
	
	@FXML
	private void lnPress() {
		addText("ln(");
	}
	
	@FXML
	private void oneDevXPress() {
		addText("1/");
	}
	
	@FXML
	private void ExpPress() {
		addText("e^");
	}
	
	@FXML
	private void equelsPress() {
		try {
			if(p == null) {
				p = new Parser(inputFieldFormula.getText());
			} else {
				p.setStrForParse(inputFieldFormula.getText());
			}
			if(d == null) {
				d = new Decision(p.getRPN(), h);
			} else {
				d.setRPN(p.getRPN()); 
			}
			outputFieldResult.setText(d.getResult() + "");  
			TreeItem<String> root = setTreeView(h.getItemTree());
			root.setExpanded(true);
			treeView.setRoot(root); 
		} catch (Exception e) {
			inputFieldFormula.setText(e.getMessage());
//			e.printStackTrace();
		}
	}

	@FXML
	private void pressButtonLeft() {
		inputFieldFormula.positionCaret(inputFieldFormula.getCaretPosition() - 50);
	}
	
	@FXML
	private void pressButtonRight() {
		inputFieldFormula.positionCaret(inputFieldFormula.getCaretPosition() + 50);
	}
	
	@FXML 
	private void pressButton(Event event) {
		if (event.getSource() instanceof Button){
			addText(((Button) event.getSource()).getText());
		} 
	}
	
	private void addText(String str) {
		inputFieldFormula.setText(inputFieldFormula.getText() + str);
		inputFieldFormula.positionCaret(inputFieldFormula.getText().length());
	}
	
	@FXML 
	private void pressCheckBox(Event event) {
		if(event.getSource() instanceof CheckBox) {
			CheckBox cb = (CheckBox)event.getSource();
			switch(cb.getText()) {
				case "log/ln":
					if(cb.isSelected()) {
						log.setDisable(false);
						ln.setDisable(false);
					} else {
						log.setDisable(true);
						ln.setDisable(true);
					}
					break;
				case "power":
					if(cb.isSelected()) {
						pow.setDisable(false);
						exp.setDisable(false);
					} else {
						pow.setDisable(true);
						exp.setDisable(true);
					}
					break;
				case "factorial":
					if(cb.isSelected()) {
						factor.setDisable(false);
					} else {
						factor.setDisable(true);
					}
					break;
			}
		}
	}
	
	@FXML 
	private void inputFormula(KeyEvent e) {  
		switch(e.getText()) {
			case "1":
				if(e.isShiftDown()) {
					addText(Constants.FACTORIAL);
				}
				else {
					addText("1");
				}
				break;
			case "2":
				addText("2");
				break;
			case "3":
				addText("3");
				break;
			case "4":
				addText("4");
				break;
			case "5":
				if(e.isShiftDown()) {
					addText(Constants.PERCENT);
				} else {
					addText("5");
				}
				break;
			case "6":
				if(e.isShiftDown()) {
					addText(Constants.POWER);
				} else {
					addText("6");
				}
				break;
			case "7":
				addText("7");
				break;
			case "8":
				if(e.isShiftDown()) {
					addText(Constants.POWER);
				} else {
					addText("8");
				}
				break;
			case "9":
				if(e.isShiftDown()) {
					addText(Constants.BRACKETLEFT);
				} else {
					addText("9");
				}
				break;
			case "0":
				if(e.isShiftDown()) {
					addText(Constants.BRACKETRIGHT);
				} else {
					addText("0");
				}
				break;
			case Constants.MINUS:
				addText(Constants.MINUS);
				break;
			case Constants.DIVISION:
				addText(Constants.DIVISION);
				break;
			case "=":
				if(e.isShiftDown()) {
					addText(Constants.PLUS);
				} else {
					addText("=");
				}
		}
//		System.out.println(e.toString());
	}
}