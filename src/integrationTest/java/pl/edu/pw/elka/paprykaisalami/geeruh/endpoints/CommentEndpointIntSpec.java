package pl.edu.pw.elka.paprykaisalami.geeruh.endpoints;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.RequestBuilder;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseIntSpec;
import pl.edu.pw.elka.paprykaisalami.geeruh.comments.adapters.api.CommentResponse;
import pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api.IssueResponse;

import java.util.stream.Stream;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.CommentDataset.FIRST_COMMENT;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.CommentDataset.SECOND_COMMENT;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.CommentDataset.SECOND_COMMENT_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueDataset.FIRST_ISSUE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueDataset.SECOND_ISSUE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.array;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.easyJson;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.CommentDataset.FIRST_COMMENT_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.FIRST_USER_LOGIN;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.FIRST_USER;

public class CommentEndpointIntSpec extends BaseIntSpec {

    @ParameterizedTest
    @MethodSource(value = "securedEndpoints")
    void shouldNotAuthorize(RequestBuilder request) throws Exception {
        // expect
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    private static Stream<Arguments> securedEndpoints() {
        return Stream.of(
                arguments(get("/comments")),
                arguments(get("/comments/asd123")),
                arguments(put("/comments/asd123")),
                arguments(delete("/comments/asd123")),
                arguments(post("/comments")));
    }

    @Test
    @WithMockUser(username = FIRST_USER_LOGIN)
    void shouldCreateComment() throws Exception {
        // given
        var issue = thereIsIssue(FIRST_ISSUE);
        var issueId = issue.issueId();

        // and
        var user = thereIsUser(FIRST_USER);
        var userId = user.userId().toString();

        // when
        val request = post("/comments")
                .param("issueId", issueId)
                .content(FIRST_COMMENT_STRING);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_COMMENT_STRING))
                .andExpect(json().node("creatorUserId").isEqualTo(userId))
                .andExpect(json().node("issueId").isEqualTo(issueId))
                .andExpect(json().node("createdAt").isNotNull())
                .andExpect(json().node("modifiedAt").isNull());
    }

    @Test
    @WithMockUser(username = FIRST_USER_LOGIN)
    void shouldGetComment() throws Exception {
        // given
        var comment = thereIsComment(FIRST_COMMENT, FIRST_ISSUE, FIRST_USER);

        // when
        val request = get("/comments/{id}", comment.commentId());

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_COMMENT_STRING))
                .andExpect(json().node("creatorUserId").isEqualTo(comment.creatorUserId().toString()))
                .andExpect(json().node("issueId").isEqualTo(comment.issueId()))
                .andExpect(json().node("createdAt").isNotNull())
                .andExpect(json().node("modifiedAt").isNull());
    }

    @Test
    @WithMockUser(username = FIRST_USER_LOGIN)
    void shouldGetComments() throws Exception {
        // given
        thereIsComment(FIRST_COMMENT, FIRST_ISSUE, FIRST_USER);

        // and
        thereIsComment(SECOND_COMMENT, SECOND_ISSUE, null);

        // when
        val request = get("/comments");

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(array(FIRST_COMMENT, SECOND_COMMENT).toString()));
    }

    @Test
    @WithMockUser(username = FIRST_USER_LOGIN)
    void shouldUpdateComment() throws Exception {
        // given
        var comment = thereIsComment(FIRST_COMMENT, FIRST_ISSUE, FIRST_USER);
        var commentId = comment.commentId().toString();

        // when
        val request = put("/comments/{commentId}", commentId)
                .content(SECOND_COMMENT_STRING);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(SECOND_COMMENT_STRING))
                .andExpect(json().node("creatorUserId").isEqualTo(comment.creatorUserId().toString()))
                .andExpect(json().node("issueId").isEqualTo(comment.issueId()))
                .andExpect(json().node("createdAt").isNotNull())
                .andExpect(json().node("modifiedAt").isNotNull());
    }

    @Test
    @WithMockUser(username = FIRST_USER_LOGIN)
    void shouldDeleteComment() throws Exception {
        // given
        var comment = thereIsComment(FIRST_COMMENT, FIRST_ISSUE, FIRST_USER);
        var commentId = comment.commentId().toString();

        // when
        val request = delete("/comments/{commentId}", commentId);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        // and when
        val getRequest = get("/comments/{commentId}", commentId);

        // then
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    private CommentResponse thereIsComment(Object commentBody, Object issueBody, Object userBody) throws Exception {
        var issue = thereIsIssue(issueBody);
        if (userBody != null) {
            thereIsUser(userBody);
        }

        val request = post("/comments")
                .param("issueId", issue.issueId())
                .content(commentBody.toString());

        val reader = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        return mapContent(reader, CommentResponse.class);
    }
}
