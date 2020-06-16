package cc.sfclub.command;

import cc.sfclub.core.I18N;
import cc.sfclub.events.message.group.GroupMessageReceivedEvent;
import cc.sfclub.user.User;

public class Unknown implements ICommand {

    @Override
    public boolean onCommand(User user, String[] args, GroupMessageReceivedEvent origin) {
        origin.reply(I18N.get().misc.UNKNOWN_OPERATOR);
        return false;
    }
}
