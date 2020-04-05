package cc.sfclub.polar.entity;

import org.nutz.repo.Base64;

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
