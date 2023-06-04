package wsb.bugtracker.project;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import wsb.bugtracker.controllers.ProjectController;
import wsb.bugtracker.services.PersonService;
import wsb.bugtracker.services.ProjectService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    PersonService personService;

    @WithMockUser(username = "testowy")
    @Test
    public void listOfProjectsReturnsSuccess() throws Exception {
        this.mockMvc.perform(get("/projects"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "testowy")
    @Test
    public void listOfProjectsContainsTableWithProjects() throws Exception {
        this.mockMvc.perform(get("/projects"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("projects/index"))
                .andExpect(content().string(containsString("projects.index_en")));
    }


    @WithMockUser(roles = {"CREATE_PROJECT"})
    @Test
    public void newProjectExceptsProjectName() throws Exception {
        this.mockMvc.perform(post("/projects/save")
                        .with(csrf())
                        .param("name", "testowy")
                        .param("project-creator", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects"));
    }
}
