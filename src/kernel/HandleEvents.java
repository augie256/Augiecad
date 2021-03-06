package kernel;
import java.util.ArrayList;

import drawable.CadCircle;
import drawable.CadLine;
import executable.Commands;
import executable.Erase;
import executable.Trim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

public class HandleEvents{
		
	
	public static final String DEFAULT_PROMPT = "Enter a Command";
	private static final String VALID_TO_GO_IN_TEXTBOX = "abcdefghijklmnopqrstuvwxyzABCDEFGHILMNOPQRSTUVWXYZ1234567890,.@";
	private static HandleEvents instance = null;
	
	private ObservableList<String> historyStrings;	
	private static String echo = "";
	
	
	private HandleEvents(){
		CadMain.textBox.setPromptText(HandleEvents.DEFAULT_PROMPT);
		historyStrings = FXCollections.observableList(new ArrayList<String>());						
	 }
	
	public static HandleEvents getInstance(){
		if(instance == null) instance = new HandleEvents();
		return instance;

	}

	public static void setEcho(String echo){
		HandleEvents.echo = echo;
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
	
	public void handle(MouseEvent m) {
		if(iShouldHandle(m)){
	if (m.getButton() == MouseButton.PRIMARY) leftClick(m);
	if (m.getButton() == MouseButton.SECONDARY) rightClick(m);
	if (m.getEventType()== MouseEvent.MOUSE_MOVED) mouseMoved(m);
		}
	
	}
	
	public void handle(WindowEvent w, Settings s) {
		Settings.saveToFile(s);
	}
	
	public void handle(ActionEvent k){
		//TODO menu set up
	}
	
	private void enterPressed(InputEvent e) {
		if(Commands.currentCommand>0){
			attemptCommandInvoke(e,CadMain.textBox.getText());
			}else{
				if(GetCommand(e)) attemptCommandInvoke(e,CadMain.textBox.getText());
		}
		historyStrings.add(CadMain.textBox.getText()+echo);
		CadMain.historyBox.setItems(reversed(historyStrings));
		CadMain.textBox.clear();
		echo = "";
	}
	
	private void abort(KeyEvent k) {
		Commands.abortAll();
		
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
	
	private void backspace() {
		if(CadMain.textBox.getLength() > 0){
			CadMain.textBox.deleteText(CadMain.textBox.getLength() -1 , (CadMain.textBox.getLength()));
		}
	}
	
	private void textBoxEntry(KeyEvent k) {
		if(k.isShiftDown() && (k.getCode() == KeyCode.DIGIT2)){
			CadMain.textBox.appendText("@");
			return;
		}
		if(VALID_TO_GO_IN_TEXTBOX.indexOf(k.getText())>-1){
			CadMain.textBox.appendText(k.getText());
		}
	}

	private boolean iShouldHandle(InputEvent event) {
		if(event.getEventType().toString() == "KEY_PRESSED"){return true;
		}else if(event.getEventType().toString() == "MOUSE_CLICKED"){return true;
		}else if(event.getEventType().toString() == "MOUSE_MOVED"){return true;
		}else return false;
	}
	
	private void leftClick(MouseEvent m) {
		if(Commands.currentCommand == 0) {CadDrawing.CURRENT_DRAWING.AttemptToSelect(m);}
		else{
			CadMain.textBox.appendText(m.getX() + "," + m.getY());
			Commands.invoke(Commands.currentCommand, m);
			historyStrings.add(CadMain.textBox.getText());
			CadMain.historyBox.setItems(reversed(historyStrings));
			CadMain.textBox.clear();
		}	
	}
	
	private void rightClick(MouseEvent m) {
		enterPressed(m);		
	}
	
	private void mouseMoved(MouseEvent m) {
		CadDrawing.CURRENT_DRAWING.redrawAll();
		CadMain.gc.setStroke(Color.GRAY);
		CadMain.gc.strokeRect(m.getX()-10, m.getY()-10, 20, 20);		
	}
	
	private void attemptCommandInvoke(InputEvent e, String input){
	switch(Commands.currentCommand){
		case Commands.LINE:{if(CadLine.validInput(input)){Commands.invoke(Commands.LINE, e);}break;}
		case Commands.CIRCLE:{if(CadCircle.validInput(input)){Commands.invoke(Commands.CIRCLE, e);}break;}
		case Commands.TRIM:{if(Trim.validInput(input)){Commands.invoke(Commands.TRIM, e);}break;}
		case Commands.ERASE:{Commands.invoke(Commands.ERASE, e);}break;
		default: echo = "??";
	  }
	}
	
	private boolean GetCommand(InputEvent e) {
		switch(CadMain.textBox.getText().toUpperCase()){
		case "L": Commands.currentCommand = Commands.LINE;return true;
		case "C": Commands.currentCommand = Commands.CIRCLE;return true;
		case "E": Commands.currentCommand = Commands.ERASE;return true;
		default: return false;}
	}
	
	private ObservableList<String> reversed(ObservableList<String> o) {
		ObservableList<String> reversed = FXCollections.observableList(new ArrayList<String>());
		for (int i=0; i<o.size(); i++){	if (i<100) reversed.add(o.get((o.size()-i)-1));}
		return reversed;
	}
		
}
