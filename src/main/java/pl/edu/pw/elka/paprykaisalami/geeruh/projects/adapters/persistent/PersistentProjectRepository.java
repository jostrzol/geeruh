package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.persistent;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.ports.ProjectRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.DomainError.NotFoundDomainError;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Primary
@Component
@Transactional(readOnly = true)
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
    public Either<DomainError, Project> findByCode(ProjectCode projectCode) {
        return actualRepository.findById(projectCode.value())
                .<Either<DomainError, ProjectPersistent>>map(Either::right)
                .orElseGet(NotFoundDomainError.supplier(Project.class, projectCode))
                .map(ProjectPersistent::toProject);
    }

    @Override
    @Transactional
    public Project save(Project issue) {
        var projectPersistent = ProjectPersistent.of(issue);
        return actualRepository.save(projectPersistent).toProject();
    }
}

@Component
interface ActualPersistentProjectRepository extends JpaRepository<ProjectPersistent, String> {
}