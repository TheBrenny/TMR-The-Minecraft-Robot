package brennfleck.jarod.tmr.scripts;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Must be @Inherited to be added to the script list, this class defines the
 * name, author, category and version of the script @Inheriting this.
 * 
 * @author Jarod Brennfleck
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptManifest {
	/**
	 * The name of this script.
	 */
	String name();
	
	/**
	 * The author or group who created this script.
	 */
	String author() default "";
	
	/**
	 * The category of this script.
	 */
	Category category() default Category.GENERAL;
	
	/**
	 * The version of this script.
	 */
	String version() default "1.0";
	
	/**
	 * The categories used in the script manifest.
	 * 
	 * @author Jarod Brennfleck
	 */
	public enum Category {
		GENERAL("General"),
		WALKING("Walking"),
		MINING("Mining"),
		COMBAT("Combat"),
		CRAFTING("Crafting");
		
		private String name;
		
		Category(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
}