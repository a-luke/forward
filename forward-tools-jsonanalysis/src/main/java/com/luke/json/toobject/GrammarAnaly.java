package com.luke.json.toobject;

import com.luke.json.model.Node;
import com.luke.json.model.NodeType;
import com.luke.json.model.Word;
import com.luke.json.model.WordType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by YANGFENG457 on 2017-04-01.
 */
public class GrammarAnaly {

    private List<Word> list;

    private int index = 0;

    private Node root;

    public GrammarAnaly(String str) {
        list = new WordAnaly(str).getWord();
    }

    public Node getRoot() {
        root = new Node();
        List<Node> sub = new ArrayList<>();
        root.setSubs(sub);
        if(WordType.LDK.equals(list.get(0).getType())){
            root.setType(NodeType.OBJECT);
            analyObj(root, sub);
        }else{
            root.setType(NodeType.ARRAY);
            analyArr(root, sub);
        }
        return root;
    }

    private void analyObj(Node prev, List<Node> sub) {
        Word word = null;
        Node node = new Node();
        node.setSubs(Collections.emptyList());
        node.setPrev(prev);
        sub.add(node);

        if (WordType.SY.equals(getNext(1).getType()) && WordType.STR.equals((word = getNext(2)).getType()) && WordType.SY.equals(getNext(3).getType())) {
            index = index + 3;
            node.setKey(word.getValue());
        } else {
            getThrow(getNext(1).getValue());
        }

        if (WordType.MH.equals(getNext(1).getType())) {
            index++;
            setValue(node);
        } else {
            getThrow(getNext(-1).getValue());
        }

        endHandle(prev);
    }

    private void analyArr(Node prev, List<Node> sub) {
        Node node = new Node();
        node.setPrev(prev);
        node.setSubs(Collections.emptyList());
        node.setKey(String.valueOf(sub.size()));
        sub.add(node);
        setValue(node);
        endHandle(prev);
    }

    private void setValue(Node node) {
        Word word = getNext(1);
        if (WordType.SY.equals(word.getType())) {
            //" value "
            getNext();
            word = getNext();
            getNext();
            node.setObjValue(word.getValue());
            node.setType(NodeType.STRING);
        } else if (WordType.NUM.equals(word.getType())) {
            index++;
            node.setObjValue(word.getValue());
            node.setType(NodeType.NUMBER);
        } else if (WordType.LDK.equals(word.getType())) {
            index++;
            node.setType(NodeType.OBJECT);
            List subList = new ArrayList<>();
            node.setSubs(subList);
            if(WordType.RDK.equals(getNext(1).getType())){
                index++;
                node.setEnd(true);
                node.setType(NodeType.EMPTY);
            }else{
                analyObj(node, subList);
            }

        } else if (WordType.LZK.equals(word.getType())) {
            index++;
            node.setType(NodeType.ARRAY);
            List subList = new ArrayList<>();
            node.setSubs(subList);
            if(WordType.RZK.equals(getNext(1).getType())){
                index++;
                node.setEnd(true);
                node.setType(NodeType.EMPTY);
            }else {
                analyArr(node, subList);
            }
        } else {
            getThrow(getNext(-1).getValue());
        }
    }


    private void endHandle(Node prev){
        Node prevNode = prev;
        while (true) {
            Word nextWord = getNext(1);
            if (nextWord == null) {
                return;
            }

            if (prevNode == null) {
                getThrow(nextWord.getValue());
            }

            if (WordType.RDK.equals(nextWord.getType()) || WordType.RZK.equals(nextWord.getType())) {
                index++;
                prevNode.setEnd(true);
                prevNode = prevNode.getPrev();
            } else if (WordType.DH.equals(nextWord.getType())) {
                index++;
                if(NodeType.ARRAY.equals(prevNode.getType())){
                    analyArr(prevNode, prevNode.getSubs());
                }else{
                    analyObj(prevNode, prevNode.getSubs());
                }
            } else {
                getThrow(nextWord.getValue());
            }

        }
    }

    private void getThrow(String info) {
        throw new RuntimeException("格式错误，位置：" + String.valueOf(index) + " -> '" + info + "'");
    }

    private Word getNext() {
        return index == list.size() ? null : list.get(++index);
    }

    private Word getNext(int i) {
        return (index + i) >= list.size() ? null : list.get(index + i);
    }

}
