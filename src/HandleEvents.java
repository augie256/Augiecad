import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HandleEvents{
	
	 int progState = 0;
	 final int WAITING_FOR_COMMAND = 1;
	 final int COMMAND_IN_PROCESS = 2;
	 RCanvas r;
	 TextField textBox;
	 ListView<String> historyBox;
	 double tmpX;
	 double tmpY;
	 ObservableList<String> o;
	 
	 HandleEvents(RCanvas r, TextField textBox, ListView<String> historyBox){
		 this.r = r;
		 this.textBox = textBox;
		 this.historyBox = historyBox;
		 progState = WAITING_FOR_COMMAND;
		 o = FXCollections.observableList(new ArrayList<String>());
	 }

	 public void handle(KeyEvent k){
		 if (k.getCode() != KeyCode.ENTER){
		 textBox.appendText(k.getText());
		 
	 }else{
			 o.add(textBox.getText());
			 historyBox.setItems(reversed(o));
			 textBox.clear();
		 }
	 }


	public void handle(MouseEvent m) {
	GraphicsContext c = r.getGraphicsContext2D();
	c.setStroke(Color.WHITE);
	
	if(progState == WAITING_FOR_COMMAND){
	tmpX = m.getX();
	tmpY = m.getY();
	progState = COMMAND_IN_PROCESS;
	}else{
	c.strokeLine(tmpX, tmpY, m.getX(), m.getY());
	progState = WAITING_FOR_COMMAND;
	}
	
	
	}

	public void setItems(RCanvas r, TextField textBox, ListView<String> historyBox) {
		this.r = r;
		this.textBox = textBox;
		this.historyBox = historyBox;
	}
	 
	private ObservableList<String> reversed(ObservableList<String> o) {
		ObservableList<String> reversed = FXCollections.observableList(new ArrayList<String>());
		System.out.println(o.size());
		for (int i=0; i<o.size(); i++){
			if (i<100) reversed.add(o.get((o.size()-i)-1));
		}
		return reversed;
	}

	public void handle(ActionEvent a) {
		// TODO Auto-generated method stub
		
	}

	public void handle(WindowEvent w, Settings s) {
		Settings.saveToFile(s);
	}

	public void setNewSize(Settings set,Stage s) {
		set.PROGRAM_WIDTH = s.getWidth();
		set.PROGRAM_HEIGHT = s.getHeight();
	}
	
}
