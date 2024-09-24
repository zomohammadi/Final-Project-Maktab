package service.Impl;

import entity.Work;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import repository.WorkRepository;
import service.ServiceOperation;

import java.util.List;
import java.util.Set;
@RequiredArgsConstructor
public class ServiceOperationImpl implements ServiceOperation {
    private final WorkRepository workRepository;
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
        System.out.println("done");


    }
    @Override
    public List<String> findAllWork() {
        List<String> list = workRepository.findAll().stream().map(Work::getName).toList();
        if (list.isEmpty())
            System.out.println("There are currently no Work.");
        return list;
    }
}
