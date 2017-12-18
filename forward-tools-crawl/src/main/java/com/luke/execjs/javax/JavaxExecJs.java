package com.luke.execjs.javax;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by yangf on 2017/11/30/0030.
 */
public class JavaxExecJs {

    @Test
    public void test() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        System.out.println(engine.getClass().getName());
        engine.eval("var j =0; function f(i) { return j=i+1; }");
        int i = 10;
//        System.out.println("Result:" + engine.eval("f("+ i +"); j;"));
        engine.put("i", i);
        System.out.println(engine.eval("f(i)"));
    }
}
