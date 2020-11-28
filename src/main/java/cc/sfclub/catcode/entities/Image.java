package cc.sfclub.catcode.entities;

import cc.sfclub.catcode.Base64;
import lombok.Builder;

/**
 * 图片消息
 */
@Builder
public class Image {
    /**
     * 图片地址
     */
    public String URI;
    /**
     * 图片ID，一般由transformer方提供
     */
    public String ID;

    @Override
    public String toString() {
        if (URI.isEmpty()) {
            return "[Image:" + ID + "]";
        }
        return "[Image:" + Base64.URLSafe.encode(URI) + "]";
    }
}