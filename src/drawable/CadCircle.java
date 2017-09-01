package drawable;

import executable.Step;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import kernel.CadDrawing;
import kernel.CadMain;
import kernel.HandleEvents;

public class CadCircle extends CadObjects{

	protected static Point2D p1 = null;
	protected static Point2D p2 = null;
	
	protected Circle circle;
	protected Point2D center;
	protected Point2D upLeftBounds;
	protected double width;
	
// constructor method(s)----------------------------------------------------------------------------------------------		
	public CadCircle(Point2D center, Point2D radius) {
		this.center = center;
		circle = new Circle();
		circle.setCenterX(center.getX());
		circle.setCenterY(center.getY());
		circle.setRadius(radius.distance(center));
		upLeftBounds = new Point2D(center.getX()-radius.distance(center),center.getY()-radius.distance(center));
		width = radius.distance(center) * 2;
		
		
	}
	
	public static boolean invoke(InputEvent evt) {
		KeyEvent k = null;
		MouseEvent m = null;
		if (evt.getEventType().toString() == "KEY_PRESSED") {
			k = (KeyEvent) evt;
		}
		if (evt.getEventType().toString() == "MOUSE_CLICKED") {
			m = (MouseEvent) evt;
		}
		switch (currentStep) {
		case 1: {step1(k, m);return true;}
		case 2: {step2(k, m);return false;}
		default: {init();return true;}
		}
	}	
	
	private static void init() {
		TEXT_BOX.setPromptText("Enter first point");
		currentStep = 1;
		HandleEvents.setEcho("ircle");
	}

	private static void step1(KeyEvent k, MouseEvent m) {
		TEXT_BOX.setPromptText("Enter second point");
		if (m != null) {
			p1 = new Point2D(scaled(m.getX()), scaled(m.getY()));
		} else {
			p1 = Step.parseFromText(CadMain.textBox.getText(),p1);
		}
		currentStep = 2;
	}

	private static void step2(KeyEvent k, MouseEvent m) {
		if (m != null) {
			p2 = new Point2D(scaled(m.getX()), scaled(m.getY()));
		} else {
			p2 = Step.parseFromText(CadMain.textBox.getText(),p1);
		}		
		CadDrawing.CURRENT_DRAWING.add(new CadCircle(p1, p2));		
		abortAll();
		TEXT_BOX.setPromptText(HandleEvents.DEFAULT_PROMPT);
	}


	@Override
	public void cadDraw(GraphicsContext gc) {
	
		gc.setStroke(Color.WHITE);
		gc.strokeOval(upLeftBounds.getX(), upLeftBounds.getY(),width, width);
	}

	public static boolean validInput(String input) {
		switch(currentStep){
		case 1:return Step.validInput(input, false, true, false);
		case 2:return Step.validInput(input, true, true, true);
		default: return true; // currestStep = 0
		}
	}

	@Override
	public void intersects() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unselect() {
		// TODO Auto-generated method stub
		
	}

	public static void abort() {
		// TODO Auto-generated method stub
		
	}

	
	
}
