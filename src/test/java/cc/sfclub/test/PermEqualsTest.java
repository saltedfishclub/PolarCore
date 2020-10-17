package cc.sfclub.test;

import cc.sfclub.user.perm.Perm;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PermEqualsTest {
    @Test
    public void run() {
        List<Perm> perms = Arrays.asList(new Perm("aa.bb"), new Perm("bb.cc"));

        Assert.assertTrue(perms.contains(new Perm("aa.bb")));
        Assert.assertTrue(perms.contains(new Perm("bb.cc")));
    }
}
