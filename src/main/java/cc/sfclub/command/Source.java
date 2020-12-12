package cc.sfclub.command;

import cc.sfclub.core.Core;
import cc.sfclub.events.MessageEvent;
import cc.sfclub.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息源
 */
@AllArgsConstructor
@Getter
public class Source {
    /*
     * 消息事件
     */
    private final MessageEvent messageEvent;
    /**
     * 产生时间
     */
    private final long time;

    /**
     * 直接获取纯文本消息
     *
     * @return 消息事件里的消息
     */
    public String getMessage() {
        return messageEvent.getMessage();
    }

    /**
     * 获取发送者
     *
     * @return 发送者
     */
    public User getSender() {
        return Core.get().userManager().byUUID(messageEvent.getUserID());
    }

    public void reply(String message) {
        messageEvent.reply(message);
    }
}
