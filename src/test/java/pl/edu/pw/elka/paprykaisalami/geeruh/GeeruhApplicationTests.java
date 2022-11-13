package pl.edu.pw.elka.paprykaisalami.geeruh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pw.elka.paprykaisalami.geeruh.repositories.StatusRepository;

@SpringBootTest
class GeeruhApplicationTests {

    @Autowired
    StatusRepository statusRepository;

    @Test
    void contextLoads() {
        statusRepository.findAll();
    }

}
