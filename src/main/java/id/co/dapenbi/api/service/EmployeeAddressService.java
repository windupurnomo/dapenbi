package id.co.dapenbi.api.service;


import id.co.dapenbi.api.entity.EmployeeAddress;
import id.co.dapenbi.api.repository.EmployeeAddressRepository;
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
public class EmployeeAddressService {
    @Autowired
    private EmployeeAddressRepository employeeAddressRepository;

    public ZCodeResponse create(EmployeeAddress employeeAddress) {
        ZCodeResponse validate = validate(employeeAddress);
        if (validate != null) return validate;
        employeeAddress.setId(UUID.randomUUID().toString());
        EmployeeAddress db = employeeAddressRepository.save(employeeAddress);
        return new ZCodeResponse(db, HttpStatus.OK);
    }

    public ZCodeResponse update(EmployeeAddress param) {
        ZCodeResponse validate = validate(param);
        if (validate != null) return validate;
        Optional<EmployeeAddress> opt = employeeAddressRepository.findById(param.getId());
        if (!opt.isPresent())
            return new ZCodeResponse("Data karyawan tidak ditemukan", HttpStatus.BAD_REQUEST);
        EmployeeAddress employeeAddress = opt.get();
        employeeAddress.setAddress(param.getAddress());
        employeeAddress.setKelurahan(param.getKelurahan());
        employeeAddress.setPosCode(param.getPosCode());
        employeeAddress.setRt(param.getRt());
        employeeAddress.setRw(param.getRw());
        EmployeeAddress db = employeeAddressRepository.save(employeeAddress);
        return new ZCodeResponse(db, HttpStatus.OK);
    }

    public EmployeeAddress findById(String id) {
        return employeeAddressRepository.findById(id).orElse(null);
    }

    public List<EmployeeAddress> findByEmployee(String employeeId) {
        return employeeAddressRepository.findByEmployee_Nip(employeeId);
    }

    public Page<EmployeeAddress> findAll(Pageable pageable) {
        return employeeAddressRepository.findAll(pageable);
    }

    public void delete(String nip) {
        employeeAddressRepository.deleteById(nip);
    }

    private ZCodeResponse validate(EmployeeAddress employeeAddress) {
        if (StringUtils.isEmpty(employeeAddress.getAddress()))
            return new ZCodeResponse("Alamat harus diisi", HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(employeeAddress.getKelurahan()))
            return new ZCodeResponse("Kelurahan harus dipilih", HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(employeeAddress.getPosCode()))
            return new ZCodeResponse("Kode Pos harus diisi", HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(employeeAddress.getRt()))
            return new ZCodeResponse("RT harus diisi", HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(employeeAddress.getRw()))
            return new ZCodeResponse("RW harus diisi", HttpStatus.BAD_REQUEST);

        return null;
    }
}
