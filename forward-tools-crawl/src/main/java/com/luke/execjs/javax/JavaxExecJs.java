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
        Object val = engine.eval("String(function(p,a,c,k,e,d){e=function(c){return(c<a?\"\":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,"
            + "String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\\\b'+e(c)"
            + "+'\\\\b','g'),k[c]);return p;}('q l=[\\'8://7-c-6-9-3.2.5/4/e/0/m.f?g=0&b=a&d=1\\',\\'8://7-c-6-9-3.2.5/4/e/0/n.f?g=0&b=a&d=1\\',\\'8://7-c-6-9-3.2.5/4/e/0/k"
            + ".f?g=0&b=a&d=1\\',\\'8://7-c-6-9-3.2.5/4/e/0/h.f?g=0&b=a&d=1\\',\\'8://7-c-6-9-3.2.5/4/e/0/i.f?g=0&b=a&d=1\\',\\'8://7-c-6-9-3.2.5/4/e/0/j.f?g=0&b=a&d=1\\',"
            + "\\'8://7-c-6-9-3.2.5/4/e/0/o.f?g=0&b=a&d=1\\',\\'8://7-c-6-9-3.2.5/4/e/0/t.f?g=0&b=a&d=1\\',\\'8://7-c-6-9-3.2.5/4/e/0/u.f?g=0&b=a&d=1\\',"
            + "\\'8://7-c-6-9-3.2.5/4/e/0/v.f?g=0&b=a&d=1\\',\\'8://7-c-6-9-3.2.5/4/e/0/s.f?g=0&b=a&d=1\\',\\'8://7-c-6-9-3.2.5/4/e/0/p.f?g=0&b=a&d=1\\',"
            + "\\'8://7-c-6-9-3.2.5/4/e/0/r.f?g=0&b=a&d=1\\'];',32,32,'518896||cdndm5|99|34|com|174|manhua1032|http|50|0ac632d165c08579fb0a588d653ecabd|key|61|type|33991|jpg|cid"
            + "|17_4191|18_8445|19_5326|16_2679|newImgs|14_4664|15_5274|20_9980|25_2859|var|26_3750|24_1798|21_7738|22_9271|23_1126'.split('|'),0,{}))");
        int i = 10;
//        System.out.println("Result:" + engine.eval("f("+ i +"); j;"));
        System.out.println(val);
    }
}
