package com.luchkovskiy.repository;

import java.util.List;

public interface CRUDRepository<K, T> {

    T read(K id);

    List<T> readAll();

    T create(T object);

    T update(T object);

    void delete(K id);

}
