package brennfleck.jarod.helpfulthings;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * A class to hold angles.
 * 
 * @author Jarod Brennfleck
 */
public class BrennyAngle {
	public float angle;
	
	/**
	 * Creates a new angle with the parameter.
	 */
	public BrennyAngle(float angle) {
		angle %= 360.0F;
		this.angle = angle;
	}
	
	/**
	 * Adds the <code>amount</code> to the angle.
	 */
	public void changeAngle(float amount) {
		angle += amount;
		angle %= 360.0F;
	}
	
	/**
	 * Sets the angle to the parameter.
	 */
	public void setAngle(float angle) {
		angle %= 360.0F;
		this.angle = angle;
	}
	
	/**
	 * Returns angle speeds for a 2D plane according to the passed
	 * <code>speed</code>.
	 */
	public BrennyAngleSpeed getAngleSpeed(float speed) {
		float xSpeed = 0.0F;
		float ySpeed = 0.0F;
		double sin = BrennyHelpful.MathHelpful.sin(angle);
		double cos = BrennyHelpful.MathHelpful.cos(angle);
		xSpeed = (float) sin * speed;
		ySpeed = (float) cos * speed;
		return new BrennyAngleSpeed(xSpeed, ySpeed, angle);
	}
	
	/**
	 * Returns a BufferedImage that has been rotated to this class' angle.
	 */
	public BufferedImage getRotation(BufferedImage bi) {
		return BrennyAngle.getRotation(bi, (double) angle);
	}
	
	/**
	 * Returns a BufferedImage that has been rotated to the passed angle.
	 */
	public static BufferedImage getRotation(BufferedImage bi, double angle) {
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle), bi.getWidth() / 2, bi.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(bi, null);
	}
	
	/**
	 * Returns the angle between the source and target points.
	 */
	public static double getAngle(BrennyPoint source, BrennyPoint target) {
		return (double) BrennyHelpful.getAngle(source.y - target.y, source.x - target.x);
	}
	
	/**
	 * Checks whether this angle is equal to the passed Object.
	 */
	public boolean equals(Object o) {
		if(o instanceof BrennyAngle) if(((BrennyAngle) o).angle == this.angle) return true;
		return false;
	}
	
	/**
	 * Creates a clone of this angle.
	 */
	public BrennyAngle clone() {
		return new BrennyAngle(this.angle);
	}
	
	/**
	 * A class that stores the vector of an object that would be moving on a 2D
	 * plane.
	 * 
	 * @author Jarod Brennfleck
	 */
	public class BrennyAngleSpeed {
		public float xSpeed;
		public float ySpeed;
		public float angle;
		
		/**
		 * Creates a BrennyAngleSpeed object using the parameters.
		 */
		public BrennyAngleSpeed(float xSpeed, float ySpeed, float angle) {
			this.xSpeed = xSpeed;
			this.ySpeed = ySpeed;
			this.angle = angle;
		}
		
		/**
		 * Returns the speeds as a float array with xSpeed being index[0] and
		 * ySpeed being index[1].
		 */
		public float[] getSpeeds() {
			return new float[] {xSpeed, ySpeed};
		}
	}
}