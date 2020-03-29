package cc.sfclub.polar;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandFilter {
    /**
     * @return command provider regex
     */
    String provider() default ".*";

    /**
     * @return command aliases(spilt by ',')
     */
    String alias() default "";

    /**
     * @return parent command
     */
    String parent() default "";
}
