package cc.sfclub.polar;

import cc.sfclub.polar.commands.Unknown;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import lombok.Getter;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class CommandManager {
    @Getter
    static volatile HashMap<String, CommandBase> CommandMap = new HashMap<>();
    Unknown u = new Unknown();

    public void register(Reflections pl) {
        scan(pl);
    }

    void scan(Reflections rfl) {
        rfl.getSubTypesOf(CommandBase.class).forEach(clazz -> {
            try {
                register(clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    public void register(CommandBase cmd) {
        try {
            CommandFilter cfilter = cmd.getClass().getAnnotation(CommandFilter.class);
            if (cfilter != null) {
                String alias = cfilter.alias();
                cmd.Provider = cfilter.provider();
                String[] aliases = alias.split(",");
                for (String s : aliases) {
                    if (CommandMap.containsKey(s)) {
                        Core.getLogger().warn("Command OVERWRITING!! Alias: {} ,from: {}", alias, cmd.getClass().getCanonicalName());
                    }
                    CommandMap.put(s, cmd);
                    cmd.Aliases.add(s);
                }
            }
            Command c = cmd.getClass().getAnnotation(Command.class);
            String name, desc, perm;
            if (c != null) {
                desc = c.description();
                perm = c.perm();

                if (c.name().isEmpty()) {
                    Core.getLogger().warn("{} has a empty name!!", cmd.getClass().getCanonicalName());
                } else {
                    cmd.Perm = perm;
                    cmd.Description = desc;
                    CommandMap.put(c.name().toLowerCase(), cmd);
                    Core.getLogger().info("Register Command: {} ~ {}", c.name().toLowerCase(), desc);
                    return;
                }
            }
            name = cmd.getClass().getSimpleName().toLowerCase();
            desc = cmd.Description;
            if (desc == null || cmd.Perm == null) return;
            CommandMap.put(cmd.getClass().getSimpleName().toLowerCase(), cmd.getClass().getDeclaredConstructor().newInstance());
            Core.getLogger().info("Register Command: {} ~ {}", name, desc);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public final void onMessage(TextMessage m) {
        if (!m.getMessage().trim().startsWith(Core.getConf().startsWith)) return;
        String[] args = m.getMessage().trim().replaceFirst(Core.getConf().startsWith.concat(" "), "").split(" ");
        CommandBase exec;
        if (!Core.getUserManager().isUserExists(m.getUID(), m.getProvider())) {
            Core.getUserManager().addUser(new User(m.getUID(), m.getProvider(), Core.getDefaultGroup()));
        }
        exec = CommandMap.getOrDefault(args[0], u);
        if (!m.getProvider().matches(exec.Provider)) {
            return;
        }
        if (m.getUser().hasPermission(exec.Perm)) {
            TextMessage tm;
            if (args.length > 1) {
                tm = new TextMessage(m.getProvider(), m.getMsgID(), m.getUID(), m.getMessage().trim().replaceFirst(Core.getConf().startsWith.concat(" "), ""), m.getGroupID());
            } else {
                tm = new TextMessage(m.getProvider(), m.getMsgID(), m.getUID(), "", m.getGroupID());
            }
            exec.onCommand(m.getUser(), tm);
        } else {
            Core.getBot(m).sendMessage(m, "Permission Denied.");
        }

    }
}
