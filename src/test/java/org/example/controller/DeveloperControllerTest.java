package org.example.controller;

import org.example.model.Developer;
import org.example.repository.DeveloperRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeveloperControllerTest {

    @Test
    void testGetDeveloperById() {
        DeveloperRepository repo = mock(DeveloperRepository.class);

        DeveloperController controller = new DeveloperController(repo);

        Developer testDev = new Developer();
        testDev.setId(1L);
        testDev.setFirstName("Алексей Лапшин");

        when(repo.getById(1L)).thenReturn(testDev);

        Developer result = controller.getDeveloperById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Алексей Лапшин", result.getFirstName());
    }

    @Test
    void testGetNonExistentDeveloper() {
        DeveloperRepository repo = mock(DeveloperRepository.class);

        DeveloperController controller = new DeveloperController(repo);

        when(repo.getById(999L)).thenReturn(null);

        assertNull(controller.getDeveloperById(999L));
    }
}
