package cc.sfclub.polar.user.data;

import cc.sfclub.polar.database.converter.PlatformConverter;
import cc.sfclub.polar.platfrom.IPlatform;
import cc.sfclub.polar.user.Permissible;
import cc.sfclub.polar.user.perm.Perm;
import io.ebean.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Model implements Permissible {
    @Id
    private long id;
    //@ManyToOne(optional = false)
    @Convert(converter = PlatformConverter.class)
    private IPlatform platform;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id")
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
