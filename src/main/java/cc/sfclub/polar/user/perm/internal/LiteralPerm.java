package cc.sfclub.polar.user.perm.internal;

import cc.sfclub.polar.user.data.User;
import cc.sfclub.polar.user.perm.Perm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LiteralPerm extends Perm {
    private String literal;
    @Override
    public String asLiteralNode() {
        return literal;
    }

    @Override
    public Result hasPermission(User user, Result regexResult) {
        return regexResult;
    }
}
