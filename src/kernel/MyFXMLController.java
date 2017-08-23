package kernel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MyFXMLController {

	@FXML public TextField acc;
	@FXML public Button cancel;
	
	
	public MyFXMLController(){
		
	}
	
	public void CloseButtonPressed(ActionEvent a){
		Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.close();
	}
	
	public TextField getText(){
		
		return acc;
	}
	
}
