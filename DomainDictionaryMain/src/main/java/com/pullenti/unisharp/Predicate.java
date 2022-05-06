package com.pullenti.unisharp;

public interface Predicate<T> {

    boolean call(T obj);
}