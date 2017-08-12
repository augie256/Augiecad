package drawing;

import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import kernel.CadMain;
import kernel.Settings;

public abstract class Commands {

	protected static final TextField textBox = CadMain.textBox;
	protected static Settings set = Settings.getInstance();
	public static String textInput = "";
	public static final int LINE = 1;
	public static final int CIRCLE = 2;
	public static final int TRIM = 3;

	public static boolean invoke(int command, InputEvent e) {
		switch (command) {
		case LINE:
			return CadLine.invoke(e);
		case CIRCLE:
			return CadCircle.invoke(e);
		case TRIM:
			return Trim.invoke(e);
		default:
			return false;
		}
	}

	protected static void finish() {
		textInput = "";
	}

	public static void setInput(String text) {
		textInput = text;
	}

	public static void abort() {
		textInput = "";
		CadLine.abort();
	}

	protected static double scaled(double d) {
		return (d * CadDrawing.CURRENT_DRAWING.getScale());
	}

}
