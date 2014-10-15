package brennfleck.jarod.pathing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import brennfleck.jarod.helpfulthings.BrennyHelpful;
import brennfleck.jarod.tmr.scripts.world.Location;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * <p>Original by Adamki11s. (<a href="https://forums.bukkit.org/threads/lib-a-pathfinding-algorithm.129786/">Unmodified Library</a>)</p>
 * <p>Modified slightly by Jarod Brennfleck to accommodate for the TMR Player Walking.</p>
 */
public class AStar {
	public static int DEFUALT_RANGE = 70;
	private final int sx, sy, sz, ex, ey, ez;
	private final World w;
	private PathingResult result;
	private HashMap<String, Tile> open = new HashMap<String, Tile>();
	private HashMap<String, Tile> closed = new HashMap<String, Tile>();
	
	private void addToOpenList(Tile t, boolean modify) {
		if(open.containsKey(t.getUID())) {
			if(modify) {
				open.put(t.getUID(), t);
			}
		} else {
			open.put(t.getUID(), t);
		}
	}
	
	private void addToClosedList(Tile t) {
		if(!closed.containsKey(t.getUID())) {
			closed.put(t.getUID(), t);
		}
	}
	
	private final int range;
	private final String endUID;
	
	public AStar(Location start, Location end, int range) throws InvalidPathException {
		// init basic info
		this.w = Minecraft.getMinecraft().theWorld;
		this.sx = (int) start.getX();
		this.sy = (int) start.getY();
		this.sz = (int) start.getZ();
		this.ex = (int) end.getX();
		this.ey = (int) end.getY();
		this.ez = (int) end.getZ();
		this.range = range;
		
		// check if theres a problem
		boolean s = true, e = true;
		int[] maxR = BrennyHelpful.MathHelpful.maxAndIndex_int(abs(sx - ex), abs(sy - ey), abs(sz - ez));
		boolean r = maxR[0] > range;
		String maxRD = "XYZ".charAt(maxR[1]) + "";
		
		if(!(s = this.isLocationWalkable(start)) || !(e = this.isLocationWalkable(end)) || r) {
			throw new InvalidPathException(s, e, range, maxR[0], maxRD);
		}
		
		// if no problem, continue on and finish the rest of the init
		short sh = 0;
		Tile t = new Tile(sh, sh, sh, null);
		t.calculateBoth(sx, sy, sz, ex, ey, ez, true);
		this.open.put(t.getUID(), t);
		this.processAdjacentTiles(t);
		StringBuilder b = new StringBuilder();
		b.append(ex - sx).append(ey - sy).append(ez - sz);
		this.endUID = b.toString();
	}
	
	public Location getEndLocation() {
		return new Location(ex, ey, ez);
	}
	
	public PathingResult getPathingResult() {
		return this.result;
	}
	
	boolean checkOnce = false;
	
	private int abs(int i) {
		return (i < 0 ? -i : i);
	}
	
	public ArrayList<Tile> iterate() {
//		if(!checkOnce) {
//			// invert the boolean flag
//			checkOnce ^= true;
//			if((abs(sx - ex) > range) || (abs(sy - ey) > range) || (abs(sz - ez) > range)) {
//				this.result = PathingResult.NO_PATH;
//				return null;// jump out
//			}
//		}
		// while not at end
		Tile current = null;
		while(canContinue()) {
			// get lowest F cost square on open list
			current = this.getLowestFTile();
			// process tiles
			this.processAdjacentTiles(current);
		}
		if(this.result != PathingResult.SUCCESS) {
			return null;
		} else {
			// path found
			LinkedList<Tile> routeTrace = new LinkedList<Tile>();
			Tile parent;
			routeTrace.add(current);
			while((parent = current.getParent()) != null) {
				routeTrace.add(parent);
				current = parent;
			}
			Collections.reverse(routeTrace);
			return new ArrayList<Tile>(routeTrace);
		}
	}
	
	private boolean canContinue() {
		// check if open list is empty, if it is no path has been found
		if(open.size() == 0) {
			this.result = PathingResult.NO_PATH;
			return false;
		} else {
			if(closed.containsKey(this.endUID)) {
				this.result = PathingResult.SUCCESS;
				return false;
			} else {
				return true;
			}
		}
	}
	
	private Tile getLowestFTile() {
		double f = 0;
		Tile drop = null;
		// get lowest F cost square
		for(Tile t : open.values()) {
			if(f == 0) {
				t.calculateBoth(sx, sy, sz, ex, ey, ez, true);
				f = t.getF();
				drop = t;
			} else {
				t.calculateBoth(sx, sy, sz, ex, ey, ez, true);
				double posF = t.getF();
				if(posF < f) {
					f = posF;
					drop = t;
				}
			}
		}
		// drop from open list and add to closed
		this.open.remove(drop.getUID());
		this.addToClosedList(drop);
		return drop;
	}
	
	private boolean isOnClosedList(Tile t) {
		return closed.containsKey(t.getUID());
	}
	
	// pass in the current tile as the parent
	private void processAdjacentTiles(Tile current) {
		// set of possible walk to locations adjacent to current tile
		HashSet<Tile> possible = new HashSet<Tile>(26);
		for(byte x = -1; x <= 1; x++) {
			for(byte y = -1; y <= 1; y++) {
				for(byte z = -1; z <= 1; z++) {
					if(x == 0 && y == 0 && z == 0) {
						continue;// don't check current square
					}
					Tile t = new Tile((short) (current.getX() + x), (short) (current.getY() + y), (short) (current.getZ() + z), current);
					if(!t.isInRange(this.range)) {
						// if block is out of bounds continue
						continue;
					}
					if(x != 0 && z != 0 && (y == 0 || y == 1)) {
						// check to stop jumping through diagonal blocks
						Tile xOff = new Tile((short) (current.getX() + x), (short) (current.getY() + y), (short) (current.getZ()), current), zOff = new Tile((short) (current.getX()), (short) (current.getY() + y), (short) (current.getZ() + z), current);
						if(!this.isTileWalkable(xOff) && !this.isTileWalkable(zOff)) {
							continue;
						}
					}
					if(this.isOnClosedList(t)) {
						// ignore tile
						continue;
					}
					// only process the tile if it can be walked on
					if(this.isTileWalkable(t)) {
						t.calculateBoth(sx, sy, sz, ex, ey, ez, true);
						possible.add(t);
					}
				}
			}
		}
		for(Tile t : possible) {
			// get the reference of the object in the array
			Tile openRef = null;
			if((openRef = this.isOnOpenList(t)) == null) {
				// not on open list, so add
				this.addToOpenList(t, false);
			} else {
				// is on open list, check if path to that square is better using
				// G cost
				if(t.getG() < openRef.getG()) {
					// if current path is better, change parent
					openRef.setParent(current);
					// force updates of F, G and H values.
					openRef.calculateBoth(sx, sy, sz, ex, ey, ez, true);
				}
			}
		}
	}
	
	private Tile isOnOpenList(Tile t) {
		return (open.containsKey(t.getUID()) ? open.get(t.getUID()) : null);
		/*
		 * for (Tile o : open) { if (o.equals(t)) { return o; } } return null;
		 */
	}
	
	private boolean isTileWalkable(Tile t) {
		Location l = new Location((sx + t.getX()), (sy + t.getY()), (sz + t.getZ()));
		Block b = w.getBlock((int) l.getX(), (int) l.getY(), (int) l.getZ());
		int i = Block.getIdFromBlock(b);
		// lava, fire, wheat and ladders cannot be walked on, and of course air
		// 85, 107 and 113 stops npcs climbing fences and fence gates
		if(i != 10 && i != 11 && i != 51 && i != 59 && i != 65 && i != 0 && i != 85 && i != 107 && i != 113 && !canBlockBeWalkedThrough(i)) {
			// make sure the blocks above are air
			Block gateBlock;
			if(Block.getIdFromBlock(gateBlock = w.getBlock((int) l.getX(), (int) l.getY() + 1, (int) l.getZ())) == 107) {
				// fench gate check, if closed continue
				return (BlockFenceGate.isFenceGateOpen(w.getBlockMetadata((int) l.getX(), (int) l.getY() + 1, (int) l.getZ())) ? canBlockBeWalkedThrough((Block.getIdFromBlock(w.getBlock((int) l.getX(), (int) l.getY() + 2, (int) l.getZ())))) : false);
			}
			return (canBlockBeWalkedThrough(Block.getIdFromBlock(w.getBlock((int) l.getX(), (int) l.getY() + 1, (int) l.getZ()))) && canBlockBeWalkedThrough(Block.getIdFromBlock(w.getBlock((int) l.getX(), (int) l.getY() + 2, (int) l.getZ()))));
		} else {
			return false;
		}
	}
	
	private boolean isLocationWalkable(Location l) {
		Block b = w.getBlock((int) l.getX(), (int) l.getY(), (int) l.getZ());
		int i = Block.getIdFromBlock(b);
		System.out.println(i + ", " + l);
		if(i != 10 && i != 11 && i != 51 && i != 65 && !canBlockBeWalkedThrough(i)) {
			// make sure the blocks above are air or can be walked through
			return (canBlockBeWalkedThrough(Block.getIdFromBlock(w.getBlock((int) l.getX(), (int) l.getY() + 1, (int) l.getZ()))) && canBlockBeWalkedThrough(Block.getIdFromBlock(w.getBlock((int) l.getX(), (int) l.getY() + 2, (int) l.getZ()))));
		} else {
			return false;
		}
	}
	
	public static boolean canBlockBeWalkedThrough(int id) {
		return (id == 0 || id == 6 || id == 30 || id == 31 || id == 32 || id == 37 || id == 38 || id == 39 || id == 40 || id == 50 || id == 55 || id == 59 || id == 63 || id == 66 || id == 75 || id == 76 || id == 78);
	}
	
	@SuppressWarnings("serial")
	public class InvalidPathException extends Exception {
		private final boolean start, end;
		private final int allowedRange, givenRange;
		private final String givenRangeDir;
		
		public InvalidPathException(boolean start, boolean end, int allowedRange, int givenRange, String givenRangeDir) {
			this.start = start;
			this.end = end;
			this.allowedRange = allowedRange;
			this.givenRange = givenRange;
			this.givenRangeDir = givenRangeDir;
		}
		
		public String getErrorReason() {
			StringBuilder sb = new StringBuilder();
			if(!start) {
				sb.append("Start Location was air. ");
			}
			if(!end) {
				sb.append("End Location was air. ");
			}
			if(givenRange > allowedRange) {
				sb.append("Given distance [" + givenRange + "] on [" + givenRangeDir + "] was greater than allowed range [" + allowedRange + "].");
			}
			return sb.toString();
		}
		public String getMessage() {
			return getErrorReason();
		}
		
		public boolean isStartSolid() {
			return !start;
		}
		
		public boolean isEndSolid() {
			return !end;
		}
		
		public boolean isRangeAllowed() {
			return givenRange < allowedRange;
		}
	}
}
