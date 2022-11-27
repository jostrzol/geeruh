package pl.edu.pw.elka.paprykaisalami.geeruh;

import lombok.val;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TrivialSpec {

    @Test
    void shouldDoSomething() {
        // given
        val a = 1;
        val b = 2;

        // when
        val result = a + b;

        // then
        assertThat(result).isEqualTo(3);
    }
}