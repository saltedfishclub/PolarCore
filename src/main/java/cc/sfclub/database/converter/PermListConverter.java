package cc.sfclub.database.converter;

import cc.sfclub.user.perm.Perm;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Converter
public class PermListConverter implements AttributeConverter<List<Perm>, String> {
    @Override
    public String convertToDatabaseColumn(List<Perm> attribute) {
        StringJoiner result = new StringJoiner(",");
        attribute.forEach(e -> result.add(e.getNode().replace("\\", "\\\\").replaceAll(",", "\\\\,")));
        return result.toString();
    }

    @Override
    public List<Perm> convertToEntityAttribute(String dbData) {
        List<Perm> result = new ArrayList<>();
        boolean flag = false;
        StringBuilder temp = new StringBuilder();
        for (char c : dbData.toCharArray()) {
            if (c == '\\') {
                if (flag) {
                    temp.append(c);
                    flag = false;
                    continue;
                }
                flag = true;
                continue;
            }
            if (c == ',') {
                if (flag) {
                    flag = false;
                    temp.append(c);
                    ;
                    continue;
                }
                result.add(Perm.of(temp.toString()));
                temp = new StringBuilder();
                continue;
            }
            temp.append(c);
        }
        result.add(Perm.of(temp.toString()));
        return result;
    }
}
