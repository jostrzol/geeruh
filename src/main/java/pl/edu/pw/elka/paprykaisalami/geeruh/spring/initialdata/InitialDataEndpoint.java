package pl.edu.pw.elka.paprykaisalami.geeruh.spring.initialdata;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.elka.paprykaisalami.geeruh.utils.ResetDbService;

@AllArgsConstructor
@RestController
@RequestMapping("initial-data")
@Tag(name = "Initial Data")
public class InitialDataEndpoint {
    InitialDataService initialDataService;

    ResetDbService resetDbService;

    @PutMapping
    public void resetInitialData(
    ) {
        resetDbService.resetDatabase();
        initialDataService.insert();
    }
}
