package kernel;
import java.util.ArrayList;
import java.util.List;

import drawable.CadObjects;
import javafx.collections.FXCollections;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class CadDrawing {

	public static CadDrawing CURRENT_DRAWING = null;
	private double drawingScale = 1;
	public static double scale = 1;
	public List<CadObjects> objects;
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
	
	public void remove(CadObjects obj){
		System.out.println(objects.indexOf(obj));
		objects.remove(objects.indexOf(obj));
		
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
	
	public void AttemptToSelect(MouseEvent e) {
		for(CadObjects o:objects){if(o.intersects(new Rectangle(e.getX()-10,e.getY()-10,20,20))){o.select();}}
		redrawAll();		
	}
	
	public void esc(){
		for(CadObjects o:objects){o.unselect();}
		redrawAll();
	}	
	
}