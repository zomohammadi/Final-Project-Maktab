package service.Impl;

import customeException.FoundException;
import customeException.NotFoundException;
import dto.RegisterSubWorkDto;
import dto.ResponceSubWorkDto;
import entity.SubWork;
import entity.Work;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import repository.Impl.SubWorkRepositoryImpl;
import repository.SubWorkRepository;
import repository.WorkRepository;
import service.AdminService;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final WorkRepository workRepository;
    private final SubWorkRepository subWorkRepository;
    private final Validator validator;


    @Override
    public void workRegister(String workName) {
        Work work = Work.builder().name(workName).build();
        Set<ConstraintViolation<Work>> violations = validator.validate(work);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Work> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return;
        }
        if (workRepository.existsByName(workName))
            throw new FoundException("work with this name is already exists");

        workRepository.save(work);

    }

    @Override
    public void subWorkRegister(RegisterSubWorkDto subWorkDto) {
        Set<ConstraintViolation<RegisterSubWorkDto>> violations = validator.validate(subWorkDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<RegisterSubWorkDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return;
        }
        if (subWorkRepository.existsByName(subWorkDto.name()))
            throw new FoundException("Sub Work with this name is already exists");
        Work work = workRepository.findById(subWorkDto.workId());
        if (work == null)
            throw new NotFoundException("work with this Id not Found");
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
        List<ResponceSubWorkDto> responceSubWorkDtos = subWorkList.stream().map(subWork -> Mapper.convertSubWorkToDto(subWork)).toList();
        if (responceSubWorkDtos.isEmpty())
            System.out.println("There are currently no SubWork.");
        return responceSubWorkDtos;
        // return subWorkRepository.findAll();
    }
}
