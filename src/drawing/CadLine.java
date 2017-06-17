package drawing;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import kernel.HandleEvents;

public class CadLine extends CadObjects {
	
	private static final int FIRST_NUMBER = 1;
	private static final int SECOND_NUMBER = 1;
	private static double x1i = 0;
	private static double y1i = 0;
	private static double x2i = 0;
	private static double y2i = 0;
	static int currentStep = 0;
	Line line; 
		
	public CadLine(double x1, double y1, double x2, double y2) {
		line = new Line(x1,y1,x2,y2);		
	}

	public static boolean invoke(InputEvent evt) {
		KeyEvent k = null;
		MouseEvent m = null;
		if(evt.getEventType().toString() == "KEY_PRESSED"){k = (KeyEvent)evt;}
		if(evt.getEventType().toString() == "MOUSE_CLICKED"){m = (MouseEvent)evt;}
		switch(currentStep){
			case 1:{step1(k,m);return true;}
			case 2:{step2(k,m);return false;}
			default:{init(k,m);return true;}
		}
	}

	private static void init(KeyEvent k, MouseEvent m) {
		textBox.setPromptText("Enter first point");
		currentStep = 1;
	}

	private static void step1(KeyEvent k, MouseEvent m) {
		textBox.setPromptText("Enter second point");
		if(m != null){
			x1i = scaled(m.getX());
			y1i = scaled(m.getY());
		}else{
			x1i = scaled(parseFromText(Commands.textInput,FIRST_NUMBER));
			y1i = scaled(parseFromText(Commands.textInput,SECOND_NUMBER));
		}
		currentStep = 2;
	}


	private static void step2(KeyEvent k, MouseEvent m) {
		if(m != null){
			x2i = scaled(m.getX());
			y2i = scaled(m.getY());
		}else{
			x2i = scaled(parseFromText(Commands.textInput,FIRST_NUMBER));
			y2i = scaled(parseFromText(Commands.textInput,SECOND_NUMBER));
		}
		CadDrawing.CURRENT_DRAWING.add(new CadLine(x1i,y1i,x2i,y2i));
		CadDrawing.CURRENT_DRAWING.redrawAll();
		currentStep = 0;
		textBox.setPromptText(HandleEvents.DEFAULT_PROMPT);
	}
	
	private static double parseFromText(String s, int n) {
		if (!s.contains(",")) return Double.parseDouble(s);	
		int commaLocation = s.indexOf(",");
		if (n == 1){return scaled(Double.parseDouble(s.substring(0, (commaLocation - 1))));
		}else{return scaled(Double.parseDouble(s.substring((commaLocation + 1),s.length()-1)));
		}
	}

	public void cadDraw(GraphicsContext gc) {
		gc.setStroke(Color.WHITE);
		gc.strokeLine(scaled(line.getStartX()),scaled(line.getStartY()),scaled(line.getEndX()),scaled(line.getEndY()));		
		
	}

	public static boolean validInput(String text) {
		String numbers = "123456789,";
		for(int i=0; i>text.length() ; i++){
			System.out.println(numbers.indexOf(text.charAt(i)) );
			if (numbers.indexOf(text.charAt(i)) == -1) return false;		
		}
		return true;
	}
	
	public static void abort(){
		currentStep = 0;
		x1i = 0; y1i = 0; x2i = 0; y2i = 0;
	}

}
