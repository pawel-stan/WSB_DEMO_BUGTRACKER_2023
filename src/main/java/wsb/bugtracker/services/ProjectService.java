package wsb.bugtracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.repositories.ProjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    final private ProjectRepository projectRepository;

    public List<Project> findAll() {

        Specification<Project> isEnabled = (root, query, builder) -> builder.equal(root.get("enabled"), true);
        Specification<Project> nameIlikeProject = (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%projekt%");
        Specification<Project> creatorNameEqual = (root, query, builder) -> builder.equal(root.get("creator").get("realName"), "Kasia D");

        return projectRepository.findAll(isEnabled.and(nameIlikeProject).and(creatorNameEqual));
    }
}
