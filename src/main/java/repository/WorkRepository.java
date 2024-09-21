package repository;

import entity.Work;

public interface WorkRepository extends BaseEntityRepository<Work> {
    boolean existsByName(String workName);
}
