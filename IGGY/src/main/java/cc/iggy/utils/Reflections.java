package cc.iggy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by VanagaS on 23-04-2017.
 */
public class Reflections {

  public static Method setMethodAccessible(Class klass, String methodName, Class<?>... params)
      throws NoSuchMethodException {
    Method method = klass.getDeclaredMethod(methodName, params);
    method.setAccessible(true);
    return method;
  }

  public static Field setFieldAccessible(Class klass, String fieldName) throws NoSuchFieldException {
    Field field = klass.getDeclaredField(fieldName);
    field.setAccessible(true);
    return field;
  }
}
