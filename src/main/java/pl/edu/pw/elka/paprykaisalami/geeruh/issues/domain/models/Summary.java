package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Summary {

    @NonNull
    String value;
}
