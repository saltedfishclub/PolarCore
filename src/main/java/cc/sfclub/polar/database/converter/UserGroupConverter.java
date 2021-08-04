package cc.sfclub.polar.database.converter;

import cc.sfclub.polar.Bot;
import cc.sfclub.polar.user.UserGroup;

import javax.persistence.AttributeConverter;

public class UserGroupConverter implements AttributeConverter<UserGroup,String> {
    @Override
    public String convertToDatabaseColumn(UserGroup attribute) {
        return attribute.getName();
    }

    @Override
    public UserGroup convertToEntityAttribute(String dbData) {
        return Bot.getUserManager().searchGroupByName(dbData);
    }
}
