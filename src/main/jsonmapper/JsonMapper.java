package main.jsonmapper;

import main.jsonwriter.JsonWriter;

public interface JsonMapper {
    
    public void write(Object obj, JsonWriter writer);
}
