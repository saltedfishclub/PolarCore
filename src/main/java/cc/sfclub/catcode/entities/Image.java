package cc.sfclub.catcode.entities;

import cc.sfclub.catcode.Base64;

public class Image {
    public String URI;
    public String ID;

    @Override
    public String toString() {
        if (URI.isEmpty()) {
            return "[Image:" + ID + "]";
        }
        return "[Image:" + Base64.URLSafe.encode(URI) + "]";
    }
}