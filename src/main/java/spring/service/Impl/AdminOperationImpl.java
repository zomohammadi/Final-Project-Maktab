package spring.service.Impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.UserSearchCriteriaDto;
import spring.dto.UserSearchResultDto;
import spring.entity.Customer;
import spring.entity.Expert;
import spring.entity.SubService;
import spring.entity.Users;
import spring.enumaration.Role;
import spring.enumaration.Status;
import spring.repository.ExpertGateway;
import spring.repository.SubServiceGateway;
import spring.service.AdminOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminOperationImpl implements AdminOperation {
    private final SubServiceGateway subServiceRepository;
    private final ExpertGateway expertGateway;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public void addSubServiceToExpert(Long expertId, Long subServiceId) {

        Expert expert = expertGateway.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("expert not found"));
        SubService subService = subServiceRepository.findById(subServiceId)
                .orElseThrow(() -> new EntityNotFoundException("subService not found"));

        if (!expert.getStatus().equals(Status.CONFIRMED))
            throw new IllegalStateException("Expert Status is not Confirmed! plz first Confirm the Expert");

        updateOperation(expert, subService);

    }

    private void updateOperation(Expert expert, SubService subService) {
        Set<SubService> subServices = expert.getSubServices();
        if (subServices == null) subServices = new HashSet<>();
        subServices.add(subService);
        //expert.setSubServices(subServices);//no need
        expertGateway.save(expert);
    }

    @Transactional
    @Override
    public void deleteSubServiceFromExpert(Long expertId, Long subServiceId) {
        if (expertId == null || subServiceId == null)
            throw new IllegalArgumentException("input can not be null");
        Expert expert = expertGateway.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("expert not found"));
        SubService subService = subServiceRepository.findById(subServiceId)
                .orElseThrow(() -> new EntityNotFoundException("subService not found"));
        deleteOperation(expert, subService);

    }

    private void deleteOperation(Expert expert, SubService subService) {
        Set<SubService> subServices = expert.getSubServices();
        if (subServices.isEmpty())
            throw new IllegalStateException("no SubService for this expert found");
        subServices.remove(subService);
        //    expert.setSubServices(subServices);//no need
        expertGateway.save(expert);
    }

    @Override
    @Transactional
    public void confirmedExpert(Long expertId) {
        if (expertId == null)
            throw new IllegalArgumentException("expertId can not be Null");
        Expert expert = expertGateway.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Expert Not Found"));
        if (expert.getStatus().equals(Status.NEW)) {
            expert.setStatus(Status.CONFIRMED);
            expertGateway.save(expert);
        }

    }

    @Override
    public List<UserSearchResultDto> searchUsers(UserSearchCriteriaDto search) {


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserSearchResultDto> query = cb.createQuery(UserSearchResultDto.class);
        Root<Users> root = query.from(Users.class);

        List<Predicate> predicates = new ArrayList<>();

        if (search.role() != null) {
            if (search.role().equals(Role.Expert)) {
                Root<Expert> expertRoot = cb.treat(root, Expert.class);
                predicates.add(cb.equal(cb.literal(Expert.class), root.type()));

                if (Boolean.TRUE.equals(search.showHighestExperts()) && Boolean.TRUE.equals(search.showLowestExperts())) {
                    throw new IllegalArgumentException("You can only select either the highest or the lowest scoring experts, not both.");
                }

                if (Boolean.TRUE.equals(search.showHighestExperts())) {

                    CriteriaQuery<Double> maxScoreQuery = cb.createQuery(Double.class);
                    Root<Expert> maxExpertRoot = maxScoreQuery.from(Expert.class);
                    maxScoreQuery.select(cb.max(maxExpertRoot.get("score")));
                    Double highestScore = entityManager.createQuery(maxScoreQuery).getSingleResult();

                    predicates.add(cb.equal(expertRoot.get("score"), highestScore));
                }

                if (Boolean.TRUE.equals(search.showLowestExperts())) {

                    CriteriaQuery<Double> minScoreQuery = cb.createQuery(Double.class);
                    Root<Expert> minExpertRoot = minScoreQuery.from(Expert.class);
                    minScoreQuery.select(cb.min(minExpertRoot.get("score")));
                    Double lowestScore = entityManager.createQuery(minScoreQuery).getSingleResult();

                    predicates.add(cb.equal(expertRoot.get("score"), lowestScore));
                }

                if (StringUtils.isNotBlank(search.subServiceName())) {
                    Join<Expert, SubService> subServiceJoin = expertRoot.join("subServices", JoinType.LEFT);
                    predicates.add(cb.like(cb.lower(subServiceJoin.get("name")), "%" + search.subServiceName().toLowerCase() + "%"));
                }

            } else if (search.role().equals(Role.Customer)) {

                if (search.showLowestExperts() != null || search.showHighestExperts() != null || StringUtils.isNotBlank(search.subServiceName())) {
                    throw new IllegalArgumentException("Customers cannot be searched by score or subServiceName fields.");
                }
                predicates.add(cb.equal(cb.literal(Customer.class), root.type()));
            }
        }
        if (StringUtils.isNotBlank(search.firstName())) {
            predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + search.firstName().toLowerCase() + "%"));
        }
        if (StringUtils.isNotBlank(search.lastName())) {
            predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + search.lastName().toLowerCase() + "%"));
        }
        if (StringUtils.isNotBlank(search.emailAddress())) {
            predicates.add(cb.like(cb.lower(root.get("emailAddress")), "%" + search.emailAddress().toLowerCase() + "%"));
        }

        query.select(cb.construct(
                UserSearchResultDto.class,
                root.get("id"),
                root.get("firstName"),
                root.get("lastName"),
                root.get("emailAddress"),
                root.get("role"),
                root.get("userName"),
                cb.selectCase()
                        .when(cb.equal(cb.literal(Role.Expert), root.get("role")), root.get("score"))
                        .otherwise(cb.nullLiteral(Double.class))
        ));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}