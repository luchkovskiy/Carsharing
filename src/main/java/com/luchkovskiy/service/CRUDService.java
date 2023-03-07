package com.luchkovskiy.service;

import java.util.List;

public interface CRUDService<K, T> {
    T read(K id);

    List<T> readAll();

    T create(T object);

    T update(T object);

    void delete(K id);

}
