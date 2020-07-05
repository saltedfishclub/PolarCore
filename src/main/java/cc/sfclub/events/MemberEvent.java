package cc.sfclub.events;

/**
 * Events about member.
 */
public abstract class MemberEvent extends Event {
    public abstract String getUserID();
}
