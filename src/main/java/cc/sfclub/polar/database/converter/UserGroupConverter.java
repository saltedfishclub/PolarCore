package cc.sfclub.polar.database.converter;

import cc.sfclub.polar.user.UserGroup;

import javax.persistence.AttributeConverter;

public class UserGroupConverter implements AttributeConverter<UserGroup,String> {
    @Override
    public String convertToDatabaseColumn(UserGroup attribute) {
        return null; //// TODO: 02/08/2021  
    }

    @Override
    public UserGroup convertToEntityAttribute(String dbData) {
        return null;
    }
}
