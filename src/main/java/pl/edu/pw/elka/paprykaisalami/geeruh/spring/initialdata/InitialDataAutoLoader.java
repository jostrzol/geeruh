package pl.edu.pw.elka.paprykaisalami.geeruh.spring.initialdata;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!prod")
@Component
@AllArgsConstructor
public class InitialDataAutoLoader implements ApplicationRunner {

    InitialDataService initialDataService;

    @Override
    public void run(ApplicationArguments args) {
        initialDataService.create();
    }
}
