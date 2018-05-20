package id.co.dapenbi.api.repository;

import id.co.dapenbi.api.entity.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, String>{
    List<EmployeeAddress> findByEmployee_Nip(String employeeId);
}
