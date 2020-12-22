package com.application.service;

import java.util.List;

public interface AppService<T> {
    public void add(T t);

    public void edit(T t);

    public void delete(int id);

    public T find(int id);

    public List<T> findAll();
}
