package pl.edu.pw.elka.paprykaisalami.geeruh.endpoints;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;

import lombok.val;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseIntSpec;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.array;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.easyJson;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.omit;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.SECOND_USER_FIRST_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.SECOND_USER_SURNAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.FIRST_USER;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.FIRST_USER_LOGIN_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.FIRST_USER_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserDataset.SECOND_USER;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.UserAttributeDataset.FIRST_USER_FIRST_NAME;

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
                .andExpect(easyJson().isEqualTo(omit(FIRST_USER, "password").toString()))
                .andExpect(json().node("userId").isPresent());
    }

    @Test
    void shouldNotLoginWithNoUser() throws Exception {
        // given
        var request = post("/login")
                .content(FIRST_USER_LOGIN_STRING);

        // expect
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldLoginWithCreatedUser() throws Exception {
        // given
        thereIsUser(FIRST_USER);

        // when
        var request = post("/login")
                .content(FIRST_USER_LOGIN_STRING);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetUser() throws Exception {
        // given
        var user = thereIsUser(FIRST_USER);

        // when
        var request = get("/users/{userId}", user.userId());

        // expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(omit(FIRST_USER, "password").toString()))
                .andExpect(json().node("userId").isEqualTo(user.userId().toString()));
    }

    @Test
    @WithMockUser
    void shouldUpdateUser() throws Exception {
        // given
        var user = thereIsUser(FIRST_USER);

        // when
        var request = put("/users/{userId}", user.userId())
                .content(new JSONObject()
                        .put("firstName", SECOND_USER_FIRST_NAME)
                        .put("secondName", (Object) null)
                        .put("surname", SECOND_USER_SURNAME)
                        .toString()
                );

        // expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(omit(FIRST_USER, "password", "firstName", "secondName", "surname").toString()))
                .andExpect(json().node("firstName").isEqualTo(SECOND_USER_FIRST_NAME))
                .andExpect(json().node("secondName").isNull())
                .andExpect(json().node("surname").isEqualTo(SECOND_USER_SURNAME))
                .andExpect(json().node("userId").isEqualTo(user.userId().toString()));
    }

    @Test
    @WithMockUser
    void shouldGetUsers() throws Exception {
        // given
        var user1 = thereIsUser(FIRST_USER);
        var user2 = thereIsUser(SECOND_USER);

        // when
        var request = get("/users");

        // expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(
                                array(
                                        omit(FIRST_USER, "password").put("userId", user1.userId()),
                                        omit(SECOND_USER, "password").put("userId", user2.userId())
                                ).toString()
                        )
                );
    }
}
