package service.Impl;

import dto.RegisterSubWorkDto;
import dto.ResponceSubWorkDto;
import entity.Expert;
import entity.SubWork;
import entity.Work;
import enumaration.Status;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import repository.ExpertRepository;
import repository.SubWorkRepository;
import repository.WorkRepository;
import service.AdminService;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final WorkRepository workRepository;
    private final SubWorkRepository subWorkRepository;
    private final ExpertRepository expertRepository;
    private final Validator validator;


    @Override
    public void workRegister(String workName) {
        Work work = Work.builder().name(workName).build();
        Set<ConstraintViolation<Work>> violations = validator.validate(work);
        boolean exists = workRepository.existsByName(workName);
        if (!violations.isEmpty() || exists) {
            for (ConstraintViolation<Work> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            if (exists)
                System.out.println("\u001B[31m" + "work with this name is already exists" + "\u001B[0m");
            return;
        }
       /* if (workRepository.existsByName(workName))
            throw new FoundException("work with this name is already exists");*/

        workRepository.save(work);

    }

    @Override
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
        SubWork subWork = Mapper.convertSubWorkDtoToEntity(subWorkDto, work);
        subWorkRepository.save(subWork);
    }

    @Override
    public List<String> findAllWork() {
        List<String> list = workRepository.findAll().stream().map(Work::getName).toList();
        if (list.isEmpty())
            System.out.println("There are currently no Work.");
        return list;
    }

    @Override
    public List<ResponceSubWorkDto> findAllSubWork() {
        List<SubWork> subWorkList = subWorkRepository.findAll();
        List<ResponceSubWorkDto> responceSubWorkDtos = subWorkList.stream()
                .map(Mapper::convertSubWorkToDto).toList();////Mapper.convertSubWorkToDto(subWork))
        if (responceSubWorkDtos.isEmpty())
            System.out.println("There are currently no SubWork.");
        return responceSubWorkDtos;
    }

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

    @Override
    public void changeExpertStatus(Long expertId, String status) {

        if (status.equalsIgnoreCase(String.valueOf(Status.NEW))
            || status.equalsIgnoreCase(String.valueOf(Status.CONFIRMED)) ||
            status.equalsIgnoreCase(String.valueOf(Status.PENDING_CONFIRMATION))) {
            Expert expert = expertRepository.findById(expertId);
            if (expert != null) {
                status = status.toLowerCase();
                String enumStatus = String.valueOf(expert.getStatus());
                if (!status.equalsIgnoreCase(enumStatus)) {
                    changeStatusOperation(status, expert);
                }
            } else {
                System.err.println("no Expert Found");
            }
        } else {
            System.err.println("enter the valid status");
        }
    }

    private void changeStatusOperation(String status, Expert expert) {
        expert.setStatus(Status.valueOf(status.toUpperCase()));
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
