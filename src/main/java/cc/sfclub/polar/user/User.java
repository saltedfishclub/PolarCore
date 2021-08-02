package cc.sfclub.polar.user;

import cc.sfclub.polar.database.converter.PlatformConverter;
import cc.sfclub.polar.database.converter.UserDataConverter;
import cc.sfclub.polar.platfrom.IPlatform;
import cc.sfclub.polar.user.data.UserData;
import cc.sfclub.polar.user.perm.Perm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_platfrom_user")
public class User implements Permissible{
    @GeneratedValue
    @Id
    private int id;
    @Convert(converter = PlatformConverter.class)
    private IPlatform platform;
    @Convert(converter = UserDataConverter.class)
    private UserData userData;
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
}
