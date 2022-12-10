package pl.edu.pw.elka.paprykaisalami.geeruh.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;

@JsonInclude(Include.NON_NULL)
@Builder
public record ApiError(
        @NonNull String code,
        String message,
        String userMessage,
        String path,
        @Singular("withContext") Map<String, Object> context
) {

    public static ApiError internalServerError() {
        return ApiError.builder()
                .code(ErrorCodes.INTERNAL_ERROR)
                .message("Server internal error")
                .build();
    }

    public static class ApiErrorBuilder {

        public ApiErrorBuilder code(Object code) {
            this.code = code.toString();
            return this;
        }
    }
}
