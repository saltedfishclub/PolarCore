package cc.sfclub.command;

import cc.sfclub.events.message.group.GroupMessageReceivedEvent;
import cc.sfclub.user.User;

@FunctionalInterface
public interface ICommand {
    boolean onCommand(User user, String[] args, GroupMessageReceivedEvent origin);
}
