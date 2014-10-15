package brennfleck.jarod.tmr.scripts;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptManifest {
	String name();
	String author() default "";
	String category() default "";
	String version() default "1.0";
}