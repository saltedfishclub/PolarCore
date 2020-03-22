package cc.sfclub.polar.user;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.utils.PermUtil;
import lombok.Getter;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.UUID;

/**
 * User object also a sql table.
 */
@SuppressWarnings("unused")
@Table("user")
public class User {
    /**
     * User group.
     */
    public String pGroup;
    /**
     * User Name
     */
    public String userName;
    /**
     * User provider
     */
    public String provider;
    /**
     * Special permissions
     */
    public ArrayList<String> permissions = new ArrayList<>();
    /**
     * User id
     */
    @Getter
    private long UID;
    /**
     * User Unique ID.For identify convenient
     */
    @Name
    @Getter
    private String uniqueID;

    public User() {

    }

    public User(long UID, String provider, String group) {
        this.UID = UID;
        this.provider = provider;
        this.pGroup = group;
        uniqueID = UUID.randomUUID().toString();
    }

    /**
     * check permission
     *
     * @param permission Permission
     * @return result
     */
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
     * add a special permission
     *
     * @param perm special permission
     */
    public void addPermission(String perm) {
        permissions.add(perm);
        save();
    }

    /**
     * @return json
     */
    @Override
    public String toString() {
        return Core.getGson().toJson(this);
    }

    /**
     * @return special permissions
     */
    public ArrayList<String> permList() {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.addAll(PermUtil.getDefaults(pGroup));
        tmp.addAll(permissions);
        return tmp;
        /*ArrayList<String> a=new ArrayList<>();
        for (String s : dbu.Permissions.split(",")) {
            a.add(s);
        }
        return a;*/
    }

    /**
     * save to database.
     */
    public void save() {
        Core.getInstance().getDao().update(this);
    }
}
