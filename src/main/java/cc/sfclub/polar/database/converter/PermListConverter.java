package cc.sfclub.polar.database.converter;

import cc.sfclub.polar.user.perm.Perm;
import cc.sfclub.polar.user.perm.PermInitializer;
import cc.sfclub.polar.user.perm.internal.LiteralPermInitializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.LinkedList;
import java.util.List;

@Converter
public class PermListConverter implements AttributeConverter<List<Perm>,String> {
    public static List<PermInitializer<?>> INITIALIZERS = new LinkedList<>();

    static {
        INITIALIZERS.add(new LiteralPermInitializer());
        //// TODO: 02/08/2021 Init or ...
    }

    @Override
    public String convertToDatabaseColumn(List<Perm> attribute) {
        JsonArray ja = new JsonArray();
        for (Perm perm : attribute) {
            JsonObject jo = new JsonObject();
            jo.addProperty("serializer",perm.serializer().getCanonicalName());
            jo.addProperty("data",perm.deserialize());
            ja.add(jo.toString());
        }
        return ja.toString();
    }

    @Override
    public List<Perm> convertToEntityAttribute(String dbData) {
        JsonArray ja = JsonParser.parseString(dbData).getAsJsonArray();
        List<Perm> perms = new LinkedList<>();
        for (JsonElement jsonElement : ja) {
            JsonObject jo = jsonElement.getAsJsonObject();
            String clazzName = jo.get("serializer").getAsString();
            String data = jo.get("data").getAsString();
            var a= INITIALIZERS.stream().filter(e->e.getClass().getCanonicalName().equals(clazzName)).findFirst().orElseThrow(()->new NullPointerException("PermInitializer "+clazzName+" not found!("+data+")")).initialize(data);
            perms.add(a);
        }
        return perms;
    }
}
