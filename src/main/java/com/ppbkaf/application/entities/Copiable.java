package com.ppbkaf.application.entities;

public interface Copiable<T> {
    void copy(T obj);
}