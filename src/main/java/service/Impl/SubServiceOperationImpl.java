package service.Impl;

import dto.RegisterSubWorkDto;
import dto.ResponceSubWorkDto;
import entity.SubWork;
import entity.Work;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import repository.SubWorkRepository;
import repository.WorkRepository;
import service.SubServiceOperation;

import java.util.List;
import java.util.Set;
@RequiredArgsConstructor
public class SubServiceOperationImpl implements SubServiceOperation {
    private final WorkRepository workRepository;
    private final SubWorkRepository subWorkRepository;
    private final Validator validator;

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
        System.out.println("done");
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
}
