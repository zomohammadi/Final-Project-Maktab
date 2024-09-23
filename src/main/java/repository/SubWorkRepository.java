package repository;

import entity.SubWork;
import entity.Work;

public interface SubWorkRepository extends BaseEntityRepository<SubWork> {
    boolean existsByName(String workName);
}
