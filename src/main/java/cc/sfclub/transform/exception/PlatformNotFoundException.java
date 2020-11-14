package cc.sfclub.transform.exception;

public class PlatformNotFoundException extends NullPointerException {
    public PlatformNotFoundException(String s) {
        super("Platform " + s + " Not Found");
    }
}
