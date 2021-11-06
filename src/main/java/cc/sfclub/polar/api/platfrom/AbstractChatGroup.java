package cc.sfclub.polar.api.platfrom;

/**
 * 聊天群对象。
 */
//todo a
public abstract class AbstractChatGroup implements IMessageSource {
    public abstract IPlatform getPlatform();
    public abstract String id();
    public abstract String name();

}
