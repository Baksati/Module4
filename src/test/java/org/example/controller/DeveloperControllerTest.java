package org.example.controller;

import org.example.model.Developer;
import org.example.repository.DeveloperRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeveloperControllerTest {

    static class TestRepo implements DeveloperRepository {
        Developer testDeveloper;

        @Override
        public Developer getById(Long id) {
            return testDeveloper;
        }

        @Override
        public List<Developer> getAll() {
            return null;
        }
        @Override
        public void save(Developer developer) {
        }
        @Override
        public void update(Developer developer) {
        }
        @Override
        public void delete(Long id) {
        }
    }

    @Test
    void testGetDeveloperById() {
        TestRepo testRepo = new TestRepo();
        DeveloperController controller = new DeveloperController(testRepo);

        Developer testDeveloper = new Developer();
        testDeveloper.setId(1L);
        testDeveloper.setFirstName("Алексей Лапшин");

        testRepo.testDeveloper = testDeveloper;

        Developer result = controller.getDeveloperById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Алексей Лапшин", result.getFirstName());
    }

    @Test
    void testGetNonExistentDeveloper() {
        TestRepo testRepo = new TestRepo();
        DeveloperController controller = new DeveloperController(testRepo);

        Developer result = controller.getDeveloperById(999L);

        assertNull(result);
    }
}
