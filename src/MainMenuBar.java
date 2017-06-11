import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;

public class MainMenuBar extends MenuBar {
 	
	Menu fileMenu;
	
	MainMenuBar(){
		fileMenu = new Menu("File");
		this.getMenus().add(fileMenu);
		
	}
}
