package id.co.dapenbi.api.repository;

import id.co.dapenbi.api.entity.EmployeeFamily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeFamilyRepository extends JpaRepository<EmployeeFamily, String> {

    List<EmployeeFamily> findByEmployee_Nip(String nip);
}
