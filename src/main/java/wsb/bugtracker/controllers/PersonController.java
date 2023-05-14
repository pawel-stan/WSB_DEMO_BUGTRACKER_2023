package wsb.bugtracker.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.filters.ProjectFilter;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.services.PersonService;

import java.util.List;

@Controller
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    final private PersonService personService;

    @GetMapping
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("person/index");
        modelAndView.addObject("people", personService.findAll());
        return modelAndView;
    }


    @GetMapping("/delete/{id}")
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
