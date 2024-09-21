package repository;

import entity.BaseEntity;

public interface UserRepository<T extends BaseEntity> extends BaseEntityRepository<T>{
    boolean existUserByNationalCode(String nationalCode);
    boolean existUserByMobileNumber(String nationalCode);
    boolean existUserByEmailAddress(String nationalCode);
    boolean existUserByUserName(String nationalCode);
}
