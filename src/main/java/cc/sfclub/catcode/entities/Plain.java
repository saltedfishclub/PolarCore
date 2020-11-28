package cc.sfclub.catcode.entities;

import lombok.Builder;

/**
 * 纯文本
 */
@Builder
public class Plain {
    /**
     * 文本内容
     */
    public String text;

    @Override
    public String toString() {
        return "[Plain:" + text + "]";
    }
}