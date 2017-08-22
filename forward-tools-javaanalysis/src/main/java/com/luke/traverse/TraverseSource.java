package com.luke.traverse;

public interface TraverseSource<T> {

    T next();

    T next(Integer index);

    boolean isArrayIndexOut();

    Integer getIndex();
}
