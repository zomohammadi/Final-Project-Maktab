package spring.mapper;

import java.util.List;

@SuppressWarnings("unused")
public interface BaseMapper<E, D> {

    E convertDtoToEntity(D d);


    D convertEntityToDTO(E e);

    List<E> convertDtoToEntity(List<D> d);


    List<D> convertEntityToDTO(List<E> e);
}
