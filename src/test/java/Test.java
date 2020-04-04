import cc.sfclub.polar.ChainCommand;

public class Test {
    public void onTest() {
        new ChainCommand("a", "b").execute((u, m) -> {
            m.reply("Hello!");
            return true;
        });
    }
}
