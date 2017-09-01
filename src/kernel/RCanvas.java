package kernel;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RCanvas extends Canvas {
	Cursor tee = Cursor.CROSSHAIR;
	
	public RCanvas() {
		
	widthProperty().addListener(evt -> CadDrawing.CURRENT_DRAWING.redrawAll());
	heightProperty().addListener(evt -> CadDrawing.CURRENT_DRAWING.redrawAll());
	setManaged(false);
	setCursor(tee);
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