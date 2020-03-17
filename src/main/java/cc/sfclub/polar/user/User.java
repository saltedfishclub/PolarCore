package cc.sfclub.polar.user;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.utils.PermUtil;
import lombok.Getter;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.UUID;

@SuppressWarnings("unused")
@Table("user")
public class User {
    @Getter
    private long UID;
    public String pGroup;
    public String userName;
    public String provider;
    public ArrayList<String> permissions = new ArrayList<>();
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

    public boolean hasPermission(String permission) {
        boolean succeed = false;
        boolean banned = false;
        for (String s : PermList()) {
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

    public void addPermission(String perm) {
        permissions.add(perm);
        save();
    }

    @Override
    public String toString() {
        return Core.getGson().toJson(this);
    }

    public ArrayList<String> PermList() {
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

    public void save() {
        Core.getInstance().getDao().update(this);
    }
}
