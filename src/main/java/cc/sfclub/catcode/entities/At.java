package cc.sfclub.catcode.entities;

public class At {
    public long userId;
    public boolean all;

    @Override
    public String toString() {
        if (all) {
            return "[AtAll]";
        }
        return "[At:" + userId + "]";
    }
}