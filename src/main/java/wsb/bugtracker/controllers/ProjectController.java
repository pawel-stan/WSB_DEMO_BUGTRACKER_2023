package wsb.bugtracker.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.filters.ProjectFilter;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.services.PersonService;
import wsb.bugtracker.services.ProjectService;

import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    final private ProjectService projectService;
    final private PersonService personService;

    @GetMapping
    ModelAndView index(@ModelAttribute ProjectFilter filter, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("projects/index");
        Page<Project> projects = projectService.findAll(filter.buildSpecification(), pageable);
        modelAndView.addObject("projects", projects);
        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);
        modelAndView.addObject("filter", filter);
        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_CREATE_PROJECT")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project newProject = new Project();
        newProject.setEnabled(true);

        modelAndView.addObject("project", newProject);

        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);

        return modelAndView;
    }

    @PostMapping("/save")
    @Secured("ROLE_CREATE_PROJECT")
    ModelAndView save(@ModelAttribute @Valid Project project,
                      BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("redirect:/projects");

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("projects/create");
            modelAndView.addObject("project", project);
            modelAndView.addObject("people", personService.findAll());
            return modelAndView;
        }

        projectService.save(project);

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    ModelAndView delete(@PathVariable Long id) {
        System.out.println("usuwanie projektu " + id);
        projectService.delete(id);
        return new ModelAndView("redirect:/projects");
    }
}
