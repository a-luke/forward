package com.luke.js;

import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.FileReader;
import java.io.LineNumberReader;

/**
 * Hello world!
 */
public class ExecJS {

    /**
     * 在 Java 中调用 JavaScript 脚本中的函数
     */
    @Test
    public void execFunction() {
        Context ct = Context.enter();
        Scriptable scope = ct.initStandardObjects();
        ct.evaluateString(scope, "function test(name){return 'Successful!' + name;}", null, 1, null);
        Object functionObject = scope.get("test", scope);
        if (!(functionObject instanceof Function)) {
            System.out.println("test is undefined or not a function.");
        } else {
            Object testArgs[] = {"Ceven"};
            Function test = (Function) functionObject;
            Object result = test.call(ct, scope, scope, testArgs);
            System.out.println(Context.toString(result));
        }

        System.out.println("Hello World!");
    }

    /**
     * 在 Java 中调用 JavaScript 脚本中的变量
     */
    @Test
    public void useJsParam() {
        Context ct = Context.enter();
        Scriptable scope = ct.initStandardObjects();
        ct.evaluateString(scope, "var test = 'Successful';", null, 1, null);
        Object jsObject = scope.get("test", scope);
        if (jsObject == Scriptable.NOT_FOUND) {
            System.out.println("test is not defined.");
        } else {
            System.out.println("test is " + Context.toString(jsObject));
        }
    }

    /**
     * 在运行环境中导入 JavaScript 工具包
     */
    @Test
    public void importJsTools(){
        Context ct = Context.enter();
        Scriptable scope = ct.initStandardObjects();
        String filename = ExecJS.class.getResource("/tools.js").getPath();
        try {
            LineNumberReader reader = new LineNumberReader(new FileReader(filename));

            String temp = null;
            StringBuffer sb = new StringBuffer();
            while((temp = reader.readLine()) != null){
                sb.append(temp).append("\n");
            }
            ct.evaluateString(scope, sb.toString(), null, 1, null);
            Object result = ct.evaluateString(scope, "test();", null, 1, null);
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            ct.exit();
        }
    }


    /**
     * 在 JavaScript 脚本中使用 Java 对象
     */
    @Test
    public void jsUseJavaObj(){
        Context ct = Context.enter();
        Scriptable scope = ct.initStandardObjects();
        String str = "var test={};";
        str += "test.call=function(){return 'Successful!';};";
        str += "java.lang.System.out.println(test.call())";
        ct.evaluateString(scope, str, null, 1, null);
    }

    @Test
    public void jsUseJavaObj2(){
        Context ct = Context.enter();
        Scriptable scope = ct.initStandardObjects();
        Object out = Context.javaToJS(System.out, scope);
        ScriptableObject.putProperty(scope, "out", out);
        ct.evaluateString(scope, "out.println('Successful!')", null, 1, null);
    }


}
