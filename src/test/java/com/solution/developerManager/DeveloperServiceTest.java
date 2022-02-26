package com.solution.developerManager;

import com.solution.developerManager.developer.DeveloperDto;
import com.solution.developerManager.developer.DeveloperRepository;
import com.solution.developerManager.developer.DeveloperService;
import com.solution.developerManager.exception.DeveloperNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeveloperServiceTest {

    @Autowired
    DeveloperService developerService;
    @Autowired
    DeveloperRepository developerRepository;

    @AfterEach
    void deleteTestData() {
        developerRepository.deleteAll(developerService.findAll());
    }

    @Test
    void saveDeveloperShouldSave() {
        developerService.saveDeveloper(new DeveloperDto("Alexandra1", "alexandra1@mail.ru"));

        assertNotNull(developerService.findDeveloperByEmail("alexandra1@mail.ru"));
    }

    @Test
    void findAllShouldReturnFullList() {
        developerService.saveDeveloper(new DeveloperDto("Alexandra1", "alexandra1@mail.ru"));
        developerService.saveDeveloper(new DeveloperDto("Alexandra2", "alexandra2@mail.ru"));
        developerService.saveDeveloper(new DeveloperDto("Alexandra3", "alexandra3@mail.ru"));
        developerService.saveDeveloper(new DeveloperDto("Alexandra4", "alexandra4@mail.ru"));

        assertNotNull(developerService.findAll());
        assertEquals(developerService.findAll().size(), 4);
    }

    @Test
    void findAllShouldReturnEmptyList() {
        assertEquals(developerService.findAll().size(), 0);
    }

    @Test
    void findDeveloperByEmailShouldReturnCorrectUser() {
        developerService.saveDeveloper(new DeveloperDto("Alexandra1", "alexandra1@mail.ru"));
        developerService.saveDeveloper(new DeveloperDto("Alexandra2", "alexandra2@mail.ru"));

        assertEquals("alexandra2@mail.ru", developerService.findDeveloperByEmail("alexandra2@mail.ru").getEmail());
    }

    @Test
    void deleteDeveloperByEmailShouldDeleteOnlyOneDeveloper() {
        developerService.saveDeveloper(new DeveloperDto("Alexandra1", "alexandra1@mail.ru"));
        developerService.saveDeveloper(new DeveloperDto("Alexandra2", "alexandra2@mail.ru"));

        developerService.deleteDeveloperByEmail("alexandra2@mail.ru");

        assertEquals(developerService.findAll().size(), 1);
    }

    @Test
    void deleteDeveloperShouldThrowExceptionIfUserNotExist() {
        assertThrows(DeveloperNotFoundException.class, () -> developerService.deleteDeveloperByEmail("alexandra2@mail.ru"));
    }

    @Test
    void updateDeveloperShouldUpdate() {
        developerService.saveDeveloper(new DeveloperDto("Alexandra1", "alexandra1@mail.ru"));
        developerService.saveDeveloper(new DeveloperDto("Alexandra2", "alexandra2@mail.ru"));

        developerService.updateDeveloper(
                new DeveloperDto("Alexandra3", "alexandra1@mail.ru"), "alexandra1@mail.ru");

        assertEquals("Alexandra3", developerService.findDeveloperByEmail("alexandra1@mail.ru").getName());
    }

    @Test
    void updateDeveloperShouldThrowExceptionIfUserNotExist() {
        assertThrows(DeveloperNotFoundException.class, () -> developerService.updateDeveloper(
                new DeveloperDto("Alexandra", "alexandra2@mail.ru"), "alexandra2@mail.ru"
        ));
    }

}
