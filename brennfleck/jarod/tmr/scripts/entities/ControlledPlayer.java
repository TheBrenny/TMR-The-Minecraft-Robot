package brennfleck.jarod.tmr.scripts.entities;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import brennfleck.jarod.helpfulthings.BrennyAngle;
import brennfleck.jarod.helpfulthings.BrennyHelpful;
import brennfleck.jarod.helpfulthings.BrennyPoint;
import brennfleck.jarod.pathing.AStar;
import brennfleck.jarod.pathing.AStar.InvalidPathException;
import brennfleck.jarod.pathing.PathingResult;
import brennfleck.jarod.pathing.Tile;
import brennfleck.jarod.tmr.TheMinecraftRobot;
import brennfleck.jarod.tmr.scripts.minecraft.MinecraftForm;
import brennfleck.jarod.tmr.scripts.world.Area;
import brennfleck.jarod.tmr.scripts.world.Block;
import brennfleck.jarod.tmr.scripts.world.Location;
import brennfleck.jarod.tmr.scripts.world.PreciseLocation;
import brennfleck.jarod.tmr.scripts.world.World;
import brennfleck.jarod.tmr.util.TmrMovementInputFromOptions;

public class ControlledPlayer extends EntityPlayerBase {
	private static ControlledPlayer theActualPlayer;
	public static final int FORWARD = 100;
	public static final int BACKWARD = 101;
	public static final int LEFT = 102;
	public static final int RIGHT = 103;
	private static Location pathStartLocation;
	private static ArrayList<Location> currentPath;
	private static ArrayList<PreciseLocation> abovePath;
	private static long lastTimeAskedForPath = -1L;
	private static boolean isSwingingItem = false;
	private static boolean isInteracting = false;
	private static boolean isUsingItem = false;
	
	public static ControlledPlayer getPlayer() {
		return ControlledPlayer.theActualPlayer;
	}
	
	public ControlledPlayer() {
		super(Minecraft.getMinecraft().thePlayer);
		if(TheMinecraftRobot.playerNeedsRemake()) theActualPlayer = this;
	}
	
	private static EntityClientPlayerMP getRealPlayer() {
		return (EntityClientPlayerMP) (theActualPlayer.theRealEntity = Minecraft.getMinecraft().thePlayer);
	}
	
	/**
	 * Returns the current path that has been calculated.
	 */
	public static ArrayList<Location> getCurrentPath() {
		return currentPath;
	}
	
	/**
	 * Returns the precise path above the currently calculated path.
	 */
	public static ArrayList<PreciseLocation> getCurrentAbovePath() {
		return abovePath;
	}
	
	/**
	 * Sets the players movement according to the direction and state given.
	 * 
	 * @see {@link #FORWARD}
	 * @see {@link #BACKWARD}
	 * @see {@link #LEFT}
	 * @see {@link #RIGHT}
	 */
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
		((TmrMovementInputFromOptions) getRealPlayer().movementInput).move(dir, state);
	}
	
	/**
	 * Returns the current status of the given direction.
	 */
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
	
	/**
	 * Statically calls {@link #getLocation(String)} using the parameter
	 * <code>"eye"</code>.
	 */
	public static Location getLocation() {
		return getPlayer().getLocation("eye");
	}
	
	/**
	 * Statically calls {@link #getPreciseLocation(String)} using the parameter
	 * <code>"eye"</code>.
	 */
	public static PreciseLocation getPreciseLocation() {
		return getPlayer().getPreciseLocation("eye");
	}
	
	/**
	 * Tells the player to jump once.
	 */
	public static void jump() {
		if(getRealPlayer().flyToggleTimer == 0) ((TmrMovementInputFromOptions) getRealPlayer().movementInput).jump();
	}
	
	/**
	 * Toggles sneaking for the player.
	 */
	public static void sneak(boolean toggle) {
		((TmrMovementInputFromOptions) getRealPlayer().movementInput).sneak(toggle);
	}
	
	/**
	 * Toggles sprinting for the player.
	 */
	public static void sprint(boolean toggle) {
		getRealPlayer().setSprinting(toggle);
	}
	
	/**
	 * Sends a chat message on the player's behalf.
	 */
	public static void sendChatMessage(String message) {
		getRealPlayer().sendChatMessage(message);
	}
	
	/**
	 * Returns the block currently under the player's feet.
	 */
	public static Block getBlockUnderFeet() {
		return World.getBlockAt(theActualPlayer.getLocation("").shift(0, -1, 0));
	}
	
	/**
	 * Walks to the target
	 * {@link brennfleck.jarod.tmr.scripts.world.Location#Location Location} by
	 * making a path first.
	 */
	public static String[] walkTo(Location location) {
		if((currentPath != null && currentPath.size() == 0) || new Area(location, location.clone().shift(0, 3, 0)).contains(theActualPlayer.getLocation(EYE))) {
			move(FORWARD, false);
			clearPathData();
			return new String[] {"true", location.getX() + "", location.getY() + "", location.getZ() + ""};
		}
		if(currentPath == null) {
			lastTimeAskedForPath = System.currentTimeMillis();
			makePath(location);
		}
		if(currentPath != null && currentPath.size() > 0) {
			Location next = walkHelper();
			return new String[] {"false", next.getX() + "", next.getY() + "", next.getZ() + ""};
		} else {
			MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Format.DARK_RED.formatCode() + "TMR: Walking path was null...");
			Minecraft.getTMR().getScript().stop();
		}
		return new String[] {"false", "null", "null", "null"};
	}
	
	/**
	 * Makes an
	 * {@link brennfleck.jarod.pathing.AStar#AStar(Location, Location, int)
	 * AStar} path and returns the path made, or returns null if no path can be
	 * made.
	 */
	public static AStar makePath(Location l) {
		try {
			l = World.getMostSuitableStandingLocation(l);
			final Location a = World.getMostSuitableStandingLocation(new Location(getRealPlayer().posX, getRealPlayer().posY, getRealPlayer().posZ));
			AStar path = new AStar(a, l, 100);
			ArrayList<Tile> thePath = path.iterate();
			if(path.getPathingResult() == PathingResult.SUCCESS) {
				abovePath = new ArrayList<PreciseLocation>();
				ArrayList<Location> theRealPath = new ArrayList<Location>();
				for(Tile t : thePath) {
					theRealPath.add(t.getLocation(a));
					abovePath.add(t.getLocation(a).shift(0, 1.0D, 0));
				}
				currentPath = theRealPath;
				pathStartLocation = a;
				return path;
			} else {
				MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Format.DARK_RED.formatCode() + "TMR: Pathing result was bad... Very bad...");
				clearPathData();
			}
		} catch(InvalidPathException e) {
			e.printStackTrace();
			System.err.println(e.getErrorReason());
			clearPathData();
			MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Format.DARK_RED.formatCode() + MinecraftForm.Format.BOLD.formatCode() + "TMR: Unable to create path!");
			MinecraftForm.sendMessageToLocalChatBox(MinecraftForm.Format.DARK_RED.formatCode() + MinecraftForm.Format.BOLD.formatCode() + "TMR: Reason: " + e.getErrorReason());
			if(Minecraft.getTMR().getScript() != null) Minecraft.getTMR().getScript().stop();
		}
		return null;
	}
	
	/**
	 * Clears all path data that has been previously used.
	 */
	public static void clearPathData() {
		currentPath = null;
		abovePath = null;
		pathStartLocation = null;
	}
	
	private static Location walkHelper() {
		boolean inLiquid = getRealPlayer().isInWater() || getRealPlayer().handleLavaMovement();
		double vectorY = (vec3Helper(pathStartLocation)[1] + 1) - theActualPlayer.getLocation("").getY();
		boolean shouldJump = vectorY > 0;
		if(shouldJump || inLiquid) jump();
		double shift = (double) ((int) (getRealPlayer().width + 1.0F)) * 0.5D;
		Location t = currentPath.get(0);
		faceLocation(new PreciseLocation(t.getX() + shift, t.getY() + 1.0D + shift, t.getZ() + shift));
		move(FORWARD, true);
		Location hLoc = World.getMostSuitableStandingLocation(theActualPlayer.getLocation(""));
		if(hLoc.equals(t)) {
			currentPath.remove(0);
			abovePath.remove(0);
		}
		return t;
	}
	
	/**
	 * Pitches and turns the head to face the
	 * {@link brennfleck.jarod.tmr.scripts.world.Entity#Entity}'s head.
	 */
	public static void faceEntity(Entity e) {
		faceLocation(e.getPreciseLocation(EYE));
	}
	
	/**
	 * Faces the player to a
	 * {@link brennfleck.jarod.tmr.scripts.world.PreciseLocation#PreciseLocation
	 * PreciseLocation}.
	 */
	public static void faceLocation(PreciseLocation location) {
		double[] vecHelp = vec3Helper(location);
		double vectorX = vecHelp[0];
		double vectorY = vecHelp[1];
		double vectorZ = vecHelp[2];
		double degree = BrennyAngle.getAngle(new BrennyPoint(theActualPlayer.getPreciseLocation("").getX(), theActualPlayer.getPreciseLocation("").getZ()), new BrennyPoint(vectorX, vectorZ));
		while(degree < 0.0D)
			degree += 360.0D;
		rotateHead(degree);
		degree = BrennyHelpful.getAngle(Math.sqrt((vectorX * vectorX) + (vectorZ * vectorZ)), vectorY - theActualPlayer.getPreciseLocation(EYE).getY());
		pitchHead(degree);
	}
	
	/**
	 * Returns the
	 * {@link brennfleck.jarod.tmr.scripts.world.PreciseLocation#PreciseLocation
	 * PreciseLocation} as a double array.
	 */
	public static double[] vec3Helper(PreciseLocation loc) {
		return new double[] {loc.getX(), loc.getY(), loc.getZ()};
	}
	
	/**
	 * Sets the players speed precisely. This is cleared with every update of
	 * the player.
	 */
	public static void preciseSpeed(float forwardSpeed, float strafeSpeed) {
		forwardSpeed = forwardSpeed > 1.0F ? 1.0F : forwardSpeed;
		strafeSpeed = strafeSpeed > 1.0F ? 1.0F : strafeSpeed;
		((TmrMovementInputFromOptions) getRealPlayer().movementInput).preciseSpeed(strafeSpeed, forwardSpeed);
	}
	
	/**
	 * Sets the player's yaw rotation to face a bearing.
	 * 
	 * @see {@link #NORTH}
	 * @see {@link #SOUTH}
	 * @see {@link #EAST}
	 * @see {@link #WEST}
	 */
	public static void faceBearing(int bearing) {
		if(bearing < 200 || bearing > 203) {
			System.out.println("BAD DIRECTION");
			return;
		}
		getRealPlayer().rotationYaw = bearing - 200;
	}
	
	/**
	 * Sets the player's yaw rotation to face a specific angle.
	 */
	public static void rotateHead(double headAngleMustBe) {
		headAngleMustBe += 180.0D;
		while(headAngleMustBe <= -180.0D)
			headAngleMustBe += 360.0D;
		while(headAngleMustBe >= 180.0D)
			headAngleMustBe -= 360.0D;
		getRealPlayer().rotationYaw = (float) headAngleMustBe;
	}
	
	/**
	 * Adds the <code>degree</code> passed to the current yaw rotation of the
	 * player.
	 */
	public static void addRotationToHead(float degree) {
		rotateHead(theActualPlayer.getHeadAngles()[0] + degree);
	}
	
	/**
	 * Sets the player's head pitch. This takes a view pitch and is converted
	 * into camera pitch. <li>+90 is vertically up.</li> <li>-90 is vertically
	 * down.</li> <li>0 is horizontal.</li> <li>45 is diagonally up.</li> <li>
	 * -45 is diagonally down.</li>
	 */
	public static void pitchHead(double degree) {
		while(degree > 90.0D)
			degree -= 180.0D;
		while(degree < -90.0D)
			degree += 180.0D;
		getRealPlayer().rotationPitch = (float) -degree;
	}
	
	/**
	 * Toggles the player to swing their currently equipped item.
	 */
	public static void swingItem(boolean toggle) {
		isSwingingItem = toggle;
	}
	
	/**
	 * Returns whether or not the player has been told to swing by a script.
	 */
	public static boolean isSwingingItem() {
		return isSwingingItem;
	}
	
	/**
	 * Tells the player to interact once. Such as when placing blocks,
	 * interacting with entities, interacting with objects, etc.
	 * 
	 * @see {@link #useItem(boolean)}
	 */
	public static void interact() {
		isInteracting = true;
	}
	
	/**
	 * Returns whether or not the player is interacting. Whenever this is
	 * called, it also tells the player to stop interacting.
	 * 
	 * @see {@link #isUsingItem()}
	 */
	public static boolean isInteracting() {
		boolean tmp = isInteracting;
		isInteracting = false;
		return tmp;
	}
	
	/**
	 * Toggles whether or not to use the currently equipped item, such as eating
	 * food.
	 * 
	 * @see {@link #interact}
	 */
	public static void useItem(boolean toggle) {
		isUsingItem = toggle;
	}
	
	/**
	 * Returns whether or not the player should be 'using' the currently
	 * equipped item.
	 */
	public static boolean isUsingItem() {
		return isUsingItem;
	}
	
	/**
	 * Resets all toggles and data that have been set for the player.
	 */
	public static void resetAttributes() {
		for(int i = 100; i < 104; i++)
			move(i, false);
		useItem(false);
		isInteracting = false;
		swingItem(false);
		preciseSpeed(0.0F, 0.0F);
		sprint(false);
		sneak(false);
		clearPathData();
	}
	
	/**
	 * Checks whether or not we can interact with the player.
	 */
	public static final boolean isValid() {
		boolean valid = true;
		if(theActualPlayer == null) valid = false;
		if(theActualPlayer != null && theActualPlayer.theRealEntity == null) valid = false;
		if(!valid) System.out.println("Player is bad!");
		return valid;
	}
}