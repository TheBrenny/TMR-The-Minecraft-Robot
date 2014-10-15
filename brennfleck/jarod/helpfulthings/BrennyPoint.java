package brennfleck.jarod.helpfulthings;

import java.awt.geom.Point2D;

public class BrennyPoint extends Point2D.Double {
	private static final long serialVersionUID = 1L;
	
	public BrennyPoint() {
		super(0, 0);
	}
	
	public BrennyPoint(double x, double y) {
		super(x, y);
	}
	public BrennyPoint(BrennyPoint point) {
		super(point.x, point.y);
	}
	public void setLocationX(double x) {
		this.x = x;
	}
	public void setLocationY(double y) {
		this.y = y;
	}
	public BrennyRectangle makeRectangle(double range) {
		return new BrennyRectangle(this.x - range, this.y - range, range * 2, range * 2);
	}
	public boolean isContainedBy(double x, double y, double width, double height) {
		return new BrennyRectangle(x, y, width, height).contains(this);
	}
}