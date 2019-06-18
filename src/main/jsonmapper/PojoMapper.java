package main.jsonmapper;

import main.annotation.JsonIgnore;
import main.annotation.JsonProperty;
import main.jsonwriter.JsonWriter;
import main.mapperfacroy.AbstractJsonMapperFactory;
import main.reflection.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PojoMapper implements JsonMapper {

    private AbstractJsonMapperFactory mapperFactory;

    private Map<String, Integer> fieldDepthMap;
    private Map<String, String> fieldNameJsonNameMap;

    public PojoMapper(Class<?> clazz, AbstractJsonMapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;

        int depth = 0;
        this.fieldDepthMap = new HashMap<String, Integer>();
        this.fieldNameJsonNameMap = new HashMap<String, String>();

        while (clazz != null) {
            Map<String, String> fields = getFieldsToSerialize(clazz);
            for (String jsonName : fields.keySet()) {
                if (!fieldNameJsonNameMap.containsValue(jsonName)) {
                    fieldNameJsonNameMap.put(fields.get(jsonName), jsonName);
                    fieldDepthMap.put(fields.get(jsonName), depth);
                }
            }
            clazz = clazz.getSuperclass();
            depth++;
        }
    }

    private Map<String, String> getFieldsToSerialize(Class<?> clazz) {
        Map<String, String> jsonNameFieldNameMap = new HashMap<String, String>();
        ReflectionUtils reflectionUtils = new ReflectionUtils();

        List<Field> publicFields = reflectionUtils.getPublicFields(clazz);
        List<Field> nonPublicFields = reflectionUtils.getNonPublicFields(clazz);

        publicFields.stream()
                .filter((field) -> !field.isAnnotationPresent(JsonIgnore.class))
                .forEach((field) -> {
                    String jsonName = getJsonName(field);
                    if (field.isAnnotationPresent(JsonProperty.class)) {
                        jsonNameFieldNameMap.put(jsonName, field.getName());
                    } else if (!jsonNameFieldNameMap.containsKey(jsonName)) {
                        jsonNameFieldNameMap.put(jsonName, field.getName());
                    }
                });

        nonPublicFields.stream()
                .filter((field) -> field.isAnnotationPresent(JsonProperty.class))
                .forEach((field) -> {
                    jsonNameFieldNameMap.put(getJsonName(field), field.getName());
                });


        return jsonNameFieldNameMap;
    }

    private String getJsonName(Field field) {
        String jsonName = null;
        if (field.isAnnotationPresent(JsonProperty.class)) {
            jsonName = field.getAnnotation(JsonProperty.class).name();
        }
        if (jsonName == null || jsonName.equals("")) {
            jsonName = field.getName();
        }
        return jsonName;
    }

    @Override
    public void write(Object obj, JsonWriter writer) {
        writer.writeObjectBegin();
        String fieldName;
        Iterator<String> it = fieldNameJsonNameMap.keySet().iterator();
        for (int i = 0; i < fieldNameJsonNameMap.size() - 1; i++) {
            fieldName = it.next();
            writeField(writer, fieldName, obj);
            writer.writeSeparator();
        }
        fieldName = it.next();
        writeField(writer, fieldName, obj);
        writer.writeObjectEnd();

    }

    private void writeField(JsonWriter writer, String fieldName, Object obj) {
        int depth = fieldDepthMap.get(fieldName);

        writer.writeString(fieldNameJsonNameMap.get(fieldName));
        writer.writePropertySeparator();
        Object fieldVal = getFieldValue(obj, fieldName, depth);

        if (fieldVal == null) {
            writer.writeNull();
        }
        JsonMapper mapper = mapperFactory.createMapper(fieldVal.getClass());
        mapper.write(fieldVal, writer);
    }

    private Object getFieldValue(Object obj, String fieldName, int depth) {
        Class<?> clazz = obj.getClass();
        for (int i = 0; i < depth; i++) {
            clazz = clazz.getSuperclass();
        }
        Object result = null;

        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            result = field.get(obj);
        } catch (Exception e) {
            Logger.getLogger(String.valueOf(PojoMapper.class)).warning(fieldName + "is Not accessible");
        }
        return result;
    }

}
