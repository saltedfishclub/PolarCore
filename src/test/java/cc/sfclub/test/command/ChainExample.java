package cc.sfclub.test.command;

import cc.sfclub.command.Chain;

public class ChainExample {
    public void Test() {
        Chain.build("root_cmd", "perm")
                .branch(
                        Chain.build("sub_1") //Normally
                                .execute(((a, b, c) -> true))
                ).branch(
                Chain.build("sub_2", "sub_2_perm")
                        .execute(((a, b, c) -> true))
        ).fallback((a, b, c) -> {
            c.reply("We couldn't find your argument.");
            return true; //If false -> default fallback.
        });
        /*
            !p root_cmd sub_1
            !p root_cmd sub_2
            When "!p root_cmd sub_3" -> "We couldn't find your argument."
            "!p root_cmd sub_1" required "perm"
            but "!p root_cmd sub_2" required "sub_2_perm".
        */
    }
}
