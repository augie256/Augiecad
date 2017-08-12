package drawing;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class CadObjects extends Commands  {
	
	public int id=0;
	static int currentStep = 0;
	static KeyEvent currentKeyEvent;
	static MouseEvent currentMouseEvent;
		
	public abstract void cadDraw(GraphicsContext gc);
	//public abstract void intersects();
	
	
}