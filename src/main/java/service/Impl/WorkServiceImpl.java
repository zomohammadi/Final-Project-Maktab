package service.Impl;

import customeException.FoundException;
import entity.Work;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import repository.WorkRepository;
import service.WorkService;

import java.util.Set;

@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {
    private final WorkRepository workRepository;
    private final Validator validator;


    @Override
    public void register(String workName) {
        Work work = Work.builder().name(workName).build();
        if (isNotValid(work)) return;
        if (workRepository.existsByName(workName))
            throw new FoundException("work with this name is already exists");

        workRepository.save(work);

    }

    private boolean isNotValid(Work workName) {
        Set<ConstraintViolation<Work>> violations = validator.validate(workName);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Work> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return true;
        }
        return false;
    }
}
