package brennfleck.jarod.tmr.scripts.items;

import net.minecraft.item.EnumAction;

/**
 * Determines what happens the player right clicks.
 * 
 * @author Jarod Brennfleck
 */
public enum ItemUseAction {
	NONE,
	EAT,
	DRINK,
	BLOCK,
	BOW;
	public static ItemUseAction getAppropriate(EnumAction e) {
		//@formatter:off
		switch(e) {
		case eat:	return EAT;
		case drink:	return DRINK;
		case block:	return BLOCK;
		case bow:	return BOW;
		case none:	return NONE;
		default:	return NONE;
		}
		//@formatter:on
	}
}