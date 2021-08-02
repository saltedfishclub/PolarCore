package cc.sfclub.test;

import cc.sfclub.polar.user.perm.Perm;
import cc.sfclub.polar.user.perm.internal.LiteralPerm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.JUnit4;

import java.util.Arrays;

public class PermListTest {
    @Test
    public void onTest(){
        var list = Arrays.asList(Perm.of("aaa"),Perm.of("bbb"));
        var LiteralPerm  = new LiteralPerm("aaa");
        Assert.assertTrue(list.contains(Perm.of("aaa")));
        Assert.assertTrue(list.contains(LiteralPerm));
    }
}
