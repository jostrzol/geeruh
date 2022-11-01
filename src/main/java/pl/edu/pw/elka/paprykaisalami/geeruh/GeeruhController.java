package pl.edu.pw.elka.paprykaisalami.geeruh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class GeeruhController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeeruhController.class);

    @RequestMapping(value = "/", method = GET)
    public @ResponseBody Map<String, String> getGreeting() {
        LOGGER.info("Handling getGreeting");
        return Map.of("hello", "world");
    }
}
