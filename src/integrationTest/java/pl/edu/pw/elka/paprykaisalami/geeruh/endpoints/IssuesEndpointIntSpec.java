package pl.edu.pw.elka.paprykaisalami.geeruh.endpoints;

import lombok.val;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseIntSpec;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api.IssueResponse;
import pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset;

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
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.FIRST_USER;

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
                                   .matches(matchesPattern(FIRST_PROJECT_CODE + "-[0-9]+")))
                .andExpect(json().node("assigneeUserId").isNull());
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
                .andExpect(json().node("issueId").isEqualTo(issueId))
                .andExpect(json().node("assigneeUserId").isNull());
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
    void shouldAssignUser() throws Exception {
        // given
        val issue = thereIsIssue(FIRST_ISSUE);
        val issueId = issue.issueId().toString();

        // and
        val user = thereIsUser(FIRST_USER);
        val userId = user.userId().toString();

        // when
        val request = put("/issues/{id}/assignee", issueId)
                .content(assignUserRequest(userId));

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_ISSUE))
                .andExpect(json().node("issueId").isEqualTo(issueId))
                .andExpect(json().node("assigneeUserId").isEqualTo(userId));
    }

    @Test
    @WithMockUser
    void shouldRelateIssue() throws Exception {
        // given
        val issue = thereIsIssue(FIRST_ISSUE);
        val issueId = issue.issueId().toString();

        // and
        val secondIssue = thereIsIssue(SECOND_ISSUE);
        val secondIssueId = secondIssue.issueId().toString();

        // when
        val request = post("/issues/{id}/related-to/{id2}", issueId, secondIssueId);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_ISSUE))
                .andExpect(json().node("issueId").isEqualTo(issueId))
                .andExpect(json().node("relatedIssues").matches(Matchers.contains(secondIssueId)));
    }

    @Test
    @WithMockUser
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void shouldUnRelateIssue() throws Exception {
        // given
        val issue =  thereIsIssueRelated(FIRST_ISSUE, SECOND_ISSUE);
        val issueId = issue.issueId().toString();
        val secondIssueId = issue.relatedIssues().stream().findFirst().get();

        // when
        val request = delete("/issues/{id}/related-to/{id2}", issueId, secondIssueId);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_ISSUE))
                .andExpect(json().node("issueId").isEqualTo(issueId))
                .andExpect(json().node("relatedIssues").matches(Matchers.empty()));
    }

    @Test
    @WithMockUser
    void shouldUnassignUser() throws Exception {
        // given
        val issue = thereIsIssueAssigned(FIRST_ISSUE, FIRST_USER);
        val issueId = issue.issueId().toString();

        // when
        val request = put("/issues/{id}/assignee", issue.issueId())
                .content(assignUserRequest(null));

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_ISSUE))
                .andExpect(json().node("issueId").isEqualTo(issueId))
                .andExpect(json().node("assigneeUserId").isNull());
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

    private IssueResponse thereIsIssueAssigned(Object issueBody, Object assigneeBody) throws Exception {
        val issue = thereIsIssue(issueBody);
        val assignee = thereIsUser(assigneeBody);

        val request = put("/issues/{id}/assignee", issue.issueId())
                .content(assignUserRequest(assignee.userId()));

        val reader = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        return mapContent(reader, IssueResponse.class);
    }

    private IssueResponse thereIsIssueRelated(Object issueBody, Object relatedIssueBody) throws Exception {
        val issue = thereIsIssue(issueBody);
        val relatedIssue = thereIsIssue(relatedIssueBody);

        val request = post("/issues/{id}/related-to/{id2}", issue.issueId(), relatedIssue.issueId());

        val reader = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        return mapContent(reader, IssueResponse.class);
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

    private String assignUserRequest(@Nullable Object userId) {
        return new JSONObject()
                .put("assigneeUserId", userId == null ? null : userId.toString())
                .toString();
    }
}
