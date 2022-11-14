package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Description {

    @NonNull
    String value;
}
