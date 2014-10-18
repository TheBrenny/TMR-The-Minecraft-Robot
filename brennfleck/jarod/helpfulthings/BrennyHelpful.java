package brennfleck.jarod.helpfulthings;

import java.util.Random;

import net.minecraft.util.MathHelper;

/**
 * A collection of helpful methods and functions.s
 * 
 * @author Jarod Brennfleck
 */
public class BrennyHelpful {
	/**
	 * Returns an angle according to the opposite and adjacent sides of the
	 * angle.
	 */
	public static double getAngle(double opp, double adj) {
		return (double) Math.toDegrees(Math.atan2(opp, adj)) - 90.0D;
	}
	
	/**
	 * Returns the <code>number</code> raised to the power of the
	 * <code>exponent</code>.
	 */
	public static double powerOf(double number, int exponent) {
		for(int i = 1; i <= exponent; i++)
			number *= i;
		return number;
	}
	
	/**
	 * Returns a percentage.
	 */
	public static float getPercentage(float amount, float max) {
		return (amount / max) * 100.0F;
	}
	
	/**
	 * All methods and functions within this class have been copied directly
	 * from Minecraft's MathHelper class.
	 * 
	 * @author Jarod Brennfleck
	 */
	public static class MathHelpful {
		/**
		 * Sin looked up in the sin table.
		 */
		public static final float sin(float angle) {
			return MathHelper.sin(angle);
		}
		
		/**
		 * Cos looked up in the sin table with the appropriate offset.
		 */
		public static final float cos(float angle) {
			return MathHelper.cos(angle);
		}
		
		/**
		 * Returns the square root of <code>num</code> as a decimal number.
		 */
		public static final float sqrt_float(float num) {
			return (float) Math.sqrt(num);
		}
		
		/**
		 * @see {@link #sqrt_float(float)}
		 */
		public static final double sqrt_double(double num) {
			return (double) Math.sqrt(num);
		}
		
		/**
		 * Returns the greatest integer less than or equal to the float
		 * argument.
		 */
		public static int floor_float(float num) {
			int var1 = (int) num;
			return num < (float) var1 ? var1 - 1 : var1;
		}
		
		/**
		 * Returns the greatest integer less than or equal to the double
		 * argument.
		 */
		public static int floor_double(double num) {
			int var2 = (int) num;
			return num < (double) var2 ? var2 - 1 : var2;
		}
		
		/**
		 * <code>Long</code> version of floor_double.
		 */
		public static long floor_double_long(double num) {
			long var2 = (long) num;
			return num < (double) var2 ? var2 - 1L : var2;
		}
		
		/**
		 * Returns <code>num</code> cast as an integer, and no greater than
		 * <code>Integer.MAX_VALUE - 1024</code>.
		 */
		public static int truncateDoubleToInt(double num) {
			return (int) (num + 1024.0D) - 1024;
		}
		
		/**
		 * Returns the absolute value of an int.
		 */
		public static float abs(float num) {
			return num >= 0.0F ? num : -num;
		}
		
		/**
		 * @see {@link #abs(float)}
		 */
		public static int abs_int(int num) {
			return num >= 0 ? num : -num;
		}
		
		/**
		 * Rounds up the passed int decimal number.
		 */
		public static int ceiling_float_int(float num) {
			int var1 = (int) num;
			return num > (float) var1 ? var1 + 1 : var1;
		}
		
		/**
		 * @see {@link #ceiling_float_int(float)}
		 */
		public static int ceiling_double_int(double num) {
			int var2 = (int) num;
			return num > (double) var2 ? var2 + 1 : var2;
		}
		
		/**
		 * Returns the value of the first parameter, clamped to be within the
		 * lower and upper limits given by the second and third parameters.
		 */
		public static int clamp_int(int num, int min, int max) {
			return num < min ? min : (num > max ? max : num);
		}
		
		/**
		 * @see {@link #clamp_int(int, int, int)}
		 */
		public static float clamp_float(float num, float min, float max) {
			return num < min ? min : (num > max ? max : num);
		}
		
		/**
		 * @see {@link #clamp_int(int, int, int)}
		 */
		public static double clamp_double(double num, double min, double max) {
			return num < min ? min : (num > max ? max : num);
		}
		
		/**
		 * I have no idea what this does... Please push request if you know...
		 */
		public static double denormalizeClamp(double par1, double par2, double par3) {
			return par3 < 0.0D ? par1 : (par3 > 1.0D ? par2 : par1 + (par2 - par1) * par3);
		}
		
		/**
		 * Maximum of the absolute value of two numbers.
		 */
		public static double abs_max(double num, double max) {
			if(num < 0.0D) num = -num;
			if(max < 0.0D) max = -max;
			return num > max ? num : max;
		}
		
		/**
		 * Buckets an integer with specifed bucket sizes, forcing return a
		 * positive integer. Args: i, bucketSize
		 */
		public static int bucketInt(int num, int bucket) {
			return num < 0 ? -((-num - 1) / bucket) - 1 : num / bucket;
		}
		
		/**
		 * Tests if a string is null or of length zero.
		 */
		public static boolean stringNullOrLengthZero(String str) {
			return str == null || str.length() == 0;
		}
		
		/**
		 * Returns a random integer between the min and max.
		 */
		public static int getRandomIntegerInRange(Random random, int min, int max) {
			return min >= max ? min : random.nextInt(max - min + 1) + min;
		}
		
		/**
		 * @see {@link #getRandomIntegerInRange(Random, int, int)}
		 */
		public static float getRandomFloatInRange(Random random, float min, float max) {
			return min >= max ? min : random.nextFloat() * (max - min) + min;
			// latter: randFloat * range + min
		}
		
		/**
		 * @see {@link #getRandomIntegerInRange(Random, int, int)}
		 */
		public static double getRandomDoubleInRange(Random random, double min, double max) {
			return min >= max ? min : random.nextDouble() * (max - min) + min;
		}
		
		/**
		 * Returns the average from a collection of <code>long</code> values.
		 */
		public static double average(long... collection) {
			long var1 = 0L;
			for(long l : collection)
				var1 += l;
			return (double) var1 / (double) collection.length;
		}
		
		/**
		 * The angle is reduced to an angle between -180 and +180 by mod, and a
		 * 360 check.
		 */
		public static float wrapAngleTo180_float(float angle) {
			angle %= 360.0F;
			if(angle >= 180.0F) {
				angle -= 360.0F;
			}
			if(angle < -180.0F) {
				angle += 360.0F;
			}
			return angle;
		}
		
		/**
		 * @see {@link #wrapAngleTo180_float(float)}
		 */
		public static double wrapAngleTo180_double(double angle) {
			angle %= 360.0D;
			if(angle >= 180.0D) angle -= 360.0D;
			if(angle < -180.0D) angle += 360.0D;
			return angle;
		}
		
		/**
		 * Parses the string as a number or returns the second parameter if it
		 * fails.
		 */
		public static int parseIntWithDefault(String str, int backup) {
			int var2 = backup;
			try {
				var2 = Integer.parseInt(str);
			} catch(Throwable var4) {
				;
			}
			return var2;
		}
		
		/**
		 * Parses the string as a number or returns the second parameter if it
		 * fails. This value is capped to <code>max</code>.
		 */
		public static int parseIntWithDefaultAndMax(String str, int backup, int max) {
			int var3 = backup;
			try {
				var3 = Integer.parseInt(str);
			} catch(Throwable var5) {
				;
			}
			if(var3 < max) {
				var3 = max;
			}
			return var3;
		}
		
		/**
		 * @see {@link #parseIntWithDefault(String, int)}
		 */
		public static double parseDoubleWithDefault(String str, double backup) {
			double var3 = backup;
			try {
				var3 = Double.parseDouble(str);
			} catch(Throwable var6) {
				;
			}
			return var3;
		}
		
		/**
		 * @see {@link #parseIntWithDefaultAndMax(String, int, int)}
		 */
		public static double parseDoubleWithDefaultAndMax(String str, double backup, double max) {
			double var5 = backup;
			try {
				var5 = Double.parseDouble(str);
			} catch(Throwable var8) {
				;
			}
			if(var5 < max) {
				var5 = max;
			}
			return var5;
		}
		
		/**
		 * Returns the maximum number in the list.
		 */
		public static int max_int(int... ints) {
			if(ints == null) return -1;
			int max = ints[0];
			for(int i : ints)
				max = i > max ? i : max;
			return max;
		}
		
		/**
		 * Returns the maximum number and it's index in the list.
		 */
		public static int[] maxAndIndex_int(int... ints) {
			if(ints == null) return new int[] {-1, -1};
			int max = ints[0];
			int index = 0;
			for(int i = 0; i < ints.length; i++) {
				if(ints[i] > max) max = ints[(index = i)];
			}
			return new int[] {max, index};
		}
		
		/**
		 * @see {@link #max_int(int...)}
		 */
		public static double max_double(double... doubles) {
			if(doubles == null) return -1;
			double max = doubles[0];
			for(double i : doubles)
				max = i > max ? i : max;
			return max;
		}
		
		/**
		 * @see {@link #maxAndIndex_int(int...)}
		 */
		public static double[] maxAndIndex_double(double... doubles) {
			if(doubles == null) return new double[] {-1, -1};
			double max = doubles[0];
			int index = 0;
			for(int i = 0; i < doubles.length; i++) {
				if(doubles[i] > max) max = doubles[(index = i)];
			}
			return new double[] {max, index};
		}
		
		/**
		 * @see {@link #max_int(int...)}
		 */
		public static float max_float(float... floats) {
			if(floats == null) return -1;
			float max = floats[0];
			for(float i : floats)
				max = i > max ? i : max;
			return max;
		}
		
		/**
		 * @see {@link #maxAndIndex_int(int...)}
		 */
		public static float[] maxAndIndex_float(float... floats) {
			if(floats == null) return new float[] {-1, -1};
			float max = floats[0];
			int index = 0;
			for(int i = 0; i < floats.length; i++) {
				if(floats[i] > max) max = floats[(index = i)];
			}
			return new float[] {max, index};
		}
		
		/**
		 * Returns the input value rounded up to the next highest power of two.
		 */
		public static int roundUpToPowerOfTwo(int p_151236_0_) {
			int var1 = p_151236_0_ - 1;
			var1 |= var1 >> 1;
			var1 |= var1 >> 2;
			var1 |= var1 >> 4;
			var1 |= var1 >> 8;
			var1 |= var1 >> 16;
			return var1 + 1;
		}
		
		/**
		 * Is the given value a power of two? (1, 2, 4, 8, 16, ...)
		 */
		private static boolean isPowerOfTwo(int i) {
			return i != 0 && (i & i - 1) == 0;
		}
		
		/**
		 * Uses a B(2, 5) De Bruijn sequence and a lookup table to efficiently
		 * calculate the log-base-two of the given value. Optimized for cases
		 * where the input value is a power-of-two. If the input value is not a
		 * power-of-two, then subtract 1 from the return value.
		 */
		private static int calculateLogBaseTwoDeBruijn(int num) {
			return MathHelper.calculateLogBaseTwo(num);
		}
		
		/**
		 * Efficiently calculates the floor of the base-2 log of an integer
		 * value. This is effectively the index of the highest bit that is set.
		 * For example, if the number in binary is 0...100101, this will return
		 * 5.
		 */
		public static int calculateLogBaseTwo(int p_151239_0_) {
			return calculateLogBaseTwoDeBruijn(p_151239_0_) - (isPowerOfTwo(p_151239_0_) ? 0 : 1);
		}
	}
}