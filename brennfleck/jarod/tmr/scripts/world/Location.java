package brennfleck.jarod.tmr.scripts.world;

import brennfleck.jarod.helpfulthings.BrennyHelpful;
import net.minecraft.util.MathHelper;

/**
 * A 3 dimensional point in the world which rounds down to an integer. Useful for {@link brennfleck.jarod.tmr.scripts.world.World#World() World}.
 * @author jarod
 */
public class Location extends PreciseLocation {
	public Location(double x, double y, double z) {
		super(x, y, z);
		place(x, y, z);
	}
	
	public double getX() {
		return BrennyHelpful.MathHelpful.floor_double(this.x);
	}
	public double getY() {
		return BrennyHelpful.MathHelpful.floor_double(this.y);
	}
	public double getZ() {
		return BrennyHelpful.MathHelpful.floor_double(this.z);
	}
	
	public Location place(double x, double y, double z) {
		x = BrennyHelpful.MathHelpful.floor_double(x);
		y = BrennyHelpful.MathHelpful.floor_double(y);
		z = MathHelper.floor_double(z);
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Location clone() {
		return new Location(this.x, this.y, this.z);
	}
}