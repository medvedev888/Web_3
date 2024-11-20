package me.vladislav.web_3.dao;

import java.util.List;
import java.util.Optional;

public interface DataAccessObject<T> {

    void deleteAll();

    void update(T t);

    Optional<List<T>> getList();

    void save(T t);

}
