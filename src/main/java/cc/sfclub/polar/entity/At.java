package cc.sfclub.polar.entity;

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
