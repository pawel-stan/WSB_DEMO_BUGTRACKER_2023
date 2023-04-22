package wsb.bugtracker.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFilter {

    private String name;

    private Person creator;

    private String globalSearch;


    public Specification<Project> buildSpecification() {
        Specification<Project> spec = Specification.where(null);
        spec = spec.and((root, query, builder) -> builder.equal(root.get("enabled"), true));

        if (name != null) {
            spec = spec.and((root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (creator != null) {
            spec = spec.and((root, query, builder) -> builder.equal(root.get("creator"), creator));
        }

        if (globalSearch != null) {
            Specification<Project> nameIlike = (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + globalSearch.toLowerCase() + "%");
            Specification<Project> descriptionIlike = (root, query, builder) -> builder.like(builder.lower(root.get("description")), "%" + globalSearch.toLowerCase() + "%");

            Specification<Project> globalSearchCondition = nameIlike.or(descriptionIlike);
            spec = spec.and(globalSearchCondition);
        }

        return spec;
    }

}
