package drawing;

import javafx.scene.canvas.GraphicsContext;

public abstract class CadObjects extends Commands  {
	
	public int id=0;
	public static GraphicsContext gc;

	
	public abstract void cadDraw(GraphicsContext gc);

		
	
}