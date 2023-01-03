package pl.edu.pw.elka.paprykaisalami.geeruh.comments.adapters.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
class CommentRequest {

    @NotBlank
    String content;
}
