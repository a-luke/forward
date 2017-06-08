package com.luke.json;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangf on 2017/3/30.
 */
public class JsonTest {
    @Test
    public void testJSON() throws Exception {

        A a = new A();
        a.setAge(23);
        a.setName("小米");
        a.setClasses("B班");
        A.C c = new A.C();
        a.setC(c);
        a.setNumber("23");
        c.setClientNo("1111111111");


        Map<String, Object> map = new HashMap<>();
        map.put("123", "\r\n123\n");
        map.put("345", 23);
        map.put("23", new boolean[]{true, false, true});
        map.put("678", new int[]{1,2,3});
        map.put("bb", a);

        List list = new ArrayList();
        list.add(a);

        map.put("list", list);
//        System.out.println(JSON.toJsonString(map));

        String jsonStr = JSON.toJsonString(a);
        System.out.println(jsonStr);
        A a1 = JSON.parser(jsonStr, A.class);
        System.out.println(a1);

    }
}


class A extends B{
    private String name;
    private Integer age;
    private C c;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    static class C{
        private String clientNo;

        public String getClientNo() {
            return clientNo;
        }

        public void setClientNo(String clientNo) {
            this.clientNo = clientNo;
        }

        @Override
        public String toString() {
            return "C{" + "clientNo='" + clientNo + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "A{" + "name='" + name + '\'' + ", age=" + age + ", c=" + c + '}';
    }
}

class B{
    private String classes;
    private String number;

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "B{" + "classes='" + classes + '\'' + ", number='" + number + '\'' + '}';
    }
}