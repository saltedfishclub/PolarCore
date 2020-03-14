package cc.sfclub.polar;

import cc.sfclub.polar.commands.Unknown;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class CommandManager {
    static volatile HashMap<String, CommandBase> CommandMap = new HashMap<>();
    Unknown u = new Unknown();

    public void register(Reflections pl) {
        scan(pl);
    }

    void scan(Reflections rfl) {
        rfl.getSubTypesOf(CommandBase.class).forEach(clazz -> {
            register(clazz);
        });
    }

    public void register(Class<? extends CommandBase> clazz) {
        try {
            CommandBase a = clazz.getDeclaredConstructor().newInstance();
            Command c = clazz.getAnnotation(Command.class);
            String name, desc, perm;
            if (c != null) {
                desc = c.description();
                perm = c.perm();
                if (c.name().isEmpty()) {
                    Core.getLogger().warn("{} has a empty name!!", clazz.getCanonicalName());
                } else {
                    CommandBase cb = clazz.getDeclaredConstructor().newInstance();
                    cb.Perm = perm;
                    CommandMap.put(c.name().toLowerCase(), cb);
                    Core.getLogger().info("Register Command: {} ~ {}", c.name().toLowerCase(), desc);
                    return;
                }
            }
            name = clazz.getSimpleName().toLowerCase();
            desc = a.getDescription();
            if (desc == null || a.getPerm() == null) return;
            if (desc != null) {
                CommandMap.put(clazz.getSimpleName().toLowerCase(), clazz.getDeclaredConstructor().newInstance());
                Core.getLogger().info("Register Command: {} ~ {}", name, desc);
            }
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
        if (args.length > 1) {
            String cmd;
            cmd = m.getMessage().trim().replaceFirst(Core.getConf().startsWith, "");
            exec = CommandMap.getOrDefault(cmd, u);
            if (m.getUser().hasPermission(exec.getPerm())) {
                exec.onCommand(m.getUser(), m);
            } else {
                Core.getBot(m).sendMessage(m, "Permission Denied.");
            }
        } else {
            exec = CommandMap.getOrDefault(args[0], u);
            if (m.getUser().hasPermission(exec.getPerm())) {
                exec.onCommand(m.getUser(), new TextMessage(m.getProvider(), m.getMsgID(), m.getUID(), "", m.getGroupID()));
            } else {
                Core.getBot(m).sendMessage(m, "Permission Denied.");
            }
        }
    }
}
