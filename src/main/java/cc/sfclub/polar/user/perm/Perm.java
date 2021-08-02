package cc.sfclub.polar.user.perm;

import cc.sfclub.polar.user.User;
import cc.sfclub.polar.user.perm.internal.LiteralPerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.WeakHashMap;

public abstract class Perm {
    private static final Logger logger = LoggerFactory.getLogger(Perm.class);
    private static final WeakHashMap<String, Perm> cachedPermObj = new WeakHashMap<>();
    /**
     * 从缓存中获取权限，当缓存中不存在时使用PureStringPerm模板新建存入并返回。
     *
     * @param perm
     * @return
     */
    public static Perm of(String perm) {
        if (!cachedPermObj.containsKey(perm)) {
            cachedPermObj.put(perm, new LiteralPerm(perm));
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

    public abstract String asLiteralNode();
    public abstract Result hasPermission(User user, Result regexResult);

    public static Result compare(String orig,String target, User u){
        return compare(of(orig),of(target),u);
    }

    public static Result compare(Perm orig, Perm target, User u) {
        Result result = Result.FAILED;
        if (target.asLiteralNode().startsWith("-") && target.asLiteralNode().matches(orig.asLiteralNode().replaceFirst("-", ""))) {
            result = Result.BANNED;
        } else {
            if (target.asLiteralNode().matches(orig.asLiteralNode())) result = Result.SUCCEED;
        }
        logger.debug("[DEBUG][Perm] Compare: {} , {} == {}", orig, target, result);
        if (u == null) {
            return result;
        }
        return target.hasPermission(u, result);
    }
    public Class<? extends PermInitializer<?>> serializer(){
        return null;
    }
    public String deserialize(){
        return asLiteralNode();
    }
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
        SUCCEED, FAILED, BANNED
    }
    @Override
    @Deprecated
    public String toString(){
        return asLiteralNode();
    }

    @Override
    public int hashCode() {
        return asLiteralNode().hashCode();
    }
}
