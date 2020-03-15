package cc.sfclub.polar.commands;

import cc.sfclub.polar.Command;
import cc.sfclub.polar.CommandBase;
import cc.sfclub.polar.Core;
import cc.sfclub.polar.LoadCallback;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;

import java.util.StringJoiner;

@Command(perm = "member.op.plugin.manage", description = "Reload Plugins", name = "plugin")
public class SimplePluginManager extends CommandBase {
    @Override
    public void onCommand(User u, TextMessage Command) {
        if (Command.getMessage().isEmpty()) {
            sendHelp(Command);
            return;
        }
        String[] args = Command.getMessage().split(" ");
        if (args[1].equalsIgnoreCase("reload")) { //here java.lang.ArrayIndexOutOfBoundsException: 1
            Command.reply("Reloading..");
            Core.getInstance().setCb(new LoadCallback(Command));
            Core.getInstance().init();
        } else if (args[1].equalsIgnoreCase("list")) {
            StringJoiner sj = new StringJoiner(",", "Plugins:\n", "");
            Core.getInstance().getPlugins().forEach(a -> sj.add(a.getPluginLoader().getPluginClassLoader().getDescription().getName()));
            Command.reply(sj.toString());
        } else {
            sendHelp(Command);
        }
    }

    public void sendHelp(TextMessage msg) {
        msg.reply(new String[]{
                "Plugin Manager.",
                "plugin list ~ list plugins",
                "plugin reload ~ reload plugins"
        });
    }
}
