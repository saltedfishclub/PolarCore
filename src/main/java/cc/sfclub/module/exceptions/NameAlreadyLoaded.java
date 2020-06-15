package cc.sfclub.module.exceptions;

public class NameAlreadyLoaded extends Exception {
    public NameAlreadyLoaded(String msg) {
        super(msg);
    }

    public NameAlreadyLoaded(String msg, Throwable cause) {
        super(msg, cause);
    }
}
