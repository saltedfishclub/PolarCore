package cc.sfclub.user.perm;

import cc.sfclub.core.Core;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Perm {
    private String node;

    public Perm(String node) {
        this.node = node;
    }

    public Perm() {

    }

    public static Result compare(String orig, String target) {
        Result result = Result.FAILED;
        if (target.startsWith("-") && target.matches(orig.replaceFirst("-", ""))) {
            result = Result.BANNED;
        } else {
            if (target.matches(orig)) result = Result.SUCCEED;
        }
        if (Core.get() != null && Core.get().config().isDebug()) {
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
