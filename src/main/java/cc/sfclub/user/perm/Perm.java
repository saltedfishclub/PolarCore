package cc.sfclub.user.perm;

import cc.sfclub.core.Core;
import cc.sfclub.user.User;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.WeakHashMap;

@Getter
@Setter
public abstract class Perm {
    private static final Logger logger = LoggerFactory.getLogger(Perm.class);
    private static final WeakHashMap<String, Perm> cachedPermObj = new WeakHashMap<>();

    protected Perm() {

    }

    /**
     * 从缓存中获取权限，当缓存中不存在时使用PureStringPerm模板新建存入并返回。
     *
     * @param perm
     * @return
     */
    public static Perm of(String perm) {
        if (!cachedPermObj.containsKey(perm)) {
            cachedPermObj.put(perm, new PureStringPerm(perm));
        }
        return cachedPermObj.get(perm);
    }

    /**
     * 注册权限到缓存，注意缓存内的对象若无额外的强引用可能被GC
     *
     * @param perm
     */
    public static void register(Perm perm) {
        cachedPermObj.put(perm.toString(), perm);
    }

    /**
     * 权限判定
     */
    public static Result compare(Perm orig, Perm target, User u) {
        Result result = Result.FAILED;
        if (target.toString().startsWith("-") && target.toString().matches(orig.toString().replaceFirst("-", ""))) {
            result = Result.BANNED;
        } else {
            if (target.toString().matches(orig.toString())) result = Result.SUCCEED;
        }
        if (Core.get() != null && Core.get().config().isDebug()) {
            logger.info("[DEBUG][Perm] Compare: {} , {} == {}", orig, target, result);
        }
        if (u == null) {
            return result;
        }
        return target.hasPermission(u, result);
    }

    public abstract Result hasPermission(User user, Result regexResult);

    /**
     * 只会比较权限节点是否一致
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (obj instanceof String) {
            String perm = (String) obj;
            return compare(Perm.of(perm), this, null) == Result.SUCCEED;
        }
        if (obj instanceof Perm) {
            Perm perm = (Perm) obj;
            return compare(perm, this, null) == Result.SUCCEED;
        }
        return false;
    }

    public enum Result {
        FAILED, BANNED, SUCCEED
    }

    /**
     * @return permission node name
     */
    @Override
    public abstract String toString();
}
