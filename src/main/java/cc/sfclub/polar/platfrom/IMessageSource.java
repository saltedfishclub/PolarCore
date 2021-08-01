package cc.sfclub.polar.platfrom;

/**
 * 信息源。
 * 一个信息源具有双向交互的能力，可以向信息源发送消息或信息源发布消息。
 */
public interface IMessageSource {
    /**
     * 以猫码格式发送信息。
     * @param message catcode-encoded
     */
    void send(String message);
    void reply(long id,String message);
}
