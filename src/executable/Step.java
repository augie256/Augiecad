package executable;

import javafx.geometry.Point2D;
import kernel.HandleEvents;

public class Step extends Commands {

	private static Step s = null;
	
	private Step(){
		
	}
	
	public static Step getInstance(){
		if(s == null) s = new Step();
		return s;
	}
	
	public static boolean validInput(String text,boolean NumberOnlyIsOk,boolean NumberCommaIsOk,boolean NumberAngleIsOk ) {
		String numbers = "0123456789,.@";
		String sub1 = null;
		String sub2 = null;
		if (text.equals("")) return false;

		// Valid text (for Line) is <number> or <number>,<number> or <number>@<angle>(Step2 only)
		
		// check for valid <number>,<number>
		if (text.contains(",")) {
			if (!NumberCommaIsOk) {HandleEvents.setEcho(" ??"); return false;}
			if (text.contains("@")) {HandleEvents.setEcho(" ??"); return false;}
			sub1 = text.substring(0, text.indexOf(","));
			sub2 = text.substring((text.indexOf(",") + 1), text.length());
			if (sub2.contains(","))	{HandleEvents.setEcho(" ??"); return false;}
		}

		// check for valid <number>@<angle>
		if (text.contains("@")) {
			if(!NumberAngleIsOk) {HandleEvents.setEcho(" ??"); return false;}
			sub1 = text.substring(0, text.indexOf("@"));
			sub2 = text.substring((text.indexOf("@") + 1), text.length());
			if (sub2.contains(","))	{HandleEvents.setEcho(" ??"); return false;}
			if (sub2.contains("@")) {HandleEvents.setEcho(" ??"); return false;}
		}

		// default to valid <number>
		if (sub2 == null) {
			if (!NumberOnlyIsOk) {HandleEvents.setEcho(" ??"); return false;}
			sub1 = text;
		}

		// more than one period in sub1?
		boolean foundDecimal = false;
		for (int i = 0; i < sub1.length(); i++) {
			if (numbers.indexOf(text.charAt(i)) == -1) {HandleEvents.setEcho(" ??");return false;}
			if (String.valueOf(text.charAt(i)).equals(".")) {
				if (foundDecimal == true) {HandleEvents.setEcho(" ??");return false;}
				foundDecimal = true;
			}
		}
		
		// more than one period in sub2?
		if (sub2 != null) {
			foundDecimal = false;
			for (int i = 0; i < sub2.length(); i++) {
				if (numbers.indexOf(text.charAt(i)) == -1) { HandleEvents.setEcho("??");return false;}
				if (String.valueOf(text.charAt(i)).equals(".")) {
					if (foundDecimal == true) {HandleEvents.setEcho(" ??");return false;}
					foundDecimal = true;
				}
			}
		}

		// if it makes it here it has passed all tests yay!!
		return true;
	}

	public static Point2D parseFromText(String s,Point2D p1) {
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
			// p1 will need to exist here
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
	
}
