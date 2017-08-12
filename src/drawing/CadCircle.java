package drawing;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class CadCircle extends CadObjects{

	protected static Point2D p1 = null;
	protected static Point2D p2 = null;
	
	protected Circle circle;
	protected Line radius;
	protected Point2D center;
	protected Point2D upLeftBounds;
	
// constructor method(s)----------------------------------------------------------------------------------------------		
	public CadCircle(Point2D center, Point2D radius) {
	//todo
	}
	
	public static boolean invoke(InputEvent e) {
		CadDrawing.CURRENT_DRAWING.add(new CadCircle(null, null));
		return false;
	}	


	@Override
	public void cadDraw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.strokeOval(100, 100, 500, 500);
	}

	
	
}
