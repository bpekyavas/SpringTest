package jp.co.axa.springtest.helper;

import jp.co.axa.springtest.entities.Employee;
import jp.co.axa.springtest.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeHelper {

    private static final String IT = "IT";
    private static final String SALES = "SALES";


    public List<Employee> filterEmployeeListByDepartment(List<Employee> employeeList, String department) {
        if (CollectionUtils.isEmpty(employeeList)) {
            throw new EmployeeNotFoundException();
        }

        if (IT.equals(department)) {
            return employeeList.stream().filter(employee -> IT.equals(employee.getDepartment()))
                    .collect(Collectors.toList());
        } else if (SALES.equals(department)) {
            return employeeList.stream().filter(employee -> SALES.equals(employee.getDepartment()))
                    .collect(Collectors.toList());
        } else {
            return employeeList;
        }
    }
}
