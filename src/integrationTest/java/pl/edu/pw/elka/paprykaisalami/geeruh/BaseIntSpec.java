package pl.edu.pw.elka.paprykaisalami.geeruh;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.TestDbService;

import java.io.IOException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class BaseIntSpec {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TestDbService testDbService;

    protected static MockHttpServletRequestBuilder get(String url, Object ... uriVariables) {
        return MockMvcRequestBuilders.get(url, uriVariables)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    }

    protected static MockHttpServletRequestBuilder put(String url, Object ... uriVariables) {
        return MockMvcRequestBuilders.put(url, uriVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    protected static MockHttpServletRequestBuilder post(String url, Object ... uriVariables) {
        return MockMvcRequestBuilders.post(url, uriVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    protected <T> T mapContent(byte[] content, Class<T> clazz) throws IOException {
        return objectMapper.readValue(content, clazz);
    }

    @BeforeEach
    protected void clearDb() {
        testDbService.resetDatabase();
    }
}
