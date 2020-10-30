package cc.sfclub.catcode.entities;

/**
 * 纯文本
 */
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