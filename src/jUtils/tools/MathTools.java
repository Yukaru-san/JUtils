package jUtils.tools;

import java.awt.Point;
import java.awt.Rectangle;

public class MathTools {

	// Returns true if p is within r
	public static boolean isPointInBounds(Point p, Rectangle r) {
		return (p.x > r.x && p.x < r.x + r.width && p.y > r.y && p.y < r.y + r.height);
	}

}
