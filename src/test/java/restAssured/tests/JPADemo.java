package restAssured.tests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.Test;
import restAssured.entitiesDB.Employee;
import restAssured.helpers.EnvHelper;
import restAssured.manager.MyPUI;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JPADemo {
    private static EntityManager entityManager;
    private static EnvHelper envHelper;

    @Test
    public void demo() throws IOException {
        envHelper = new EnvHelper();
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
        entityManager = emf.createEntityManager();

//        Employee employee = entityManager.find(Employee.class, 2595);
        Employee employee = new Employee();
        employee.setName("Max");
        employee.setSurname("BBBB");
        employee.setCity("Seoul");
        employee.setPosition("QA");

        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        int id = employee.getId();

        entityManager.getTransaction().begin();
        employee = entityManager.find(Employee.class, id);
        entityManager.getTransaction().commit();
        assertNotNull(employee);
        System.out.println(employee);

        entityManager.getTransaction().begin();
        entityManager.remove(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        employee = entityManager.find(Employee.class, id);
        entityManager.getTransaction().commit();
        assertNull(employee);

        System.out.println(employee);
    }

    @Test
    public void demoDelete() throws IOException {
        envHelper = new EnvHelper();
        Properties properties = envHelper.getProperties();
        PersistenceUnitInfo myPui = new MyPUI(properties);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPui, myPui.getProperties());
        entityManager = emf.createEntityManager();

        Employee employee = new Employee();
        employee.setName("Max1");
        employee.setSurname("BBBB");
        employee.setCity("Seoul");
        employee.setPosition("QA");

        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        employee = getByName(employee.getName());
        entityManager.getTransaction().commit();
        assertNotNull(employee);

        System.out.println(employee);
    }

    private Employee getByName(String name) {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT em FROM Employee em WHERE em.name = :employeeName", Employee.class);
        query.setParameter("employeeName", name);
        return query.getSingleResult();
    }
}
