package main.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


public class ReflectionUtils {
    
    public enum ModifierType {
        PUBLIC, PRIVATE, PROTECTED, PACKAEG_PRIVATE
    }
    
    public ModifierType getModifierType(Field field) {
        int modifiers = field.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            return ModifierType.PUBLIC;
        } else if (Modifier.isProtected(modifiers)) {
            return ModifierType.PROTECTED;
        } else if (Modifier.isPrivate(modifiers)) {
            return ModifierType.PRIVATE;
        } else  {
            return ModifierType.PACKAEG_PRIVATE;
        }
    }
    
    public List<Field> getPublicFields(Class<?> clazz) {
        List<Field> result = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())) {
                result.add(field);
            }
        }
        
        return result;
    }
    
    public List<Field> getNonPublicFields(Class<?> clazz) {
        List<Field> result = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                result.add(field);
            }
        }
        return result;
    }
    
}
