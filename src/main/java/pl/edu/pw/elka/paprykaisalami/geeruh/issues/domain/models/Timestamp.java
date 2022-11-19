package pl.edu.pw.elka.paprykaisalami.geeruh.issues.domain.models;

import lombok.NonNull;
import lombok.Value;

import java.util.Date;

@Value(staticConstructor = "of")
public class Timestamp {
    @NonNull
    Date value;
}
