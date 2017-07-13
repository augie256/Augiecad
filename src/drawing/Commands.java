package drawing;

import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import kernel.HandleEvents;
import kernel.Settings;

public abstract class Commands {

		protected static final TextField textBox = HandleEvents.textBox;
		protected static Settings set;
		public static String textInput = "";
		public static final int LINE = 1;
		public static final int CIRCLE = 2;
		public static final int TRIM = 3;
		protected static boolean mouseEventOk = true;
		protected static boolean keyEventOk = true;
		
	public static boolean invoke(int command, InputEvent e) {
		switch (command) {
	       case LINE: return CadLine.invoke(e); 
	       case CIRCLE: return CadCircle.invoke(e);
	       case TRIM: return Trim.invoke(e);
	       default: return false;
		   }
	}
	
	public static boolean isMouseOk(){
		return mouseEventOk;
	}
	
	public static boolean isKeyOk(){
		return keyEventOk;
	}
	
	protected static void finish(){
		mouseEventOk = true;
		keyEventOk = true;
		textInput = "";
	}

	public static void setInput(String text) {
		textInput = text;		
	}

	public static void abort(){
		mouseEventOk = true;
		keyEventOk = true;
		textInput = "";
		CadLine.abort();
	}
	
	protected static double scaled(double d){
		return (d * CadDrawing.CURRENT_DRAWING.getScale());
	}
	
	
	public static void setSettings(Settings set){
		Commands.set = set;
	}
}
