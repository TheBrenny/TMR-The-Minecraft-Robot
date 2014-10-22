package net.minecraft.client.gui;

import java.awt.Color;

import brennfleck.jarod.helpfulthings.BrennyHelpful;
import brennfleck.jarod.tmr.TheMinecraftRobot;
import brennfleck.jarod.tmr.scripts.ScriptList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;

public class GuiTmrScriptSelection extends GuiScreen {
	public GuiButton start, stop, pause, unpause, recollect;
	public GuiScreen callback;
	public ScriptSelector scriptSelector;
	public int selectedIndex = -1;
	
	public GuiTmrScriptSelection(GuiScreen callback) {
		this.mc = Minecraft.getMinecraft();
		this.callback = callback;
	}
	
	public void initGui() {
		scriptSelector = new GuiTmrScriptSelection.ScriptSelector();
		boolean yesScript = Minecraft.getTMR().getScript() != null;
		this.buttonList.add(this.start = new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, GuiTmrButton.SCRIPT_START.getTitle()));
		this.buttonList.add(this.pause = new GuiButton(2, this.width / 2 - 154, this.height - 28, 100, 20, GuiTmrButton.SCRIPT_PAUSE.getTitle()));
		this.buttonList.add(this.unpause = new GuiButton(3, this.width / 2 - 50, this.height - 28, 100, 20, GuiTmrButton.SCRIPT_UNPAUSE.getTitle()));
		this.buttonList.add(this.stop = new GuiButton(4, this.width / 2 - 50, this.height - 52, 100, 20, GuiTmrButton.SCRIPT_STOP.getTitle()));
		this.buttonList.add(this.recollect = new GuiButton(5, this.width / 2 + 54, this.height - 52, 100, 20, GuiTmrButton.RECOLLECT.getTitle()));
		this.buttonList.add(new GuiButton(0, this.width / 2 + 54, this.height - 28, 100, 20, "Back"));
		this.updateButtons();
	}
	
	public void actionPerformed(GuiButton button) {
		switch(button.id) {
		case 0:
			Minecraft.getMinecraft().displayGuiScreen(this.callback);
			break;
		case 1:
			if(Minecraft.getTMR().getScript() == null) {
				Minecraft.getTMR().setActiveScript(ScriptList.getInstantiatedScript(this.selectedIndex));
				Minecraft.getTMR().getScript().start();
				Minecraft.getMinecraft().displayGuiScreen(this.callback);
			}
			break;
		case 2:
		case 3:
			Minecraft.getTMR().getScript().pause(button.id == 2 ? true : false);
			break;
		case 4:
			Minecraft.getTMR().getScript().stop();
			break;
		case 5:
			TheMinecraftRobot.collectScripts();
			break;
		}
		updateButtons();
	}
	
	public void updateButtons() {
		boolean yesScript = Minecraft.getTMR().getScript() != null;
		this.start.enabled = !yesScript && selectedIndex != -1;
		this.stop.enabled = yesScript;
		this.pause.enabled = yesScript && Minecraft.getTMR().getScript().getState() == 1;
		this.unpause.enabled = yesScript && Minecraft.getTMR().getScript().getState() == 2;
	}
	
	public void drawScreen(int intVar1, int intVar2, float floatVar3) {
		this.scriptSelector.draw(intVar1, intVar2, floatVar3);
		this.drawCenteredString(this.fontRendererObj, "The Minecraft Robot Scripts", this.width / 2, 10, 16777215);
		super.drawScreen(intVar1, intVar2, floatVar3);
	}
	
	protected void keyTyped(char keyChar, int keyCode) {}
	
	class ScriptSelector extends GuiSlot {
		private static final String __OBFID = "CL_00000712";
		
		public ScriptSelector() {
			super(GuiTmrScriptSelection.this.mc, GuiTmrScriptSelection.this.width, GuiTmrScriptSelection.this.height, 32, GuiTmrScriptSelection.this.height - 64, 16);
		}
		
		protected int getSize() {
			return ScriptList.size();
		}
		
		protected void elementClicked(int index, boolean parBool1, int parInt2, int parInt3) {
			GuiTmrScriptSelection.this.selectedIndex = index;
			GuiTmrScriptSelection.this.updateButtons();
		}
		
		protected boolean isSelected(int index) {
			return index == GuiTmrScriptSelection.this.selectedIndex;
		}
		
		public int getWidth() {
			return GuiTmrScriptSelection.this.width - 12;
		}
		
		protected int getHeight() {
			return ScriptList.size() * this.slotHeight;
		}
		
		protected void drawBackground() {
			GuiTmrScriptSelection.this.drawDefaultBackground();
		}
		
		protected void drawSlot(int index, int slotX, int slotY, int p_148126_4_, Tessellator tesselator, int p_148126_6_, int p_148126_7_) {
			String[] sm = ScriptList.getScriptManifest(index);
			String name = sm[0];
			String version = sm[2];
			String author = sm[1];
			String category = sm[3];
			
			if(BrennyHelpful.MathHelpful.stringNullOrLengthZero(name)) {
				name = "Script " + (index + 1);
			}
			if(BrennyHelpful.MathHelpful.stringNullOrLengthZero(version)) {
				version = "1.0";
			}
			if(BrennyHelpful.MathHelpful.stringNullOrLengthZero(author)) {
				author = "someone";
			}
			if(BrennyHelpful.MathHelpful.stringNullOrLengthZero(category)) {
				category = "All Sorts";
			}
			
			String full = name + " " + version + " by " + author + " in category [" + category + "]";
			
			GuiTmrScriptSelection.this.drawString(GuiTmrScriptSelection.this.fontRendererObj, full, slotX + 2, slotY + 2, new Color(100, 100, 100).getRGB());
			GuiTmrScriptSelection.this.drawString(GuiTmrScriptSelection.this.fontRendererObj, name, slotX + 2, slotY + 2, 16777215);
		}
	}
}