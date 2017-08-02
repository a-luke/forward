package com.luke.json.toobject;

import com.luke.json.model.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by yangf on 2017/4/5.
 */
public class ParserObject {

    public static <T> void parserObject(T t, Node node) throws Exception {
        if (t instanceof Iterable || t instanceof Object[] || isBaseArray(t)) {
            arrayTypeHandle(t, node);
        } else {
            objectTypeHandle(t, node);
        }
    }

    private static void arrayTypeHandle(Object objs, Node node) {
        if (objs instanceof Iterable || objs instanceof Object[]) {
            inerableHandle(objs, node);
        } else {
            baseArrayHandle(objs, node);
        }
    }

    private static void objectTypeHandle(Object obj, Node node) throws Exception {
        if (obj instanceof Map) {
            mapHandle(obj, node);
        } else {
            objHandle(obj, node);
        }
    }

    private static void inerableHandle(Object objs, Node node) {
        Collection array = (Collection) objs;
        for (Node object : node.getSubs()) {
            array.add(object.getObjValue());
        }
    }

    private static void baseArrayHandle(Object obj, Node node) {
        if (obj.getClass() == byte[].class) {
            byte[] bytes = (byte[]) obj;
            for (Node n : node.getSubs()) {
                bytes[Integer.valueOf(n.getKey())] = Byte.valueOf(n.getObjValue());
            }
        }
        if (obj.getClass() == char[].class) {
            char[] chars = (char[]) obj;
            for (Node n : node.getSubs()) {
                chars[Integer.valueOf(n.getKey())] = Character.valueOf(n.getObjValue().charAt(0));
            }
        }
        if (obj.getClass() == int[].class) {
            int[] ints = (int[]) obj;
            for (Node n : node.getSubs()) {
                ints[Integer.valueOf(n.getKey())] = Integer.valueOf(n.getObjValue());
            }
        }
        if (obj.getClass() == short[].class) {
            short[] shorts = (short[]) obj;
            for (Node n : node.getSubs()) {
                shorts[Integer.valueOf(n.getKey())] = Short.valueOf(n.getObjValue());
            }
        }
        if (obj.getClass() == long[].class) {
            long[] longs = (long[]) obj;
            for (Node n : node.getSubs()) {
                longs[Integer.valueOf(n.getKey())] = Long.valueOf(n.getObjValue());
            }
        }
        if (obj.getClass() == float[].class) {
            float[] floats = (float[]) obj;
            for (Node n : node.getSubs()) {
                floats[Integer.valueOf(n.getKey())] = Float.valueOf(n.getObjValue());
            }
        }
        if (obj.getClass() == double[].class) {
            double[] doubles = (double[]) obj;
            for (Node n : node.getSubs()) {
                doubles[Integer.valueOf(n.getKey())] = Double.valueOf(n.getObjValue());
            }
        }
        if (obj.getClass() == boolean[].class) {
            boolean[] booleans = (boolean[]) obj;
            for (Node n : node.getSubs()) {
                booleans[Integer.valueOf(n.getKey())] = Boolean.valueOf(n.getObjValue());
            }
        }
    }

    private static void mapHandle(Object obj, Node node) {
        Map map = (Map) obj;
        for (Node n : node.getSubs()) {
            map.put(n.getKey(), n.getStrValue());
        }
    }

    private static void objHandle(Object obj, Node node) throws Exception {
        Class clazz = obj.getClass();
        List<Field> allField = new ArrayList<>();
        List<Method> allMethod = new ArrayList<>();

        //装载该对象的所有属性和get，set方法
        traverseFather(clazz, allField, allMethod);
        setFieldValue(obj, allField, node);
    }

    private static void setFieldValue(Object obj, List<Field> allField, Node node)
            throws Exception {
        for (Field field : allField) {
            field.setAccessible(true);
            if (isBaseType(field.getType())) {
                node = node.getSub(field.getName());
                field.set(obj, convertValue(field.getType(), node.getObjValue()));
            } else {
                node = node.getSub(field.getName());
                Object value = getInnerClass(obj.getClass(), field.getType());
                field.set(obj, value);
                parserObject(value, node);
            }
            field.setAccessible(false);
            node = node.getPrev();
        }
    }

    //内部类的情况
    private static Object getInnerClass(Class clazz, Class innerClazz) throws Exception {
        Class innerClazzs[] = clazz.getDeclaredClasses();
        for (Class cls : innerClazzs) {
            if (cls == innerClazz) {
                Constructor con = cls.getDeclaredConstructor();
                //和字段一样，强制获取内部类的访问权限
                con.setAccessible(true);
                return con.newInstance();
            }
        }
        return innerClazz.newInstance();
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

    private static Object convertValue(Class clazz, String str) {
        if (clazz == Integer.class) {
            return Integer.valueOf(str);
        }
        return str;
    }

    private static boolean isStringType(Class clazz) {
        return clazz == Character.class || clazz == String.class || clazz == Enumeration.class;
    }

    private static boolean isNumberType(Class obj) {
        return obj == null || obj == Byte.class || obj == Short.class || obj == Integer.class
                || obj == Long.class || obj == Float.class || obj == Double.class
                || obj == Boolean.class || obj == Date.class;
    }

    private static boolean isBaseType(Class obj) {
        return isNumberType(obj) || isStringType(obj);
    }

    private static boolean isBaseArray(Object obj) {
        Class clazz = obj.getClass();
        return char[].class == clazz || byte[].class == clazz || short[].class == clazz
                || int[].class == clazz || long[].class == clazz || float[].class == clazz
                || double[].class == clazz || boolean[].class == clazz;
    }
}

