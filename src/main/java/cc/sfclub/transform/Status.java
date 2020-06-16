package cc.sfclub.transform;

import cc.sfclub.util.Since;

@Since("4.0")
public enum Status {
    ;

    @Since("4.0")
    public enum Message {
        /**
         * Control Message failed
         */
        SEND_FAILED,
        /**
         * Control Message Succeed
         */
        SEND_SUCCEED,
        /**
         * It must be a unknown error.
         */
        UNKNOWN
    }
}
