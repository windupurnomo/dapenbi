package id.co.dapenbi.api.service;


import id.co.dapenbi.api.entity.EmployeeFamily;
import id.co.dapenbi.api.enums.RelationStatus;
import id.co.dapenbi.api.repository.EmployeeFamilyRepository;
import id.co.dapenbi.api.util.ZCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeFamilyService {
    @Autowired
    private EmployeeFamilyRepository employeeFamilyRepository;

    public ZCodeResponse create(EmployeeFamily employeeFamily) {
        ZCodeResponse validate = validate(employeeFamily);
        if (validate != null) return validate;
        employeeFamily.setId(UUID.randomUUID().toString());
        EmployeeFamily db = employeeFamilyRepository.save(employeeFamily);
        return new ZCodeResponse(db, HttpStatus.OK);
    }

    public ZCodeResponse update(EmployeeFamily param) {
        ZCodeResponse validate = validate(param);
        if (validate != null) return validate;
        Optional<EmployeeFamily> opt = employeeFamilyRepository.findById(param.getId());
        if (!opt.isPresent())
            return new ZCodeResponse("Data karyawan tidak ditemukan", HttpStatus.BAD_REQUEST);
        EmployeeFamily employeeFamily = opt.get();
        employeeFamily.setName(param.getName());
        employeeFamily.setBirthDate(param.getBirthDate());
        employeeFamily.setDivorceDate(param.getDivorceDate());
        employeeFamily.setRelation(param.getRelation());
        employeeFamily.setSex(param.getSex());
        EmployeeFamily db = employeeFamilyRepository.save(employeeFamily);
        return new ZCodeResponse(db, HttpStatus.OK);
    }

    public EmployeeFamily findById(String id) {
        return employeeFamilyRepository.findById(id).orElse(null);
    }

    public List<EmployeeFamily> findByEmployeeId(String nip) {
        return employeeFamilyRepository.findByEmployee_Nip(nip);
    }

    public Page<EmployeeFamily> findAll(Pageable pageable) {
        return employeeFamilyRepository.findAll(pageable);
    }

    public void delete(String nip) {
        employeeFamilyRepository.deleteById(nip);
    }

    private ZCodeResponse validate(EmployeeFamily employeeFamily) {
        if (StringUtils.isEmpty(employeeFamily.getRelation()))
            return new ZCodeResponse("Status Hubungan Keluarga harus diisi", HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(employeeFamily.getName()))
            return new ZCodeResponse("Nama harus diisi", HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(employeeFamily.getBirthDate()))
            return new ZCodeResponse("Tanggal lahir harus diisi", HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(employeeFamily.getSex()))
            return new ZCodeResponse("Jenis Kelamin harus diisi", HttpStatus.BAD_REQUEST);
        if (RelationStatus.parse(employeeFamily.getRelation()) == null)
            return new ZCodeResponse("Status Hubungan Keluarga Tidak Valid", HttpStatus.BAD_REQUEST);
        return null;
    }
}
