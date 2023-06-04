package wsb.bugtracker.person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import wsb.bugtracker.controllers.PersonController;
import wsb.bugtracker.services.PersonService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonController personController;

    @MockBean
    private PersonService personService;

    @Test
    public void controllerExists() throws Exception {
        assertThat(personController).isNotNull();
    }


    @WithMockUser(roles={"LIST_USER"})
    @Test
    public void listOfPeopleReturnsSuccess() throws Exception {
        this.mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void listOfPeopleIsSecured() throws Exception {
        this.mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


}
