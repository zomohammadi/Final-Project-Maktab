package repository;

import entity.Expert;

import java.util.List;

public interface ExpertRepository extends UserRepository<Expert>{
    List<byte[]> getPictureByUserName(String userName);
}
