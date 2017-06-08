package com.luke.json;

import com.luke.json.model.JsonObject;
import com.luke.json.model.Node;
import com.luke.json.model.NodeType;
import com.luke.json.toobject.GrammarAnaly;
import com.luke.json.toobject.WordAnaly;
import org.junit.Test;

/**
 * Created by yangf on 2017/3/30.
 */
public class TestObj {


    @Test
    public void wordAnalyTest(){
        System.out.println(new WordAnaly("{\"qwe\":123.1, \"asd\":\"aaa\"}").getWord());
    }

    @Test
    public void GrammarAnalyTest(){
        Node root = new GrammarAnaly("{\"qwe\":123.1,\"asd\":\"aaa\",\"obj\":{\"cc\": \"1111\", \"ert\":[123,456], \"sss\":123}}").getRoot();
        Node node = root;
        StringBuilder str = new StringBuilder();
        traverseStr(root, str);
        String jsonStr = str.deleteCharAt(str.lastIndexOf(",")).toString().replace(",]", "]").replace(",}", "}");
        System.out.println(jsonStr);
    }

    @Test
    public void parserTest(){
        String str = "{\"qwe\":123.1,\"asd\":\"aaa\",\"obj\":{\"cc\": \"1111\", \"ert\":[123,456], \"sss\":123}}";
        JsonObject JsonObject = JSON.parser(str);
        System.out.println(JsonObject.get("obj").get("ert").getValue());
    }


    public void traverseStr(Node node, StringBuilder str){
        if(NodeType.OBJECT.equals(node.getType())){
            str.append("{");
        }else if (NodeType.ARRAY.equals(node.getType())){
            str.append("[");
        }
        for(Node sub : node.getSubs()){
            if(NodeType.OBJECT.equals(node.getType())){
                str.append(sub.getKey()).append(":");
                if(sub.getObjValue() != null){
                    str.append(sub.getObjValue()).append(",");
                }
            }else if (NodeType.ARRAY.equals(node.getType())){
                str.append(sub.getObjValue()).append(",");
            }
            traverseStr(sub, str);
        }
        if(NodeType.OBJECT.equals(node.getType())){
            str.append("},");
        }else if (NodeType.ARRAY.equals(node.getType())){
            str.append("],");
        }
    }


    private void traverse(Node node){
        System.out.println(node);
        for(Node sub : node.getSubs()){
            traverse(sub);
        }
    }


}
