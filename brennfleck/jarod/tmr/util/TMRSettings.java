package brennfleck.jarod.tmr.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.minecraft.util.MathHelper;
import brennfleck.jarod.helpfulthings.BrennyHelpful;

public class TMRSettings {
	public float mouseClickSensitivity;
	public float mouseMoveSensitivity;
	public float keyPressSensitivity;
	public boolean showGame;
	private File optionsFile;
	
	public TMRSettings(File opFileDir) {
		mouseClickSensitivity = 0.1F;
		mouseMoveSensitivity = 0.1F;
		keyPressSensitivity = 0.1F;
		showGame = true;
		optionsFile = new File(opFileDir, "tmr-options.txt");
		loadOptions();
	}
	
	public void loadOptions() {
		try {
			if(this.optionsFile == null || !this.optionsFile.exists()) return;
			BufferedReader buffRead = new BufferedReader(new FileReader(this.optionsFile));
			String lineIn = "";
			while((lineIn = buffRead.readLine()) != null) {
				try {
					String[] split = lineIn.split(":");
					if(split[0].equals("mouseClickSensitivity")) {
						this.mouseClickSensitivity = this.parseFloat(split[1]);
					}
					if(split[0].equals("mouseMoveSensitivity")) {
						this.mouseMoveSensitivity = this.parseFloat(split[1]);
					}
					if(split[0].equals("keyPressSensitivity")) {
						this.keyPressSensitivity = this.parseFloat(split[1]);
					}
					if(split[0].equals("showGame")) {
						this.showGame = this.parseBoolean(split[1]);
					}
				} catch(Exception e) {
					System.err.println("Bad option: " + lineIn);
				}
			}
			buffRead.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveOptions() {
		String[] options = new String[] {"mouseClickSensitivity:", "mouseMoveSensitivity:", "keyPressSensitivity:", "showGame:"};
		int index = -1;
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(optionsFile));
			index++;
			pw.println(options[index++] + mouseClickSensitivity);
			pw.println(options[index++] + mouseMoveSensitivity);
			pw.println(options[index++] + keyPressSensitivity);
			pw.println(options[index++] + showGame);
			pw.flush();
			pw.close();
		} catch(Exception e) {
			if(index >= 0 && index < options.length) {
				System.err.println("Failed writing option: " + options[index]);
			} else {
				System.err.println("Failed doing something other than writing an option. Index: " + index);
			}
			System.err.println("Reason: " + e.getLocalizedMessage());
		}
	}
	
	private float parseFloat(String s) {
		return s.equals("true") ? 1.0F : (s.equals("false") ? 0.0F : Float.parseFloat(s));
	}
	
	private boolean parseBoolean(String s) {
		return s.equals("true");
	}
	
	public static enum Options {
		MOUSE_CLICK_SENSITIVITY("Mouse Click Sensitivity", true, false, 0.1F, 1.0F, 0.1F),
		MOUSE_MOVE_SENSITIVITY("Mouse Move Sensitivity", true, false, 0.1F, 1.0F, 0.1F),
		KEY_PRESS_SENSITIVITY("Key Press Sensitivity", true, false, 0.1F, 1.0F, 0.1F),
		SHOW_GAME("Show game visuals", false, true);
		private String name;
		private boolean isFloat;
		private boolean isBoolean;
		private float valueMin;
		private float valueMax;
		private float valueStep;
		
		private Options(String name, boolean isFloat, boolean isBoolean) {
			this(name, isFloat, isBoolean, 0.0F, 1.0F, 1.0F);
		}
		
		private Options(String name, boolean isFloat, boolean isBoolean, float min, float max, float step) {
			this.name = name;
			this.isFloat = isFloat;
			this.isBoolean = isBoolean;
			this.valueMin = min;
			this.valueMax = max;
			this.valueStep = step;
		}
		
		public String getName() {
			return name;
		}
		
		public boolean isFloat() {
			return isFloat;
		}
		
		public boolean isBoolean() {
			return isBoolean;
		}
		
		public float getValueMin() {
			return valueMin;
		}
		
		public float getValueMax() {
			return valueMax;
		}
		
		public float getValueStep() {
			return valueStep;
		}
		
		protected float snapToStep(float num) {
			if(this.valueStep > 0.0F) {
				num = this.valueStep * (float) Math.round(num / this.valueStep);
			}
			return num;
		}
		
		public float snapToStepClamp(float num) {
			return BrennyHelpful.MathHelpful.clamp_float(snapToStep(num), this.valueMin, this.valueMax);
		}
		
		public float normalizeValue(float num) {
			return MathHelper.clamp_float((this.snapToStepClamp(num) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
		}
		
		public float denormalizeValue(float num) {
			return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(num, 0.0F, 1.0F));
		}
	}
}