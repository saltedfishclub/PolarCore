package cc.sfclub.catcode.entities;

public class At {

    /**
     * 目标用户ID
     */
    public String userId;
    /**
     * 是否是at所有人
     */
    public boolean all;

    @Override
    public String toString() {
        if (all) {
            return "[AtAll]";
        }
        return "[At:" + userId + "]";
    }
}