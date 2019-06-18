package main.jsonmapper;

import main.jsonwriter.JsonWriter;

public class BooleanMapper implements JsonMapper {

    
    public void write(Boolean obj, JsonWriter writer) {
        writer.writeBoolean(obj);    
    }
    
    @Override
    public void write(Object obj, JsonWriter writer) {
        if (obj == null) {
            writer.writeNull();
            return;
        }
        if (obj instanceof Boolean) {
            write((Boolean)obj, writer);
        } else {
            throw new IllegalArgumentException();
        }
    }

}
