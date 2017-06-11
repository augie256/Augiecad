import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Settings implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String filename = "Settings.acs";
		
	public double PROGRAM_WIDTH = 1200;
	public double PROGRAM_HEIGHT = 800;
	public double HISTORY_BOX_HEIGHT = 50;
		
	
	public static Settings loadFromFile(){
		try {
			FileInputStream filein = new FileInputStream(filename);
			ObjectInputStream objectin = new ObjectInputStream(filein);
			Settings s = (Settings) objectin.readObject();
			objectin.close();
			System.out.println("The Object was succesfully loaded from a file");
			return s;
			} catch (Exception ex) {
			return new Settings();
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
}
