package com.luke.listener.list;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/6/4.
 */
public class ListListenerHelperTest {

    public static void main(String[] args) {

        List list = new ArrayList();


        ObservableList observableList = new ObservableListWrapper(list);

        //FXCollections 可以用于构造不同的Observable

        observableList.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println(observable);
            }
        });

        observableList.addListener(new ListChangeListener<List>() {
            @Override
            public void onChanged(Change<? extends List> c) {
                //判断执行的是什么操作
                c.next();
                System.out.println("是否是add：" + c.wasAdded());
                System.out.println("是否是remove：" + c.wasRemoved());
                System.out.println(c.getList());
            }
        });

        observableList.add(123);
        observableList.remove(0);

    }
}
