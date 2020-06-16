package cc.sfclub.transform;

import cc.sfclub.util.Since;

@Since("4.0")
public interface Talkable {
    /**
     * Send a message.
     *
     * @param message text Message
     * @return message ID
     */
    @Since("4.0")
    Status.Message sendMessage(String message);

    @Since("4.0")
    Transform getTransform();

}
