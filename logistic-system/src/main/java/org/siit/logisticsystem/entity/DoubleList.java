package org.siit.logisticsystem.entity;

import java.util.ArrayList;
import java.util.List;

public class DoubleList<T> {
    private List<T> failed = new ArrayList<>();
    private List<T> added = new ArrayList<>();

    public List<T> getFailed() {
        return failed;
    }

    public void setFailed(List<T> failed) {
        this.failed = failed;
    }

    public List<T> getAdded() {
        return added;
    }

    public void setAdded(List<T> added) {
        this.added = added;
    }

    public void addInFailed(T item){
        failed.add(item);

    }

    public void addInAdded(T item){
        added.add(item);

    }

}
