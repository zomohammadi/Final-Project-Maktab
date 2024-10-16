package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.dto.projection.OrdersBriefProjection;
import spring.dto.projection.SuggestionBriefProjection;
import spring.dto.projection.SuggestionInfoProjection;
import spring.entity.Expert;
import spring.entity.Orders;
import spring.entity.Suggestion;

import java.util.List;

public interface SuggestionGateway extends JpaRepository<Suggestion, Long> {
    @Query(nativeQuery = true,
            value = """
                    select o.id as OrderId,
                    s.name as subServiceName,
                    o.address as address
                      ,u.user_name as userName,
                      o.price_Suggested as priceSuggested
                      ,o.service_Description as ServiceDescription,
                      o.time_For_Service_Done as timeForServiceDone
                     from orders o
                      inner join sub_service s on o.sub_service_id=s.id
                      inner join users u on u.id = o.customer_id
                      inner join expert_sub_services es on es.sub_services_id = s.id
                      inner join expert e on e.id = es.experts_id
                      where e.id = :id
                      and o.order_status in (0, 1)
                                                                      
                      """)
    List<OrdersBriefProjection> listOrders(@Param("id") Long expertId);

    boolean existsSuggestionByExpertAndOrder(Expert expert, Orders order);

    @Query(nativeQuery = true,
            value = """
                    select s.id as suggestionId
                         , s.duration_of_service as  durationOfService
                         , s.price_suggested as  priceSuggested
                         , s.suggested_time_start_service as  SuggestedTimeStartService
                         , u.user_name as  userName
                         , u.first_name as  firstName
                         , u.last_name  as lastName
                         , e.score as  score
                    from orders o
                             inner join customer c on c.id = o.customer_id
                             inner join suggestion s on o.id = s.order_id
                             inner join expert e on e.id = s.expert_id
                             inner join users u on u.id = e.id
                    where c.id = :customerId
                      and o.id = :orderId
                      order by e.score
                    """
    )
    List<SuggestionBriefProjection> listOrderSuggestions(@Param("customerId") Long customerId, @Param("orderId") Long orderId);

  @Query(
          """
select s.suggestedTimeStartService as suggestedTimeStartService,
s.durationOfService as durationOfService,
s.priceSuggested as priceSuggested
 from Suggestion s where s.expert= :expert and s.order = :order
"""
  )
  SuggestionInfoProjection getSuggestedTimeStartService(@Param("expert") Expert expert, @Param("order") Orders order);
}
