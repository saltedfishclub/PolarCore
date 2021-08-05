package cc.sfclub.polar.user;

import cc.sfclub.polar.database.converter.PlatformConverter;
import cc.sfclub.polar.database.converter.UserDataConverter;
import cc.sfclub.polar.platfrom.IPlatform;
import cc.sfclub.polar.user.data.UserData;
import cc.sfclub.polar.user.perm.Perm;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "t_platfrom_user")
@Data
public class User implements Permissible{
    @GeneratedValue
    @Id
    @Column
    private long id;
    @Convert(converter = PlatformConverter.class)
    @Column
    private IPlatform platform;
    @Convert(converter = UserDataConverter.class)
    @Column
    private UserData userData;
    @Column
    private String platformIdentifier; //such as Telegram UID, ??? UIN

    @Override
    public boolean hasPermission(Perm perm) {
        return userData.hasPermission(perm);
    }

    @Override
    public boolean hasPermission(String perm) {
        return userData.hasPermission(perm);
    }

    @Override
    public List<Perm> getPermissions() {
        return userData.getPermissions();
    }

    @Override
    public void delPermission() {
    //todo
    }

    @Override
    public void addPermissions(Perm... perms) {

    }

}
