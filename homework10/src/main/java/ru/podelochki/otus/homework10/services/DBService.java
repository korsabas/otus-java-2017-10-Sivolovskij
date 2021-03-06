package ru.podelochki.otus.homework10.services;

import ru.podelochki.otus.homework10.models.DataSet;

public interface DBService {
	<T extends DataSet> T load(long id, Class<T> clazz);

    <T extends DataSet> void save(T entity);

    void close();
}
