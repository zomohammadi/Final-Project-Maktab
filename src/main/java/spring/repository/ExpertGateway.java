package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.entity.Expert;

public interface ExpertGateway extends JpaRepository<Expert, Long> {

    @Query("""
            select CASE WHEN COUNT(u) > 0 THEN true ELSE false end from Users u
             inner join Expert d
             on u.id = d.id
             where u.nationalCode = :nationalCode
            """)
    boolean existUserByNationalCode(String nationalCode);

    @Query("""
                        select CASE WHEN COUNT(u) > 0 THEN true ELSE false end from Users u
                         inner join Expert d
                         on u.id = d.id
            where u.mobileNumber = :mobileNumber
            """)
    boolean existUserByMobileNumber(String mobileNumber);

    @Query("select CASE WHEN COUNT(u) > 0 THEN true ELSE false end from Users u where u.emailAddress = :emailAddress ")
    boolean existUserByEmailAddress(String emailAddress);

    @Query("select CASE WHEN COUNT(u) > 0 THEN true ELSE false end from Users u where u.userName = :userName ")
    boolean existUserByUserName(String userName);

    @Query("select e.picture as Picture from Expert e where e.userName = :userName")
    byte[] getPictureByUserName(@Param("userName") String userName);

}
