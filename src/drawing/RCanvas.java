package drawing;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RCanvas extends Canvas {
	Cursor tee;
	
	public RCanvas() {
	widthProperty().addListener(evt -> redrawAll());
	heightProperty().addListener(evt -> redrawAll());
	setManaged(false);
	tee = Cursor.CROSSHAIR;
	setCursor(tee);
	}
	
	
	
	public void redrawAll() {
		clear();
		if (CadDrawing.CURRENT_DRAWING != null){
			CadDrawing.CURRENT_DRAWING.redrawAll();
		}
	}


	public void draw() {

	}
	
	public void clear(){
		double width = getWidth();
		double height = getHeight();
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);
		gc.setStroke(Color.BLACK);
		gc.fillRect(0, 0, width, height);
	}
	
	@Override
	public boolean isResizable() {
	return true;
	}

	
	
}