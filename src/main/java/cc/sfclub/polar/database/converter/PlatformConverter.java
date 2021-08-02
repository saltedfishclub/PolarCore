package cc.sfclub.polar.database.converter;

import cc.sfclub.polar.platfrom.IPlatform;

import javax.persistence.AttributeConverter;

public class PlatformConverter implements AttributeConverter<IPlatform,String > {
    @Override
    public String convertToDatabaseColumn(IPlatform attribute) {
        //// TODO: 02/08/2021  
        return null;
    }

    @Override
    public IPlatform convertToEntityAttribute(String dbData) {
        //// TODO: 02/08/2021
        return null;
    }
}
