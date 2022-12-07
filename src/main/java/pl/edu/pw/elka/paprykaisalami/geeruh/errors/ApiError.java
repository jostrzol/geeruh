package pl.edu.pw.elka.paprykaisalami.geeruh.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@JsonInclude(Include.NON_NULL)
@Jacksonized
@Builder
@Value
public class ApiError {

    @NotNull String code;

    String message;

    String userMessage;

    String path;

    @Singular("withContext")
    Map<String, Object> context;

    public static class ApiErrorBuilder {

        public ApiErrorBuilder code(Object code) {
            this.code = code.toString();
            return this;
        }
    }
}
