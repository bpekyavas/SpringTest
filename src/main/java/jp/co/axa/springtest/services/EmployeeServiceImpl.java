package jp.co.axa.springtest.services;

import jp.co.axa.springtest.entities.Employee;
import jp.co.axa.springtest.helper.EmployeeHelper;
import jp.co.axa.springtest.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeHelper employeeHelper;

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getAllEmployeesByDepartment(String department) {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeHelper.filterEmployeeListByDepartment(employeeList, department);
    }
}
