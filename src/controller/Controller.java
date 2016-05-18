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
import model.MathObject;

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
	
	@FXML 
	private ScrollPane sp1;
	
	@FXML 
	private ScrollPane sp2;

	private Parser p = null;
	private Decision d = null;
	private History h = new History();
	
	@FXML
	private void setStepResult() {
		TreeUtil.checkNoExpandedItem(h.getItemRoot());
		inputFieldFormula.setText(TreeUtil.genStrStep(h.getItemRoot())); 
	}
	
	@FXML
	private void pressButtonLeftFormula() {
		inputFieldFormula.positionCaret(inputFieldFormula.getCaretPosition() - 50);
	}
	
	@FXML
	private void pressButtonRightFormula() {
		inputFieldFormula.positionCaret(inputFieldFormula.getCaretPosition() + 50);
	}
	
	@FXML 
	private void convolution() {		// гавно
		TreeUtil.doConvolution(h.getItemRoot());
		setStepResult();
	}
	
	@FXML
	private void scan() { 				// гавнище
		TreeUtil.doScan(h.getItemRoot());
		setStepResult();
	}

	
	@FXML 
	private void clearPress() {
		inputFieldFormula.setText("");
		outputFieldResult.setText("");
		treeView.setRoot(null);
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
		addText(Constants.LOG + Constants.BRACKETLEFT);
	}
	
	@FXML
	private void lnPress() {
		addText(Constants.LN + Constants.BRACKETLEFT);
	}
	
	@FXML
	private void oneDevXPress() {
		addText(Constants.ONE + Constants.DIVISION);
	}
	
	@FXML
	private void ExpPress() {
		addText(Constants.EKSPANENTA + Constants.POWER);
	}
	
	@FXML
	private void equalsPress() {
		if(inputFieldFormula.getText().length() > 0) {
			try {
				if(p == null) {
					p = new Parser(inputFieldFormula.getText(), h);
				} else {
					p.setStrForParse(inputFieldFormula.getText());
				}
				if(d == null) {
					d = new Decision(h);
				} else {
					d.setRPN(); 
				}
				outputFieldResult.setText(d.getResult() + "");  
				TreeItem<String> root = TreeUtil.setTreeView(h.getItemRoot());
				root.setExpanded(true);
				treeView.setRoot(root); 
			} catch (Exception e) {
				inputFieldFormula.setText(e.getMessage());
//				e.printStackTrace();
			}
		}
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
				case Constants.RADIO_BUTTON_LOG_LN:
					if(cb.isSelected()) {
						log.setDisable(false);
						ln.setDisable(false);
					} else {
						log.setDisable(true);
						ln.setDisable(true);
					}
					break;
				case Constants.RADIO_BUTTON_POWER:
					if(cb.isSelected()) {
						pow.setDisable(false);
						exp.setDisable(false);
					} else {
						pow.setDisable(true);
						exp.setDisable(true);
					}
					break;
				case Constants.RADIO_BUTTON_FACTORIAL:
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
		switch(e.getCode()) {
			case DIGIT1:
				addText(Constants.ONE);
				break;
			case DIGIT2:
				addText(Constants.S_TWO);
				break;
			case DIGIT3:
				addText(Constants.THREE);
				break;
			case DIGIT4:
				addText(Constants.FOUR);
				break;
			case DIGIT5:
				if(e.isShiftDown()) {
					addText(Constants.PERCENT);
				} else {
					addText(Constants.FIVE);
				}
				break;
			case DIGIT6:
				if(e.isShiftDown()) {
					addText(Constants.POWER);
				} else {
					addText(Constants.SIX);
				}
				break;
			case DIGIT7:
				addText(Constants.SEVEN);
				break;
			case DIGIT8:
				if(e.isShiftDown()) {
					addText(Constants.MULTIPLICATION);
				} else {
					addText(Constants.EIGHT);
				}
				break;
			case DIGIT9:
				if(e.isShiftDown()) {
					addText(Constants.BRACKETLEFT);
				} else {
					addText(Constants.NINE);
				}
				break;
			case DIGIT0:
				if(e.isShiftDown()) {
					addText(Constants.BRACKETRIGHT);
				} else {
					addText(Constants.ZERO);
				}
				break;			
			case MINUS:
				addText(Constants.MINUS);
				break;
			case DIVIDE:
				addText(Constants.DIVISION);
				break;
			case EQUALS:
				if(e.isShiftDown()) {
					addText(Constants.PLUS);
				}
				break;
			case ADD:
				addText(Constants.PLUS);
				break;
			case BACK_SPACE:
				delete();
				break;
			case ENTER:
				equalsPress();
				break;
			case SUBTRACT:
				addText(Constants.MINUS);
				break;
			case MULTIPLY:
				addText(Constants.MULTIPLICATION);
				break;
			case DECIMAL:
				addText(Constants.POINT + "");
		}
//		System.out.println(e); 
	}
}
