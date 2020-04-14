package jp.co.axa.springtest.services;

import java.util.List;

import jp.co.axa.springtest.entities.Employee;

public interface EmployeeService {
    Employee getEmployeeByName(String name);

    List<Employee> getAllEmployees();

    List<Employee> getAllEmployeesByDepartment(String department);
}
