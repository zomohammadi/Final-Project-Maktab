package service.Impl;

import entity.Expert;
import entity.SubWork;
import enumaration.Status;
import lombok.RequiredArgsConstructor;
import repository.ExpertRepository;
import repository.SubWorkRepository;
import service.AdminService;

import java.util.Set;

@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final SubWorkRepository subWorkRepository;
    private final ExpertRepository expertRepository;

    @Override
    public void addSubWorkToExpert(Long expertId, Long subWorkId) {
        Expert expert = expertRepository.findById(expertId);
        SubWork subWork = subWorkRepository.findById(subWorkId);
        if (expert != null && subWork != null) {
            if (!expert.getStatus().equals(Status.CONFIRMED)) {
                System.err.println("Expert Status is not Confirmed! plz first Confirm the Expert");
                return;
            }
            updateOperation(expert, subWork);
        } else {
            if (expert == null)
                System.err.println("expert not found");
            if (subWork == null)
                System.err.println("subWork not found");
        }
    }

    private void updateOperation(Expert expert, SubWork subWork) {
        Set<SubWork> subWorks = expert.getSubWorks();
        subWorks.add(subWork);
        expert.setSubWorks(subWorks);
        expertRepository.update(expert);
        System.out.println("done");
    }

    @Override
    public void deleteSubWorkFromExpert(Long expertId, Long subWorkId) {
        Expert expert = expertRepository.findById(expertId);
        SubWork subWork = subWorkRepository.findById(subWorkId);
        if (expert != null && subWork != null) {
            deleteOperation(expert, subWork);
        }
    }

    private void deleteOperation(Expert expert, SubWork subWork) {
        Set<SubWork> subWorks = expert.getSubWorks();
        subWorks.remove(subWork);
        expert.setSubWorks(subWorks);
        expertRepository.update(expert);
        System.out.println("done");
    }



}

/*
public void subWorkRegister(RegisterSubWorkDto subWorkDto) {
        Set<ConstraintViolation<RegisterSubWorkDto>> violations = validator.validate(subWorkDto);
        boolean exists = subWorkRepository.existsByName(subWorkDto.name());
        Work work = workRepository.findById(subWorkDto.workId());
        if (!violations.isEmpty() || exists || work == null) {
            for (ConstraintViolation<RegisterSubWorkDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (exists)
                System.out.println("\u001B[31m" + " Sub Work with this name is already exists" + "\u001B[0m");
            if (work == null)
                System.out.println("\u001B[31m" + " work with this Id not Found" + "\u001B[0m");
            return;
        }
//        if (subWorkRepository.existsByName(subWorkDto.name()))
//            throw new FoundException("Sub Work with this name is already exists");
//        Work work = workRepository.findById(subWorkDto.workId());
//        if (work == null)
//            throw new NotFoundException("work with this Id not Found");
SubWork subWork = Mapper.convertSubWorkDtoToEntity(subWorkDto, work);
        subWorkRepository.save(subWork);
}
 */
