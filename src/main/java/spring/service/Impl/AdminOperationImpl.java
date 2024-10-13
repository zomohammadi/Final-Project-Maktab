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
    private final ExpertGateway expertRepository;

    @Transactional
    @Override
    public void addSubServiceToExpert(Long expertId, Long subServiceId) {

        Expert expert = expertRepository.findById(expertId)
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
        expertRepository.save(expert);
    }

    @Transactional
    @Override
    public void deleteSubServiceFromExpert(Long expertId, Long subServiceId) {
        if (expertId == null || subServiceId == null)
            throw new IllegalArgumentException("input can not be null");
        Expert expert = expertRepository.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("expert not found"));
        SubService subService = subServiceRepository.findById(subServiceId)
                .orElseThrow(() -> new EntityNotFoundException("subService not found"));
        deleteOperation(expert, subService);

    }

    private void deleteOperation(Expert expert, SubService subService) {
        Set<SubService> subServices = expert.getSubServices();
        if (subServices == null)
            throw new IllegalStateException("no SubService found for delete");
        subServices.remove(subService);
        //    expert.setSubServices(subServices);//no need
        expertRepository.save(expert);
    }


}