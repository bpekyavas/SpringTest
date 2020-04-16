package jp.co.axa.springtest.unittest.helper;

import jp.co.axa.springtest.entities.Employee;
import jp.co.axa.springtest.exception.EmployeeNotFoundException;
import jp.co.axa.springtest.helper.EmployeeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeHelperTest {

    @InjectMocks
    private EmployeeHelper employeeHelper;

    @Test
    public void shouldReturnEmployeeListWhenDepartmentIT() {
        //given
        String department = "IT";
        //when
        List<Employee> resultList = employeeHelper.filterEmployeeListByDepartment(mockEmployeeList(), department);
        //then
        assertThat(resultList).isNotEmpty();
        assertThat(resultList).hasSize(2);
        assertThat(resultList).extracting("id", "name", "department")
                .contains(tuple(1L, "Alex", "IT"),
                        tuple(2L, "Berna", "IT"));

    }

    @Test
    public void shouldReturnEmployeeListWhenDepartmentIsSales() {
        //given
        String department = "SALES";
        //when
        List<Employee> resultList = employeeHelper.filterEmployeeListByDepartment(mockEmployeeList(), department);
        //then
        assertThat(resultList).isNotEmpty();
        assertThat(resultList).hasSize(1);
        assertThat(resultList).extracting("id", "name", "department")
                .contains(tuple(3L, "Taro", "SALES"));

    }

    @Test
    public void shouldReturnEmployeeListWhenDepartmentIsAll() {
        //given
        String department = "ALL";
        //when
        List<Employee> resultList = employeeHelper.filterEmployeeListByDepartment(mockEmployeeList(), department);
        //then
        assertThat(resultList).isNotEmpty();
        assertThat(resultList).hasSize(3);
        assertThat(resultList).extracting("id", "name", "department")
                .contains(tuple(1L, "Alex", "IT"),
                        tuple(2L, "Berna", "IT"),
                        tuple(3L, "Taro", "SALES"));

    }


    @Test
    public void shouldThrowExceptionWhenEmployeesNotFound() {
        //given
        String department = "SALES";
        List<Employee> mockList = new ArrayList<>();

        //when
        Throwable thrown = catchThrowable(() -> {
            employeeHelper.filterEmployeeListByDepartment(mockList, department);
        });
        //then
        assertThat(thrown).isInstanceOf(EmployeeNotFoundException.class);
    }


    private List<Employee> mockEmployeeList() {
        Employee alex = new Employee(1L, "Alex", "IT");
        Employee berna = new Employee(2L, "Berna", "IT");
        Employee taro = new Employee(3L, "Taro", "SALES");

        List<Employee> mockList = new ArrayList<>();
        mockList.add(alex);
        mockList.add(berna);
        mockList.add(taro);

        return mockList;
    }
}