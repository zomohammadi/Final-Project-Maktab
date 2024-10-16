package spring.service;

public interface AdminOperation {
    void addSubServiceToExpert(Long expertId, Long subServiceId);
    void deleteSubServiceFromExpert(Long expertId, Long subServiceId);
    void confirmedExpert(Long expertId);
}
