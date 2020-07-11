package cc.sfclub.command;

import cc.sfclub.core.Core;
import cc.sfclub.events.MessageEvent;
import cc.sfclub.events.message.direct.PrivateMessageReceivedEvent;
import cc.sfclub.events.message.group.GroupMessageReceivedEvent;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CommandListener {
    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    public void onMessage(MessageEvent event) {
        if (event.getMessage() != null && event.getMessage().startsWith(Core.get().config().getCommandPrefix() + " ")) {
            String command = event.getMessage().substring((Core.get().config().getCommandPrefix() + " ").length());
            try {
                Core.get().dispatcher().execute(command, new Source(event));
            } catch (CommandSyntaxException exception) {
                if (event instanceof GroupMessageReceivedEvent) {
                    ((GroupMessageReceivedEvent) event).getGroup().reply(event.getMessageID(), exception.getMessage());
                } else if (event instanceof PrivateMessageReceivedEvent) {
                    ((PrivateMessageReceivedEvent) event).getContact().reply(event.getMessageID(), exception.getMessage());
                }
            }
        }
    }
}
