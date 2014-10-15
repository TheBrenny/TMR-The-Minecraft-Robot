package brennfleck.jarod.helpfulthings;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class BrennyAngle {
	public float angle;
	
	public BrennyAngle(float angle) {
		//@formatter:off
		while(angle >= 360.0F) angle -= 360.0F;
		while(angle < 0.0F) angle += 360.0F;
		this.angle = angle;
		//@formatter:on
	}
	
	public BrennyAngle(BrennyAngle angle) {
		this(angle.angle);
	}
	
	public void changeAngle(float amount) {
		//@formatter:off
		angle += amount;
		while(angle >= 360) angle -= 360.0F;
		while(angle < 0) angle += 360.0F;
		//@formatter:on
	}
	
	public void setAngle(float angle) {
		//@formatter:off
		while(angle >= 360.0F) angle -= 360.0F;
		while(angle < 0.0F) angle += 360.0F;
		this.angle = angle;
		//@formatter:on
	}
	
	public BrennyAngleSpeed getAngleSpeed(float speed) {
		float xSpeed = 0.0F;
		float ySpeed = 0.0F;
		double sin = BrennyHelpful.math_sin(angle);
		double cos = BrennyHelpful.math_cos(angle);
		xSpeed = (float) sin * speed;
		ySpeed = (float) cos * speed;
		return new BrennyAngleSpeed(xSpeed, ySpeed, angle);
	}
	
	public BufferedImage getRotation(BufferedImage bi) {
		return BrennyAngle.getRotation(bi, (double) angle);
	}
	
	public static BufferedImage getRotation(BufferedImage bi, double angle) {
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle), bi.getWidth() / 2, bi.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(bi, null);
	}
	
	public static double getAngle(BrennyPoint source, BrennyPoint target) {
		return (double) BrennyHelpful.getAngle(source.y - target.y, source.x - target.x);
	}
	
	public boolean equals(Object o) {
		if(o instanceof BrennyAngle) if(((BrennyAngle) o).angle == this.angle) return true;
		return false;
	}
	
	public class BrennyAngleSpeed {
		public float xSpeed;
		public float ySpeed;
		public float angle;
		
		public BrennyAngleSpeed(float xSpeed, float ySpeed, float angle) {
			this.xSpeed = xSpeed;
			this.ySpeed = ySpeed;
			this.angle = angle;
		}
		
		public float[] getSpeeds() {
			return new float[] {xSpeed, ySpeed};
		}
	}
}