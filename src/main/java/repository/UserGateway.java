package repository;

import entity.BaseEntity;

public interface UserGateway<T extends BaseEntity> extends BaseEntityGateway<T> {
    boolean existUserByNationalCode(String nationalCode);
    boolean existUserByMobileNumber(String nationalCode);
    boolean existUserByEmailAddress(String nationalCode);
    boolean existUserByUserName(String nationalCode);
}
