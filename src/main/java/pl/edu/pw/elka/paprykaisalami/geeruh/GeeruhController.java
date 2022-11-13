package pl.edu.pw.elka.paprykaisalami.geeruh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.edu.pw.elka.paprykaisalami.geeruh.models.Status;
import pl.edu.pw.elka.paprykaisalami.geeruh.repositories.StatusRepository;

@Controller
public class GeeruhController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeeruhController.class);

    protected static final String PERSISTANCE_UNIT = "geeruh.main.sn";

    protected final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT);

    protected final EntityManager entityManager = emf.createEntityManager();

    StatusRepository statusRepository = new StatusRepository(entityManager);


    @RequestMapping(value = "/", method = GET)
    public @ResponseBody List<Status> getGreeting() {
        LOGGER.info("Handling getGreeting");
        // var status = new Status("kod", "name");
        var status = new Status();
        status.setId(123);
        status.setName("name");
        statusRepository.save(status);
        var statuses = statusRepository.findAll();
        return statuses;
        // return Map.of("hello", "");
    }
}
