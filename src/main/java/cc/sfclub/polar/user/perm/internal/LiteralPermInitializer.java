package cc.sfclub.polar.user.perm.internal;

import cc.sfclub.polar.user.perm.Perm;
import cc.sfclub.polar.user.perm.PermInitializer;

public class LiteralPermInitializer extends PermInitializer<LiteralPerm> {
    @Override
    public Perm initialize(String data) {
        return Perm.of(data);
    }

}
