package pl.edu.pw.elka.paprykaisalami.geeruh.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

@JsonInclude(Include.NON_NULL)
@Jacksonized
@Builder
@Value
public class Error {

    @NotNull String code;

    String message;

    String userMessage;

    String path;

    static class ErrorBuilder {

        ErrorBuilder code(Object code) {
            this.code = code.toString();
            return this;
        }
    }
}
