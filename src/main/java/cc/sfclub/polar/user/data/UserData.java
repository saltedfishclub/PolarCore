package cc.sfclub.polar.user.data;

import cc.sfclub.polar.user.Permissible;
import cc.sfclub.polar.user.perm.Perm;
import cc.sfclub.polar.user.perm.internal.PermListConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "t_user_data")
@Data
public class UserData implements Permissible {
    @Id
    @GeneratedValue
    public int uid;
    public String nickName;
    public long registrationTime;
    @Convert(converter=PermListConverter.class)
    public List<Perm> permissionNodes;
    public String userGroup;

    @Override
    public boolean hasPermission(Perm perm) {
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
}
