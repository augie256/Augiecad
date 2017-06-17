package kernel;
import java.util.ArrayList;

import drawing.CadDrawing;
import drawing.CadObjects;
import drawing.Commands;
import drawing.RCanvas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HandleEvents{
	
	 private int currentCommand = 0; //0 indicates waiting for command
	 Stage stage;
	 GraphicsContext gc;
	 public static RCanvas canvas; 
	 public static TextField textBox;
	 ListView<String> historyBox;
	 ObservableList<String> historyStrings;	 
	 public CadDrawing drawing;
	 public static final String DEFAULT_PROMPT = "Enter a Command";
	 	 
	HandleEvents(RCanvas drawArea, TextField textBox, ListView<String> historyBox, Stage s){
		 stage = s;
		 canvas = drawArea;
		 CadDrawing.canvas = drawArea;
		 gc = drawArea.getGraphicsContext2D();		
		 HandleEvents.textBox = textBox;
		 HandleEvents.textBox.setPromptText(HandleEvents.DEFAULT_PROMPT);
		 this.historyBox = historyBox;
		 historyStrings = FXCollections.observableList(new ArrayList<String>());
		 drawing = CadDrawing.CURRENT_DRAWING;
		 drawing.setGraphicsContext(gc);
		 CadObjects.gc = gc;
	 }

	public void handle(KeyEvent k){
		if(iShouldHandle(k)){
		   switch (k.getCode()) {
	       case ENTER: enterPressed(k); break;
	       case ESCAPE: abort(k); break;
	       case UP: zoom(true); break;
	       case DOWN: zoom(false); break;
	       default: textBoxEntry(k);}
		 }
	}

	private void zoom(boolean isUp) {
		if(isUp){
			CadDrawing.CURRENT_DRAWING.DRAWING_SCALE = CadDrawing.CURRENT_DRAWING.DRAWING_SCALE * .5;
			CadDrawing.CURRENT_DRAWING.redrawAll();
		}else{
			CadDrawing.CURRENT_DRAWING.DRAWING_SCALE = CadDrawing.CURRENT_DRAWING.DRAWING_SCALE * 2;
			CadDrawing.CURRENT_DRAWING.redrawAll();
		}
		
	}

	private void textBoxEntry(KeyEvent k) {
		textBox.appendText(k.getText());
	
	}

	private void abort(KeyEvent k) {
		Commands.abort();
		
	}

	private void enterPressed(InputEvent e) {	
		switch (currentCommand) {
	       case Commands.LINE: Commands.input(textBox.getText()); again(Commands.invoke(Commands.LINE, e)); break;
	       case Commands.CIRCLE: Commands.input(textBox.getText()); again(Commands.invoke(Commands.CIRCLE, e)); break;
	       case Commands.TRIM: Commands.input(textBox.getText()); again(Commands.invoke(Commands.TRIM, e)); break;
	       default: GetCommand(e);}
		if (Commands.textInput == "??") textBox.appendText(" ??");
		historyStrings.add(textBox.getText());
		historyBox.setItems(reversed(historyStrings));
		textBox.clear();
		
	}

	private void again(boolean more) {
		if (!more) currentCommand = 0;
		
	}

	private void GetCommand(InputEvent e) {
		currentCommand = Commands.LINE;
		Commands.input(textBox.getText());
		again(Commands.invoke(Commands.LINE, e));
	}

	private boolean iShouldHandle(InputEvent event) {
		if(event.getEventType().toString() == "KEY_PRESSED"){return Commands.isKeyOk();
		}else if(event.getEventType().toString() == "MOUSE_CLICKED"){return Commands.isMouseOk();
		}else return false;
	}

	public void handle(MouseEvent m) {
		if(iShouldHandle(m)){
	if (m.getButton() == MouseButton.PRIMARY) leftClick(m);
	if (m.getButton() == MouseButton.SECONDARY) rightClick(m);
	if (m.getEventType()== MouseEvent.MOUSE_MOVED) mouseMoved();
		}
	
	}
	
	private void leftClick(MouseEvent m) {
		switch (currentCommand) {
	       case Commands.LINE: Commands.input(textBox.getText()); again(Commands.invoke(Commands.LINE, m)); break;
	       case Commands.CIRCLE: Commands.input(textBox.getText()); again(Commands.invoke(Commands.CIRCLE, m)); break;
	       case Commands.TRIM: Commands.input(textBox.getText()); again(Commands.invoke(Commands.TRIM, m)); break;
	       default: Commands.input("??");}
		
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

	public void handle(WindowEvent w, Settings s) {
		Settings.saveToFile(s);
	}

}
