package drawing;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import kernel.HandleEvents;

public class CadLine extends CadObjects {

	protected static Point2D p1 = null;
	protected static Point2D p2 = null;

	public static void abort() {
		currentStep = 0;
		p1 = null;
		p2 = null;
	}

	private static void init() {
		textBox.setPromptText("Enter first point");
		currentStep = 1;
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
		case 1: {
			step1(k, m);
			return true;
		}
		case 2: {
			step2(k, m);
			return false;
		}
		default: {
			init();
			return true;
		}
		}
	}

	private static Point2D parseFromText(String s) {
		Point2D p = null;
		// text is already validated, so simple tests are fine
		if (s.contains(",")) {
			int commaLocation = s.indexOf(",");
			Double tmpX, tmpY;
			tmpX = scaled(Double.parseDouble(s.substring(0, (commaLocation - 1))));
			tmpY = scaled(Double.parseDouble(s.substring((commaLocation + 1), s.length() - 1)));
			p = new Point2D(tmpX, tmpY);
			return p;
		} else if (s.contains("@")) {
			// p1 will exist here because @ is only allowed on step 2
			int atLocation = s.indexOf("@");
			double length = scaled(Double.parseDouble(s.substring(0, (atLocation))));
			double angle = Double.parseDouble(s.substring((atLocation + 1), s.length()));

			int xQuad = 0;
			int yQuad = 0;
			if (angle < 90) {
				xQuad = 1;
				yQuad = -1;
			} else if (angle > 90 && (angle < 180)) {
				xQuad = -1;
				yQuad = -1;
			} else if (angle > 180 && (angle < 270)) {
				xQuad = -1;
				yQuad = 1;
			} else if (angle > 270 && (angle < 360)) {
				xQuad = 1;
				yQuad = 1;
			} else {
				if (angle == 0 || angle == 360) {
					p = new Point2D(p1.getX() + length, p1.getY());
				}
				if (angle == 90) {
					p = new Point2D(p1.getX(), p1.getY() + length);
				}
				if (angle == 180) {
					p = new Point2D(p1.getX() - length, p1.getY());
				}
				if (angle == 270) {
					p = new Point2D(p1.getX(), p1.getY() - length);
				}
			}

			if (xQuad != 0) {
				Double tmpX = p1.getX() + (xQuad * (Math.cos(Math.toRadians(angle)) * length));
				Double tmpY = p1.getY() + (yQuad * (Math.sin(Math.toRadians(angle)) * length));
				p = new Point2D(tmpX, tmpY);
			}
			return p;
		} else {
			// only one number
			double length = Double.parseDouble(s);
			p = new Point2D(p1.getX() + length, p1.getY());
		}

		return p;
	}

	private static void step1(KeyEvent k, MouseEvent m) {
		textBox.setPromptText("Enter second point");
		if (m != null) {
			p1 = new Point2D(scaled(m.getX()), scaled(m.getY()));
		} else {
			p1 = parseFromText(Commands.textInput);
		}
		currentStep = 2;
	}

	private static void step2(KeyEvent k, MouseEvent m) {
		if (m != null) {
			p2 = new Point2D(scaled(m.getX()), scaled(m.getY()));
		} else {
			p2 = parseFromText(Commands.textInput);
		}		
		CadDrawing.CURRENT_DRAWING.add(new CadLine(p1, p2));
		CadDrawing.CURRENT_DRAWING.redrawAll();
		abort();
		textBox.setPromptText(HandleEvents.DEFAULT_PROMPT);
	}

	public static boolean validInput(String text) {
		String numbers = "0123456789,.@";
		String sub1 = null;
		String sub2 = null;
		if (currentStep == 0)
			return true;
		if (text.equals(""))
			return false;

		// Valid text (for Line) is <number> or <number>,<number> or
		// <number>@<angle>(Step2 only)
		// check for valid <number>,<number>
		if (text.contains(",")) {
			if (text.contains("@"))
				return false;
			sub1 = text.substring(0, text.indexOf(","));
			sub2 = text.substring((text.indexOf(",") + 1), text.length());
			if (sub2.contains(","))
				return false;
		}

		// check for valid <number>@<angle>
		if (text.contains("@")) {
			if (currentStep == 1)
				return false;
			sub1 = text.substring(0, text.indexOf("@"));
			sub2 = text.substring((text.indexOf("@") + 1), text.length());
			if (sub2.contains(","))
				return false;
			if (sub2.contains("@"))
				return false;
		}

		// default to valid <number>
		if (sub2 == null) {
			if (currentStep == 1)
				return false;
			sub1 = text;
		}

		// more than one period in sub1?
		boolean foundDecimal = false;
		for (int i = 0; i < sub1.length(); i++) {
			if (numbers.indexOf(text.charAt(i)) == -1) {
				Commands.textInput = "??";
				return false;
			}
			if (String.valueOf(text.charAt(i)).equals(".")) {
				if (foundDecimal == true)
					return false;
				foundDecimal = true;
			}
		}

		// more than one period in sub2?
		if (sub2 != null) {
			foundDecimal = false;
			for (int i = 0; i < sub2.length(); i++) {
				if (numbers.indexOf(text.charAt(i)) == -1) {
					Commands.textInput = "??";
					return false;
				}
				if (String.valueOf(text.charAt(i)).equals(".")) {
					if (foundDecimal == true)
						return false;
					foundDecimal = true;
				}
			}
		}

		// if it makes it here it has passed all tests yay!!
		return true;
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

}
