package wsb.bugtracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.Authority;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.repositories.AuthorityRepository;
import wsb.bugtracker.repositories.PersonRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonService {

    final private PersonRepository personRepository;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    final private AuthorityRepository authorityRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public void saveAdmin() {
        String username = "admin";
        String password = "aA123456";

        Optional<Person> person = personRepository.findByUsername(username);

        if (person.isPresent()) {
            System.out.println("Użytkownik administracyjny już istnieje, przerywamy");
            saveAllAuthorities(person.get());
            return;
        }

        System.out.println("Tworzymy użytkownika administracyjnego...");

        Person newPerson = new Person();
        newPerson.setUsername(username);
        newPerson.setRealName(username);

        String hashedPassword = bCryptPasswordEncoder.encode(password);
        newPerson.setPassword(hashedPassword);

        personRepository.save(newPerson);

        saveAllAuthorities(newPerson);
    }

    public void saveAllAuthorities(Person person) {
        List<Authority> authorities = authorityRepository.findAll();
        Set<Authority> authoritySet = new HashSet<>(authorities);

        person.setAuthorities(authoritySet);

        personRepository.save(person);
    }
}
