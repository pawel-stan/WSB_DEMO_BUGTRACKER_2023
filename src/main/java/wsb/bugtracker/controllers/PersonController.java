package wsb.bugtracker.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.services.PersonService;

@Controller
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    final private PersonService personService;

    @GetMapping
    @Secured("ROLE_LIST_USER")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("person/index");
        modelAndView.addObject("people", personService.findAll());
        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    @Secured("ROLE_DELETE_USER")
    ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("person/index");
        try {
            personService.delete(id);
        } catch (DataIntegrityViolationException e) {
            modelAndView.addObject("message", "nie udało się usunąć użytkownika ponieważ jest używany w innych miejscach systemu");
        }
        modelAndView.addObject("people", personService.findAll());
        return modelAndView;
    }
}
