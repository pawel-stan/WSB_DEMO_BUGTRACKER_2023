package wsb.bugtracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.repositories.ProjectRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    final private ProjectRepository projectRepository;

    final private PersonService personService;

    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    public Page<Project> findAll(Specification<Project> specification, Pageable pageable) {
        return projectRepository.findAll(specification, pageable);
    }

    public void save(Project project) {
        if (project.getDateCreated() == null) {
            project.setDateCreated(new Date());
        }
        projectRepository.save(project);
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    public String createProjectShortName(String name) {
        String[] words = name.split(" ");
        String shortName = "";
        for (String word : words) {
            shortName += word.substring(0, 1);
        }
        return shortName;
    }

    public String createProjectDescription(Project project) {
        String desc = project.getName() + " created by " +  personService.getProjectCreatorData(project);
        return desc;
    }
}
