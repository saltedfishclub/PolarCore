package cc.sfclub.polar.user;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;

@Table("perm")
public class Group {
    public ArrayList<String> nodes = new ArrayList<>();
    @Name
    public String pGroup;
    @Id
    private transient int index;
    public String extend;
    public boolean isDefault = false;
}
