package kernel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Settings implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String filename = "Settings.acs";
		
	public double PROGRAM_WIDTH = 1200;
	public double PROGRAM_HEIGHT = 800;
	public double HISTORY_BOX_HEIGHT = 50;
	public double DRAWING_ACCURACY = 0.03125;
	private static Settings instance = null;
	
	private Settings(){		
	}
	
	public static Settings getInstance(){
		if(loadFromFile()){
			return instance;
		}else{
			instance = new Settings();
			return instance;
		}
	} 
	
	private static boolean loadFromFile(){
		try {
				FileInputStream filein = new FileInputStream(filename);
				ObjectInputStream objectin = new ObjectInputStream(filein);
				Settings s = (Settings) objectin.readObject();
				objectin.close();
				instance = s;				
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}		
	}
	
	public static void saveToFile(Object o){
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(o);
			objectOut.close();
			System.out.println("The Object was succesfully written to a file");
			} catch (Exception ex) {
			ex.printStackTrace();
			}
		
	}
	
	public void setSaveSize(Scene scene) {
		PROGRAM_WIDTH = scene.getWidth();
		PROGRAM_HEIGHT = scene.getHeight(); 		
	}
	
	public void showSettingBox(){
		MyFXMLController xml;
		Parent root;
		FXMLLoader loader = new FXMLLoader(instance.getClass().getResource("SettingsDialogBox.fxml"));
		try {
			root = loader.load();
			xml = loader.getController();
		} catch (IOException e) {			
			e.printStackTrace();
			return;
		}
		Scene pop = new Scene(root);
		Stage dialog = new Stage();
		dialog.setScene(pop);
		dialog.initOwner(CadMain.stage);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.setResizable(false);
		dialog.setTitle("Settings");
		dialog.show();
		xml.getText().setText(Double.toString(DRAWING_ACCURACY));
		}
	
}
