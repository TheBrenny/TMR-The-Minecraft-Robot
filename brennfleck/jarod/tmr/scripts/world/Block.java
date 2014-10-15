package brennfleck.jarod.tmr.scripts.world;

import brennfleck.jarod.pathing.AStar;
import brennfleck.jarod.tmr.scripts.player.Item;
import brennfleck.jarod.tmr.scripts.player.Item.ItemType;


import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;

public class Block {
	private net.minecraft.block.Block actualBlock;
	private int idNum;
	private String name;
	private float slipperiness;
	private SoundType stepSound;
	private boolean canProvidePower;
	private boolean isCollidable;
	private boolean isNormalCube;
	private Material material;
	
	public Block(net.minecraft.block.Block block) {
		this.idNum = net.minecraft.block.Block.getIdFromBlock(block);
		this.name = net.minecraft.block.Block.blockRegistry.getNameForObject(block);
		this.slipperiness = block.slipperiness;
		this.stepSound = block.stepSound;
		this.canProvidePower = block.canProvidePower();
		this.isCollidable = !AStar.canBlockBeWalkedThrough(this.idNum);
		this.isNormalCube = block.isBlockNormalCube();
		this.material = Material.getMaterial(block.getMaterial());
	}
	
	public Item.ItemType getBestTool() {
		if(material.isAnyOf(Material.IRON, Material.ANVIL,Material.ROCK)) return ItemType.PICKAXE;
		if(material.isAnyOf(Material.GRASS, Material.GROUND, Material.SAND, Material.SNOW)) return ItemType.SHOVEL;
		return ItemType.OTHER;
	}
	public int getID() {
		return this.idNum;
	}
	public boolean isCollidable() {
		return this.isCollidable;
	}
	public String toString() {
		return "id=" + idNum + ", name=" + name + ", collides=" + isCollidable;
	}
	
	public enum Material {
		AIR(net.minecraft.block.material.Material.air),
		IRON(net.minecraft.block.material.Material.iron),
		ICE(net.minecraft.block.material.Material.ice),
		ANVIL(net.minecraft.block.material.Material.anvil),
		CARPET(net.minecraft.block.material.Material.carpet),
		CIRCUITS(net.minecraft.block.material.Material.circuits),
		GRASS(net.minecraft.block.material.Material.grass),
		GROUND(net.minecraft.block.material.Material.ground),
		GLASS(net.minecraft.block.material.Material.glass),
		WOOD(net.minecraft.block.material.Material.wood),
		ROCK(net.minecraft.block.material.Material.rock),
		SAND(net.minecraft.block.material.Material.sand),
		SNOW(net.minecraft.block.material.Material.snow);
		
		private net.minecraft.block.material.Material material;
		
		Material(net.minecraft.block.material.Material material) {
			this.material = material;
		}
		
		public boolean isAnyOf(Material... mat) {
			for(Material m : mat) {
				if(m == this) return true;
			}
			return false;
		}
		
		public static Material getMaterial(net.minecraft.block.material.Material material) {
			for(Material mat : Material.values()) {
				if(mat.material == material) {
					return mat;
				}
				if(material == net.minecraft.block.material.Material.craftedSnow) return SNOW;
			}
			return Material.AIR;
		}
	}
}