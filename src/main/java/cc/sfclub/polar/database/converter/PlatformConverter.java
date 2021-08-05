package cc.sfclub.polar.database.converter;

import cc.sfclub.polar.Bot;
import cc.sfclub.polar.platfrom.IPlatform;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PlatformConverter implements AttributeConverter<IPlatform,String > {
    @Override
    public String convertToDatabaseColumn(IPlatform attribute) {
        return attribute.getName();
    }

    @Override
    public IPlatform convertToEntityAttribute(String dbData) {
        return Bot.getPlatformManager().getByName(dbData);
    }
}
