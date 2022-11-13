package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class IssueId {

    UUID value;
}
