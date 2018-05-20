package id.co.dapenbi.api.service;


import id.co.dapenbi.api.entity.Employee;
import id.co.dapenbi.api.repository.EmployeeRepository;
import id.co.dapenbi.api.util.ZCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public ZCodeResponse create(Employee employee){
        ZCodeResponse validate = validate(employee);
        if (validate != null) return validate;
        Employee db = employeeRepository.save(employee);
        return new ZCodeResponse(db, HttpStatus.OK);
    }

    public ZCodeResponse update(Employee param){
        ZCodeResponse validate = validate(param);
        if (validate != null) return validate;
        Optional<Employee> opt = employeeRepository.findById(param.getNip());
        if (!opt.isPresent())
            return new ZCodeResponse("Data karyawan tidak ditemukan", HttpStatus.BAD_REQUEST);
        Employee employee = opt.get();
        employee.setName(param.getName());
        employee.setBirthDate(param.getBirthDate());
        employee.setFinishDate(param.getFinishDate());
        employee.setMarital(param.getMarital());
        employee.setSalary(param.getSalary());
        employee.setSex(param.getSex());
        employee.setStartDate(param.getStartDate());
        Employee db = employeeRepository.save(employee);
        return new ZCodeResponse(db, HttpStatus.OK);
    }

    public Employee findByNip(String nip){
        return employeeRepository.findById(nip).orElse(null);
    }

    public Page<Employee> findAll(Pageable pageable){
        return employeeRepository.findAll(pageable);
    }

    public void delete(String nip){
        employeeRepository.deleteById(nip);
    }

    private ZCodeResponse validate(Employee employee) {
        if (StringUtils.isEmpty(employee.getNip()))
            return new ZCodeResponse("NIP harus diisi", HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(employee.getName()))
            return new ZCodeResponse("Nama harus diisi", HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(employee.getBirthDate()))
            return new ZCodeResponse("Tanggal lahir harus diisi", HttpStatus.BAD_REQUEST);
        return null;
    }

    public double countMP(String employeeId, String mpsPercent, String mpsRupiah) {
        double fpr = 0;
        int type = 1;
        double mp = 0;
        if (mpsPercent != null){

        }
        if (mpsRupiah != null){

        }
        return 0;
    }
}
