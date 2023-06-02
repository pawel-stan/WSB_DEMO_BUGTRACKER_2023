package wsb.bugtracker.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.AuthorityName;
import wsb.bugtracker.services.PersonService;

@AllArgsConstructor
@Service
public class Bootstrap implements InitializingBean {

    private PersonService personService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Startujemy...");

        saveMissingAuthorities();

        personService.saveAdmin();
    }

    void saveMissingAuthorities() {
        for (AuthorityName authorityName : AuthorityName.values()) {
            // TODO: Sprawdź, czy istnieje Authority o takiej nazwie i jeśli nie, to stwórz nowe
        }
    }
}
