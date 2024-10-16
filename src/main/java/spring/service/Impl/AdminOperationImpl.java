package spring.service.Impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.Expert;
import spring.entity.SubService;
import spring.enumaration.Status;
import spring.repository.ExpertGateway;
import spring.repository.SubServiceGateway;
import spring.service.AdminOperation;

import java.util.HashSet;
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

}