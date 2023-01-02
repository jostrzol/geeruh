package pl.edu.pw.elka.paprykaisalami.geeruh.comments.domain.models;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record CommentId(@NotNull UUID value) {
}
