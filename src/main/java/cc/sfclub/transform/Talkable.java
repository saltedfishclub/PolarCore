package cc.sfclub.transform;

public interface Talkable {
    /**
     * Send a message.
     *
     * @param message text Message
     * @return message ID
     */

    Status.Message sendMessage(String message);


    Transform getTransform();

}
