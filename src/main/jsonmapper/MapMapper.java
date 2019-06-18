package main.jsonmapper;

import java.util.Iterator;
import java.util.Map;

import main.jsonwriter.JsonWriter;
import main.mapperfacroy.AbstractJsonMapperFactory;

public class MapMapper implements JsonMapper {

private AbstractJsonMapperFactory mapperFactory;
    
    public MapMapper(AbstractJsonMapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
        
    }
    
    
    public void write(Map<?, ?> map, JsonWriter writer) {
        writer.writeObjectBegin();
        
        Object key;
        Object value;
        
        Iterator<?> it = map.keySet().iterator();
        
        for (int i = 0; i < map.size() - 1; i++) {
            key = it.next();
            if (key.toString().equals("")) {
                throw new IllegalStateException();
            }
            writer.writeString(key.toString());
            writer.writePropertySeparator();
            value = map.get(key);
            
            if (value == null) {
                writer.writeNull();
                writer.writeSeparator();
                continue;
            }
            JsonMapper mapper = mapperFactory.createMapper(value.getClass());
            mapper.write(value, writer);
            writer.writeSeparator();
        }
        key = it.next();
        if (key.toString().equals("")) {
            throw new IllegalStateException();
        }
        writer.writeString(key.toString());
        writer.writePropertySeparator();
        value = map.get(key);
        
        if (value == null) {
            writer.writeNull();
        } else {
            JsonMapper mapper = mapperFactory.createMapper(value.getClass());
            mapper.write(value, writer);
        }
        
        
        writer.writeObjectEnd();
    } 
    
    @Override
    public void write(Object obj, JsonWriter writer) {
        if (obj == null) {
            writer.writeNull();
            return;
        }
        if (obj instanceof Map<?, ?>) {
            write((Map<?, ?>)obj, writer);
        } else {
            throw new IllegalArgumentException();
        }
        
    }

}
