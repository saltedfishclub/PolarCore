package cc.sfclub.user.perm;

import cc.sfclub.core.modules.Core;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Perm {
    private final String node;

    public static Result compare(String orig, String target) {
        Result result = Result.FAILED;
        if (target.startsWith("-") && orig.matches(target.replaceFirst("-", ""))) {
            result = Result.BANNED;
        } else {
            if (orig.matches(target)) result = Result.SUCCEED;
        }
        if (Core.get().config().isDebug()) {
            Core.getLogger().info("[DEBUG][Perm] Compare: {} , {} == {}", orig, target, result);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (obj instanceof String) {
            String perm = (String) obj;
            return compare(node, perm) == Result.SUCCEED;
        }
        if (obj instanceof Perm) {
            Perm perm = (Perm) obj;
            return compare(node, perm.node) == Result.SUCCEED;
        }
        return false;
    }

    public enum Result {
        FAILED, BANNED, SUCCEED
    }
}
