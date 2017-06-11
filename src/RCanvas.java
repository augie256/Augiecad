import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class RCanvas extends Canvas {
	Cursor tee;
	
	public RCanvas() {
	widthProperty().addListener(evt -> draw());
	heightProperty().addListener(evt -> draw());
	setManaged(false);
	tee = Cursor.CROSSHAIR;
	setCursor(tee);
	}
	
	private void draw() {
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