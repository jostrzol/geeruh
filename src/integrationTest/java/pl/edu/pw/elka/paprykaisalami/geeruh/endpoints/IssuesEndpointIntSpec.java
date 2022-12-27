package pl.edu.pw.elka.paprykaisalami.geeruh.endpoints;

import lombok.val;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseIntSpec;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api.IssueResponse;

import java.util.stream.Stream;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueDataset.FIRST_ISSUE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueDataset.FIRST_ISSUE_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueDataset.SECOND_ISSUE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueDataset.SECOND_ISSUE_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueDataset.THIRD_ISSUE_NO_DESCRIPTION_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.easyJson;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.array;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.DATE_REGEX;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.jsonUnitRegex;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.FIRST_PROJECT_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectDataset.FIRST_PROJECT;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusDataset.FIRST_STATUS;

public class IssuesEndpointIntSpec extends BaseIntSpec {

    @ParameterizedTest
    @MethodSource(value = "securedEndpoints")
    void shouldNotAuthorize(RequestBuilder request) throws Exception {
        // expect
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    private static Stream<Arguments> securedEndpoints() {
        return Stream.of(
                arguments(get("/issues")),
                arguments(get("/issues/1")),
                arguments(put("/issues/1")),
                arguments(post("/issues"))
        );
    }

    @Test
    @WithMockUser
    void shouldCreateIssue() throws Exception {
        // given
        thereIsProject(FIRST_PROJECT_CODE, FIRST_PROJECT);
        thereIsStatus(FIRST_STATUS_CODE, FIRST_STATUS);

        // when
        val request = post("/issues")
                .param("projectCode", FIRST_PROJECT_CODE)
                .param("statusCode", FIRST_STATUS_CODE)
                .content(FIRST_ISSUE_STRING);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_ISSUE_STRING))
                .andExpect(json().node("issueId")
                                   .matches(matchesPattern(FIRST_PROJECT_CODE + "-[0-9]+")));
    }

    @Test
    @WithMockUser
    void shouldCreateIssue_whenNoDescriptionProvided() throws Exception {
        // given
        thereIsProject(FIRST_PROJECT_CODE, FIRST_PROJECT);
        thereIsStatus(FIRST_STATUS_CODE, FIRST_STATUS);

        // when
        val request = post("/issues")
                .param("projectCode", FIRST_PROJECT_CODE)
                .param("statusCode", FIRST_STATUS_CODE)
                .content(THIRD_ISSUE_NO_DESCRIPTION_STRING);


        // expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(THIRD_ISSUE_NO_DESCRIPTION_STRING))
                .andExpect(easyJson().node("description").isEqualTo(""));
    }

    @Test
    @WithMockUser
    void shouldGetIssue() throws Exception {
        // given
        val issue = thereIsIssue(FIRST_ISSUE);
        val issueId = issue.issueId().toString();

        // when
        val request = get("/issues/{id}", issueId);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_ISSUE_STRING))
                .andExpect(json().node("issueId").isEqualTo(issueId));
    }

    @Test
    @WithMockUser
    void shouldGetIssues() throws Exception {
        // given
        thereIsIssue(FIRST_ISSUE);

        // and
        thereIsIssue(SECOND_ISSUE);

        // when
        val request = get("/issues");

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(array(FIRST_ISSUE, SECOND_ISSUE).toString()));
    }

    @Test
    @WithMockUser
    void shouldUpdateIssue() throws Exception {
        // given
        val issue = thereIsIssue(FIRST_ISSUE);
        val issueId = issue.issueId().toString();

        // when
        val request = put("/issues/{id}", issueId)
                .content(SECOND_ISSUE_STRING);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(SECOND_ISSUE_STRING))
                .andExpect(json().node("issueId").isEqualTo(issueId));
    }

    @Test
    @WithMockUser
    void shouldReportHistory() throws Exception {
        // given
        val issue = thereIsIssue(FIRST_ISSUE);
        val issueId = issue.issueId().toString();

        // and
        thereIsIssueUpdate(SECOND_ISSUE, issueId);

        // when
        val request = get("/issues/{id}/history", issueId);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(array(
                        new JSONObject()
                                .put("historicIssue", FIRST_ISSUE)
                                .put("type", "INSERT")
                                .put("timestamp", jsonUnitRegex(DATE_REGEX)),
                        new JSONObject()
                                .put("historicIssue", SECOND_ISSUE)
                                .put("type", "UPDATE")
                                .put("timestamp", jsonUnitRegex(DATE_REGEX))
                )));
    }

    private IssueResponse thereIsIssue(Object body) throws Exception {
        thereIsProject(FIRST_PROJECT_CODE, FIRST_PROJECT);
        thereIsStatus(FIRST_STATUS_CODE, FIRST_STATUS);


        val request = post("/issues")
                .param("projectCode", FIRST_PROJECT_CODE)
                .param("statusCode", FIRST_STATUS_CODE)
                .content(body.toString());

        val reader = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        return mapContent(reader, IssueResponse.class);
    }

    private void thereIsIssueUpdate(Object body, Object issueId) throws Exception {
        val request = put("/issues/{issueId}", issueId.toString())
                .content(body.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }
}
