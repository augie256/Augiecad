package drawing;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import kernel.HandleEvents;
import kernel.Settings;

public class CadDrawing {

	public static CadDrawing CURRENT_DRAWING = null;
	public double DRAWING_SCALE = 1;
	public static double scale = 1;
	public ObservableList<CadObjects> o;
	private GraphicsContext gc;
	public static RCanvas canvas;
		
	public CadDrawing(Settings s, double Accuracy) {
		o = FXCollections.observableList(new ArrayList<CadObjects>());
		CURRENT_DRAWING = this;
	}

	static public CadDrawing loadFromFile(String filepath){
		try {
			FileInputStream filein = new FileInputStream(filepath);
			ObjectInputStream objectin = new ObjectInputStream(filein);
			CadDrawing c = (CadDrawing) objectin.readObject();
			objectin.close();
			return c;
			} catch (Exception ex) {
			return CURRENT_DRAWING;
			}		
	}
	
	public void add(CadObjects obj){
		o.add(obj);
	}
	
	public void redrawAll(){
		if (canvas!=null){canvas.clear();}
		if (gc!=null){for(int i=0; i < o.size(); i++){o.get(i).cadDraw(gc);}
	}
		
}

	public void setGraphicsContext(GraphicsContext gc) {
		this.gc = gc;		
	}
	
	public void setScale(double d) {
		this.DRAWING_SCALE = d;	
		}
		
			
	
		
}