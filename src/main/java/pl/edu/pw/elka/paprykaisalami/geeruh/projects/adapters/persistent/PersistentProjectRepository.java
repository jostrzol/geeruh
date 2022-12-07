package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.persistent;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Primary
@Component
class PersistentProjectRepository implements ProjectRepository {

    ActualPersistentProjectRepository actualRepository;

    @Override
    public List<Project> findAll() {
        return actualRepository.findAll()
                .stream()
                .map(ProjectPersistent::toProject)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Project> findByCode(ProjectCode projectCode) {
        return actualRepository.findById(projectCode.getValue())
                .map(ProjectPersistent::toProject);
    }

    @Override
    public Project save(Project issue) {
        var projectPersistent = ProjectPersistent.of(issue);
        return actualRepository.save(projectPersistent).toProject();
    }
}

@Component
interface ActualPersistentProjectRepository extends JpaRepository<ProjectPersistent, String> {
}