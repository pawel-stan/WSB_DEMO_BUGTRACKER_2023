package wsb.bugtracker.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.models.Issue;
import wsb.bugtracker.repositories.IssueRepository;
import wsb.bugtracker.services.ProjectService;

@Controller
@AllArgsConstructor
@RequestMapping("/issues")
public class IssueController {

    private final IssueRepository issueRepository;
    private final ProjectService projectService;

    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        Issue newIssue = new Issue();

        modelAndView.addObject("issue", newIssue);
        modelAndView.addObject("projects", projectService.findAll());

        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView save(@ModelAttribute @Valid Issue issue,
                      BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("redirect:/issues");

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("issues/create");
            modelAndView.addObject("projects", projectService.findAll());
            return modelAndView;
        }

        issueRepository.save(issue);

        return modelAndView;
    }
}
