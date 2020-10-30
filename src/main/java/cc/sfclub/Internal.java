package cc.sfclub;

import java.lang.annotation.Documented;

/**
 * 带有这个注解的方法表示仅PolarCore内部使用，不要调用。
 */
@Documented
@Internal
public @interface Internal {
}
