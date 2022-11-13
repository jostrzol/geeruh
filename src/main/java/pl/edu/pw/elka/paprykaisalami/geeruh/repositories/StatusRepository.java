package pl.edu.pw.elka.paprykaisalami.geeruh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.elka.paprykaisalami.geeruh.models.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
