package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInputFromOptions;

public class TmrMovementInputFromOptions extends MovementInputFromOptions {
	private boolean shouldSneak = false;
	private boolean shouldJump = false;
	private float nextStrafe = 0.0F;
	private float nextForward = 0.0F;
	public TmrMovementInputFromOptions(GameSettings gameSettings) {
		super(gameSettings);
	}
	
	public void updatePlayerMoveState() {
		this.moveStrafe = nextStrafe;
		this.moveForward = nextForward;
		this.nextForward = this.nextStrafe = 0.0F;
		
		if(this.gameSettings.keyBindForward.getIsKeyPressed() || Direction.FORWARD.getState()) ++this.moveForward;
		if(this.gameSettings.keyBindBack.getIsKeyPressed() || Direction.BACKWARD.getState()) --this.moveForward;
		if(this.gameSettings.keyBindLeft.getIsKeyPressed() || Direction.LEFT.getState()) ++this.moveStrafe;
		if(this.gameSettings.keyBindRight.getIsKeyPressed() || Direction.RIGHT.getState()) --this.moveStrafe;
		
		if(this.gameSettings.keyBindJump.getIsKeyPressed() || shouldJump) jump = !(shouldJump = false);
		else this.jump = false;
		
		this.sneak = gameSettings.keyBindSneak.getIsKeyPressed() || this.shouldSneak;
		
		if(this.sneak) {
			this.moveStrafe = this.moveStrafe * 0.3F;
			this.moveForward = this.moveForward * 0.3F;
		}
	}
	
	public void move(Direction dir, boolean state) {
		dir.state = state;
	}
	public void preciseSpeed(float strafeSpeed, float forwardSpeed) {
		this.nextForward = forwardSpeed;
		this.nextStrafe = strafeSpeed;
	}
	
	public void jump() {
		this.shouldJump = true;
	}
	
	public void sneak(boolean toggle) {
		this.shouldSneak = toggle;
	}
	
	public static enum Direction {
		NONE(false),
		FORWARD(false),
		BACKWARD(false),
		LEFT(false),
		RIGHT(false);
		
		private boolean state;
		
		Direction(boolean state) {
			this.state = state;
		}
		
		public boolean getState() {
			return this.state;
		}
	}
}