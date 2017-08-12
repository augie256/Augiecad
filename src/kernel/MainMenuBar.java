package kernel;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

import javafx.scene.control.Menu;

public class MainMenuBar extends MenuBar {
 	
	Menu fileMenu;
	HandleEvents events;
	Stage stage;
	private static MainMenuBar instance;
	
	private MainMenuBar(){
		buildMenu();		
	}
	
	public static MainMenuBar getInstance(){
		if(instance == null) instance = new MainMenuBar();
		return instance;

	}


	private void buildMenu() {
		fileMenu = new Menu("File");
		MenuItem open = new MenuItem("Open");
		MenuItem save = new MenuItem("Save");
		fileMenu.getItems().addAll(open, save);
	    open.setOnAction(a -> open());
	    save.setOnAction(a -> save());
		this.getMenus().addAll(fileMenu);
	}

	private Object open() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Drawing File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Drawing files", "*.drw"),
		         new ExtensionFilter("All Files", "*.*"));
		//File f = fileChooser.showOpenDialog(stage);
		return null;
	}
	
	private Object save() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
