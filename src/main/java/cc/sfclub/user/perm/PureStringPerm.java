package cc.sfclub.user.perm;

import cc.sfclub.user.User;

/**
 * 纯文本模板
 */
public class PureStringPerm extends Perm {
    private final String node;

    public PureStringPerm(String node) {
        super();
        this.node = node;
    }

    @Override
    public Result hasPermission(User user, Result regexYes) {
        return regexYes;
    }

    @Override
    public String toString() {
        return node;
    }
}
