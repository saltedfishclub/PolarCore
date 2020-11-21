package cc.sfclub.events;

/**
 * Events about member.
 */
public abstract class MemberEvent extends GroupEvent {
    public abstract String getUserID();
}
