package cc.sfclub.polar.database.converter;

import cc.sfclub.polar.user.data.UserData;

import javax.persistence.AttributeConverter;

public class UserDataConverter implements AttributeConverter<UserData,Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserData attribute) {
        return attribute.getUid();
    }

    @Override
    public UserData convertToEntityAttribute(Integer dbData) {
        //// TODO: 02/08/2021
        return null;
    }
}
