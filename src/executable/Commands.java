package executable;

import drawable.CadCircle;
import drawable.CadLine;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import kernel.CadDrawing;
import kernel.CadMain;
import kernel.Settings;

public abstract class Commands {

	public static int currentCommand = 0;
	protected static final TextField TEXT_BOX = CadMain.textBox;
	protected static final Settings SET = Settings.getInstance();
	protected static final Step STEP = Step.getInstance();
	public static final int LINE = 1;
	public static final int CIRCLE = 2;
	public static final int ERASE = 3;
	public static final int TRIM = 4;

	public static boolean invoke(int command, InputEvent e) {
		switch (command) {
		case LINE:
			if(CadLine.invoke(e)){currentCommand = LINE;return true;
			}else{finish();	return false;}
		case CIRCLE:
			if(CadCircle.invoke(e)){currentCommand = CIRCLE;return true;
			}else{finish(); return false;}
		case ERASE:
			if(Erase.invoke(e)){currentCommand = ERASE;return true;
			}else{finish(); return false;}
		case TRIM:
			if(CadLine.invoke(e)){currentCommand = TRIM;return true;
			}else{finish(); return false;}
		default: finish(); return false;
		}
	}

	protected static void finish() {
		currentCommand = 0;
		abortAll();
	}

	public static void abortAll() {
		CadLine.abort();
		CadCircle.abort();
		Erase.abort();
		Trim.abort();
		CadDrawing.CURRENT_DRAWING.esc();
	}

	protected static double scaled(double d) {
		return (d * CadDrawing.CURRENT_DRAWING.getScale());
	}

}
