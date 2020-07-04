package cc.sfclub.transform;


import cc.sfclub.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Contact implements IReceiver {
    private final long ID;

    /**
     * @return nickname
     */
    public abstract String getNickname();

    /**
     * return a name which is the name in platform.
     *
     * @return UserName
     */
    public abstract String getUsername();

    /**
     * convert to permissible users.
     *
     * @return User
     */
    public abstract User asPermObj();

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) return true;
        if (!(obj instanceof Contact)) return false;
        Contact contact = (Contact) obj;
        return contact.getID() == this.ID;
    }
}
