package cc.sfclub.polar.platfrom;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public interface IBot {
    @NotNull
    Iterator<IContact> getContacts();

    @NotNull
    Iterator<AbstractChatGroup> getChatGroups();

    @Nullable
    AbstractChatGroup groupById(String id);

    @Nullable
    IContact contactByID(String id);

    @NotNull
    IPlatform getPlatform();

    @NotNull
    String getUID();

    /**
     * 互斥标签。
     * 在互斥标签内的 多个 Bot 在同一个上下文中只能有一个 Bot 处理事件。
     * // TODO: 01/08/2021  
     * @return
     */
    @NotNull
    List<String> lockedIdentifiers();
}
