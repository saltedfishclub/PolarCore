package cc.sfclub.test;

import cc.sfclub.database.converter.PermListConverter;
import cc.sfclub.database.converter.StrListConverter;
import cc.sfclub.user.perm.Perm;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ListConverterTest {
    private final String simpleListAsString = "a,b";
    /* \\
     ,
     a*/
    private final String complexListAsString = "\\\\" + "," + "\\," + "," + "a";
    private final String specialListAsString = ".*";
    @Test
    public void on() {
        StrListConverter strListConverter = new StrListConverter();
        List<String> simpleList = Arrays.asList("a", "b");
        Assert.assertEquals(strListConverter.convertToDatabaseColumn(simpleList), simpleListAsString);
        Assert.assertEquals(simpleList, strListConverter.convertToEntityAttribute(simpleListAsString));

        List<String> complexList = Arrays.asList("\\", ",", "a");
        Assert.assertEquals(strListConverter.convertToDatabaseColumn(complexList), complexListAsString);
        Assert.assertEquals(complexList, strListConverter.convertToEntityAttribute(complexListAsString));

        List<Perm> specialList = Arrays.asList(Perm.of(".*"));
        PermListConverter permListConverter = new PermListConverter();
        Assert.assertEquals(permListConverter.convertToEntityAttribute(".*"), specialList);
    }
}
