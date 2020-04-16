package jp.co.axa.springtest.it.controllers;

import java.util.Arrays;
import java.util.List;

import jp.co.axa.springtest.controllers.EmployeeRestController;
import jp.co.axa.springtest.entities.Employee;
import jp.co.axa.springtest.services.EmployeeService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeRestController.class)  //  auto-configure the Spring MVC infrastructure for our unit tests.
public class EmployeeRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;  //  test MVC controllers without starting a full HTTP server.

    @MockBean
    private EmployeeService service;

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {

        // Using here BDDMockito, not normal mockito
        Employee alex = new Employee(1L, "Alex", "IT");
        List<Employee> allEmployees = Arrays.asList(alex);
        given(service.getAllEmployees()).willReturn(allEmployees);

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) //jsonPath  provides an easy way to extract JSON doc
                .andExpect(jsonPath("$[0].name", is(alex.getName())))
                .andExpect(jsonPath("$[0].department", is(alex.getDepartment())))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void givenEmployee_whenGetEmployeeWithName_thenReturnJsonObject()
            throws Exception {

        Employee alex = new Employee(1L, "Alex", "IT");
        given(service.getEmployeeByName("Alex")).willReturn(alex);

        mvc.perform(get("/api/employee/?name=Alex")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.name", is(alex.getName())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.department", is(alex.getDepartment())));
    }

    @Test
    public void givenDepartment_whenGetEmployeeByDepartment_thenReturnJsonObject()
            throws Exception {

        Employee alex = new Employee(1L, "Alex", "IT");
        Employee berna = new Employee(2L, "Berna", "IT");

        given(service.getAllEmployeesByDepartment("IT")).willReturn(Lists.newArrayList(alex, berna));

        mvc.perform(get("/api/employee/department/?department=IT")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(alex.getName())))
                .andExpect(jsonPath("$[0].department", is(alex.getDepartment())))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].name", is(berna.getName())))
                .andExpect(jsonPath("$[1].department", is(berna.getDepartment())))
                .andExpect(jsonPath("$[1].id", is(2)));
    }
}