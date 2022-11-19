package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class IssueId {

    @NonNull
    UUID value;
}
