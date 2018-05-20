package id.co.dapenbi.api.controller;

import id.co.dapenbi.api.entity.Employee;
import id.co.dapenbi.api.service.CalculationService;
import id.co.dapenbi.api.service.EmployeeService;
import id.co.dapenbi.api.util.ZCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CalculationService calculationService;

    @PostMapping
    public ZCodeResponse create(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping
    public ZCodeResponse update(@RequestBody Employee employee) {
        return employeeService.update(employee);
    }

    @GetMapping
    public Page<Employee> findAll(Pageable pageable) {
        return employeeService.findAll(pageable);
    }

    @GetMapping("/{nip}")
    public Employee findOne(@PathVariable String nip) {
        return employeeService.findByNip(nip);
    }

    @DeleteMapping("/{nip}")
    public void delete(@PathVariable String nip) {
        employeeService.delete(nip);
    }

    @GetMapping("/mp/{employeeId}")
    public double countMp(@PathVariable String employeeId,
                          @RequestParam(required = false) double mpsPercent,
                          @RequestParam(required = false) double mpsRupiah){
        return calculationService.countMP(employeeId, mpsPercent, mpsRupiah);
    }

}
