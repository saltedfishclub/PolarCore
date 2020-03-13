package cc.sfclub.polar.events.messages;

public class DeleteMessage extends TextMessage {
    public DeleteMessage(String Provider, int MsgID, long User, String Message, long Group) {
        super(Provider, MsgID, User, Message, Group);
    }
}
