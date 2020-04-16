package jp.co.axa.springtest.unittest.services;

import jp.co.axa.springtest.entities.Employee;
import jp.co.axa.springtest.exception.EmployeeNotFoundException;
import jp.co.axa.springtest.helper.EmployeeHelper;
import jp.co.axa.springtest.repositories.EmployeeRepository;
import jp.co.axa.springtest.services.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;


@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeHelper employeeHelper;


    @Test
    public void givenOneEmployee_WhenValidName_thenEmployeeShouldBeFound() {
        //given
        String name = "Alex";
        //mock
        Employee alex = new Employee(1L, "Alex", "IT");
        Mockito.when(employeeRepository.findByName(alex.getName()))
                .thenReturn(alex);
        //when
        Employee found = employeeService.getEmployeeByName(name);
        //then
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    public void shouldReturnAllEmployeesList() {
        //given
        String name = "Alex";
        //mock
        Employee alex = new Employee(1L, "Alex", "IT");
        Employee berna = new Employee(2L, "Berna", "IT");
        Employee saurabh = new Employee(3L, "Saurabh", "IT");

        List<Employee> mockList = new ArrayList<>();
        mockList.add(alex);
        mockList.add(berna);
        mockList.add(saurabh);

        Mockito.when(employeeRepository.findAll()).thenReturn(mockList);
        //when
        List<Employee> resultList = employeeService.getAllEmployees();
        //then
        assertThat(resultList).isNotEmpty();
        assertThat(resultList).hasSize(3);
        assertThat(resultList).extracting("id", "name", "department")
                .contains(tuple(1L, "Alex", "IT"),
                        tuple(2L, "Berna", "IT"),
                        tuple(3L, "Saurabh", "IT"));

        Mockito.verify(employeeRepository).findAll();
    }

    @Test
    public void shouldReturnEmployeesListByDepartmentWhenDepartmentIsIT() {
        //given
        String department = "IT";
        //mock
        Employee alex = new Employee(1L, "Alex", "IT");
        Employee berna = new Employee(2L, "Berna", "IT");
        Employee taro = new Employee(3L, "Taro", "SALES");

        List<Employee> mockList = new ArrayList<>();
        mockList.add(alex);
        mockList.add(berna);
        mockList.add(taro);
        Mockito.when(employeeRepository.findAll()).thenReturn(mockList);

        List<Employee> filteredList = new ArrayList<>();
        filteredList.add(alex);
        filteredList.add(berna);
        Mockito.when(employeeHelper.filterEmployeeListByDepartment(mockList, department))
                .thenReturn(filteredList);
        //when
        List<Employee> resultList = employeeService.getAllEmployeesByDepartment(department);
        //then
        assertThat(resultList).isNotEmpty();
        assertThat(resultList).hasSize(2);
        assertThat(resultList).extracting("id", "name", "department")
                .contains(tuple(1L, "Alex", "IT"),
                        tuple(2L, "Berna", "IT"));


        Mockito.verify(employeeRepository).findAll();
        Mockito.verify(employeeHelper).filterEmployeeListByDepartment(anyList(), anyString());
    }

    @Test
    public void shouldGetAllEmployeesByDepartmentThrowExceptionWhenEmployeesNotFound() {
        //given
        String department = "IT";
        //mock
        List<Employee> mockList = new ArrayList<>();
        Mockito.when(employeeRepository.findAll()).thenReturn(mockList);
        Mockito.doThrow(EmployeeNotFoundException.class)
                .when(employeeHelper).filterEmployeeListByDepartment(mockList, department);
        //when
        Throwable thrown = catchThrowable(() -> {
            employeeService.getAllEmployeesByDepartment(department);
        });
        //then
        assertThat(thrown).isInstanceOf(EmployeeNotFoundException.class);
    }


}