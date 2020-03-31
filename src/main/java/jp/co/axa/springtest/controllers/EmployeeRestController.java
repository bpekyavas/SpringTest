package jp.co.axa.springtest.controllers;

import java.util.List;
import jp.co.axa.springtest.entities.Employee;
import jp.co.axa.springtest.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee")
    public Employee getEmployee(@RequestParam(defaultValue = "Alex") String name) {
        return employeeService.getEmployeeByName(name);
    }
}