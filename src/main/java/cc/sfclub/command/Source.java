package cc.sfclub.command;

import cc.sfclub.events.MessageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Source {
    private final MessageEvent messageEvent;

    public String getMessage() {
        return messageEvent.getMessage();
    }

    public String getSender() {
        return messageEvent.getUserID();
    }
}
