package cc.sfclub.polar.user.perm;

import cc.sfclub.polar.user.Perm;
import cc.sfclub.polar.user.User;
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
