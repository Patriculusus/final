package main.jsonmapper;

import java.util.Collection;
import java.util.Iterator;

import main.jsonwriter.JsonWriter;
import main.mapperfacroy.AbstractJsonMapperFactory;

public class CollectionMapper implements JsonMapper {
    
    private AbstractJsonMapperFactory mapperFactory;
    
    public CollectionMapper(AbstractJsonMapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
        
    }
    
    
    public void write(Collection<?> collection, JsonWriter writer) {
        writer.writeArrayBegin();
        Object obj;
        Iterator<?> it = collection.iterator();
        for (int i = 0; i < collection.size() - 1; i++) {
            obj = it.next();
            if (obj == null) {
                writer.writeNull();
                writer.writeSeparator();
                continue;
            }
            JsonMapper mapper = mapperFactory.createMapper(obj.getClass());
            mapper.write(obj, writer);
            writer.writeSeparator();
        }
        obj = it.next();
        if (obj == null) {
            writer.writeNull();
        } else {
            JsonMapper mapper = mapperFactory.createMapper(obj.getClass());
            mapper.write(obj, writer);
        }
        writer.writeArrayEnd();
    } 
    
    @Override
    public void write(Object obj, JsonWriter writer) {
        if (obj == null) {
            writer.writeNull();
            return;
        }
        if (obj instanceof Collection<?>) {
            write((Collection<?>)obj, writer);
        } else {
            throw new IllegalArgumentException();
        }
        
    }

}
