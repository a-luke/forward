package com.luke.json.tostring;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by yangf on 2017/3/30.
 */
public class ToStringHandle {

    public static void toJsonString(Object obj, StringBuilder result) {
        if (isBaseType(obj)) {
            result.append(getString(obj));
        } else if (obj instanceof Iterable || obj instanceof Object[] || isBaseArray(obj)) {
            arrayTypeHandle(obj, result);
        } else {
            objectTypeHandle(obj, result);
        }
    }

    private static void arrayTypeHandle(Object objs, StringBuilder result) {
        result.append("[");
        if (objs instanceof Iterable || objs instanceof Object[]) {
            inerableHandle(objs, result);
        } else {
            baseArrayHandle(objs, result);
        }
        result.deleteCharAt(result.lastIndexOf(","));
        result.append("]");
    }

    private static void objectTypeHandle(Object obj, StringBuilder result) {
        result.append('{');
        if (obj instanceof Map) {
            mapHandle(obj, result);
        } else {
            objHandle(obj, result);
        }
        result.deleteCharAt(result.lastIndexOf(","));
        result.append('}');
    }

    private static void inerableHandle(Object objs, StringBuilder result) {
        Iterable array = null;
        List list = new ArrayList();
        if (isBaseArray(objs)) {
            for (Object object : (Object[]) objs) {
                list.add(object);
            }
            array = list;
        } else {
            array = (Iterable) objs;
        }

        for (Object obj : array) {
            toJsonString(obj, result);
            result.append(',');
        }
    }

    private static void baseArrayHandle(Object obj, StringBuilder result) {
        if (obj.getClass() == byte[].class) {
            for (byte b : (byte[]) obj) {
                result.append(b).append(',');
            }
        }
        if (obj.getClass() == char[].class) {
            for (char b : (char[]) obj) {
                result.append('"').append(b).append('"').append(',');
            }
        }
        if (obj.getClass() == int[].class) {
            for (int b : (int[]) obj) {
                result.append(b).append(',');
            }
        }
        if (obj.getClass() == short[].class) {
            for (short b : (short[]) obj) {
                result.append(b).append(',');
            }
        }
        if (obj.getClass() == long[].class) {
            for (long b : (long[]) obj) {
                result.append(b).append(',');
            }
        }
        if (obj.getClass() == float[].class) {
            for (float b : (float[]) obj) {
                result.append(b).append(',');
            }
        }
        if (obj.getClass() == double[].class) {
            for (double b : (double[]) obj) {
                result.append(b).append(',');
            }
        }
        if (obj.getClass() == boolean[].class) {
            for (boolean b : (boolean[]) obj) {
                result.append(b).append(',');
            }
        }
    }

    private static void mapHandle(Object obj, StringBuilder result) {
        Set<Map.Entry> set = ((Map) obj).entrySet();
        List<Map.Entry> entryList = new ArrayList<>();
        entryList.addAll(set);
        objToStr(obj, entryList, result);
    }

    private static void objHandle(Object obj, StringBuilder result) {
        Class clazz = obj.getClass();
        List<Field> allField = new ArrayList<>();
        List<Method> allMethod = new ArrayList<>();

        //装载该对象的所有属性和get，set方法
        traverseFather(clazz, allField, allMethod);
        objToStr(obj, allField, result);
    }

    private static void objToStr(Object obj, List allField, StringBuilder result) {
        for (Object object : allField) {
            Object key = null;
            Object value = null;

            if (object instanceof Field) {
                Field field = (Field) object;
                field.setAccessible(true);
                key = field.getName();
                try {
                    value = field.get(obj);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                Map.Entry entry = (Map.Entry) object;
                key = entry.getKey();
                value = entry.getValue();
            }
            toJsonString(key, result);
            result.append(':');
            toJsonString(value, result);
            result.append(',');
        }
    }

    private static void traverseFather(Class clazz, List<Field> allField, List<Method> allMethod) {
        Method[] methods = clazz.getDeclaredMethods();
        Field[] fields = clazz.getDeclaredFields();

        for (Method method : methods) {
            allMethod.add(method);
        }

        for (Field field : fields) {
            allField.add(field);
        }

        clazz = clazz.getSuperclass();
        if (clazz != null && clazz != Object.class) {
            traverseFather(clazz, allField, allMethod);
        }
    }

    private static String getString(Object obj) {
        if (isNumberType(obj)) {
            return String.valueOf(obj);
        }

        if (isStringType(obj)) {
            return '"' + wipeESC(obj.toString()) + '"';
        }

        return obj.toString();
    }

    private static boolean isStringType(Object obj) {
        return obj instanceof Character || obj instanceof String || obj instanceof Enumeration;
    }

    private static boolean isNumberType(Object obj) {
        return obj == null || obj instanceof Byte || obj instanceof Short || obj instanceof Integer
                || obj instanceof Long || obj instanceof Float || obj instanceof Double
                || obj instanceof Boolean || obj instanceof Date;
    }

    private static boolean isBaseType(Object obj) {
        return isNumberType(obj) || isStringType(obj);
    }

    private static boolean isBaseArray(Object obj) {
        Class clazz = obj.getClass();
        return char[].class == clazz || byte[].class == clazz || short[].class == clazz
                || int[].class == clazz || long[].class == clazz || float[].class == clazz
                || double[].class == clazz || boolean[].class == clazz;
    }

    private static String wipeESC(String str) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\n':
                    sb.append("\\n");
                    continue;
                case '\\':
                    sb.append("\\n");
                    continue;
                case '\"':
                    sb.append("\\\"");
                    continue;
                case '\'':
                    sb.append("\\'");
                    continue;
                case '\t':
                    sb.append("\\t");
                    continue;
                case '\r':
                    sb.append("\\r");
                    continue;
                case '\b':
                    sb.append("\\b");
                    continue;
                case '\f':
                    sb.append("\\f");
                    continue;
                default:
                    sb.append(c);
                    continue;
            }
        }
        return sb.toString();
    }

}
