package cc.sfclub.transform.contact;

import cc.sfclub.util.Since;

@Since("4.0")
public interface Contact {
    /**
     * e.g Telegram User ID,WeChat user id
     *
     * @return Transform id
     */
    @Since("4.0")
    long getTID();

    /**
     * PolarCore UserID
     *
     * @return UserID
     */
    @Since("4.0")
    String getUID();
}
