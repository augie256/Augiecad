package drawing;

import com.sun.javafx.geom.Point2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import kernel.HandleEvents;

public class CadLine extends CadObjects {
	
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
		System.out.println("line init");
	}

	private static void step1(KeyEvent k, MouseEvent m) {
		System.out.println("line Step 1");
		textBox.setPromptText("Enter second point");
		if(m != null){
			x1i = scaled(m.getX());
			y1i = scaled(m.getY());
		}else{
			Point2D p = parseFromText(Commands.textInput);
			x1i = scaled(p.x);
			y1i = scaled(p.y);
		}
		currentStep = 2;
		
	}

	private static void step2(KeyEvent k, MouseEvent m) {
		System.out.println("line Step 2");
		if(m != null){
			x2i = scaled(m.getX());
			y2i = scaled(m.getY());
		}else{
			Point2D p = parseFromText(Commands.textInput);
			x2i = scaled(p.x);
			y2i = scaled(p.y);
		}
		CadDrawing.CURRENT_DRAWING.add(new CadLine(x1i,y1i,x2i,y2i));
		CadDrawing.CURRENT_DRAWING.redrawAll();
		abort();
		textBox.setPromptText(HandleEvents.DEFAULT_PROMPT);
	}
	
	private static Point2D parseFromText(String s) {
		Point2D p = new Point2D();
		//text is already validated, so simple tests are fine
		if(s.contains(",")){
			int commaLocation = s.indexOf(",");
			p.x = (float) scaled(Float.parseFloat(s.substring(0, (commaLocation - 1))));
			p.y = (float) scaled(Double.parseDouble(s.substring((commaLocation + 1),s.length()-1)));
			return p;
		}else if(s.contains("@")){
			// x1i and y1i will exist here because @ is only allowed on step 2  
			int atLocation = s.indexOf("@");
			double length = scaled(Double.parseDouble(s.substring(0, (atLocation))));
			double angle = Double.parseDouble(s.substring((atLocation + 1),s.length()));
					
			int xQuad = 0;
			int yQuad = 0;
			if (angle < 90){
				xQuad = 1;
				yQuad = -1;
			}else if(angle > 90 && (angle < 180)){
				xQuad = -1;
				yQuad = -1;
			}else if(angle > 180 && (angle < 270)){
				xQuad = -1;
				yQuad = 1;
			}else if(angle > 270 && (angle < 360)){
				xQuad = 1;
				yQuad = 1;
			}else{
				if(angle == 0 || angle == 360){
					p.x = (float) (x1i + length);
					p.y = (float) y1i;
				} 
				
				if(angle == 90){
					p.x = (float) (x1i);
					p.y = (float) (y1i + length);
				} 
				if(angle == 180){
					p.x = (float) (x1i - length);
					p.y = (float) y1i;
				} 
				
				if(angle == 270){
					p.x = (float) (x1i);
					p.y = (float) (y1i - length);
				}				
			}
			
			if (xQuad != 0){
				System.out.println(length);
				System.out.println(angle);
				System.out.println(Math.sin(Math.toRadians(angle))*length);
				System.out.println(Math.cos(Math.toRadians(angle))*length);
				 p.x = (float) (x1i + (xQuad * (Math.cos(Math.toRadians(angle))*length)));
				 p.y = (float) (y1i + (yQuad * (Math.sin(Math.toRadians(angle))*length)));
				 System.out.println(p);
			}
			return p;
		}else{
			//only one number
			double length = Double.parseDouble(s);
			p.x = (float) (x1i + length);
			p.y = (float) y1i;			
		}
		
		return p;	
	}

	public void cadDraw(GraphicsContext gc) {
		gc.setStroke(Color.WHITE);
		gc.strokeLine(scaled(line.getStartX()),scaled(line.getStartY()),scaled(line.getEndX()),scaled(line.getEndY()));		
		
	}

	public static boolean validInput(String text) {
		String numbers = "0123456789,.@";
		String sub1 = null;
		String sub2 = null;
		if (currentStep == 0) return true;
		if(text.equals("")) return false;
				
		// Valid text (for Line) is <number> or <number>,<number> or <number>@<angle>(Step2 only)  
		//check for valid <number>,<number>
		if (text.contains(",")){
			if(text.contains("@")) return false;
			sub1 = text.substring(0, text.indexOf(","));
			sub2 = text.substring((text.indexOf(",") + 1), text.length());
			if(sub2.contains(",")) return false;		
		}
		
		//check for valid <number>@<angle>
		if (text.contains("@")){
			if (currentStep == 1) return false;
			sub1 = text.substring(0, text.indexOf("@"));
			sub2 = text.substring((text.indexOf("@") + 1),text.length());
			if(sub2.contains(",")) return false;
			if(sub2.contains("@")) return false;
		}
		
		//default to valid <number>
		if (sub2 == null){
			if (currentStep == 1) return false;
			sub1 = text;			
		}
		
		//more than one period in sub1?
		boolean foundDecimal = false;					
		for (int i=0;i<sub1.length();i++){
				if (numbers.indexOf(text.charAt(i)) == -1){
				Commands.textInput="??"; 
				return false;
				}
				if(String.valueOf(text.charAt(i)).equals(".")){
					if(foundDecimal == true) return false;
					foundDecimal = true;									
				}
		}
		
		//more than one period in sub2?
		if (sub2 != null){
			foundDecimal = false;					
			for (int i=0;i<sub2.length();i++){
					if (numbers.indexOf(text.charAt(i)) == -1){
					Commands.textInput="??"; 
					return false;
					}
					if(String.valueOf(text.charAt(i)).equals(".")){
						if(foundDecimal == true) return false;
						foundDecimal = true;									
					}
			}
		}

	// if it makes it here it has passed all tests yay!!
	return true;
	}
	
	public static void abort(){
		currentStep = 0;
		x1i = 0; y1i = 0; x2i = 0; y2i = 0;
	}

}
