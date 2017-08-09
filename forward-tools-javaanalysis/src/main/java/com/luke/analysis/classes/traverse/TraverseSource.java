package com.luke.analysis.classes.traverse;

public interface TraverseSource<T> {

    T next();

    T next(Integer index);

    boolean isArrayIndexOut();

    Integer getIndex();
}
