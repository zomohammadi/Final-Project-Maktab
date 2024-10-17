package spring.service.Impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.UserSearchCriteriaDto;
import spring.entity.Expert;
import spring.entity.SubService;
import spring.entity.Users;
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
    private final EntityManager entityManager;

    @Override
    public List<Users> searchUsers(UserSearchCriteriaDto searchCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> query = cb.createQuery(Users.class);
        Root<Users> userRoot = query.from(Users.class);

        List<Predicate> predicates = new ArrayList<>();

        // Add filtering conditions based on DTO fields
        if (searchCriteria.role() != null) {
            predicates.add(cb.equal(userRoot.get("role"), searchCriteria.role()));
        }
        if (searchCriteria.firstName() != null) {
            predicates.add(cb.like(cb.lower(userRoot.get("firstName")), "%" + searchCriteria.firstName().toLowerCase() + "%"));
        }
        if (searchCriteria.lastName() != null) {
            predicates.add(cb.like(cb.lower(userRoot.get("lastName")), "%" + searchCriteria.lastName().toLowerCase() + "%"));
        }
        if (searchCriteria.emailAddress() != null) {
            predicates.add(cb.like(cb.lower(userRoot.get("emailAddress")), "%" + searchCriteria.emailAddress().toLowerCase() + "%"));
        }

        // Special case for Experts (use a join to filter on subService and score)
        if (searchCriteria.subService() != null || searchCriteria.minScore() != null || searchCriteria.maxScore() != null) {
            Join<Users, Expert> expertJoin = userRoot.join("expert", JoinType.INNER);

            if (searchCriteria.subService() != null) {
                predicates.add(cb.isMember(searchCriteria.subService(), expertJoin.get("subServices")));
            }
            if (searchCriteria.minScore() != null) {
                predicates.add(cb.greaterThanOrEqualTo(expertJoin.get("score"), searchCriteria.minScore()));
            }
            if (searchCriteria.maxScore() != null) {
                predicates.add(cb.lessThanOrEqualTo(expertJoin.get("score"), searchCriteria.maxScore()));
            }
        }

        // Apply the predicates to the query
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}