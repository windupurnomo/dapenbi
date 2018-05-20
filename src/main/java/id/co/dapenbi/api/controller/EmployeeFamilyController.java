package id.co.dapenbi.api.controller;

import id.co.dapenbi.api.entity.EmployeeFamily;
import id.co.dapenbi.api.service.EmployeeFamilyService;
import id.co.dapenbi.api.util.ZCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee-family")
public class EmployeeFamilyController {
    @Autowired
    private EmployeeFamilyService employeeFamilyService;

    @PostMapping
    public ZCodeResponse create(@RequestBody EmployeeFamily employeeFamily) {
        return employeeFamilyService.create(employeeFamily);
    }

    @PutMapping
    public ZCodeResponse update(@RequestBody EmployeeFamily employeeFamily) {
        return employeeFamilyService.update(employeeFamily);
    }

    @GetMapping
    public Page<EmployeeFamily> findAll(Pageable pageable) {
        return employeeFamilyService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public EmployeeFamily findOne(@PathVariable String id) {
        return employeeFamilyService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        employeeFamilyService.delete(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<EmployeeFamily> findByEmployee(@PathVariable String employeeId){
        return employeeFamilyService.findByEmployeeId(employeeId);
    }

}
