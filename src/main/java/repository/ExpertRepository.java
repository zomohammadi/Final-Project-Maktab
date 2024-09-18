package repository;

import java.util.List;

public interface ExpertRepository {
    List<byte[]> getPictureByUserName(String userName);
}
