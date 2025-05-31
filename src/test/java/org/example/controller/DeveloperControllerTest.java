package org.example.controller;

import org.example.model.Developer;
import org.example.repository.DeveloperRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeveloperControllerTest {

    @Mock
    private DeveloperRepository repo;

    @InjectMocks
    private DeveloperController controller;

    private Developer testDeveloper;

    @BeforeEach
    void setUp() {
        testDeveloper = new Developer();
        testDeveloper.setId(1L);
        testDeveloper.setFirstName("Алексей Лапшин");
    }

    @Test
    void shouldReturnDeveloperWhenExists() {
        when(repo.getById(1L)).thenReturn(testDeveloper);

        Developer result = controller.getDeveloperById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Алексей Лапшин", result.getFirstName());
    }

    @Test
    void shouldReturnNullWhenNotExists() {
        when(repo.getById(999L)).thenReturn(null);

        Developer result = controller.getDeveloperById(999L);

        assertNull(result);
    }
}
