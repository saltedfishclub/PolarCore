package cc.sfclub.database.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Converter
public class StrListConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        StringJoiner result = new StringJoiner(",");
        attribute.forEach(e -> result.add(e.replace("\\", "\\\\").replaceAll(",", "\\\\,")));
        return result.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        List<String> result = new ArrayList<>();
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
                result.add(temp.toString());
                temp = new StringBuilder();
                continue;
            }
            temp.append(c);
        }
        result.add(temp.toString());
        return result;
    }
}
