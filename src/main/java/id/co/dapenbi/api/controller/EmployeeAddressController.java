package id.co.dapenbi.api.controller;

import id.co.dapenbi.api.entity.EmployeeAddress;
import id.co.dapenbi.api.service.EmployeeAddressService;
import id.co.dapenbi.api.util.ZCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee-address")
public class EmployeeAddressController {
    @Autowired
    private EmployeeAddressService employeeAddressService;

    @PostMapping
    public ZCodeResponse create(@RequestBody EmployeeAddress employeeAddress) {
        return employeeAddressService.create(employeeAddress);
    }

    @PutMapping
    public ZCodeResponse update(@RequestBody EmployeeAddress employeeAddress) {
        return employeeAddressService.update(employeeAddress);
    }

    @GetMapping
    public Page<EmployeeAddress> findAll(Pageable pageable) {
        return employeeAddressService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public EmployeeAddress findOne(@PathVariable String id) {
        return employeeAddressService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        employeeAddressService.delete(id);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeAddress findByEmployee(@PathVariable String employeeId) {
        List<EmployeeAddress> addresses = employeeAddressService.findByEmployee(employeeId);
        return addresses.size() > 0 ? addresses.get(0) : null;
    }

}
