package cc.sfclub.command;

import cc.sfclub.events.MessageEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Source {
    private final MessageEvent messageEvent;
}
