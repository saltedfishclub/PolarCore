package cc.sfclub.module.exceptions;

import java.io.IOException;

public class InvalidModule extends IOException {
    public InvalidModule(String msg) {
        super(msg);
    }

    public InvalidModule(String msg, Throwable caused) {
        super(msg, caused);
    }
}
