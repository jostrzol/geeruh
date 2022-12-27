package pl.edu.pw.elka.paprykaisalami.geeruh.endpoints;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.RequestBuilder;

import lombok.val;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseIntSpec;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.FIRST_USER;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.FIRST_USER_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.SECOND_USER;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.SECOND_USER_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.easyJson;
import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;

public class UserEndpointIntSpec extends BaseIntSpec {

    @ParameterizedTest
    @MethodSource(value = "securedEndpoints")
    void shouldNotAuthorize(RequestBuilder request) throws Exception {
        // expect
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    private static Stream<Arguments> securedEndpoints() {
        return Stream.of(
                arguments(get("/users")),
                arguments(get("/users/123")),
                arguments(put("/users/123")));
    }

    @Test
    void shouldCreateUser() throws Exception {
        // given
        val request = post("/users")
                .content(FIRST_USER_STRING);

        // expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_USER.remove("password").toString()))
                .andExpect(json().node("userId").isNotNull());
    }
    //
    // @Test
    // @WithMockUser
    // void shouldCreateProject_whenNoDescriptionProvided() throws Exception {
    // // given
    // val request = post("/projects/{projectCode}", THIRD_PROJECT_CODE)
    // .content(THIRD_PROJECT_NO_DESCRIPTION_STRING);
    //
    // // expect
    // mockMvc.perform(request)
    // .andExpect(status().isOk())
    // .andExpect(easyJson().isEqualTo(THIRD_PROJECT_NO_DESCRIPTION_STRING))
    // .andExpect(easyJson().node("description").isEqualTo(""));
    // }
    //
    // @Test
    // @WithMockUser
    // void shouldGetProject() throws Exception {
    // // given
    // thereIsProject(FIRST_PROJECT_CODE, FIRST_PROJECT);
    //
    // // when
    // val request = get("/projects/{projectCode}", FIRST_PROJECT_CODE);
    //
    // // then
    // mockMvc.perform(request)
    // .andExpect(status().isOk())
    // .andExpect(easyJson().isEqualTo(FIRST_PROJECT_STRING))
    // .andExpect(json().node("code").isEqualTo(FIRST_PROJECT_CODE));
    // }
    //
    // @Test
    // @WithMockUser
    // void shouldGetProjects() throws Exception {
    // // given
    // thereIsProject(FIRST_PROJECT_CODE, FIRST_PROJECT);
    //
    // // and
    // thereIsProject(SECOND_PROJECT_CODE, SECOND_PROJECT);
    //
    // // when
    // val request = get("/projects");
    //
    // // then
    // mockMvc.perform(request)
    // .andExpect(status().isOk())
    // .andExpect(easyJson().isEqualTo(array(FIRST_PROJECT,
    // SECOND_PROJECT).toString()));
    // }
    //
    // @Test
    // @WithMockUser
    // void shouldUpdateProject() throws Exception {
    // // given
    // thereIsProject(FIRST_PROJECT_CODE, FIRST_PROJECT);
    //
    // // when
    // val request = put("/projects/{projectCode}", FIRST_PROJECT_CODE)
    // .content(SECOND_PROJECT_STRING);
    //
    // // then
    // mockMvc.perform(request)
    // .andExpect(status().isOk())
    // .andExpect(easyJson().isEqualTo(SECOND_PROJECT_STRING))
    // .andExpect(json().node("code").isEqualTo(FIRST_PROJECT_CODE));
    // }
}
