package cc.sfclub.user.perm;

import cc.sfclub.user.User;

public class PureStringPerm extends Perm {
    private String node;

    public PureStringPerm(String node) {
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
