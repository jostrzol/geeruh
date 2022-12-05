package pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models;


import lombok.Value;
import org.springframework.lang.NonNull;

@Value(staticConstructor = "of")
public class ProjectCode {

    @NonNull
    String value;
}
