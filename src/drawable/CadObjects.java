package drawable;


import executable.Commands;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public abstract class CadObjects extends Commands  {
	
	public int id=0;
	protected boolean selected = false;
	static int currentStep = 0;
	static KeyEvent currentKeyEvent;
	static MouseEvent currentMouseEvent;
		
	public abstract void cadDraw(GraphicsContext gc);
	public abstract boolean intersects(Rectangle rectangle);
	public void unselect() {selected = false;}
	public void select() {selected = true;}
	public boolean isSelected(){return selected;}
	
}