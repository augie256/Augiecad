package drawing;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.canvas.GraphicsContext;
import kernel.CadMain;

public class CadDrawing {

	public static CadDrawing CURRENT_DRAWING = null;
	private double drawingScale = 1;
	public static double scale = 1;
	public List<CadObjects> objects;
	public List<CadObjects> selected;
	private GraphicsContext gc = CadMain.gc;
	public static RCanvas canvas = CadMain.drawArea;
		
	public CadDrawing(double Accuracy) {
		objects = FXCollections.observableList(new ArrayList<CadObjects>());
		CURRENT_DRAWING = this;
	}
	
	public void add(CadObjects obj){
		objects.add(obj);
		obj.id = objects.indexOf(obj);
		redrawAll();
	}
	
	public void redrawAll(){
		if (canvas!=null){canvas.clear();}
		if (gc!=null){for(int i=0; i < objects.size(); i++){			
			objects.get(i).cadDraw(gc);
			}
		}
		
	}

	public void setGraphicsContext(GraphicsContext gc) {
		this.gc = gc;
		canvas = (RCanvas) gc.getCanvas();
	}
	
	public void setScale(double d) {
		drawingScale = d;	
	}

	public double getScale() {
		return drawingScale;	
	}
	
	public CadObjects AttemptToSelect() {
		return null;		
	}
	
		
}