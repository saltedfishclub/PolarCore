package cc.sfclub.polar.user;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.utils.PermUtil;
import lombok.Getter;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;

/**
 * Group. Also a database table
 */
@Table("perm")
public class Group {
    /**
     * Permission nodes
     */
    @Getter
    public ArrayList<String> nodes = new ArrayList<>();
    /**
     * Group Name
     */
    @Name
    public String pGroup;
    /**
     * Extends (single)
     */
    public String extend;
    /**
     * is it a default group?
     */
    public boolean isDefault = false;
    /**
     * sql index.
     */
    @Id
    private transient int index;

    /**
     * save to database
     */
    public void save() {
        Core.getInstance().getDao().update(this);
    }

    public boolean hasPermission(String permission) {
        boolean succeed = false;
        boolean banned = false;
        for (String s : permList()) {
            PermUtil.Result res = PermUtil.compare(permission, s);
            if (res == PermUtil.Result.SUCCEED) {
                succeed = true;
            }
            if (res == PermUtil.Result.BANNED) {
                banned = true;
            }
        }

        return succeed && !banned;
    }

    /**
     * give a special permission
     *
     * @param perm special permission
     */
    public void addPermission(String perm) {
        nodes.add(perm);
        save();
    }

    /**
     * remove a special permission
     *
     * @param perm special permission
     */
    public void delPermission(String perm) {
        nodes.remove(perm);
        save();
    }

    /**
     * @return special permissions
     */
    public ArrayList<String> permList() {
        if (extend.isEmpty()) {
            return nodes;
        }
        ArrayList<String> tmp = new ArrayList<>();
        tmp.addAll(PermUtil.getDefaults(extend));
        tmp.addAll(nodes);
        return tmp;
    }
}
