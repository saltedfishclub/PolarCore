package cc.sfclub.polar.utils;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.user.Group;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import java.util.ArrayList;

public final class PermUtil {
    Dao dao = Core.getDao();
    public ArrayList<String> getDefaults(String group) {
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

    public void addGroup(Group group) {
        dao.insert(group);
        Core.getLogger().info(group.pGroup + " has been added to DB.");
        if (Core.getConf().debug) {
            Core.getLogger().info("with these permissions:");
            group.nodes.forEach(Core.getLogger()::info);
        }
    }

    public void delGroup(Group group) {
        dao.delete(group);
    }

    public Group getGroup(String name) {
        return dao.fetch(Group.class, Cnd.where("pGroup", "=", name));
    }

    public boolean compare(String orig, String target) {
        return orig.matches(target);
    }
}
