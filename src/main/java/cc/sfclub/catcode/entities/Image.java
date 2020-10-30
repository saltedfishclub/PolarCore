package cc.sfclub.catcode.entities;

import cc.sfclub.catcode.Base64;

/**
 * 图片消息
 */
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