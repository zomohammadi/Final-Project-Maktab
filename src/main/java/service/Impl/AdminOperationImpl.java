package service.Impl;

import entity.Expert;
import entity.SubService;
import enumaration.Status;
import lombok.RequiredArgsConstructor;
import repository.ExpertGateway;
import repository.SubServiceGateway;
import service.AdminOperation;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class AdminOperationImpl implements AdminOperation {
    private final SubServiceGateway subServiceRepository;
    private final ExpertGateway expertRepository;

    @Override
    public void addSubServiceToExpert(Long expertId, Long subServiceId) {
        Expert expert = expertRepository.findById(expertId);
        SubService subService = subServiceRepository.findById(subServiceId);
        if (expert != null && subService != null) {
            if (!expert.getStatus().equals(Status.CONFIRMED)) {
                System.err.println("Expert Status is not Confirmed! plz first Confirm the Expert");
                return;
            }
            updateOperation(expert, subService);
        } else {
            if (expert == null)
                System.err.println("expert not found");
            if (subService == null)
                System.err.println("subService not found");
        }
    }

    private void updateOperation(Expert expert, SubService subService) {
        Set<SubService> subServices = expert.getSubServices();
        if (subServices == null) subServices = new HashSet<>();
        subServices.add(subService);
        expert.setSubServices(subServices);
        expertRepository.update(expert);
        System.out.println("add Sub Service To Expert done");
    }

    @Override
    public void deleteSubServiceFromExpert(Long expertId, Long subServiceId) {
        Expert expert = expertRepository.findById(expertId);
        SubService subService = subServiceRepository.findById(subServiceId);
        if (expert != null && subService != null) {
            deleteOperation(expert, subService);
        }
    }

    private void deleteOperation(Expert expert, SubService subService) {
        Set<SubService> subServices = expert.getSubServices();
        subServices.remove(subService);
        expert.setSubServices(subServices);
        expertRepository.update(expert);
        System.out.println("delete SubService From Expert done");
    }


}

/*
public void subServiceRegister(RegisterSubServiceDto subServiceDto) {
        Set<ConstraintViolation<RegistersubServiceDto>> violations = validator.validate(subServiceDto);
        boolean exists = subServiceRepository.existsByName(subServiceDto.name());
        Service service = serviceRepository.findById(subServiceDto.serviceId());
        if (!violations.isEmpty() || exists || service == null) {
            for (ConstraintViolation<RegisterSubServiceDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (exists)
                System.out.println("\u001B[31m" + " Sub service with this name is already exists" + "\u001B[0m");
            if (service == null)
                System.out.println("\u001B[31m" + " service with this Id not Found" + "\u001B[0m");
            return;
        }
//        if (subServiceRepository.existsByName(subServiceDto.name()))
//            throw new FoundException("Sub Service with this name is already exists");
//        Service service = serviceRepository.findById(subServiceDto.serviceId());
//        if (service == null)
//            throw new NotFoundException("service with this Id not Found");
SubService subService = Mapper.convertSubServiceDtoToEntity(subServiceDto, service);
        subServiceRepository.save(subService);
}
 */
