package brennfleck.jarod.tmr.scripts.items;

import brennfleck.jarod.tmr.scripts.minecraft.MinecraftForm;
import net.minecraft.item.EnumRarity;

/**
 * Determines how rare the item is.
 * 
 * @author Jarod Brennfleck
 */
public enum ItemRarity {
	COMMON(MinecraftForm.Format.WHITE, "Common"),
	UNCOMMON(MinecraftForm.Format.YELLOW, "Uncommon"),
	RARE(MinecraftForm.Format.AQUA, "Rare"),
	EPIC(MinecraftForm.Format.LIGHT_PURPLE, "Epic");
	private MinecraftForm.Format chatFormat;
	private String name;
	
	private ItemRarity(MinecraftForm.Format chatFormat, String name) {
		this.chatFormat = chatFormat;
		this.name = name;
	}
	
	public MinecraftForm.Format getChatFormat() {
		return chatFormat;
	}
	
	public String getName() {
		return name;
	}
	
	public static ItemRarity getAppropriate(EnumRarity e) {
		//@formatter:off
		switch(e) {
		case common:	return COMMON;
		case uncommon:	return UNCOMMON;
		case rare:		return RARE;
		case epic:		return EPIC;
		default:	return COMMON;
		}
		//@formatter:on
	}
}