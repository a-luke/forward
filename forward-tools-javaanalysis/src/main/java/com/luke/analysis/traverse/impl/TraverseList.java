package com.luke.analysis.traverse.impl;

import com.luke.analysis.traverse.TraverseSource;

import java.util.List;

public class TraverseList<T> implements TraverseSource<T> {
    private List<T> source;

    private Integer index = -1;

    public TraverseList(List<T> source) {
        this.source = source;
    }

    @Override
    public T next() {
        return source.get(++index);
    }

    @Override
    public T next(Integer index) {
        int step = (this.index == -1 ? 0 : this.index) + index;
        if (step < 0 || step >= source.size()) {
            return null;
        }
        return source.get(step);
    }

    @Override
    public boolean isArrayIndexOut() {
        return index >= source.size() - 1;
    }
}
