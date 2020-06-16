package cc.sfclub.transform.contact;

import cc.sfclub.util.Since;
import lombok.Getter;

@Since("4.0")
public class Contact {

    /**
     * e.g Telegram User ID,WeChat user id
     *
     * @return Transform id
     */
    @Since("4.0")
    @Getter
    private long TID;

    /**
     * PolarCore UserID
     *
     * @return UserID
     */
    @Since("4.0")
    @Getter
    private String UID;
}
