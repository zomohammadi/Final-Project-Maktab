package repository;

import entity.Expert;

import java.util.List;

public interface ExpertGateway extends UserGateway<Expert> {
    List<byte[]> getPictureByUserName(String userName);
}
