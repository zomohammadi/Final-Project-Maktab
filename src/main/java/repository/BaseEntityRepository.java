package repository;

import entity.BaseEntity;

public interface BaseEntityRepository <T extends BaseEntity>{
    void save(T t);
}
