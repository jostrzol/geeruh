package pl.edu.pw.elka.paprykaisalami.geeruh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("hello")
public class GeeruhController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeeruhController.class);

    @GetMapping
    public Map<String, String> helloWorld() {
        LOGGER.info("Handling helloWorld");
        return Map.of("hello", "world");
    }

    @GetMapping("user")
    public Map<String, String> helloUser() {
        LOGGER.info("Handling helloUser");
        return Map.of("hello", "user");
    }

    @GetMapping("admin")
    public Map<String, String> helloAdmin() {
        LOGGER.info("Handling helloAdmin");
        return Map.of("hello", "admin");
    }
}