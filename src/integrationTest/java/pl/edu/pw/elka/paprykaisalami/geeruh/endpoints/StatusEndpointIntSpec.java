package pl.edu.pw.elka.paprykaisalami.geeruh.endpoints;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.array;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.JsonUtils.easyJson;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.SECOND_STATUS_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusDataset.FIRST_STATUS;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusDataset.FIRST_STATUS_STRING;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusDataset.SECOND_STATUS;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusDataset.SECOND_STATUS_STRING;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;

import lombok.val;
import pl.edu.pw.elka.paprykaisalami.geeruh.BaseIntSpec;

public class StatusEndpointIntSpec extends BaseIntSpec {

    @ParameterizedTest
    @MethodSource(value = "securedEndpoints")
    void shouldNotAuthorize(RequestBuilder request) throws Exception {
        // expect
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    private static Stream<Arguments> securedEndpoints() {
        return Stream.of(
                arguments(get("/statuses")),
                arguments(get("/statuses/OPN")),
                arguments(put("/statuses/OPN")),
                arguments(post("/statuses"))
        );
    }

    @Test
    @WithMockUser
    void shouldCreateStatus() throws Exception {
        // given
        val request = post("/statuses/{statusCode}", FIRST_STATUS_CODE)
                .content(FIRST_STATUS_STRING);

        // expect
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_STATUS_STRING))
                .andExpect(json().node("code").isEqualTo(FIRST_STATUS_CODE));
    }

    @Test
    @WithMockUser
    void shouldGetStatus() throws Exception {
        // given
        thereIsStatus(FIRST_STATUS_CODE, FIRST_STATUS);

        // when
        val request = get("/statuses/{statusCode}", FIRST_STATUS_CODE);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(FIRST_STATUS_STRING))
                .andExpect(json().node("code").isEqualTo(FIRST_STATUS_CODE));
    }

    @Test
    @WithMockUser
    void shouldGetStatuses() throws Exception {
        // given
        thereIsStatus(FIRST_STATUS_CODE, FIRST_STATUS);

        // and
        thereIsStatus(SECOND_STATUS_CODE, SECOND_STATUS);

        // when
        val request = get("/statuses");

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(array(FIRST_STATUS, SECOND_STATUS).toString()));
    }

    @Test
    @WithMockUser
    void shouldUpdateStatus() throws Exception {
        // given
        thereIsStatus(FIRST_STATUS_CODE, FIRST_STATUS);

        // when
        val request = put("/statuses/{statusCode}", FIRST_STATUS_CODE)
                .content(SECOND_STATUS_STRING);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(easyJson().isEqualTo(SECOND_STATUS_STRING))
                .andExpect(json().node("code").isEqualTo(FIRST_STATUS_CODE));
    }
}
