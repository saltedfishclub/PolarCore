package cc.sfclub.polar;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    /**
     * @return cmd name.
     */
    String name() default "";

    /**
     * @return cmd description
     */
    String description() default "";

    /**
     * @return cmd permissions
     */
    String perm() default "";
}
