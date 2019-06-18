package main.jsonmapper;

import java.lang.reflect.Array;

import main.jsonwriter.JsonWriter;
import main.mapperfacroy.AbstractJsonMapperFactory;

public class PrimitiveArrayMapper implements JsonMapper {
    
    private AbstractJsonMapperFactory mapperFactory;

    public PrimitiveArrayMapper(AbstractJsonMapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
        
    }

    public void write(Array array, JsonWriter writer) {
        
    }
    
    @Override
    public void write(Object array, JsonWriter writer) {
        if (array == null) {
            writer.writeNull();
            return;
        }

        writer.writeArrayBegin();
        JsonMapper mapper;
        if (Array.getLength(array) > 0) {
            mapper = mapperFactory.createMapper(Array.get(array, 0).getClass());
            Object obj;
            for (int i = 0; i < Array.getLength(array) - 1; i++) {
                obj = Array.get(array, i);
                mapper.write(obj, writer);
                writer.writeSeparator();
            }
            obj = Array.get(array, Array.getLength(array) - 1);
            mapper.write(obj, writer);
        }
        
        writer.writeArrayEnd();

    }
}
