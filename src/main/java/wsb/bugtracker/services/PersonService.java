package wsb.bugtracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    final private PersonRepository personRepository;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Value("${my.wsb.bugtracker.admin.username}")
    private String adminUsername;

    @Value("${my.wsb.bugtracker.admin.pass}")
    private String adminPass;

    public void saveAdmin() {
        String username = adminUsername;
        String password = adminPass;

        System.out.println("nazwa użytkownika administratora: " + adminUsername);

        Optional<Person> person = personRepository.findByUsername(username);

        if (person.isPresent()) {
            System.out.println("Użytkownik administracyjny już istnieje, przerywamy");
            return;
        }

        System.out.println("Tworzymy użytkownika administracyjnego...");

        Person newPerson = new Person();
        newPerson.setUsername(username);
        newPerson.setRealName(username);

        String hashedPassword = bCryptPasswordEncoder.encode(password);
        newPerson.setPassword(hashedPassword);

        personRepository.save(newPerson);
    }

    public void saveAllAuthorities(Person person) {
        // Znajdź wszystkie istniejące w bazie Authority
        // Dodaj użytkownikowi person wpis w PersonAuthorities, jeżeli jeszcze takiego nie ma dla danego uprawnienia
    }
}
