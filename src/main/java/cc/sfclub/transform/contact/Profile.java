package cc.sfclub.transform.contact;

public interface Profile {
    String getNickName();

    String getRealName();

    Level getLevel();

    enum Level {
        NORMAL, ADMIN
    }
}
