package cc.sfclub.polar.utils;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.user.Group;
import org.nutz.dao.Cnd;

import java.util.ArrayList;

public final class PermUtil {
    public static ArrayList<String> getDefaults(String group) {
        Group g = getGroup(group);
        ArrayList<String> str = new ArrayList<>();
        if (g != null) {
            if (g.extend != null) {
                Group e;
                if (getGroup(g.extend) != null) {
                    str.addAll(getDefaults(g.extend));
                    str.addAll(g.nodes);
                    return str;
                } else {
                    Core.getLogger().warn("Group {} extends a NULL!!", group);
                }
            }
            return g.nodes;
        } else {
            return new ArrayList<>();
        }
    }

    public static void addGroup(Group group) {
        Core.getInstance().getDao().insert(group);
        Core.getLogger().info(group.pGroup + " has been added to DB.");
        if (Core.getConf().debug) {
            Core.getLogger().info("with these permissions:");
            group.nodes.forEach(Core.getLogger()::info);
        }
    }

    public static void delGroup(Group group) {
        Core.getInstance().getDao().delete(group);
    }

    public static Group getGroup(String name) {
        return Core.getInstance().getDao().fetch(Group.class, Cnd.where("pGroup", "=", name));
    }

    public static Result compare(String orig, String target) {
        Result result = Result.FAILED;
        if (target.startsWith("-") && orig.matches(target.replaceFirst("-", ""))) {
            result = Result.BANNED;
        } else {
            if (orig.matches(target)) result = Result.SUCCEED;
        }
        if (Core.getConf().debug) {
            Core.getLogger().info("[DEBUG][Perm] Compare: {} , {} == {}", orig, target, result);
        }
        return result;
    }

    public enum Result {
        FAILED, BANNED, SUCCEED
    }
}
