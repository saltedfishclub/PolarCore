package cc.sfclub.transform.contact;

import lombok.Getter;


public class Contact {

    /**
     * e.g Telegram User ID,WeChat user id
     *
     * @return Transform id
     */

    @Getter
    private long TID;

    /**
     * PolarCore UserID
     *
     * @return UserID
     */

    @Getter
    private String UID;
}
