package cc.sfclub.polar.user;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.utils.PermUtil;
import lombok.Getter;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.UUID;

@Table("user")
public class User {
    @Getter
    private long UID;
    public String pGroup;
    public String UserName;
    public String Provider;
    public ArrayList<String> Permissions = new ArrayList<>();
    @Name
    @Getter
    private String UniqueID;

    public User() {

    }

    public User(long UID, String Provider, String Group) {
        this.UID = UID;
        this.Provider = Provider;
        this.pGroup = Group;
        UniqueID = UUID.randomUUID().toString();
    }

    public boolean hasPermission(String Permission) {
        boolean succeed = false;
        boolean banned = false;
        for (String s : PermList()) {
            PermUtil.Result res = PermUtil.compare(Permission, s);
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
        Permissions.add(perm);
        save();
    }

    @Override
    public String toString() {
        return Core.getGson().toJson(this);
    }

    public ArrayList<String> PermList() {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.addAll(Core.getPermManager().getDefaults(pGroup));
        tmp.addAll(Permissions);
        return tmp;
        /*ArrayList<String> a=new ArrayList<>();
        for (String s : dbu.Permissions.split(",")) {
            a.add(s);
        }
        return a;*/
    }

    public void save() {
        Core.getDao().update(this);
    }
}
