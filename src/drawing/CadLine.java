package drawing;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import kernel.CadMain;
import kernel.HandleEvents;

public class CadLine extends CadObjects {

	protected static Point2D p1 = null;
	protected static Point2D p2 = null;

	public static void abort() {
		currentStep = 0;
		p1 = null;
		p2 = null;
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
		HandleEvents.setEcho("ine");
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
		CadDrawing.CURRENT_DRAWING.add(new CadLine(p1, p2));		
		abort();
		TEXT_BOX.setPromptText(HandleEvents.DEFAULT_PROMPT);
	}

	protected Point2D end = null;
	protected Line line = null;
	protected Point2D mid = null;
	protected Point2D start = null;

	// constructor method(s)----------------------------------------------------------------------------------------------
	public CadLine(Point2D start, Point2D end) {
		this.start = start;
		this.end = end;
		mid = start.midpoint(end);
		line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
	}

	// instance method(s)-------------------------------------------------------------------------------------------------
	@Override
	public void cadDraw(GraphicsContext gc) {
		gc.setStroke(Color.WHITE);
		gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
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

}
