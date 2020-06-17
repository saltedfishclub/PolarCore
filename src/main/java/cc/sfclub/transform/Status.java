package cc.sfclub.transform;

public enum Status {
    ;


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
