package cc.sfclub.polar;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name() default "";
    String description() default "";
    String perm() default "";
}
