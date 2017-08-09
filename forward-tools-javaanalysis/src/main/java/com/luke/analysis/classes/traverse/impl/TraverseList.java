package com.luke.analysis.classes.traverse.impl;

import com.luke.analysis.classes.traverse.TraverseSource;

import java.util.List;

public class TraverseList<T> implements TraverseSource<T> {
    private List<T> source;

    private Integer index = -1;

    public TraverseList(List<T> source) {
        this.source = source;
    }

    @Override
    public T next() {
        int step = ++index;
        if (step >= source.size()) {
            return null;
        }
        return source.get(step);
    }

    @Override
    public T next(Integer index) {
        int step = (this.index == -1 ? (index == 0 ? 0 : -1) : this.index) + index;
        if (step < 0 || step >= source.size()) {
            return null;
        }
        return source.get(step);
    }

    @Override
    public boolean isArrayIndexOut() {
        return index >= source.size() - 1;
    }

    @Override
    public Integer getIndex() {
        return index;
    }
}
