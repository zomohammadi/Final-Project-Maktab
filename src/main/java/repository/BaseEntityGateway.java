package repository;

import entity.BaseEntity;

import java.util.List;

public interface BaseEntityGateway<T extends BaseEntity> {
    void save(T t);

    T findById(Long id);

    List<T> findAll();

    void update(T t);
}
