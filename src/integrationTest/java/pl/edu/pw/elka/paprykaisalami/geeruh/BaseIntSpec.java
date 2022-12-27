package pl.edu.pw.elka.paprykaisalami.geeruh;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api.IssueResponse;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.api.ProjectResponse;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.api.StatusResponse;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.TestDbService;
import pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.api.UserResponse;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    public ProjectResponse thereIsProject(String code, Object body) throws Exception {
        var request = post("/projects/{code}", code)
                .content(body.toString());

        var reader = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        return mapContent(reader, ProjectResponse.class);
    }

    public StatusResponse thereIsStatus(String code, Object body) throws Exception {
        var request = post("/statuses/{code}", code)
            .content(body.toString());

        var reader = mockMvc.perform(request)
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsByteArray();

        return mapContent(reader, StatusResponse.class);
    }


    public UserResponse thereIsUser(Object body) throws Exception {
        var request = post("/users")
                .content(body.toString());

        var reader = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        return mapContent(reader, UserResponse.class);
    }
}
