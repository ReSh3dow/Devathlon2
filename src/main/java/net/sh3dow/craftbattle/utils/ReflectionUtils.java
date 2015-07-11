package net.sh3dow.craftbattle.utils;

import java.lang.reflect.Field;

/**
 * Created by Sh3dow on 10.07.2015.
 */
public final class ReflectionUtils {

    public static Field getField(Class clazz, String fieldName)
    {
        Field f = null;
        try {
            f = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        f.setAccessible(true);

        return f;
    }


    public static Object getObject(Class clazz, String fieldName, Object instance)
    {
        Field f = getField(clazz,fieldName);
        try {
            return f.get(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setObject(Class clazz, String fieldName, Object instance, Object value)
    {
        Field f = getField(clazz,fieldName);
        try {
            f.set(instance,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
