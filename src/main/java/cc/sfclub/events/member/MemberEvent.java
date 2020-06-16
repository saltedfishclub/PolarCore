package cc.sfclub.events.member;

import cc.sfclub.events.Event;

/**
 * Events about member.
 */
public abstract class MemberEvent extends Event {
    public abstract String getUserID();
}
