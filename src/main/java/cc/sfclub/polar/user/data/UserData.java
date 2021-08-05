package cc.sfclub.polar.user.data;

import cc.sfclub.polar.database.converter.PermListConverter;
import cc.sfclub.polar.user.Permissible;
import cc.sfclub.polar.user.UserGroup;
import cc.sfclub.polar.user.perm.Perm;
import io.ebean.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Table(name = "t_user_data")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserData extends Model implements Permissible {
    @Id
    @GeneratedValue
    public int id;
    public String nickName;
    public long registrationTime;
    @Convert(converter = PermListConverter.class)
    public List<Perm> permissionNodes = new LinkedList<>();
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
