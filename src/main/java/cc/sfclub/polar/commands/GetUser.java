package cc.sfclub.polar.commands;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import cc.sfclub.polar.utils.MathUtils;
import cc.sfclub.polar.wrapper.Bot;

public class GetUser extends CommandBase {
    @Override
    public String getDescription() {
        return "Get info about a user";
    }

    @Override
    public String getPerm() {
        return "member.mod.cmd.getuser";
    }

    @Override
    public void onCommand(User u, TextMessage Command) {
        Bot bot = Core.getWrappers().get(Command.getProvider());
        String[] args = Command.getMessage().split(" ");
        switch (args.length) {
            case 1:
                if (!MathUtils.isNumeric(args[0])) {
                    bot.sendMessage(Command, new String[]{
                            "Illegal Arguments.",
                            "Usage:<UID> [Platfrom]"
                    });
                    return;
                }
                User usr = Core.getUserManager().getUser(Long.parseLong(args[0]), Command.getProvider());
                if (usr != null) {
                    bot.sendMessage(Command, "[" + usr.getUID() + "(" + usr.getUserName() + ")] Group: " + usr.getPGroup() + ",UUID: " + usr.getUniqueID());
                } else {
                    bot.sendMessage(Command, "Not Found.");
                }
                break;
            case 2:
                User us1r = Core.getUserManager().getUser(Long.parseLong(args[0]), args[1]);
                if (us1r != null) {
                    bot.sendMessage(Command, "[" + us1r.getUID() + "(" + us1r.getUserName() + ")] Group: " + us1r.getPGroup() + ",UUID: " + us1r.getUniqueID());
                } else {
                    bot.sendMessage(Command, "Not Found.");
                }
                break;
            default:
                bot.sendMessage(Command, "Wrong Usage.");
                break;
        }
    }
}
