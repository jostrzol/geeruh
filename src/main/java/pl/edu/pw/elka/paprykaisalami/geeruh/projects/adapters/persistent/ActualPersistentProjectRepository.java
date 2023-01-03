package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public
interface ActualPersistentProjectRepository extends JpaRepository<ProjectPersistent, String> {
}
