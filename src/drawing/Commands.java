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
		private static boolean mouseEventOk = true;
		private static boolean keyEventOk = true;
		
	public static boolean invoke(int command, InputEvent e) {
		switch (command) {
	       case 1: return CadLine.invoke(e); 
	       case 2: return CadCircle.invoke(e);
	       case 3: return Trim.invoke(e);
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

	public static void input(String text) {
		textInput = text;		
	}

	public static void abort(){
		mouseEventOk = true;
		keyEventOk = true;
		textInput = "";
		CadLine.abort();
	}
	
	protected static double scaled(double d){
		return (d * CadDrawing.CURRENT_DRAWING.DRAWING_SCALE);
	}
	
	public static void setSettings(Settings set){
		Commands.set = set;
	}
}
