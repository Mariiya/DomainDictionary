package com.pullenti.unisharp;

public interface EventHandler<T> {

    void call(Object sender, T arg);
}
