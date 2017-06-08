package com.luke.json;

import com.luke.json.model.JsonObject;
import com.luke.json.model.Node;
import com.luke.json.toobject.GrammarAnaly;
import com.luke.json.toobject.ParserObject;
import com.luke.json.tostring.ToStringHandle;

/**
 * Created by yangf on 2017/3/29.
 */
public class JSON {
    public static String toJsonString(Object obj) {
        StringBuilder result = new StringBuilder();
        ToStringHandle.toJsonString(obj, result);
        return result.toString();
    }

    public static JsonObject parser(String str){
        GrammarAnaly grammarAnaly = new GrammarAnaly(str);
        Node node = grammarAnaly.getRoot();
        JsonObject jsonObject = new JsonObject(node);
        return jsonObject;
    }

    public static <T> T parser(String str, Class<T> clazz)
        throws Exception {
        GrammarAnaly grammarAnaly = new GrammarAnaly(str);
        Node node = grammarAnaly.getRoot();
        T t = clazz.newInstance();
        ParserObject.parserObject(t, node);
        return t;
    }
}