package cc.sfclub.polar.user;

import cc.sfclub.polar.Core;
import lombok.Getter;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;

/**
 * Group. Also a database table
 */
@Table("perm")
public class Group {
    /**
     * Permission nodes
     */
    @Getter
    public ArrayList<String> nodes = new ArrayList<>();
    /**
     * Group Name
     */
    @Name
    public String pGroup;
    /**
     * Extends (single)
     */
    public String extend;
    /**
     * is it a default group?
     */
    public boolean isDefault = false;
    /**
     * sql index.
     */
    @Id
    private transient int index;

    /**
     * save to database
     */
    public void save() {
        Core.getInstance().getDao().update(this);
    }
}
