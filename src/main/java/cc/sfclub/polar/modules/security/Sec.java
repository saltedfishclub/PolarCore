package cc.sfclub.polar.modules.security;

import cc.sfclub.polar.Command;
import cc.sfclub.polar.CommandBase;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import cc.sfclub.polar.utils.MathUtils;
import cc.sfclub.polar.utils.UserUtil;

@Command(perm = "module.polar.security", description = "Polar Security", name = "psec")
public class Sec extends CommandBase {

    @Override
    public void onCommand(User u, TextMessage msg) {
        if (msg.getMessage().isEmpty()) {
            showHelp(msg);
            return;
        }
        String[] args = msg.getMessage().split(" ");
        if (args.length == 4) {
            if (args[1].contains("ban")) {
                int value = -1;
                if (args[1].equalsIgnoreCase("unban")) {
                    value = 4;
                }
                if (!MathUtils.isNumeric(args[2])) {
                    wrongArgs(msg);
                    return;
                }
                User usr = UserUtil.getUser(Long.parseLong(args[2]), args[3].replace("this", msg.getProvider()));
                if (usr == null) {
                    msg.reply("User not found.");
                    return;
                }
                PolarSec.priority.getPriority().put(usr.getUniqueID(), value);
                msg.reply(usr.getUniqueID() + "'s priority has set to " + value);
                return;
            } else if (args[1].equalsIgnoreCase("query")) {
                if (!MathUtils.isNumeric(args[2])) {
                    wrongArgs(msg);
                    return;
                }
                User usr = UserUtil.getUser(Long.parseLong(args[2]), args[3].replace("this", msg.getProvider()));
                if (usr == null) {
                    msg.reply("User not found.");
                    return;
                }
                msg.reply(new String[]{
                        "User " + usr.getUniqueID(),
                        "Priority: " + PolarSec.priority.getPriority().get(usr.getUniqueID())
                });
                return;
            }
        }
        msg.reply(new String[]{
                "User " + u.getUniqueID(),
                "Priority: " + PolarSec.priority.getPriority().get(u.getUniqueID())
        });
    }

    public void showHelp(TextMessage Command) {
        Command.reply(new String[]{
                "Polar Security by iceBear67.",
                "psec ban <UID> <Platfrom/this>",
                "psec unban <UID> <Platfrom/this>",
                "psec query <UID> <Platform/this> --Query status",
                "psec me --Get your priority."
        });
    }

    public void wrongArgs(TextMessage Command) {
        Command.reply("Unknown args.");
    }
}
