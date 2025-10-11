package com.softcomp.ga.models;

public class Gene<T> {
    private T value;

    public Gene(T gene) {
        this.value = gene;
    }

    public T get() {
        return value;
    }

    public void set(T gene) {
        this.value = gene;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
