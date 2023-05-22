package com.luchkovskiy.service;

import java.util.List;

public interface CRUDService<K, T> {
    T findById(K id);

    List<T> findAll();

    T create(T object);

    T update(T object);

    void delete(K id);

}
