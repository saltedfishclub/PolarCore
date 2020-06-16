package cc.sfclub.transform.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Profile {
    private final String nickName;
    private final String realName;
    private final Level level;

    enum Level {
        NORMAL, ADMIN
    }
}
