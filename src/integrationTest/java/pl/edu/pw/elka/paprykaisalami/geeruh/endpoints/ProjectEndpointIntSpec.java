package pl.edu.pw.elka.paprykaisalami.geeruh.endpoints;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseIntSpec;

import java.util.stream.Stream;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.CommentDataset.FIRST_COMMENT;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueDataset.FIRST_ISSUE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.array;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.easyJson;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.FIRST_PROJECT_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.SECOND_PROJECT_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.THIRD_PROJECT_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectDataset.FIRST_PROJECT;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectDataset.FIRST_PROJECT_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectDataset.SECOND_PROJECT;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectDataset.SECOND_PROJECT_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectDataset.THIRD_PROJECT_NO_DESCRIPTION_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.FIRST_USER_LOGIN;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.FIRST_USER;

public class ProjectEndpointIntSpec extends BaseIntSpec {

    @ParameterizedTest
    @MethodSource(value = "securedEndpoints")
    void shouldNotAuthorize(RequestBuilder request) throws Exception {
        // expect
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    private static Stream<Arguments> securedEndpoints() {
        return Stream.of(
                arguments(get("/projects")),
                arguments(get("/projects/TST")),
                arguments(put("/projects/TST")),
                arguments(post("/projects")));
    }

    @Test
    @WithMockUser
    void shouldCreateProject() throws Exception {
        // given
        val request = post("/projects/{projectCode}", FIRST_PROJECT_CODE)
                .content(FIRST_PROJECT_STRING);

        // expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_PROJECT_STRING))
                .andExpect(json().node("code").isEqualTo(FIRST_PROJECT_CODE));
    }

    @Test
    @WithMockUser
    void shouldCreateProject_whenNoDescriptionProvided() throws Exception {
        // given
        val request = post("/projects/{projectCode}", THIRD_PROJECT_CODE)
                .content(THIRD_PROJECT_NO_DESCRIPTION_STRING);

        // expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(THIRD_PROJECT_NO_DESCRIPTION_STRING))
                .andExpect(easyJson().node("description").isEqualTo(""));
    }

    @Test
    @WithMockUser
    void shouldGetProject() throws Exception {
        // given
        thereIsProject(FIRST_PROJECT_CODE, FIRST_PROJECT);

        // when
        val request = get("/projects/{projectCode}", FIRST_PROJECT_CODE);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_PROJECT_STRING))
                .andExpect(json().node("code").isEqualTo(FIRST_PROJECT_CODE));
    }

    @Test
    @WithMockUser
    void shouldGetProjects() throws Exception {
        // given
        thereIsProject(FIRST_PROJECT_CODE, FIRST_PROJECT);

        // and
        thereIsProject(SECOND_PROJECT_CODE, SECOND_PROJECT);

        // when
        val request = get("/projects");

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(array(FIRST_PROJECT, SECOND_PROJECT).toString()));
    }

    @Test
    @WithMockUser
    void shouldUpdateProject() throws Exception {
        // given
        thereIsProject(FIRST_PROJECT_CODE, FIRST_PROJECT);

        // when
        val request = put("/projects/{projectCode}", FIRST_PROJECT_CODE)
                .content(SECOND_PROJECT_STRING);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(SECOND_PROJECT_STRING))
                .andExpect(json().node("code").isEqualTo(FIRST_PROJECT_CODE));
    }


    @Test
    @WithMockUser(username = FIRST_USER_LOGIN)
    void shouldDeleteProject() throws Exception {
        // given
        thereIsComment(FIRST_COMMENT, FIRST_ISSUE, FIRST_USER);

        // when
        val request = delete("/projects/{projectCode}", FIRST_PROJECT_CODE)
                .content(SECOND_PROJECT_STRING);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk());

        // and when
        val getRequest = get("/projects/{projectCode}", FIRST_PROJECT_CODE)
                .content(SECOND_PROJECT_STRING);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound());
    }
}
