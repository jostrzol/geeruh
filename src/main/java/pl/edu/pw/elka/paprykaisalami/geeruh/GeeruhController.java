package pl.edu.pw.elka.paprykaisalami.geeruh;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@AllArgsConstructor
@Controller
public class GeeruhController {

    private final StatusRepository statusRepository;


    @RequestMapping(value = "/", method = GET)
    public @ResponseBody List<Status> getGreeting() {
        log.info("Handling getGreeting");
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
