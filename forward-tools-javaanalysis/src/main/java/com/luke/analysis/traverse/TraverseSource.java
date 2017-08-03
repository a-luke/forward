package com.luke.analysis.traverse;

public interface TraverseSource<T> {

    public T next();

    public T next(Integer index);

    public boolean isArrayIndexOut();
}
