package wsb.bugtracker.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.services.PersonService;
import wsb.bugtracker.services.ProjectService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    PersonService personService;

    @InjectMocks
    ProjectService projectService;

    @Test
    void createShortNameCorrectly() {
        assertAll("short name tests",
                () -> assertEquals("BWP", projectService.createProjectShortName("Bardzo Wielki Projekt")),
                () -> assertEquals("BWP", projectService.createProjectShortName("Bardzo Wygodny Projekt"))

        );
    }

    @Test
    void createDescriptionCorrectly() {


        when(personService.getProjectCreatorData(any(Project.class))).thenReturn("Mietek");

        Project project = new Project();
        project.setName("System Bankowy");

        assertEquals("System Bankowy created by Mietek", projectService.createProjectDescription(project));

    }
}
