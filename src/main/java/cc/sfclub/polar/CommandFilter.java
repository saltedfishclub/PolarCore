package cc.sfclub.polar;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandFilter {
    String provider() default ".*";

    String alias() default "";
}
