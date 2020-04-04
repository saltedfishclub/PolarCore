package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import cc.sfclub.polar.utils.UserUtil;
import lombok.Getter;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class CommandManager {
    @Getter
    private HashMap<String, CommandBase> commandMap = new HashMap<>();
    private Unknown u = new Unknown();
    public static final Unknown FALLBACK = new Unknown();
    @Getter
    private static CommandManager instance = new CommandManager();

    private CommandManager() {
    }

    /**
     * Register a chain-command
     *
     * @param chainCommand CommandChain
     */
    public void register(ChainCommand chainCommand) {
        RootCommandTemplate template = new RootCommandTemplate(chainCommand);
        register(template);
    }

    /**
     * register a command
     *
     * @param cmd command.
     */
    public void register(CommandBase cmd) {
        try {
            CommandFilter cfilter = cmd.getClass().getAnnotation(CommandFilter.class);
            if (cfilter != null) {
                String alias = cfilter.alias();
                cmd.provider = cfilter.provider();
                String[] aliases = alias.split(",");
                for (String s : aliases) {
                    if (commandMap.containsKey(s)) {
                        Core.getLogger().warn("Command OVERWRITING!! Alias: {} ,from: {}", alias, cmd.getClass().getCanonicalName());
                    }
                    commandMap.put(s, cmd);
                    cmd.aliases.add(s);
                }
            }
            Command c = cmd.getClass().getAnnotation(Command.class);
            String name;
            String desc;
            String perm;
            if (c != null) {
                desc = c.description();
                perm = c.perm();

                if (c.name().isEmpty()) {
                    Core.getLogger().warn("{} has a empty name!!", cmd.getClass().getCanonicalName());
                } else {
                    cmd.perm = perm;
                    cmd.description = desc;
                    commandMap.put(c.name().toLowerCase(), cmd);
                    Core.getLogger().info("Register Command: {} ~ {}", c.name().toLowerCase(), desc);
                    return;
                }
            }
            name = cmd.getClass().getSimpleName().toLowerCase();
            desc = cmd.description;
            if (desc == null || cmd.perm == null) return;
            commandMap.put(cmd.getClass().getSimpleName().toLowerCase(), cmd.getClass().getDeclaredConstructor().newInstance());
            Core.getLogger().info("Register Command: {} ~ {}", name, desc);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, priority = 9)
    public final void onMessage(TextMessage m) {
        if (!m.getMessage().trim().startsWith(Core.getConf().startsWith)) return;
        String[] args = m.getMessage().trim().replaceFirst(Core.getConf().startsWith.concat(" "), "").split(" ");
        CommandBase exec;
        if (!UserUtil.isUserExists(m.getUID(), m.getProvider())) {
            UserUtil.addUser(new User(m.getUID(), m.getProvider(), Core.getConf().defaultGroup));
        }
        exec = commandMap.getOrDefault(args[0], u);
        if (!m.getProvider().matches(exec.provider)) {
            return;
        }
        if (m.getUser().hasPermission(exec.getPerm())) {
            TextMessage tm;
            if (args.length > 1) {
                tm = new TextMessage(m.getProvider(), m.getMsgID(), m.getUID(), m.getMessage().trim().replaceFirst(Core.getConf().startsWith.concat(" "), ""), m.getGroupID());
            } else {
                tm = new TextMessage(m.getProvider(), m.getMsgID(), m.getUID(), "", m.getGroupID());
            }
            exec.onCommand(m.getUser(), tm);
        } else {
            m.reply("Permission Denied.");
        }

    }
}
