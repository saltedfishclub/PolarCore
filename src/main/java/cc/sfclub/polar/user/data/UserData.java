package cc.sfclub.polar.user.data;

import cc.sfclub.polar.user.Permissible;
import cc.sfclub.polar.user.UserGroup;
import cc.sfclub.polar.user.perm.Perm;
import cc.sfclub.polar.database.converter.PermListConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "t_user_data")
@Data
public class UserData implements Permissible {
    @Id
    @GeneratedValue
    public int uid;
    public String nickName;
    public long registrationTime;
    @Convert(converter=PermListConverter.class)
    public List<Perm> permissionNodes=new LinkedList<>();
    @Convert
    public UserGroup userGroup;

    @Override
    public boolean hasPermission(Perm perm) {
        //// TODO: 02/08/2021
        return permissionNodes.contains(perm);
    }

    @Override
    public boolean hasPermission(String perm) {
        return permissionNodes.contains(Perm.of(perm));
    }

    @Override
    public List<Perm> getPermissions() {
        return permissionNodes;
    }

    @Override
    public void delPermission() {
        ///todo
    }

    @Override
    public void addPermissions(Perm... perms) {

    }
}
