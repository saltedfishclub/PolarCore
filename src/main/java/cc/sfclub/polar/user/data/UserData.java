package cc.sfclub.polar.user.data;

import javax.persistence.Table;
import java.util.List;

@Table(name = "t_user_data")
public class UserData {
    public String nickName;
    public long registrationTime;
    public List<String> permissionNodes;
}
