package main.mapperfacroy;

import main.jsonmapper.JsonMapper;

public abstract class AbstractJsonMapperFactory {
    
    public abstract void addMapper(Class<?> clazz, JsonMapper mapper);
    
    public abstract JsonMapper createMapper(Class<?> clazz);
    
    protected abstract JsonMapper createPojoMapper(Class<?> clazz);

}
