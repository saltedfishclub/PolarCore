package cc.sfclub.transform.internal;

import cc.sfclub.core.Initializer;
import cc.sfclub.transform.Contact;
import cc.sfclub.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VirtContact extends Contact {
    private static final Logger logger = LoggerFactory.getLogger("VirtualContact");
    public VirtContact(long ID) {
        super(ID);
    }

    @Override
    public String getNickname() {
        return "NICK_NAME";
    }

    @Override
    public String getUsername() {
        return User.CONSOLE_USER_NAME;
    }

    @Override
    public User asPermObj() {
        return Initializer.getCONSOLE();
    }

    @Override
    public void sendMessage(String message) {
        logger.info("[CONSOLE_PRIVATE_MESSAGE] {}", message);
    }

    @Override
    public void reply(long messageId, String message) {
        logger.info("[CONSOLE_PRIVATE_REPLY] {}", message);
    }
}
