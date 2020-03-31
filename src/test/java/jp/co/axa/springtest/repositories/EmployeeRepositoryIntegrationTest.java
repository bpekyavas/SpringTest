package jp.co.axa.springtest.repositories;

import jp.co.axa.springtest.entities.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) // Used to provide a bridge between Spring Boot test features and JUnit
@DataJpaTest // Configure DB & hibernate
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager; // For setup the database with data

    @Autowired
    private EmployeeRepository employeeRepository; //Repository we want to test

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Employee jonathan = new Employee("Jonathan");
        entityManager.persist(jonathan);
        entityManager.flush();

        // when
        Employee found = employeeRepository.findByName(jonathan.getName());

        // then
        assertThat(found.getName()).isEqualTo(jonathan.getName());
    }

    @Test
    public void givenNoEmployee_WhenFindByName_thenReturnNothing() {
        // when
        Employee found = employeeRepository.findByName("Yoshitaka");

        // then
        assertThat(found).isNull();
    }

}
