package cc.sfclub.polar.user;

import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;

@Table("perm")
public class Group {
    public ArrayList<String> nodes = new ArrayList<>();
    public String pGroup;
    public long internalID;
    public boolean isDefault = false;
}
