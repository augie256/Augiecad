package kernel;
import java.util.ArrayList;

import drawing.CadDrawing;
import drawing.CadLine;
import drawing.Commands;
import drawing.RCanvas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HandleEvents{
		
	// all other drawing package classes will use HandleEvents to access all GUI components
	public static final String DEFAULT_PROMPT = "Enter a Command";
	private static final String VALID_TO_GO_IN_TEXTBOX = "abcdefghijklmnopqrstuvwxyzABCDEFGHILMNOPQRSTUVWXYZ1234567890,.@";
	public static MainMenuBar menu;
	public static Stage stage;
	public static GraphicsContext gc;
	public static RCanvas canvas; 
	public static TextField textBox;
	public static ListView<String> historyBox;
		
	private ObservableList<String> historyStrings;	 
	private CadDrawing drawing;
	private int currentCommand = 0; //0 indicates waiting for command
	 	 
	HandleEvents(RCanvas dA, TextField tB, ListView<String> hB, MainMenuBar m, Stage s){
		HandleEvents.menu = m;
		HandleEvents.stage = s;
		HandleEvents.canvas = dA;
		HandleEvents.gc = dA.getGraphicsContext2D();		
		HandleEvents.textBox = tB;
		HandleEvents.textBox.setPromptText(HandleEvents.DEFAULT_PROMPT);
		HandleEvents.historyBox = hB;
		historyStrings = FXCollections.observableList(new ArrayList<String>());
		drawing = CadDrawing.CURRENT_DRAWING;
		drawing.setGraphicsContext(gc);		
	 }

	public void handle(KeyEvent k){
		if(iShouldHandle(k)){
		   switch (k.getCode()) {
	       case ENTER: enterPressed(k); break;
	       case ESCAPE: abort(k); break;
	       case UP: zoom(true); break;
	       case DOWN: zoom(false); break;
	       case BACK_SPACE: backspace(); 
	       default: textBoxEntry(k);}
		 }
	}
	
	private void backspace() {
		if(textBox.getLength() > 0){
		textBox.deleteText(textBox.getLength() -1 , (textBox.getLength()));
		}
	}

	public void handle(MouseEvent m) {
		if(iShouldHandle(m)){
	if (m.getButton() == MouseButton.PRIMARY) leftClick(m);
	if (m.getButton() == MouseButton.SECONDARY) rightClick(m);
	if (m.getEventType()== MouseEvent.MOUSE_MOVED) mouseMoved();
		}
	
	}
	
	public void handle(WindowEvent w, Settings s) {
		Settings.saveToFile(s);
	}
	
	public void handle(ActionEvent k){
		//TODO menu set up
	}
	
	private void enterPressed(InputEvent e) {
		if(currentCommand>0){
			attemptCommandInvoke(e,textBox.getText());			
			historyStrings.add(textBox.getText());
			historyBox.setItems(reversed(historyStrings));
			textBox.clear();
		}else{
			if(GetCommand(e)) attemptCommandInvoke(e,textBox.getText());
		}
		
	}
	
	private void textBoxEntry(KeyEvent k) {
		if(k.isShiftDown() && (k.getCode() == KeyCode.DIGIT2)){
			textBox.appendText("@");
			return;
		}
		if(VALID_TO_GO_IN_TEXTBOX.indexOf(k.getText())>-1){
		textBox.appendText(k.getText());
		}
	}
	
	private boolean GetCommand(InputEvent e) {
		currentCommand = Commands.LINE;
		return true;
	}
	
	private void attemptCommandInvoke(InputEvent e, String input){
	switch(currentCommand){
		case Commands.LINE:{
			if(CadLine.validInput(input)){
				Commands.setInput(textBox.getText());
				again(Commands.invoke(Commands.LINE, e)); 
				break;
			}
			
			
			break;
		}	
		case Commands.CIRCLE:{Commands.setInput(textBox.getText());	again(Commands.invoke(Commands.CIRCLE, e)); break;}
		case Commands.TRIM:{Commands.setInput(textBox.getText()); again(Commands.invoke(Commands.TRIM, e)); break;}
		default:{Commands.setInput(textBox.getText() + " ??");
     	}
	  }
	}
		
	
	private void zoom(boolean isUp) {
		if(isUp){
			CadDrawing.CURRENT_DRAWING.setScale(CadDrawing.CURRENT_DRAWING.getScale() * .8);
			CadDrawing.CURRENT_DRAWING.redrawAll();
		}else{
			CadDrawing.CURRENT_DRAWING.setScale(CadDrawing.CURRENT_DRAWING.getScale() * 1.25);
			CadDrawing.CURRENT_DRAWING.redrawAll();		
		}
	}

	private void abort(KeyEvent k) {
		Commands.abort();
		
	}

	private void again(boolean more) {
		if (!more) currentCommand = 0;
		
	}

	private boolean iShouldHandle(InputEvent event) {
		if(event.getEventType().toString() == "KEY_PRESSED"){return Commands.isKeyOk();
		}else if(event.getEventType().toString() == "MOUSE_CLICKED"){return Commands.isMouseOk();
		}else return false;
	}
	
	private void leftClick(MouseEvent m) {
		if(currentCommand == 0) {CadDrawing.CURRENT_DRAWING.AttemptToSelect();}
		else{
			textBox.appendText(m.getX() + "," + m.getY());
			Commands.setInput(textBox.getText());again(Commands.invoke(Commands.LINE, m));
			historyStrings.add(textBox.getText());
			historyBox.setItems(reversed(historyStrings));
			textBox.clear();
		}	
	}
	
	private void rightClick(MouseEvent m) {
		enterPressed(m);		
	}
	
	private void mouseMoved() {
			
	}
	
	private ObservableList<String> reversed(ObservableList<String> o) {
		ObservableList<String> reversed = FXCollections.observableList(new ArrayList<String>());
		for (int i=0; i<o.size(); i++){	if (i<100) reversed.add(o.get((o.size()-i)-1));}
		return reversed;
	}
}
