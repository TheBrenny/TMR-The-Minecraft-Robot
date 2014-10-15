package brennfleck.jarod.tmr.scripts.player;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import brennfleck.jarod.helpfulthings.BrennyAngle;
import brennfleck.jarod.helpfulthings.BrennyHelpful;
import brennfleck.jarod.helpfulthings.BrennyPoint;
import brennfleck.jarod.pathing.AStar;
import brennfleck.jarod.pathing.PathingResult;
import brennfleck.jarod.pathing.Tile;
import brennfleck.jarod.pathing.AStar.InvalidPathException;
import brennfleck.jarod.tmr.scripts.minecraft.MinecraftForm;
import brennfleck.jarod.tmr.scripts.world.Area;
import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;
import brennfleck.jarod.tmr.scripts.world.PreciseLocation;
import brennfleck.jarod.tmr.scripts.world.World;
import brennfleck.jarod.tmr.utils.TmrMovementInputFromOptions;


public class Player {
	private static Location pathStartLocation;
	private static ArrayList<Location> currentPath;
	private static ArrayList<PreciseLocation> abovePath;
	private static long lastTimeAskedForPath = -1L;
	private static boolean isSwingingItem = false;
	private static boolean isInteracting = false;
	private static boolean isUsingItem = false;
	public static final int FORWARD = 100;
	public static final int BACKWARD = 101;
	public static final int LEFT = 102;
	public static final int RIGHT = 103;
	public static final int SOUTH = 200;
	public static final int WEST = 201;
	public static final int NORTH = 202;
	public static final int EAST = 203;
	public static final String EYE = "eye";
	public static final String FEET = "feet";
	
	private static EntityClientPlayerMP getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	public static final boolean isValid() {
		boolean valid = true;
		if(getPlayer() == null) valid = false;
		if(!valid) System.out.println("Player is bad!");
		return valid;
	}
	
	public static ArrayList<Location> getCurrentPath() {
		return Player.currentPath;
	}
	
	public static ArrayList<PreciseLocation> getCurrentAbovePath() {
		return Player.abovePath;
	}
	
	public static void move(int direction, boolean state) {
		TmrMovementInputFromOptions.Direction dir = TmrMovementInputFromOptions.Direction.NONE;
		// @formatter:off
		switch(direction) {
		case FORWARD: dir = dir.FORWARD; break;
		case BACKWARD: dir = dir.BACKWARD; break;
		case LEFT: dir = dir.LEFT; break;
		case RIGHT: dir = dir.RIGHT; break;
		}
		// @formatter:on
		((TmrMovementInputFromOptions) getPlayer().movementInput).move(dir, state);
	}
	
	public static Object[] getMovementState(int direction) {
		TmrMovementInputFromOptions.Direction dir = TmrMovementInputFromOptions.Direction.NONE;
		// @formatter:off
		switch(direction) {
		case FORWARD: dir = dir.FORWARD; break;
		case BACKWARD: dir = dir.BACKWARD; break;
		case LEFT: dir = dir.LEFT; break;
		case RIGHT: dir = dir.RIGHT; break;
		}
		// @formatter:on
		return new Object[] {dir.name(), dir.getState()};
	}
	
	public static void jump() {
		if(getPlayer().flyToggleTimer == 0) ((TmrMovementInputFromOptions) getPlayer().movementInput).jump();
	}
	
	public static void sneak(boolean toggle) {
		((TmrMovementInputFromOptions) getPlayer().movementInput).sneak(toggle);
	}
	
	public static void sprint(boolean toggle) {
		getPlayer().setSprinting(toggle);
	}
	
	public static void sendChatMessage(String message) {
		getPlayer().sendChatMessage(message);
	}
	
	public static Location getLocation(String spot) {
		return getPreciseLocation(spot).getNonPrecise();
	}
	
	public static PreciseLocation getPreciseLocation(String spot) {
		double theY = spot.equalsIgnoreCase(EYE) ? getPlayer().posY : getPlayer().boundingBox.minY;
		return new PreciseLocation(getPlayer().posX, theY, getPlayer().posZ);
	}
	
	public static Block getBlockUnderFeet() {
		return World.getBlockAt(getLocation("").shift(0, -1, 0));
	}
	
	public static String[] walkTo(Location location) {
		if((currentPath != null && currentPath.size() == 0) || new Area(location, location.clone().shift(0, 3, 0)).contains(getLocation(EYE))) {
			move(FORWARD, false);
			currentPath = null;
			return new String[] {"true", location.getX() + "", location.getY() + "", location.getZ() + ""};
		}
		if(currentPath == null) {
			lastTimeAskedForPath = System.currentTimeMillis();
			makePath(location);
		}
		if(currentPath != null && currentPath.size() > 0) {
			Location next = walkHelper();
			return new String[] {"false", next.getX() + "", next.getY() + "", next.getZ() + ""};
		}
		else {
			MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Color.DARK_RED.colorCode() + "TMR: Walking path was null...");
			Minecraft.getTMR().getScript().stop();
		}
		return new String[] {"false", "null", "null", "null"};
	}
	
	public static AStar makePath(Location l) {
		try {
			l = World.getMostSuitableStandingLocation(l);
			final Location a = World.getMostSuitableStandingLocation(new Location(getPlayer().posX, getPlayer().posY, getPlayer().posZ));
			AStar path = new AStar(a, l, 100);
			ArrayList<Tile> thePath = path.iterate();
			if(path.getPathingResult() == PathingResult.SUCCESS) {
				abovePath = new ArrayList<PreciseLocation>();
				ArrayList<Location> theRealPath = new ArrayList<Location>();
				for(Tile t : thePath) {
					theRealPath.add(t.getLocation(a));
					abovePath.add(t.getLocation(a).shift(0, 1.0D, 0));
				}
				Player.currentPath = theRealPath;
				Player.pathStartLocation = a;
				return path;
			} else {
				MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Color.DARK_RED.colorCode() + "TMR: Pathing result was bad... Very bad...");
				Player.currentPath = null;
				Player.pathStartLocation = null;
			}
		} catch(InvalidPathException e) {
			e.printStackTrace();
			System.err.println(e.getErrorReason());
			Player.currentPath = null;
			Player.pathStartLocation = null;
			MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Color.DARK_RED.colorCode() + MinecraftForm.Format.BOLD.formatCode() + "TMR: Unable to create path!");
			MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Color.DARK_RED.colorCode() + MinecraftForm.Format.BOLD.formatCode() + "TMR: Reason: " + e.getErrorReason());
			if(Minecraft.getTMR().getScript() != null) Minecraft.getTMR().getScript().stop();
		}
		return null;
	}
	
	private static Location walkHelper() {
		boolean inLiquid = getPlayer().isInWater() || getPlayer().handleLavaMovement();
		double vectorY = (vec3Helper(Player.pathStartLocation)[1] + 1) - getLocation("").getY();
		boolean shouldJump = vectorY > 0;
		if(shouldJump || inLiquid) jump();
		double shift = (double) ((int) (getPlayer().width + 1.0F)) * 0.5D;
		Location t = currentPath.get(0);
		faceLocation(new PreciseLocation(t.getX() + shift, t.getY() + 1.0D + shift, t.getZ() + shift));
		move(FORWARD, true);
		Location hLoc = World.getMostSuitableStandingLocation(getLocation(""));
		if(hLoc.equals(t)) {
			currentPath.remove(0);
			abovePath.remove(0);
		}
		return t;
	}
	
	public static void faceLocation(PreciseLocation location) {
		double[] vecHelp = vec3Helper(location);
		double vectorX = vecHelp[0];
		double vectorY = vecHelp[1];
		double vectorZ = vecHelp[2];
		double degree = BrennyAngle.getAngle(new BrennyPoint(Player.getPreciseLocation("").getX(), Player.getPreciseLocation("").getZ()), new BrennyPoint(vectorX, vectorZ));
		while(degree < 0.0D)
			degree += 360.0D;
		rotateHead(degree);
		degree = BrennyHelpful.getAngle(Math.sqrt((vectorX * vectorX) + (vectorZ * vectorZ)), vectorY - Player.getPreciseLocation(EYE).getY());
		pitchHead(degree);
	}
	
	public static double[] vec3Helper(PreciseLocation loc) {
		return new double[] {loc.getX(), loc.getY(), loc.getZ()};
	}
	
	public static void preciseSpeed(float forwardSpeed, float strafeSpeed) {
		forwardSpeed = forwardSpeed > 1.0F ? 1.0F : forwardSpeed;
		strafeSpeed = strafeSpeed > 1.0F ? 1.0F : strafeSpeed;
		((TmrMovementInputFromOptions) getPlayer().movementInput).preciseSpeed(strafeSpeed, forwardSpeed);
	}
	
	public static int getFacingDirection() {
		return (BrennyHelpful.MathHelpful.floor_double((double) (getHeadAngles()[0] * 4.0F / 360.0F) + 0.5D) & 3) + 200;
	}
	
	public static void faceDirection(int direction) {
		if(direction < 200 || direction > 203) {
			System.out.println("BAD DIRECTION");
			return;
		}
		getPlayer().rotationYaw = direction - 200;
	}
	
	public static void rotateHead(double headAngleMustBe) {
		headAngleMustBe += 180.0D;
		while(headAngleMustBe <= -180.0D)
			headAngleMustBe += 360.0D;
		while(headAngleMustBe >= 180.0D)
			headAngleMustBe -= 360.0D;
		getPlayer().rotationYaw = (float) headAngleMustBe;
	}
	
	public static void addRotationToHead(float degree) {
		rotateHead(getHeadAngles()[0] + degree);
	}
	
	public static void pitchHead(double degree) {
		while(degree > 90.0D)
			degree -= 180.0D;
		while(degree < -90.0D)
			degree += 180.0D;
		getPlayer().rotationPitch = (float) -degree;
	}
	
	public static float[] getHeadAngles() {
		return new float[] {getPlayer().rotationYaw, getPlayer().rotationPitch};
	}
	
	public static void swingItem(boolean toggle) {
		Player.isSwingingItem = toggle;
	}
	
	public static boolean isSwingingItem() {
		return Player.isSwingingItem;
	}
	
	public static void interact() {
		Player.isInteracting = true;
	}
	
	public static boolean isInteracting() {
		boolean tmp = Player.isInteracting;
		Player.isInteracting = false;
		return tmp;
	}
	
	public static void useItem(boolean toggle) {
		Player.isUsingItem = toggle;
	}
	
	public static boolean isUsingItem() {
		return Player.isUsingItem;
	}
	
	public static void resetAttributes() {
		for(int i = 100; i < 104; i++)
			move(i, false);
	}
}